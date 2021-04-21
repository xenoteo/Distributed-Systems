package lab.task2;

import akka.actor.typed.*;
import akka.actor.typed.javadsl.Behaviors;

public class Z2_Main {

    public static Behavior<Void> create() {
        return Behaviors.setup(
                context -> {
                    // create text service
                    ActorRef<Command> actorTextService = context.spawn(ActorTextService.create(), "textService");

                    // create workers (they will register by themselves)
                    context.spawn(ActorUpperCase.create(), "upper1");
                    context.spawn(ActorUpperCase.create(), "upper2");

                    // send text
                    Thread.sleep(2000);
                    actorTextService.tell(new Request("hello"));

                    return Behaviors.receive(Void.class)
                            .onSignal(Terminated.class, sig -> Behaviors.stopped())
                            .build();
                });
    }

    public static void main(String[] args) {
        ActorSystem.create(Z2_Main.create(), "z2main");
    }
}
