package xenoteo.com.github.homework;

import akka.actor.typed.ActorSystem;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * The wrapper class for the dispatcher actor.
 */
public class Dispatcher {

    /**
     * The system of the dispatcher actor.
     */
    private final ActorSystem<DispatcherActor.IQuery> dispatcherSystem;

    public Dispatcher() {
        this.dispatcherSystem = ActorSystem.create(DispatcherActor.create(), "dispatcher");
    }

    /**
     * Gets the dispatcher system.
     *
     * @return the dispatcher system
     */
    public ActorSystem<DispatcherActor.IQuery> getDispatcherSystem() {
        return dispatcherSystem;
    }



    /**
     * The dispatcher actor.
     */
    public static final class DispatcherActor extends AbstractBehavior<DispatcherActor.IQuery> {

        public DispatcherActor(ActorContext<IQuery> context) {
            super(context);
        }

        /**
         * Creates the dispatcher actor.
         *
         * @return the dispatcher actor
         */
        public static Behavior<IQuery> create() {
            return Behaviors.setup(DispatcherActor::new);
        }

        @Override
        public Receive<IQuery> createReceive() {
            return newReceiveBuilder()
                    .onMessage(Query.class, this::onQuery)
                    .onMessage(ErrorQuery.class, this::onErrorQuery)
                    .build();
        }

        /**
         * Responds to the received query,
         * that is creates the map of the satellite errors and counts the percent of not overdue satellite responses.
         *
         * @param query  the query
         * @return the dispatcher actor
         */
        private Behavior<IQuery> onQuery(Query query) {
            Map<Integer, SatelliteAPI.Status> errorSatelliteMap = new HashMap<>();
            List<SatelliteRunnable> satelliteRunnables = new LinkedList<>();
            for (int id = query.firstSatelliteId; id < query.firstSatelliteId + query.range; id++){
                satelliteRunnables.add(new SatelliteRunnable(id, errorSatelliteMap, query.timeout));
            }

            // executing all the satellite requests in concurrently
            ExecutorService executor = Executors.newCachedThreadPool();
            CompletableFuture<?>[] futures = satelliteRunnables.stream()
                    .map(task -> CompletableFuture.runAsync(task, executor))
                    .toArray(CompletableFuture[]::new);
            CompletableFuture.allOf(futures).join();
            executor.shutdown();

            int notOverdueCount = (int) satelliteRunnables.stream().filter(satellite -> !satellite.isOverdue).count();
            double notOverdueResponsePercent = 1.0 * notOverdueCount / query.range;

            query.stationSystem.tell(new Station.StationActor.Response(
                    query.queryId, errorSatelliteMap, notOverdueResponsePercent, query.startTime, query));
            return this;
        }

        /**
         * Responds to the received error query,
         * that is gets the number of the required satellite errors.
         *
         * @param query  the error query
         * @return the dispatcher actor
         */
        private Behavior<IQuery> onErrorQuery(ErrorQuery query){
            int errors = DBConnector.getErrors(query.satelliteId);
            query.stationSystem.tell(new Station.StationActor.ErrorResponse(query.satelliteId, errors));
            return this;
        }



        /**
         * The interface for queries.
         */
        public interface IQuery {}



        /**
         * The class representing a query sent by a monitoring station to the dispatcher.
         */
        public static final class Query implements IQuery {

            /**
             * The query ID.
             */
            private final int queryId;

            /**
             * The ID of the first satellite of the range chosen to monitoring.
             */
            private final int firstSatelliteId;

            /**
             * The number of satellites to be monitored.
             */
            private final int range;

            /**
             * The max time of waiting for the data from the single satellite.
             */
            private final int timeout;

            /**
             * The station system to send response to.
             */
            private final ActorSystem<Station.StationActor.IResponse> stationSystem;

            /**
             * The time when the query was created.
             */
            private final long startTime;

            public Query(int queryId, int firstSatelliteId, int range, int timeout, ActorSystem<Station.StationActor.IResponse> system, long startTime) {
                this.queryId = queryId;
                this.firstSatelliteId = firstSatelliteId;
                this.range = range;
                this.timeout = timeout;
                this.stationSystem = system;
                this.startTime = startTime;
            }

            @Override
            public String toString() {
                return String.format("(%d, %d, %d, %d)", queryId, firstSatelliteId, range, timeout);
            }
        }



        /**
         * The class representing an error query sent by a monitoring station to the dispatcher.
         */
        public static final class ErrorQuery implements IQuery{

            /**
             * The satellite ID.
             */
            private final int satelliteId;

            /**
             * The station system to send response to.
             */
            private final ActorSystem<Station.StationActor.IResponse> stationSystem;

            public ErrorQuery(int satelliteId, ActorSystem<Station.StationActor.IResponse> stationSystem) {
                this.satelliteId = satelliteId;
                this.stationSystem = stationSystem;
            }
        }
    }


    /**
     * The runnable responsible for making a single satellite request to Satellite API.
     */
    private static class SatelliteRunnable implements Runnable {

        /**
         * The satellite ID.
         */
        private final int satelliteId;

        /**
         * The error satellite map to update in case of an error.
         */
        private final Map<Integer, SatelliteAPI.Status> errorSatelliteMap;

        /**
         * The allowed timeout.
         */
        private final int timeout;

        /**
         * Whether the request is overdue.
         */
        private boolean isOverdue;

        public SatelliteRunnable(int satelliteId, Map<Integer, SatelliteAPI.Status> errorSatelliteMap, int timeout) {
            this.satelliteId = satelliteId;
            this.errorSatelliteMap = errorSatelliteMap;
            this.timeout = timeout;
            this.isOverdue = false;
        }

        @Override
        public void run() {
            long startTime = System.currentTimeMillis();
            SatelliteAPI.Status status = SatelliteAPI.getStatus(satelliteId);
            long endTime = System.currentTimeMillis();

            if ((endTime - startTime) <= timeout){
                if (status != SatelliteAPI.Status.OK){
                    errorSatelliteMap.put(satelliteId, status);
                    DBConnector.incrementErrors(satelliteId);
                }
            }
            else {
                isOverdue = true;
            }
        }
    }

}
