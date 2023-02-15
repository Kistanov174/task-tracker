import model.Epic;
import model.Subtask;
import model.Task;
import service.Status;
import service.Task_Manager;

public class Main {
    public static void main(String[] args) {
        Task_Manager taskManager = new Task_Manager();

        System.out.println("Создаем и выводим 2 задачи и 2 эпика");
        Task first = new Task("The first task", "This task is number one", taskManager);
        Task second = new Task("The second task", "This task is number two", taskManager);
        taskManager.createTask(first);
        taskManager.createTask(second);
        System.out.println(taskManager.getTasksList());

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
        System.out.println(taskManager.getEpicsList());
        System.out.println(taskManager.getSubtasksList() + "\n");

        System.out.println("Изменяем статус первой задачи, первой подзадачи первого эпика"
                + " и единственной подзадачи второго эпика, выводи результат");
        first.setStatus(Status.DONE);
        taskManager.updateTask(first);
        System.out.println(taskManager.getTasksList());

        alone.setStatus(Status.DONE);
        numberOne.setStatus(Status.DONE);
        taskManager.updateSubtask(numberOne);
        taskManager.updateSubtask(alone);
        System.out.println(taskManager.getEpicsList());
        System.out.println(taskManager.getSubtasksList() + "\n");

        System.out.println("Удаляем первую задачу и вторую подзадачу первоо эпика, выводим результат");
        taskManager.removeTaskById(first.getId());
        taskManager.removeSubtaskById(numberTwo.getId());
        System.out.println(taskManager.getTasksList());
        System.out.println(taskManager.getEpicsList());
        System.out.println(taskManager.getSubtasksList());
    }
}