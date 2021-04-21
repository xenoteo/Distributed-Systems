package lab.task2;

public class Request implements Command {
    final String text;

    public Request(String text) {
        this.text = text;
    }
}
