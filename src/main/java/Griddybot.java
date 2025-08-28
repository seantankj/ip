import java.util.ArrayList;
import java.util.Scanner;

public class Griddybot {
    public static void main(String[] args) {
        String line = "_________________________________" + System.lineSeparator();
        System.out.println(line + "Hello! I'm Griddybot." + System.lineSeparator() + "What can I do for you?" + System.lineSeparator() + line);

        boolean isExit = false;
        ArrayList<Task> listItems = new ArrayList<>();
        int counter = 0;

        while (!isExit) {
            String inputLine;
            Scanner input = new Scanner(System.in);
            inputLine = input.nextLine();


            if (inputLine.startsWith("mark")) {
                int taskNumber = Integer.parseInt(inputLine.substring(5));
                if (taskNumber > listItems.size()) {
                    System.out.println(line + "That task does not exist. Try again." + System.lineSeparator() + line);
                } else {
                    listItems.get(taskNumber - 1).markAsDone();
                    System.out.println(line + "I've marked this task as done:" + System.lineSeparator() + "[" + listItems.get(taskNumber - 1).getStatusIcon() + "] " + listItems.get(taskNumber - 1).description + System.lineSeparator() + line);
                }
            } else if (inputLine.startsWith("unmark")) {
                int taskNumber = Integer.parseInt(inputLine.substring(7));
                if (taskNumber > listItems.size()) {
                    System.out.println(line + "That task does not exist. Try again." + System.lineSeparator() + line);
                } else {
                    listItems.get(taskNumber - 1).markAsUndone();
                    System.out.println(line + "I've marked this task as not done:" + System.lineSeparator() + "[" + listItems.get(taskNumber - 1).getStatusIcon() + "] " + listItems.get(taskNumber - 1).description + System.lineSeparator() + line);
                }
            } else {
                switch (inputLine) {
                case "list":
                    System.out.print(line);
                    for (Task listItem : listItems) {
                        counter++;
                        System.out.print(counter + ".[" + listItem.getStatusIcon() + "] " + listItem.description + System.lineSeparator());
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
}
