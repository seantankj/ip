package griddy.ui;

import griddy.task.Task;
import griddy.storage.Storage;
import griddy.parser.Parser;
import griddy.parser.Parser.Command;
import griddy.parser.Parser.CommandType;
import griddy.tasklist.TaskList;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Griddybot {
    private static final String FILE_PATH = "data/save.txt";

    private TaskList taskList;
    private Storage storage;
    private Ui ui;
    private Scanner scanner;

    public Griddybot() {
        this.taskList = new TaskList();
        this.storage = new Storage(FILE_PATH);
        this.ui = new Ui();
        this.scanner = new Scanner(System.in);
    }

    public static void main(String[] args) throws GriddyException {
        Griddybot bot = new Griddybot();
        bot.run();
    }

    public void run() throws GriddyException {
        ui.printWelcome();
        loadTasksFromStorage();

        boolean isExit = false;
        while (!isExit) {
            String inputLine = scanner.nextLine();

            try {
                Command command = Parser.parseCommand(inputLine);
                isExit = executeCommand(command);
            } catch (GriddyException e) {
                ui.printError(e.getMessage());
            }
        }

        scanner.close();
    }

    private boolean executeCommand(Command command) throws GriddyException {
        CommandType type = command.getType();
        String fullCommand = command.getFullCommand();

        switch (type) {
        case MARK:
            handleMarkCommand(fullCommand);
            break;
        case UNMARK:
            handleUnmarkCommand(fullCommand);
            break;
        case TODO:
            handleTodoCommand(fullCommand);
            break;
        case DEADLINE:
            handleDeadlineCommand(fullCommand);
            break;
        case EVENT:
            handleEventCommand(fullCommand);
            break;
        case DELETE:
            handleDeleteCommand(fullCommand);
            break;
        case FIND:
            handleFindCommand(fullCommand);
            break;
        case LIST:
            handleListCommand();
            break;
        case BYE:
            handleByeCommand();
            return true;
        }

        return false;
    }

    private void handleMarkCommand(String inputLine) throws GriddyException {
        try {
            int taskNumber = Parser.parseTaskNumber(inputLine, 5);
            validateTaskNumber(taskNumber);

            taskList.markTask(taskNumber - 1);
            Task task = taskList.getTask(taskNumber - 1);

            // Save all tasks to file (overwrites the entire file)
            try {
                storage.saveAllTasks(taskList.getAllTasks());
            } catch (IOException e) {
                ui.printError("Failed to save tasks: " + e.getMessage());
            }

            ui.printTaskMarked(task, true);
        } catch (IndexOutOfBoundsException e) {
            throw new GriddyException(GriddyException.numberOutOfRange);
        }
    }

    private void handleUnmarkCommand(String inputLine) throws GriddyException {
        try {
            int taskNumber = Parser.parseTaskNumber(inputLine, 7);
            validateTaskNumber(taskNumber);

            taskList.unmarkTask(taskNumber - 1);
            Task task = taskList.getTask(taskNumber - 1);

            try {
                storage.saveAllTasks(taskList.getAllTasks());
            } catch (IOException e) {
                ui.printError("Failed to save tasks: " + e.getMessage());
            }

            ui.printTaskMarked(task, false);
        } catch (IndexOutOfBoundsException e) {
            throw new GriddyException(GriddyException.numberOutOfRange);
        }
    }

    private void handleTodoCommand(String inputLine) throws GriddyException {
        Task task = Parser.createTodoTask(inputLine);
        taskList.addTask(task);

        try {
            storage.saveTask(task);
        } catch (IOException e) {
            ui.printError("Something went wrong: " + e.getMessage());
        }

        ui.printTaskAdded(task, taskList.size());
    }

    private void handleDeadlineCommand(String inputLine) throws GriddyException {
        Task task = Parser.createDeadlineTask(inputLine);
        taskList.addTask(task);

        try {
            storage.saveTask(task);
        } catch (IOException e) {
            ui.printError("Something went wrong: " + e.getMessage());
        }

        ui.printTaskAdded(task, taskList.size());
    }

    private void handleEventCommand(String inputLine) throws GriddyException {
        Task task = Parser.createEventTask(inputLine);
        taskList.addTask(task);

        try {
            storage.saveTask(task);
        } catch (IOException e) {
            ui.printError("Something went wrong: " + e.getMessage());
        }

        ui.printTaskAdded(task, taskList.size());
    }

    private void handleDeleteCommand(String inputLine) throws GriddyException {
        try {
            int taskNumber = Parser.parseTaskNumber(inputLine, 7);
            validateTaskNumber(taskNumber);

            Task taskToDelete = taskList.getTask(taskNumber - 1);
            taskList.deleteTask(taskNumber - 1);
            storage.deleteTaskFromFile(taskNumber);

            ui.printTaskDeleted(taskToDelete, taskList.size());
        } catch (IndexOutOfBoundsException e) {
            throw new GriddyException(GriddyException.numberOutOfRange);
        }
    }

    private void handleFindCommand(String inputLine) throws GriddyException {
        try {
            String keyword = Parser.parseSearchKeyword(inputLine);
            ArrayList<Task> matchingTasks = taskList.findTasks(keyword);
            ui.printSearchResults(matchingTasks, keyword);
        } catch (GriddyException e) {
            ui.printError(e.getMessage());
        }
    }

    private void handleListCommand() {
        ui.printTaskList(taskList.getAllTasks());
    }

    private void handleByeCommand() {
        ui.printBye();
    }

    private void loadTasksFromStorage() {
        try {
            ArrayList<Task> loadedTasks = storage.loadTasks();
            taskList = new TaskList(loadedTasks);
            ui.printLoadedTasks(loadedTasks.size());
        } catch (FileNotFoundException e) {
            ui.printLoadedTasks(0);
        }
    }

    private void validateTaskNumber(int taskNumber) throws GriddyException {
        if (taskNumber < 1 || taskNumber > taskList.size()) {
            throw new GriddyException(GriddyException.numberOutOfRange);
        }
    }


}