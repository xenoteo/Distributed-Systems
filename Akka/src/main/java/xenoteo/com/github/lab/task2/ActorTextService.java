package lab.task2;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import akka.actor.typed.receptionist.Receptionist;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ActorTextService extends AbstractBehavior<Command>  {
    private ActorRef<Receptionist.Listing> listingResponseAdapter;
    private List<ActorRef<String>> workers = new LinkedList<>();

    public ActorTextService(ActorContext<Command> context) {
        super(context);
        this.listingResponseAdapter = context.messageAdapter(Receptionist.Listing.class, ListingResponse::new);
        context
                .getSystem()
                .receptionist()
                .tell(
                        Receptionist.subscribe(ActorUpperCase.upperCaseServiceKey, listingResponseAdapter)
                );
    }

    public static Behavior<Command> create() {
        return Behaviors.setup(ActorTextService::new);
    }

    // --- define message handlers
    @Override
    public Receive<Command> createReceive() {

        System.out.println("creating receive for text service");

        return newReceiveBuilder()
                .onMessage(Request.class, this::onRequest)
                .onMessage(ListingResponse.class, response -> onListingResponse(response.listing))
                .build();
    }

    private Behavior<Command> onRequest(Request msg) {
        System.out.println("request: " + msg.text);
        for (ActorRef<String> worker : workers) {
            System.out.println("sending to worker: " + worker);
            worker.tell(msg.text);
        }
        return this;
    }

    private Behavior<Command> onListingResponse(Receptionist.Listing msg){
        this.workers = new ArrayList<>(msg.getAllServiceInstances(ActorUpperCase.upperCaseServiceKey));
        return this;
    }

}
