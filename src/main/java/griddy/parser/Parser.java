package griddy.parser;

import griddy.task.Task;
import griddy.task.ToDo;
import griddy.task.Deadline;
import griddy.task.Event;
import griddy.ui.GriddyException;

public class Parser {

    public static Command parseCommand(String inputLine) throws GriddyException {
        String trimmedInput = inputLine.trim();

        if (trimmedInput.startsWith("mark")) {
            return new Command(CommandType.MARK, trimmedInput);
        } else if (trimmedInput.startsWith("unmark")) {
            return new Command(CommandType.UNMARK, trimmedInput);
        } else if (trimmedInput.startsWith("todo")) {
            return new Command(CommandType.TODO, trimmedInput);
        } else if (trimmedInput.startsWith("deadline")) {
            return new Command(CommandType.DEADLINE, trimmedInput);
        } else if (trimmedInput.startsWith("event")) {
            return new Command(CommandType.EVENT, trimmedInput);
        } else if (trimmedInput.startsWith("delete")) {
            return new Command(CommandType.DELETE, trimmedInput);
        } else if (trimmedInput.startsWith("find ")) {
                return new Command(CommandType.FIND, trimmedInput);
        } else if (trimmedInput.equals("list")) {
            return new Command(CommandType.LIST, trimmedInput);
        } else if (trimmedInput.equals("bye")) {
            return new Command(CommandType.BYE, trimmedInput);
        } else {
            throw new GriddyException(GriddyException.wrongKeyword);
        }
    }

    public static Task createTodoTask(String inputLine) throws GriddyException {
        String removeCommand = inputLine.substring(4).trim();
        if (removeCommand.isEmpty()) {
            throw new GriddyException(GriddyException.emptyTodo);
        }
        return new ToDo(removeCommand);
    }

    public static Task createDeadlineTask(String inputLine) throws GriddyException {
        String removeCommand = inputLine.substring(8).trim();
        if (removeCommand.isEmpty()) {
            throw new GriddyException(GriddyException.emptyDeadline);
        } else if (!inputLine.contains("/by")) {
            throw new GriddyException(GriddyException.syntaxDeadline);
        }

        String[] firstSplit = removeCommand.split(" /by ");
        if (firstSplit.length != 2) {
            throw new GriddyException(GriddyException.syntaxDeadline);
        }

        String description = firstSplit[0].trim();
        String by = firstSplit[1].trim();
        return new Deadline(description, by);
    }

    public static Task createEventTask(String inputLine) throws GriddyException {
        String removeCommand = inputLine.substring(5).trim();

        if (removeCommand.isEmpty()) {
            throw new GriddyException(GriddyException.emptyEvent);
        } else if (!inputLine.contains("/from") || !inputLine.contains("/to")) {
            throw new GriddyException(GriddyException.syntaxEvent);
        }

        String[] firstSplit = removeCommand.split(" /from ");
        if (firstSplit.length != 2) {
            throw new GriddyException(GriddyException.syntaxEvent);
        }

        String[] secondSplit = firstSplit[1].split(" /to ");
        if (secondSplit.length != 2) {
            throw new GriddyException(GriddyException.syntaxEvent);
        }

        String description = firstSplit[0].trim();
        String from = secondSplit[0].trim();
        String to = secondSplit[1].trim();
        return new Event(description, from, to);
    }

    public static String parseSearchKeyword(String inputLine) throws GriddyException {
        if (inputLine.length() <= 5) { // "find " is 5 characters
            throw new GriddyException("Search keyword cannot be empty");
        }
        return inputLine.substring(5).trim(); // Remove "find " and get keyword
    }

    public static int parseTaskNumber(String inputLine, int commandLength) throws GriddyException {
        String numberStr = inputLine.substring(commandLength).trim();
        if (numberStr.isEmpty()) {
            throw new GriddyException("Task number cannot be empty");
        }

        try {
            return Integer.parseInt(numberStr);
        } catch (NumberFormatException e) {
            throw new GriddyException("Invalid task number format");
        }
    }

    public enum CommandType {
        MARK, UNMARK, TODO, DEADLINE, EVENT, DELETE, FIND, LIST, BYE
    }

    public static class Command {
        private CommandType type;
        private String fullCommand;

        public Command(CommandType type, String fullCommand) {
            this.type = type;
            this.fullCommand = fullCommand;
        }

        public CommandType getType() {
            return type;
        }

        public String getFullCommand() {
            return fullCommand;
        }
    }
}
