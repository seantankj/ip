package griddy.ui;

public class GriddyException extends Exception {

    static String line = "_____________________________________________________" + System.lineSeparator();

    public GriddyException(String message) {
        super(message);
    }

    public static final String wrongKeyword = line + "[ERROR] Command not recognised" + System.lineSeparator() + line;
    public static final String numberOutOfRange = line + "[ERROR] Number out of range, please check number"
            + System.lineSeparator() + line;
    public static final String unmarkNotNumber = line + "[ERROR] "
            + System.lineSeparator() + line;
    public static final String emptyMark = line + "[ERROR] mark cannot be empty" + System.lineSeparator() + line;
    public static final String syntaxMark = line + "[ERROR] mark syntax error, syntax should be:" + System.lineSeparator()
            + "mark {task number}" + System.lineSeparator() + line;
    public static final String emptyUnmark = line + "[ERROR] unmark cannot be empty" + System.lineSeparator() + line;
    public static final String syntaxUnmark = line + "[ERROR] unmark syntax error, syntax should be:" + System.lineSeparator()
            + "unmark {task number}" + System.lineSeparator() + line;
    public static final String emptyTodo = line + "[ERROR] todo cannot be empty" + System.lineSeparator() + line;
    public static final String emptyDeadline = line + "[ERROR] deadline cannot be empty" + System.lineSeparator() + line;
    public static final String syntaxDeadline = line + "[ERROR] deadline syntax error, syntax should be:" + System.lineSeparator()
            + "deadline {name} /by {due date}" + System.lineSeparator() + line;
    public static final String emptyEvent = line + "[ERROR] event cannot be empty" + System.lineSeparator() + line;
    public static final String syntaxEvent = line + "[ERROR] event syntax error, syntax should be:" + System.lineSeparator()
            + "event {name} /from {start} /to {end}" + System.lineSeparator() + line;
}
