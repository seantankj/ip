import java.util.ArrayList;
import java.util.Scanner;

public class Griddybot {
    public static void main(String[] args) {

        String line = "_________________________________" + System.lineSeparator();

        printWelcome(line);

        boolean isExit = false;
        ArrayList<Task> listItems = new ArrayList<>();
        int listNumber = 0;

        while (!isExit) {
            String inputLine;
            Scanner input = new Scanner(System.in);
            inputLine = input.nextLine();

            if (inputLine.startsWith("mark")) {
                mark(inputLine, listItems, line);
            } else if (inputLine.startsWith("unmark")) {
                unmark(inputLine, listItems, line);
            } else if (inputLine.startsWith("todo")) {
                createTodo(inputLine, listItems, line);
            } else if (inputLine.startsWith("deadline")) {
                createDeadline(inputLine, listItems, line);
            } else if  (inputLine.startsWith("event")) {
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
                    System.out.println(line + "Bye. Come back soon!" + System.lineSeparator() + line);
                    isExit = true;
                    break;
                default:
                    Task t = new Task(inputLine);
                    listItems.add(t);
                    System.out.println(line + "added: " + t.description + System.lineSeparator() + line);
                }
            }
        }
    }

    private static void createEvent(String inputLine, ArrayList<Task> listItems, String line) {
        String description = inputLine.substring(6, inputLine.indexOf("/from"));
        String from =  inputLine.substring(inputLine.indexOf("/from") + 6,  inputLine.indexOf("/to"));
        String to =  inputLine.substring(inputLine.indexOf("/to") + 4);
        Event e = new Event(description, from, to);
        listItems.add(e);

        System.out.println(line + "Got it. I've added this task:" + System.lineSeparator() + e + System.lineSeparator() + "Now you have " + listItems.size() + " task(s) in the list." + System.lineSeparator() + line);
    }

    private static void createDeadline(String inputLine, ArrayList<Task> listItems, String line) {
        String description = inputLine.substring(9, inputLine.indexOf("/by"));
        String by =  inputLine.substring(inputLine.indexOf("/by") + 4);
        Deadline d = new Deadline(description, by);
        listItems.add(d);

        System.out.println(line + "Got it. I've added this task:" + System.lineSeparator() + d + System.lineSeparator() + "Now you have " + listItems.size() + " task(s) in the list." + System.lineSeparator() + line);
    }

    private static void createTodo(String inputLine, ArrayList<Task> listItems, String line) {
        ToDo td = new ToDo(inputLine.substring(5));
        listItems.add(td);
        System.out.println(line + "Got it. I've added this task:" + System.lineSeparator() + td + System.lineSeparator() + "Now you have " + listItems.size() + " task(s) in the list." + System.lineSeparator() + line);
    }

    private static void unmark(String inputLine, ArrayList<Task> listItems, String line) {
        int taskNumber = Integer.parseInt(inputLine.substring(7));
        if (taskNumber > listItems.size()) {
            System.out.println(line + "That task does not exist. Try again." + System.lineSeparator() + line);
        } else {
            listItems.get(taskNumber - 1).markAsUndone();
            System.out.println(line + "I've marked this task as not done:" + System.lineSeparator() + listItems.get(taskNumber - 1) + System.lineSeparator() + line);
        }
    }

    private static void mark(String inputLine, ArrayList<Task> listItems, String line) {
        int taskNumber = Integer.parseInt(inputLine.substring(5));
        if (taskNumber > listItems.size()) {
            System.out.println(line + "That task does not exist. Try again." + System.lineSeparator() + line);
        } else {
            listItems.get(taskNumber - 1).markAsDone();
            System.out.println(line + "I've marked this task as done:" + System.lineSeparator() + listItems.get(taskNumber - 1) + System.lineSeparator() + line);
        }
    }

    private static void printWelcome(String line){
        System.out.println(line + "Hello! I'm Griddybot." + System.lineSeparator() + "What can I do for you?" + System.lineSeparator() + line);
    }

}
