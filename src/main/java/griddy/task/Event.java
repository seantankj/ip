package griddy.task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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
        this.from = parseDateTime(from, true);  // true for "from"
        this.to = parseDateTime(to, false);     // false for "to"
    }

    private LocalDateTime parseDateTime(String dateTimeStr, boolean isFrom) {
        // Try different date/time formats
        DateTimeFormatter[] formatters = {
                DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"),     // 2019-10-15 1800
                DateTimeFormatter.ofPattern("d/M/yyyy HHmm"),       // 2/12/2019 1800
                DateTimeFormatter.ofPattern("yyyy-MM-dd"),          // 2019-10-15 (date only)
                DateTimeFormatter.ofPattern("d/M/yyyy"),            // 2/12/2019 (date only)
                DateTimeFormatter.ofPattern("ha"),                  // 2pm, 6pm (time only)
                DateTimeFormatter.ofPattern("HHmm")                 // 1400, 1800 (time only)
        };

        for (DateTimeFormatter formatter : formatters) {
            try {
                if (dateTimeStr.contains(" ")) {
                    // Has date and time component
                    if (isFrom) this.isFromValidDate = true;
                    else this.isToValidDate = true;
                    return LocalDateTime.parse(dateTimeStr, formatter);
                } else if (dateTimeStr.contains("pm") || dateTimeStr.contains("am")) {
                    // Time only with am/pm - use today's date
                    LocalDate today = LocalDate.now();
                    if (isFrom) this.isFromValidDate = true;
                    else this.isToValidDate = true;
                    return LocalDateTime.of(today, LocalDateTime.parse("1/1/2000 " + dateTimeStr,
                            DateTimeFormatter.ofPattern("d/M/yyyy ha")).toLocalTime());
                } else if (dateTimeStr.matches("\\d{4}")) {
                    // 4-digit time format (e.g., 1800)
                    LocalDate today = LocalDate.now();
                    if (isFrom) this.isFromValidDate = true;
                    else this.isToValidDate = true;
                    return LocalDateTime.of(today, LocalDateTime.parse("1/1/2000 " + dateTimeStr,
                            DateTimeFormatter.ofPattern("d/M/yyyy HHmm")).toLocalTime());
                } else {
                    // Try as date only
                    LocalDate date = LocalDate.parse(dateTimeStr, formatter);
                    if (isFrom) this.isFromValidDate = true;
                    else this.isToValidDate = true;
                    return date.atStartOfDay();
                }
            } catch (DateTimeParseException e) {
                // Try next formatter
            }
        }

        // If all parsing fails, it's not a valid date - store as string
        if (isFrom) this.isFromValidDate = false;
        else this.isToValidDate = false;
        return LocalDateTime.now(); // Placeholder, won't be used
    }

    public String getFromFormatted() {
        if (!isFromValidDate) {
            return originalFromString;
        }
        return formatDateTime(from);
    }

    public String getToFormatted() {
        if (!isToValidDate) {
            return originalToString;
        }
        return formatDateTime(to);
    }

    private String formatDateTime(LocalDateTime dateTime) {
        if (dateTime.getHour() == 0 && dateTime.getMinute() == 0) {
            // Date only
            return dateTime.format(DateTimeFormatter.ofPattern("MMM dd yyyy"));
        } else {
            // Date and time
            return dateTime.format(DateTimeFormatter.ofPattern("MMM dd yyyy, h:mma"));
        }
    }

    public String getFromForSaving() {
        if (!isFromValidDate) {
            return originalFromString;
        }
        return formatForSaving(from);
    }

    public String getToForSaving() {
        if (!isToValidDate) {
            return originalToString;
        }
        return formatForSaving(to);
    }

    private String formatForSaving(LocalDateTime dateTime) {
        if (dateTime.getHour() == 0 && dateTime.getMinute() == 0) {
            return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } else {
            return dateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm"));
        }
    }

    @Override
    public String toString() {
        return "[E][" + getStatusIcon() + "] " + description + " (from: " + getFromFormatted() +
                " to: " + getToFormatted() + ")";
    }
}
