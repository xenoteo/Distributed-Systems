package lab.task2;

import akka.actor.typed.Behavior;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;
import akka.actor.typed.receptionist.Receptionist;
import akka.actor.typed.receptionist.ServiceKey;

import java.util.Locale;

public class ActorUpperCase extends AbstractBehavior<String>{

    public static final ServiceKey<String> upperCaseServiceKey =
            ServiceKey.create(String.class, "upperCaseService");


    // --- constructor and create
    public ActorUpperCase(ActorContext<String> context) {
        super(context);
    }

    public static Behavior<String> create() {
        return Behaviors.setup(
                context -> {
                    context
                            .getSystem()
                            .receptionist()
                            .tell(Receptionist.register(upperCaseServiceKey, context.getSelf()));
                    System.out.println(context.getSelf().path() + " registered");
                    return new ActorUpperCase(context).createReceive();
                }
        );
    }

    // --- define message handlers
    @Override
    public Receive<String> createReceive() {
        return newReceiveBuilder()
                .onMessage(String.class, this::onMessage)
                .build();
    }

    private Behavior<String> onMessage(String msg) {
        System.out.println(msg.toUpperCase(Locale.ROOT));
        return this;
    }
}
