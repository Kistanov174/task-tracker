package service;

import java.util.*;
import model.*;

public class TaskManager {
    private int counter;
    private Map<Integer, Task> tasks;
    private Map<Integer, Epic> epics;
    private Map<Integer, Subtask> subtasks;

    public TaskManager() {
        counter = 0;
        tasks = new HashMap<>();
        epics = new HashMap<>();
        subtasks = new HashMap<>();
    }

    public int getId() {
        return ++counter;
    }

    public Collection<Task> getAllTasks() {
        if (tasks != null) {
            return tasks.values();
        }
        return Collections.emptyList();
    }

    public Collection<Epic> getAllEpics() {
        if (epics != null) {
            return epics.values();
        }
        return Collections.emptyList();
    }

    public Collection<Subtask> getAllSubtasks() {
        if (subtasks != null) {
            return subtasks.values();
        }
        return Collections.emptyList();
    }

    public void removeAllTasks() {
        tasks.clear();
    }

    public void removeAllEpics() {
        removeAllSubTasks();
        epics.clear();
    }

    public void removeAllSubTasks() {
        subtasks.clear();
        for (Epic epic : epics.values()) {
            updateEpic(epic);
        }
    }

    public Task getTaskById(Integer id) {
        return tasks.get(id);
    }

    public Epic getEpicById(Integer id) {
        return epics.get(id);
    }

    public Subtask getSubTaskById(Integer id) {
        return subtasks.get(id);
    }

    public void createTask(Task task) {
        task.setId();
        tasks.put(task.getId(), task);
    }

    public void createEpic(Epic epic) {
        epic.setId();
        epics.put(epic.getId(), epic);
    }

    public void createSubtask(Subtask subtask) {
        if (epics.containsKey(subtask.epicId)) {
            subtask.setId();
            subtasks.put(subtask.getId(), subtask);
            epics.get(subtask.epicId).addSubtaskId(subtask.getId());
            updateEpic(epics.get(subtask.epicId));
        }
    }

    public void removeTaskById(Integer id) {
        tasks.remove(id);
    }

    public void removeEpicById(Integer id) {
        for (Integer idOfSubtask : epics.get(id).getSubtasksId()) {
            removeSubtaskById(idOfSubtask);
        }
        epics.remove(id);
    }

    public void removeSubtaskById(Integer id) {
        Integer epicId = subtasks.get(id).epicId;
        epics.get(epicId).removeSubtaskId(id);
        subtasks.remove(id);
        updateEpic(epics.get(epicId));
    }

    public void updateTask(Task task) {
        if (tasks.containsKey(task.getId())) {
            tasks.put(task.getId(), task);
        }
    }

    public void updateSubtask(Subtask subtask) {
        if (subtasks.containsKey(subtask.getId())) {
            subtasks.put(subtask.getId(), subtask);
            updateEpic(epics.get(subtask.epicId));
        }
    }

    private void updateEpic(Epic epic) {
        int numberOfDoneSubtask = 0;
        if (epics.containsKey(epic.getId())) {
            List<Subtask> subtaskOfEpic = getSubtasksOfEpic(epic.getId());
            for (Subtask subtask : subtaskOfEpic) {
                if (subtask.getStatus().equals(Status.DONE)) {
                    numberOfDoneSubtask++;
                }
            }
            if (numberOfDoneSubtask == subtaskOfEpic.size()) {
                epic.setStatus(Status.DONE);
            }
            if (numberOfDoneSubtask > 0 && numberOfDoneSubtask < subtaskOfEpic.size()) {
                epic.setStatus(Status.IN_PROGRESS);
            }
        }
    }

    public List<Subtask> getSubtasksOfEpic(Integer epicId) {
        List<Subtask> subtaskOfEpic = new ArrayList<>();
        if (epics.containsKey(epicId)) {
            for(Integer subtaskId : epics.get(epicId).getSubtasksId()) {
                subtaskOfEpic.add(subtasks.get(subtaskId));
            }
            return subtaskOfEpic;
        }
        return Collections.emptyList();
    }
}
