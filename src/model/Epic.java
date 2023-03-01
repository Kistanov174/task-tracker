package model;

import service.InMemoryTaskManager;
import service.TaskManager;

import java.util.ArrayList;
import java.util.List;

public class Epic extends Task{
    private final List<Integer> subtasksId;
    public Epic(String name, String description, TaskManager taskManager) {
        super(name, description, taskManager);
        subtasksId = new ArrayList<>();
    }

    public void addSubtaskId(Integer id) {
        subtasksId.add(id);
    }

    public  void removeSubtaskId(Integer id) {
        subtasksId.remove(id);
    }

    public List<Integer> getSubtasksId() {
        return subtasksId;
    }
}
