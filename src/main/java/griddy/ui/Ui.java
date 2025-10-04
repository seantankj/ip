package griddy.ui;

import griddy.task.Task;
import java.util.ArrayList;

/**
 * Has all the functions needed for printing UI elements.
 */
public class Ui {
    private String line = "_____________________________________________________" + System.lineSeparator();

    /**
     * Prints the welcome ASCII art and message when starting the bot.
     */
    public void printWelcome() {
        System.out.println(line + "Hello! I'm" + System.lineSeparator() +
                "   ___     _    _    _      _         _" + System.lineSeparator() +
                "  / __|_ _(_)__| |__| |_  _| |__  ___| |_" + System.lineSeparator() +
                " | (_ | '_| / _` / _` | || | '_ \\/ _ \\  _|" + System.lineSeparator() +
                "  \\___|_| |_\\__,_\\__,_\\\\_, |_.__/\\___/\\__|" + System.lineSeparator() +
                "                       |__/" + System.lineSeparator() +
                "What can I do for you?" + System.lineSeparator() + line);
    }

    /**
     * Prints the goodbye message when stopping the bot.
     */
    public void printBye() {
        System.out.println(line + "Bye. Come back soon!" + System.lineSeparator() + line);
    }

    /**
     * Prints out the task list.
     *
     * @param listItems ArrayList of all stored tasks.
     */
    public void printTaskList(ArrayList<Task> listItems) {
        int listNumber = 0;
        System.out.print(line);
        for (Task listItem : listItems) {
            listNumber++;
            System.out.print(listNumber + "." + listItem + System.lineSeparator());
        }
        System.out.print(line);
    }

    /**
     * Prints out the task description and total number of tasks.
     *
     * @param task Task description.
     * @param totalTasks Total number of tasks.
     */
    public void printTaskAdded(Task task, int totalTasks) {
        System.out.println(line + "Got it. I've added this task:" + System.lineSeparator() +
                task + System.lineSeparator() + "Now you have " + totalTasks +
                " task(s) in the list." + System.lineSeparator() + line);
    }

    /**
     * Prints out the task description and remaining number of tasks.
     *
     * @param task Task description.
     * @param remainingTasks Remaining number of tasks.
     */
    public void printTaskDeleted(Task task, int remainingTasks) {
        System.out.println(line + "I've deleted this task:" + System.lineSeparator() +
                task + System.lineSeparator() + "Now you have " + remainingTasks +
                " task(s) in the list." + System.lineSeparator() + line);
    }

    /**
     * Prints out the task description and marked status.
     *
     * @param task Task description.
     * @param isDone If the task is marked as done or not.
     */
    public void printTaskMarked(Task task, boolean isDone) {
        String status = isDone ? "done" : "not done";
        System.out.println(line + "I've marked this task as " + status + ":" +
                System.lineSeparator() + task + System.lineSeparator() + line);
    }

    public void printError(String message) {
        System.out.println(message);
    }

    /**
     * Prints out the number of tasks loaded from the save file on boot.
     *
     * @param count Number of loaded tasks.
     */
    public void printLoadedTasks(int count) {
        if (count > 0) {
            System.out.println("Loaded " + count + " task(s) from saved file.");
        } else {
            System.out.println("No previous save file found. Starting fresh.");
        }
    }

    /**
     * Prints out the search results in a list.
     *
     * @param matchingTasks List of matching tasks.
     * @param keyword Keyword used to search tasks.
     */
    public void printSearchResults(ArrayList<Task> matchingTasks, String keyword) {
        System.out.print(line);
        if (matchingTasks.isEmpty()) {
            System.out.println("No matching tasks found for keyword: " + keyword);
        } else {
            System.out.println("Here are the matching tasks in your list:");
            int displayIndex = 1;
            for (Task task : matchingTasks) {
                System.out.println(displayIndex + "." + task);
                displayIndex++;
            }
        }
        System.out.print(line);
    }
}
