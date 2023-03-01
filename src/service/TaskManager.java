package service;

import model.*;
import java.util.List;

public interface TaskManager {
    int getId();

    List<Task> getAllTasks();

    List<Epic> getAllEpics();

    List<Subtask> getAllSubtasks();

    void removeAllTasks();

    void removeAllEpics();

    void removeAllSubTasks();

    Task getTaskById(Integer id);

    Epic getEpicById(Integer id);

    Subtask getSubTaskById(Integer id);

    void createTask(Task task);

    void createEpic(Epic epic);

    void createSubtask(Subtask subtask);

    void removeTaskById(Integer id);

    void removeEpicById(Integer id);

    void removeSubtaskById(Integer id);

    void updateTask(Task task);

    void updateSubtask(Subtask subtask);

    void updateEpic(Epic epic);

    List<Subtask> getSubtasksOfEpic(Integer epicId);
    HistoryManager getHistoryManager();
}
