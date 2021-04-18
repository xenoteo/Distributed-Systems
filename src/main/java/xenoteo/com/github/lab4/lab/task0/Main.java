package xenoteo.com.github.lab4.lab.task0;

import akka.actor.typed.ActorSystem;

public class Main {
    public static void main(String[] args) {
        final ActorSystem<String> system =
                ActorSystem.create(HelloActor.create(), "helloActor");
        system.tell("hello world");
    }
}
