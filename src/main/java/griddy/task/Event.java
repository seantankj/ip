package griddy.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a task with a task description, a start date and an end date.
 */
public class Event extends Task {
    protected LocalDateTime from;
    protected LocalDateTime to;
    protected String originalFromString;
    protected String originalToString;
    protected boolean isFromValidDate;
    protected boolean isToValidDate;

    public Event(String description, String from, String to) {
        super(description);
        this.originalFromString = from;
        this.originalToString = to;
        this.from = parseDateTime(from, true);
        this.to = parseDateTime(to, false);
    }

    private LocalDateTime parseDateTime(String dateTimeStr, boolean isFrom) {
        DateTimeFormatter[] formatters = {
                DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"),
                DateTimeFormatter.ofPattern("d/M/yyyy HHmm"),
                DateTimeFormatter.ofPattern("yyyy-MM-dd"),
                DateTimeFormatter.ofPattern("d/M/yyyy"),
                DateTimeFormatter.ofPattern("ha"),
                DateTimeFormatter.ofPattern("HHmm")
        };

        for (DateTimeFormatter formatter : formatters) {
            try {
                if (dateTimeStr.contains(" ")) {
                    if (isFrom) this.isFromValidDate = true;
                    else this.isToValidDate = true;
                    return LocalDateTime.parse(dateTimeStr, formatter);
                } else if (dateTimeStr.contains("pm") || dateTimeStr.contains("am")) {
                    LocalDate today = LocalDate.now();
                    if (isFrom) this.isFromValidDate = true;
                    else this.isToValidDate = true;
                    return LocalDateTime.of(today, LocalDateTime.parse("1/1/2000 " + dateTimeStr,
                            DateTimeFormatter.ofPattern("d/M/yyyy ha")).toLocalTime());
                } else if (dateTimeStr.matches("\\d{4}")) {
                    LocalDate today = LocalDate.now();
                    if (isFrom) this.isFromValidDate = true;
                    else this.isToValidDate = true;
                    return LocalDateTime.of(today, LocalDateTime.parse("1/1/2000 " + dateTimeStr,
                            DateTimeFormatter.ofPattern("d/M/yyyy HHmm")).toLocalTime());
                } else {
                    LocalDate date = LocalDate.parse(dateTimeStr, formatter);
                    if (isFrom) this.isFromValidDate = true;
                    else this.isToValidDate = true;
                    return date.atStartOfDay();
                }
            } catch (DateTimeParseException e) {
                // Try next formatter
            }
        }

        if (isFrom) this.isFromValidDate = false;
        else this.isToValidDate = false;
        return LocalDateTime.now();
    }

    /**
     * Formats the "/from" section of the command to "from:" in the list
     *
     * @return formatted String.
     */
    public String getFromFormatted() {
        if (!isFromValidDate) {
            return originalFromString;
        }
        return formatDateTime(from);
    }

    /**
     * Formats the "/to" section of the command to "to:" in the list
     *
     * @return formatted String.
     */
    public String getToFormatted() {
        if (!isToValidDate) {
            return originalToString;
        }
        return formatDateTime(to);
    }

    private String formatDateTime(LocalDateTime dateTime) {
        if (dateTime.getHour() == 0 && dateTime.getMinute() == 0) {
            return dateTime.format(DateTimeFormatter.ofPattern("MMM dd yyyy"));
        } else {
            return dateTime.format(DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma"));
        }
    }

    /**
     * Overrides toString for Event class, to include [E][], (from: ) and (to: ).
     *
     * @return custom String for Events.
     */
    @Override
    public String toString() {
        return "[E][" + getStatusIcon() + "] " + description + " (from: " + getFromFormatted() +
                " to: " + getToFormatted() + ")";
    }

}
