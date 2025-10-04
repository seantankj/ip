package griddy.task;

/**
 * Represents a task with only a task description.
 */
public class ToDo extends Task{

    public ToDo(String description) {
        super(description);
    }

    /**
     * Overrides toString for ToDo class, to include [T][].
     *
     * @return custom String for ToDos.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
