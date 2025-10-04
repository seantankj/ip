package griddy.ui;

import griddy.task.Task;
import java.util.ArrayList;

public class Ui {
    private String line = "_____________________________________________________" + System.lineSeparator();

    public void printWelcome() {
        System.out.println(line + "Hello! I'm" + System.lineSeparator() +
                "   ___     _    _    _      _         _" + System.lineSeparator() +
                "  / __|_ _(_)__| |__| |_  _| |__  ___| |_" + System.lineSeparator() +
                " | (_ | '_| / _` / _` | || | '_ \\/ _ \\  _|" + System.lineSeparator() +
                "  \\___|_| |_\\__,_\\__,_\\\\_, |_.__/\\___/\\__|" + System.lineSeparator() +
                "                       |__/" + System.lineSeparator() +
                "What can I do for you?" + System.lineSeparator() + line);
    }

    public void printBye() {
        System.out.println(line + "Bye. Come back soon!" + System.lineSeparator() + line);
    }

    public void printTaskList(ArrayList<Task> listItems) {
        int listNumber = 0;
        System.out.print(line);
        for (Task listItem : listItems) {
            listNumber++;
            System.out.print(listNumber + "." + listItem + System.lineSeparator());
        }
        System.out.print(line);
    }

    public void printTaskAdded(Task task, int totalTasks) {
        System.out.println(line + "Got it. I've added this task:" + System.lineSeparator() +
                task + System.lineSeparator() + "Now you have " + totalTasks +
                " task(s) in the list." + System.lineSeparator() + line);
    }

    public void printTaskDeleted(Task task, int remainingTasks) {
        System.out.println(line + "I've deleted this task:" + System.lineSeparator() +
                task + System.lineSeparator() + "Now you have " + remainingTasks +
                " task(s) in the list." + System.lineSeparator() + line);
    }

    public void printTaskMarked(Task task, boolean isDone) {
        String status = isDone ? "done" : "not done";
        System.out.println(line + "I've marked this task as " + status + ":" +
                System.lineSeparator() + task + System.lineSeparator() + line);
    }

    public void printError(String message) {
        System.out.println(message);
    }

    public void printLoadedTasks(int count) {
        if (count > 0) {
            System.out.println("Loaded " + count + " task(s) from saved file.");
        } else {
            System.out.println("No previous save file found. Starting fresh.");
        }
    }
}
