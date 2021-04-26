package xenoteo.com.github.homework;

import akka.actor.typed.ActorSystem;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;

import java.util.Map;

/**
 * The wrapper class for a monitoring station actor.
 */
public class Station {

    /**
     * The system of the dispatcher actor.
     */
    private final ActorSystem<Dispatcher.DispatcherActor.IQuery> dispatcherSystem;

    /**
     * The system of the monitoring station actor.
     */
    private final ActorSystem<StationActor.IResponse> stationSystem;

    /**
     * The number of queries performed by the station so far.
     */
    private int queriesNumber;

    public Station(String stationName, Dispatcher dispatcher) {
        this.dispatcherSystem = dispatcher.getDispatcherSystem();
        this.stationSystem = ActorSystem.create(StationActor.create(stationName), stationName);
        this.queriesNumber = 0;
    }

    /**
     * Performs the query.
     *
     * @param firstSatelliteId  the ID of the first satellite if the range
     * @param range  the number of satellites to query
     * @param timeout  the timeout
     */
    public void query(int firstSatelliteId, int range, int timeout){
        queriesNumber++;
        int queryId = queriesNumber;

        dispatcherSystem.tell(new Dispatcher.DispatcherActor.Query(
                queryId, firstSatelliteId, range, timeout, stationSystem, System.currentTimeMillis()));

    }

    /**
     * Performs the error query.
     *
     * @param satelliteId  the ID of the satellite to query the number of errors
     */
    public void queryError(int satelliteId){
        dispatcherSystem.tell(new Dispatcher.DispatcherActor.ErrorQuery(satelliteId, stationSystem));
    }



    /**
     * The monitoring station actor.
     */
    public static final class StationActor extends AbstractBehavior<StationActor.IResponse> {
        /**
         * The station name.
         */
        private final String stationName;

        public StationActor(ActorContext<IResponse> context, String stationName) {
            super(context);
            this.stationName = stationName;
        }

        /**
         * Creates the station actor.
         *
         * @param stationName  the station name
         * @return the station actor
         */
        public static Behavior<IResponse> create(String stationName) {
            return Behaviors.setup(context -> new StationActor(context, stationName));
        }

        @Override
        public Receive<IResponse> createReceive() {
            return newReceiveBuilder()
                    .onMessage(Response.class, this::onResponse)
                    .onMessage(ErrorResponse.class, this::onErrorResponse)
                    .build();
        }

        /**
         * Reacts to the received response, that is prints all the received information.
         *
         * @param response  the response
         * @return the station actor
         */
        private Behavior<IResponse> onResponse(Response response) {
            long responseTime = System.currentTimeMillis() - response.startTime;
            System.out.printf("\nStation %s received response to the query %s\n", stationName, response.query);
            System.out.printf("The response time is %d ms\n", responseTime);
            System.out.printf("%.2f%% of satellites responded in the required timeout\n",
                    response.notOverdueResponsePercent * 100);
            System.out.printf("There are %d errors:\n", response.errorSatelliteMap.size());
            response.errorSatelliteMap.forEach((key, value) -> System.out.printf("%d - %s\n", key, value));
            return this;
        }

        /**
         * Reacts to the received error response, that is prints the number of satellite error if it is greater than 0.
         *
         * @param response  the error response
         * @return the station actor
         */
        private Behavior<IResponse> onErrorResponse(ErrorResponse response){
            if (response.satelliteErrors > 0){
                System.out.printf("\nStation %s received response to the error query\n", stationName);
                System.out.printf("Satellite %d: %d errors total\n", response.satelliteId, response.satelliteErrors);
            }
            return this;
        }



        /**
         * The interface for responses to a monitoring station query.
         */
        public interface IResponse {}



        /**
         * The class representing a response to a monitoring station query.
         */
        public static final class Response implements IResponse {

            /**
             * The query ID.
             */
            private final int queryId;

            /**
             * The map from satellite ID to its error status; the map only for those satellites that returned errors.
             */
            private final Map<Integer, SatelliteAPI.Status> errorSatelliteMap;

            /**
             * The percent of satellites that returned information in the required time.
             */
            private final double notOverdueResponsePercent;

            /**
             * The time when the query was created.
             */
            private final long startTime;

            /**
             * The copy of the query.
             */
            private final Dispatcher.DispatcherActor.Query query;

            public Response(int queryId, Map<Integer, SatelliteAPI.Status> errorSatelliteMap,
                            double notOverdueResponsePercent, long startTime, Dispatcher.DispatcherActor.Query query) {
                this.queryId = queryId;
                this.errorSatelliteMap = errorSatelliteMap;
                this.notOverdueResponsePercent = notOverdueResponsePercent;
                this.startTime = startTime;
                this.query = query;
            }
        }



        /**
         * The class representing an error response to a monitoring station error query.
         */
        public static final class ErrorResponse implements IResponse {

            /**
             * The satellite ID.
             */
            private final int satelliteId;

            /**
             * The satellite number of errors.
             */
            private final int satelliteErrors;

            public ErrorResponse(int satelliteId, int satelliteErrors) {
                this.satelliteId = satelliteId;
                this.satelliteErrors = satelliteErrors;
            }
        }
    }
}
