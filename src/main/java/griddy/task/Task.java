package griddy.task;

/**
 * Abstract class that contains todo, deadline and event.
 */
public abstract class Task {

    protected String description;
    protected boolean isDone;

    public Task (String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    /**
     * Marks a task as done.
     */
    public void markAsDone() {
        isDone = true;
    }

    /**
     * Marks a task as undone.
     */
    public void markAsUndone() {
        isDone = false;
    }

    @Override
    public String toString() {
        return "[" + getStatusIcon() + "] " + description;
    }
}
