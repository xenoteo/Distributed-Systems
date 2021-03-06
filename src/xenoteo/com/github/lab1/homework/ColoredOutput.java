package xenoteo.com.github.lab1.homework;

/**
 * THe class responsible for colored outputs.
 */
public class ColoredOutput {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";

    /**
     * Prints the string and resets the color.
     *
     * @param string  the string
     */
    private static void printlnAndReset(String string){
        System.out.println(string);
        System.out.print(ANSI_RESET);
    }

    /**
     * Prints the string in blue color ending with a new line.
     *
     * @param string  the string
     */
    public static void printlnBlue(String string){
        System.out.print(ANSI_BLUE);
        printlnAndReset(string);
    }

    /**
     * Prints the string in green color ending with a new line.
     *
     * @param string  the string
     */
    public static void printlnGreen(String string){
        System.out.print(ANSI_GREEN);
        printlnAndReset(string);
    }

    /**
     * Prints the string in purple color ending with a new line.
     *
     * @param string  the string
     */
    public static void printlnPurple(String string){
        System.out.print(ANSI_PURPLE);
        printlnAndReset(string);
    }

    /**
     * Prints the string in red color ending with a new line.
     *
     * @param string  the string
     */
    public static void printlnRed(String string){
        System.out.print(ANSI_RED);
        printlnAndReset(string);
    }
}
