package xenoteo.com.github.lab1.homework;

public class ColoredOutput {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    private static void printlnAndReset(String string){
        System.out.println(string);
        System.out.print(ANSI_RESET);
    }

    private static void printAndReset(String string){
        System.out.print(string);
        System.out.print(ANSI_RESET);
    }

    public static void printlnBlue(String string){
        System.out.print(ANSI_BLUE);
        printlnAndReset(string);
    }

    public static void printBlue(String string){
        System.out.print(ANSI_BLUE);
        printAndReset(string);
    }

    public static void printlnGreen(String string){
        System.out.print(ANSI_GREEN);
        printlnAndReset(string);
    }

    public static void printlnPurple(String string){
        System.out.print(ANSI_PURPLE);
        printlnAndReset(string);
    }
}
