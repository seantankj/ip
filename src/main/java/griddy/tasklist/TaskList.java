package griddy.tasklist;

import griddy.task.Task;
import java.util.ArrayList;
/**
 * Represents a list of tasks to be stored.
 */
public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Adds a task of corresponding type to the list.
     *
     * @param task A task of certain type.
     */
    public void addTask(Task task) {
        tasks.add(task);
    }

    /**
     * Deletes a task based on input number.
     *
     * @param index The index number of the task to be deleted.
     * @throws IndexOutOfBoundsException If index number is greater than list length.
     */
    public void deleteTask(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= tasks.size()) {
            throw new IndexOutOfBoundsException("Task number out of range");
        }
        tasks.remove(index);
    }

    /**
     * Returns a task based on input number.
     *
     * @param index The index number of the task to be found.
     * @return Task description.
     * @throws IndexOutOfBoundsException If index number is greater than list length.
     */
    public Task getTask(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= tasks.size()) {
            throw new IndexOutOfBoundsException("Task number out of range");
        }
        return tasks.get(index);
    }

    /**
     * Marks a task based on input number.
     *
     * @param index The index number of the task to be marked as done.
     * @throws IndexOutOfBoundsException If index number is greater than list length.
     */
    public void markTask(int index) throws IndexOutOfBoundsException {
        Task task = getTask(index);
        task.markAsDone();
    }

    /**
     * Unmarks a task based on input number.
     *
     * @param index The index number of the task to be unmarked as done.
     * @throws IndexOutOfBoundsException If index number is greater than list length.
     */
    public void unmarkTask(int index) throws IndexOutOfBoundsException {
        Task task = getTask(index);
        task.markAsUndone();
    }

    public ArrayList<Task> getAllTasks() {
        return new ArrayList<>(tasks);
    }

    public int size() {
        return tasks.size();
    }

    /**
     * Finds a task based on keyword.
     *
     * @param keyword The keyword to search for matching tasks.
     * @return ArrayList of matching tasks.
     */
    public ArrayList<Task> findTasks(String keyword) {
        ArrayList<Task> matchingTasks = new ArrayList<>();
        String lowerKeyword = keyword.toLowerCase();

        for (Task task : tasks) {
            String taskString = task.toString().toLowerCase();

            if (taskString.contains(lowerKeyword)) {
                matchingTasks.add(task);
            }
        }
        return matchingTasks;
    }
}
