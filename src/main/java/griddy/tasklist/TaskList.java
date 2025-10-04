package griddy.tasklist;

import griddy.task.Task;
import java.util.ArrayList;

public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList() {
        this.tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> tasks) {
        this.tasks = tasks;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public void deleteTask(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= tasks.size()) {
            throw new IndexOutOfBoundsException("Task number out of range");
        }
        tasks.remove(index);
    }

    public Task getTask(int index) throws IndexOutOfBoundsException {
        if (index < 0 || index >= tasks.size()) {
            throw new IndexOutOfBoundsException("Task number out of range");
        }
        return tasks.get(index);
    }

    public void markTask(int index) throws IndexOutOfBoundsException {
        Task task = getTask(index);
        task.markAsDone();
    }

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

    public boolean isEmpty() {
        return tasks.isEmpty();
    }

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
