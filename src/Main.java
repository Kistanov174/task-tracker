import model.*;
import service.*;

public class Main {
    public static void main(String[] args) {
        TaskManager taskManager = new Managers().getDefault();

        Task first = new Task("The first task", "This task is number one", taskManager);
        Task second = new Task("The second task", "This task is number two", taskManager);
        taskManager.createTask(first);
        taskManager.createTask(second);

        Epic twoItems = new Epic("The double epic", "This epic has two subtasks", taskManager);
        taskManager.createEpic(twoItems);
        Subtask numberOne = new Subtask("The first", "Number one", twoItems.getId(), taskManager);
        taskManager.createSubtask(numberOne);
        Subtask numberTwo = new Subtask("The second", "Number two", twoItems.getId(), taskManager);
        taskManager.createSubtask(numberTwo);

        Epic oneItem = new Epic("The single epic", "This epic has one subtask", taskManager);
        taskManager.createEpic(oneItem);
        Subtask alone = new Subtask("The subtask", "Single subtask", oneItem.getId(), taskManager);
        taskManager.createSubtask(alone);

        first.setStatus(Status.DONE);
        taskManager.updateTask(first);

        alone.setStatus(Status.DONE);
        numberOne.setStatus(Status.DONE);
        taskManager.updateSubtask(numberOne);
        taskManager.updateSubtask(alone);

        taskManager.getTaskById(1);
        printIdOfWatchedTasks(taskManager);
        taskManager.getTaskById(2);
        printIdOfWatchedTasks(taskManager);
        taskManager.getEpicById(3);
        printIdOfWatchedTasks(taskManager);
        taskManager.getSubTaskById(4);
        printIdOfWatchedTasks(taskManager);
        taskManager.getSubTaskById(4);
        printIdOfWatchedTasks(taskManager);
        taskManager.getSubTaskById(5);
        printIdOfWatchedTasks(taskManager);
        taskManager.getTaskById(1);
        printIdOfWatchedTasks(taskManager);
        taskManager.getTaskById(1);
        taskManager.getTaskById(1);
        taskManager.getTaskById(1);
        printIdOfWatchedTasks(taskManager);
        taskManager.getTaskById(2);
        printIdOfWatchedTasks(taskManager);
    }
    private static void printIdOfWatchedTasks(TaskManager taskManager) {
        for (Task task : taskManager.getHistoryManager().getHistory()) {
            System.out.print(task.getId());
        }
        System.out.println("");
    }
}