package griddy.ui;

import griddy.task.Deadline;
import griddy.task.Event;
import griddy.task.Task;
import griddy.task.ToDo;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.BufferedWriter;

public class Griddybot {
    public static void main(String[] args) throws GriddyException {

        String line = "_____________________________________________________" + System.lineSeparator();
        String file = "data/save.txt";

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

        try {
            writeToFile("E [" + e.getStatusIcon() + "]" + removeCommand + System.lineSeparator());
        } catch (IOException exception) {
            System.out.println("Something went wrong: " + exception.getMessage());
        }

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

        try {
            writeToFile("D [" + d.getStatusIcon() + "]" + removeCommand + System.lineSeparator());
        } catch (IOException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }

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

        try {
            writeToFile("T [" + td.getStatusIcon() + "]" + removeCommand + System.lineSeparator());
        } catch (IOException e) {
            System.out.println("Something went wrong: " + e.getMessage());
        }

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

    private static void writeToFile(String textToAdd) throws IOException {
        FileWriter fw = new FileWriter("data/save.txt", true);
        fw.write(textToAdd);
        fw.close();
    }

    private static void printFileContents(String filePath) throws FileNotFoundException {
        File f = new File(filePath);
        Scanner s = new Scanner(f);
        while (s.hasNext()) {
            System.out.println(s.nextLine());
        }
    }
    
}
