package model;

import service.TaskManager;

public class Subtask extends Task{
    public final int epicId;

    public Subtask(String name, String description, int epicId, TaskManager taskManager) {
        super(name, description, taskManager);
        this.epicId = epicId;
    }

    @Override
    public void setStatus(Status status) {
        this.status = status;
        taskManager.updateSubtask(this);
    }
}
