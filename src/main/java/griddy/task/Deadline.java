package griddy.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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
        // Try different date/time formats
        DateTimeFormatter[] formatters = {
                DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"),     // 2019-10-15 1800
                DateTimeFormatter.ofPattern("d/M/yyyy HHmm"),       // 2/12/2019 1800
                DateTimeFormatter.ofPattern("yyyy-MM-dd"),          // 2019-10-15 (date only)
                DateTimeFormatter.ofPattern("d/M/yyyy")             // 2/12/2019 (date only)
        };

        for (DateTimeFormatter formatter : formatters) {
            try {
                if (dateTimeStr.contains(" ")) {
                    // Has time component
                    this.isValidDate = true;
                    return LocalDateTime.parse(dateTimeStr, formatter);
                } else {
                    // Date only, set time to start of day
                    LocalDate date = LocalDate.parse(dateTimeStr, formatter);
                    this.isValidDate = true;
                    return date.atStartOfDay();
                }
            } catch (DateTimeParseException e) {
                // Try next formatter
            }
        }

        // If all parsing fails, it's not a valid date - store as string
        this.isValidDate = false;
        return LocalDateTime.now(); // Placeholder, won't be used
    }

    public String getByFormatted() {
        if (!isValidDate) {
            // Return original string if not a valid date
            return originalByString;
        }

        // Format as "MMM dd yyyy" for display if it's a valid date
        DateTimeFormatter displayFormatter = DateTimeFormatter.ofPattern("MMM dd yyyy");
        if (by.getHour() == 0 && by.getMinute() == 0) {
            // Date only
            return by.format(displayFormatter);
        } else {
            // Date and time
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma");
            return by.format(timeFormatter);
        }
    }

    public String getByForSaving() {
        if (!isValidDate) {
            // Save original string if not a valid date
            return originalByString;
        }

        // Format for saving to file if it's a valid date
        if (by.getHour() == 0 && by.getMinute() == 0) {
            return by.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } else {
            return by.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
        }
    }

    @Override
    public String toString() {
        return "[D][" + getStatusIcon() + "] " + description + " (by: " + getByFormatted() + ")";
    }
}
