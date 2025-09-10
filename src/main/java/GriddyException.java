public class GriddyException extends Exception {

    static String line = "_____________________________________________________" + System.lineSeparator();

    public GriddyException(String message) {
        super(message);
    }

    public static final String wrongKeyword = line + "[ERROR] Command not recognised" + System.lineSeparator() + line;
    public static final String numberOutOfRange = line + "[ERROR] Number out of range, please check number"
            + System.lineSeparator() + line;
    public static final String emptyTodo = line + "[ERROR] todo cannot be empty" + System.lineSeparator() + line;
    public static final String emptyDeadline = line + "[ERROR] deadline cannot be empty" + System.lineSeparator() + line;
    public static final String emptyEvent = line + "[ERROR] event cannot be empty" + System.lineSeparator() + line;
}
