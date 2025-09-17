package griddy.ui;

import griddy.task.Deadline;
import griddy.task.Event;
import griddy.task.Task;
import griddy.task.ToDo;

import java.util.ArrayList;
import java.util.Scanner;

public class Griddybot {
    public static void main(String[] args) throws GriddyException {

        String line = "_____________________________________________________" + System.lineSeparator();

        printWelcome(line);

        boolean isExit = false;
        ArrayList<Task> listItems = new ArrayList<>();
        int listNumber = 0;
        Scanner input = new Scanner(System.in);

        while (!isExit) {
            String inputLine;
            inputLine = input.nextLine();

            try {
                if (inputLine.startsWith("mark")) {
                    mark(inputLine, listItems, line);
                } else if (inputLine.startsWith("unmark")) {
                    unmark(inputLine, listItems, line);
                } else if (inputLine.startsWith("todo")) {
                    createTodo(inputLine, listItems, line);
                } else if (inputLine.startsWith("deadline")) {
                    createDeadline(inputLine, listItems, line);
                } else if (inputLine.startsWith("event")) {
                    createEvent(inputLine, listItems, line);
                } else if (inputLine.startsWith("delete")) {
                    deleteTask(inputLine, listItems, line);
                } else {
                    switch (inputLine) {
                    case "list":
                        System.out.print(line);
                        for (Task listItem : listItems) {
                            listNumber++;
                            System.out.print(listNumber + "." + listItem + System.lineSeparator());
                        }
                        System.out.print(line);
                        break;
                    case "bye":
                        printBye(line);
                        isExit = true;
                        break;
                    default:
                        throw new GriddyException(GriddyException.wrongKeyword);
                    }
                }
            } catch (GriddyException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static void deleteTask(String inputLine, ArrayList<Task> listItems, String line) throws GriddyException {
        String removeCommand = inputLine.substring(7);
        if (removeCommand.isEmpty()) {
            throw new GriddyException(GriddyException.emptyDelete);
        }

        int taskNumber = 0;
        try {
            taskNumber = Integer.parseInt(removeCommand);
        } catch (NumberFormatException e) {
            throw new GriddyException(GriddyException.syntaxDelete);
        }

        if (taskNumber > listItems.size()) {
            throw new GriddyException(GriddyException.numberOutOfRange);
        }

        System.out.println(line + "I've deleted this task:" + System.lineSeparator() + listItems.get(taskNumber - 1) +
                System.lineSeparator() + "Now you have " + (listItems.size() - 1) + " task(s) in the list." + System.lineSeparator() + line);

        listItems.remove(taskNumber - 1);
    }

    private static void createEvent(String inputLine, ArrayList<Task> listItems, String line) throws GriddyException {
        String removeCommand = inputLine.substring(5);

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
        Event e = new Event(description, from, to);
        listItems.add(e);

        System.out.println(line + "Got it. I've added this task:" + System.lineSeparator() + e + System.lineSeparator()
                + "Now you have " + listItems.size() + " task(s) in the list." + System.lineSeparator() + line);
    }

    private static void createDeadline(String inputLine, ArrayList<Task> listItems, String line) throws GriddyException {
        String removeCommand = inputLine.substring(8);
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
        Deadline d = new Deadline(description, by);
        listItems.add(d);

        System.out.println(line + "Got it. I've added this task:" + System.lineSeparator() + d + System.lineSeparator()
                + "Now you have " + listItems.size() + " task(s) in the list." + System.lineSeparator() + line);
    }

    private static void createTodo(String inputLine, ArrayList<Task> listItems, String line) throws GriddyException {
        ToDo td = null;
        String removeCommand = inputLine.substring(4);
        if (removeCommand.isEmpty()) {
            throw new GriddyException(GriddyException.emptyTodo);
        }

        td = new ToDo(inputLine.substring(5));
        listItems.add(td);
        System.out.println(line + "Got it. I've added this task:" + System.lineSeparator() + td + System.lineSeparator()
                + "Now you have " + listItems.size() + " task(s) in the list." + System.lineSeparator() + line);
    }

    private static void unmark(String inputLine, ArrayList<Task> listItems, String line) throws GriddyException {
        String removeCommand = inputLine.substring(6);
        if (removeCommand.isEmpty()) {
            throw new GriddyException(GriddyException.emptyUnmark);
        }
        int taskNumber = 0;
        try {
            taskNumber = Integer.parseInt(inputLine.substring(7));
        } catch (NumberFormatException e) {
            throw new GriddyException(GriddyException.syntaxUnmark);
        }

        if (taskNumber > listItems.size()) {
            throw new GriddyException(GriddyException.numberOutOfRange);
        }

        listItems.get(taskNumber - 1).markAsUndone();
        System.out.println(line + "I've marked this task as not done:" + System.lineSeparator() + listItems.get(taskNumber - 1)
                + System.lineSeparator() + line);

    }

    private static void mark(String inputLine, ArrayList<Task> listItems, String line) throws GriddyException {
        String removeCommand = inputLine.substring(4);
        if (removeCommand.isEmpty()) {
            throw new GriddyException(GriddyException.emptyMark);
        }
        int taskNumber = 0;
        try {
            taskNumber = Integer.parseInt(inputLine.substring(5));
        } catch (NumberFormatException e) {
            throw new GriddyException(GriddyException.syntaxMark);
        }

        if (taskNumber > listItems.size()) {
            throw new GriddyException(GriddyException.numberOutOfRange);
        }

        listItems.get(taskNumber - 1).markAsDone();
        System.out.println(line + "I've marked this task as done:" + System.lineSeparator() + listItems.get(taskNumber - 1)
                + System.lineSeparator() + line);

    }

    private static void printWelcome(String line) {
        System.out.println(line + "Hello! I'm" + System.lineSeparator() +
                "   ___     _    _    _      _         _" + System.lineSeparator() +
                "  / __|_ _(_)__| |__| |_  _| |__  ___| |_" + System.lineSeparator() +
                " | (_ | '_| / _` / _` | || | '_ \\/ _ \\  _|" + System.lineSeparator() +
                "  \\___|_| |_\\__,_\\__,_|\\_, |_.__/\\___/\\__|" + System.lineSeparator() +
                "                       |__/" + System.lineSeparator() +
                "What can I do for you?" + System.lineSeparator() + line);
    }

    private static void printBye(String line) {
        System.out.println(line + "Bye. Come back soon!" + System.lineSeparator() + line);
    }

}
