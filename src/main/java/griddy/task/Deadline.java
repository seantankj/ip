package griddy.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a task with a task description and a due date.
 */
public class Deadline extends Task {
    protected LocalDateTime by;
    protected String originalByString;
    protected boolean isValidDate;

    public Deadline(String description, String by) {
        super(description);
        this.originalByString = by;
        this.by = parseDateTime(by);
    }

    private LocalDateTime parseDateTime(String dateTimeStr) {
        DateTimeFormatter[] formatters = {
                DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"),
                DateTimeFormatter.ofPattern("d/M/yyyy HHmm"),
                DateTimeFormatter.ofPattern("yyyy-MM-dd"),
                DateTimeFormatter.ofPattern("d/M/yyyy")
        };

        for (DateTimeFormatter formatter : formatters) {
            try {
                if (dateTimeStr.contains(" ")) {
                    this.isValidDate = true;
                    return LocalDateTime.parse(dateTimeStr, formatter);
                } else {
                    LocalDate date = LocalDate.parse(dateTimeStr, formatter);
                    this.isValidDate = true;
                    return date.atStartOfDay();
                }
            } catch (DateTimeParseException e) {
                // Try next formatter
            }
        }

        this.isValidDate = false;
        return LocalDateTime.now();
    }

    /**
     * Formats the "/by" section of the command to "get by:" in the list
     *
     * @return formatted String.
     */
    public String getByFormatted() {
        if (!isValidDate) {
            return originalByString;
        }

        DateTimeFormatter displayFormatter = DateTimeFormatter.ofPattern("MMM dd yyyy");
        if (by.getHour() == 0 && by.getMinute() == 0) {
            return by.format(displayFormatter);
        } else {
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma");
            return by.format(timeFormatter);
        }
    }

    /**
     * Overrides toString for Deadline class, to include [D][] and (by: ).
     *
     * @return custom String for Deadlines.
     */
    @Override
    public String toString() {
        return "[D][" + getStatusIcon() + "] " + description + " (by: " + getByFormatted() + ")";
    }

}
