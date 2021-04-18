package xenoteo.com.github.lab4.lab.task1;

import akka.actor.typed.ActorRef;
import akka.actor.typed.Behavior;
import akka.actor.typed.SupervisorStrategy;
import akka.actor.typed.javadsl.AbstractBehavior;
import akka.actor.typed.javadsl.ActorContext;
import akka.actor.typed.javadsl.Behaviors;
import akka.actor.typed.javadsl.Receive;


public class MathActor extends AbstractBehavior<MathActor.MathCommand> {

    // --- define messages accepted by this actor
    public interface MathCommand {}

    public static final class MathCommandAdd implements MathCommand {
        public final int firstNumber;
        public final int secondNumber;

        public MathCommandAdd(int firstNumber, int secondNumber) {
            this.firstNumber = firstNumber;
            this.secondNumber = secondNumber;
        }
    }

    public static final class MathCommandMultiply implements MathCommand {
        public final int firstNumber;
        public final int secondNumber;
        public final ActorRef<MathCommand> replyTo;

        public MathCommandMultiply(int firstNumber, int secondNumber, ActorRef<MathCommand> replyTo) {
            this.firstNumber = firstNumber;
            this.secondNumber = secondNumber;
            this.replyTo = replyTo;
        }
    }

    // TODO: MathCommandDivide

    public static final class MathCommandResult implements MathCommand {
        public final int result;

        public MathCommandResult(int result) {
            this.result = result;
        }
    }

    // --- sub-actors, constructor and create
    private ActorRef<MathCommandMultiply> actorMultiply;
    // TODO: actorDivide

    public MathActor(ActorContext<MathCommand> context) {
        super(context);
        actorMultiply = getContext().spawn(MathActorMultiply.create(), "actorMultiply");
        // TODO: actorDivide
        // TODO: uncomment this to change supervisor strategy
//        actorMultiply = getContext().spawn(
//                Behaviors.supervise(MathActorMultiply.create())
//                        .onFailure(Exception.class, SupervisorStrategy.restart()), "actorMultiply");

    }

    public static Behavior<MathCommand> create() {
        return Behaviors.setup(MathActor::new);
    }

    // --- define message handlers
    @Override
    public Receive<MathCommand> createReceive() {
        return newReceiveBuilder()
                .onMessage(MathCommandAdd.class, this::onMathCommandAdd)
                .onMessage(MathCommandMultiply.class, this::onMathCommandMultiply)
                // TODO: handle MathCommandDivide
                .onMessage(MathCommandResult.class, this::onMathCommandResult)
                .build();
    }

    private Behavior<MathCommand> onMathCommandAdd(MathCommandAdd mathCommandAdd) {
        System.out.println("actorMath: received command: add");
        int result = mathCommandAdd.firstNumber + mathCommandAdd.secondNumber;
        System.out.println("actorMath: add result = " + result);
        return this;
    }

    private Behavior<MathCommand> onMathCommandMultiply(MathCommandMultiply mathCommandMultiply) {
        System.out.println("actorMath: received command: multiply");
        System.out.println("actorMath: sending to actorMultiply");
        actorMultiply.tell(new MathCommandMultiply(mathCommandMultiply.firstNumber, mathCommandMultiply.secondNumber, getContext().getSelf()));
        return this;
    }

    // TODO: handle MathCommandDivide

    private Behavior<MathCommand> onMathCommandResult(MathCommandResult mathCommandResult) {
        System.out.println("actorMath: received result: " + mathCommandResult.result);
        return this;
    }

}
