package griddy.storage;

import griddy.task.Task;
import griddy.task.ToDo;
import griddy.task.Deadline;
import griddy.task.Event;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Storage {
    private String filePath;

    public Storage(String filePath) {
        this.filePath = filePath;
    }

    public void saveTask(Task task) throws IOException {
        String taskData = formatTaskForSaving(task);
        FileWriter fw = new FileWriter(filePath, true);
        fw.write(taskData + System.lineSeparator());
        fw.close();
    }

    public ArrayList<Task> loadTasks() throws FileNotFoundException {
        ArrayList<Task> tasks = new ArrayList<>();
        File f = new File(filePath);
        if (!f.exists()) {
            return tasks;
        }

        Scanner s = new Scanner(f);
        while (s.hasNext()) {
            String line = s.nextLine().trim();
            if (line.isEmpty()) {
                continue;
            }

            try {
                Task task = parseTaskFromLine(line);
                if (task != null) {
                    tasks.add(task);
                }
            } catch (Exception e) {
                System.out.println("Error parsing line: " + line);
            }
        }
        s.close();
        return tasks;
    }

    public void deleteTaskFromFile(int taskNumber) {
        try {
            List<String> lines = Files.readAllLines(Paths.get(filePath));

            if (taskNumber < 1 || taskNumber > lines.size()) {
                System.out.println("Invalid line number");
                return;
            }

            lines.remove(taskNumber - 1);
            Files.write(Paths.get(filePath), lines);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String formatTaskForSaving(Task task) {
        String taskType = "";
        String taskContent = "";

        if (task instanceof ToDo) {
            taskType = "T";
            taskContent = task.toString().substring(7); // Remove "[T]" prefix
        } else if (task instanceof Deadline) {
            taskType = "D";
            String taskStr = task.toString();
            String content = taskStr.substring(7); // Remove "[D][ ]" or "[D][X]"
            // Convert "(by: date)" back to " /by date" format
            if (content.contains(" (by: ")) {
                String desc = content.substring(0, content.indexOf(" (by: "));
                String byPart = content.substring(content.indexOf(" (by: ") + 6, content.length() - 1);
                taskContent = desc + " /by " + byPart;
            } else {
                taskContent = content;
            }
        } else if (task instanceof Event) {
            taskType = "E";
            String taskStr = task.toString();
            String content = taskStr.substring(7); // Remove "[E][ ]" or "[E][X]"
            // Convert "(from: start to: end)" back to " /from start /to end" format
            if (content.contains(" (from: ") && content.contains(" to: ")) {
                String desc = content.substring(0, content.indexOf(" (from: "));
                String fromPart = content.substring(content.indexOf(" (from: ") + 8, content.indexOf(" to: "));
                String toPart = content.substring(content.indexOf(" to: ") + 5, content.length() - 1);
                taskContent = desc + " /from " + fromPart + " /to " + toPart;
            } else {
                taskContent = content;
            }
        }

        return taskType + " [" + task.getStatusIcon() + "] " + taskContent;
    }

    private Task parseTaskFromLine(String line) {
        if (line.length() < 6) return null;

        char taskType = line.charAt(0);
        boolean isDone = line.charAt(2) == 'X';
        String taskContent = line.substring(6);

        Task task = null;

        switch (taskType) {
        case 'T':
            task = new ToDo(taskContent);
            break;
        case 'D':
            if (taskContent.contains(" /by ")) {
                String[] parts = taskContent.split(" /by ");
                if (parts.length == 2) {
                    task = new Deadline(parts[0].trim(), parts[1].trim());
                }
            }
            break;
        case 'E':
            if (taskContent.contains(" /from ") && taskContent.contains(" /to ")) {
                String[] firstSplit = taskContent.split(" /from ");
                if (firstSplit.length == 2) {
                    String[] secondSplit = firstSplit[1].split(" /to ");
                    if (secondSplit.length == 2) {
                        task = new Event(firstSplit[0].trim(), secondSplit[0].trim(), secondSplit[1].trim());
                    }
                }
            }
            break;
        }

        if (task != null && isDone) {
            task.markAsDone();
        }

        return task;
    }
}
