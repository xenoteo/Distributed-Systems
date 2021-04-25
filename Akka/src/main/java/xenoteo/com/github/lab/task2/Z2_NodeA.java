package xenoteo.com.github.lab.task2;

import akka.actor.typed.*;
import akka.actor.typed.javadsl.Behaviors;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import java.io.File;


public class Z2_NodeA {

    public static Behavior<Void> create() {
        return Behaviors.setup(
                context -> {

                    context.spawn(ActorUpperCase.create(), "upper1");
                    context.spawn(ActorUpperCase.create(), "upper2");

                    return Behaviors.receive(Void.class)
                            .onSignal(Terminated.class, sig -> Behaviors.stopped())
                            .build();
                });
    }

    public static void main(String[] args) throws InterruptedException {
        File configFile = new File("src/main/nodeA.conf");
        Config config = ConfigFactory.parseFile(configFile);
        System.out.println("Node A: config: " + config);

        ActorSystem.create(Z2_NodeA.create(), "ClusterSystem", config);
    }
}
