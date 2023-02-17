package model;

import service.TaskManager;

public class Task {
    protected Integer id;
    protected String Name;
    protected String description;
    protected Status status;
    protected TaskManager taskManager;


    public Task(String name, String description, TaskManager taskManager) {
        this.Name = name;
        this.description = description;
        status = Status.NEW;
        this.taskManager = taskManager;
    }

    public Integer getId() {
        return id;
    }

    public void setId() {
        id = taskManager.getId();
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
        taskManager.updateTask(this);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " " + this.Name + ": " + this.description + " с идентификатором - "
                + this.id + " имееет статус " + this.getStatus().getTitle();
    }
}
