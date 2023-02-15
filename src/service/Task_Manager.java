package service;

import java.util.*;
import model.*;

public class Task_Manager {
    private int counter;
    private Map<Integer, Task> tasksList;
    private Map<Integer, Epic> epicsList;
    private Map<Integer, Subtask> subtasksList;
    private List<Task> tasks;
    private List<Epic> epics;
    private List<Subtask> subtasks;

    public Task_Manager() {
        counter = 0;
        tasksList = new HashMap<>();
        epicsList = new HashMap<>();
        subtasksList = new HashMap<>();
        tasks = new ArrayList<>();
        epics = new ArrayList<>();
        subtasks = new ArrayList<>();
    }

    public int getId() {
        return ++counter;
    }

    public List<Task> getTasksList() {
        tasks.clear();
        if (tasksList != null) {
            for (Integer key : tasksList.keySet()) {
                tasks.add(tasksList.get(key));
            }
            return tasks;
        }
        return Collections.emptyList();
    }

    public List<Epic> getEpicsList() {
        epics.clear();
        if (epicsList != null) {
            for (Integer key : epicsList.keySet()) {
                epics.add(epicsList.get(key));
            }
            return epics;
        }
        return Collections.emptyList();
    }

    public List<Subtask> getSubtasksList() {
        subtasks.clear();
        if (subtasksList != null) {
            for (Integer key : subtasksList.keySet()) {
                subtasks.add(subtasksList.get(key));
            }
            return subtasks;
        }
        return Collections.emptyList();
    }

    public void removeAllTasks() {
        tasksList.clear();
    }

    public void removeAllEpics() {
        removeAllSubTasks();
        epicsList.clear();
    }

    public void removeAllSubTasks() {
        subtasksList.clear();
        for (Epic epic : epicsList.values()) {
            updateEpic(epic);
        }
    }

    public Task getTaskById(Integer id) {
        return tasksList.get(id);
    }

    public Epic getEpicById(Integer id) {
        return epicsList.get(id);
    }

    public Subtask getSubTaskById(Integer id) {
        return subtasksList.get(id);
    }

    public void createTask(Task task) {
        task.setId();
        tasksList.put(task.getId(), task);
    }

    public void createEpic(Epic epic) {
        epic.setId();
        epicsList.put(epic.getId(), epic);
    }

    public void createSubtask(Subtask subtask) {
        if (epicsList.containsKey(subtask.epicId)) {
            subtask.setId();
            subtasksList.put(subtask.getId(), subtask);
            epicsList.get(subtask.epicId).addSubtaskId(subtask.getId());
            updateEpic(epicsList.get(subtask.epicId));
        }
    }

    public void removeTaskById(Integer id) {
        tasksList.remove(id);
    }

    public void removeEpicById(Integer id) {
        for (Integer idOfSubtask : epicsList.get(id).getSubtasksId()) {
            removeSubtaskById(idOfSubtask);
        }
        epicsList.remove(id);
    }

    public void removeSubtaskById(Integer id) {
        Integer epicId = subtasksList.get(id).epicId;
        epicsList.get(epicId).removeSubtaskId(id);
        subtasksList.remove(id);
        updateEpic(epicsList.get(epicId));
    }

    public void updateTask(Task task) {
        if (tasksList.containsKey(task.getId())) {
            tasksList.put(task.getId(), task);
        }
    }

    public void updateSubtask(Subtask subtask) {
        if (subtasksList.containsKey(subtask.getId())) {
            subtasksList.put(subtask.getId(), subtask);
            updateEpic(epicsList.get(subtask.epicId));
        }
    }

    private void updateEpic(Epic epic) {
        int numberOfDoneSubtask = 0;
        if (epicsList.containsKey(epic.getId())) {
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
        if (epicsList.containsKey(epicId)) {
            for(Integer subtaskId : epicsList.get(epicId).getSubtasksId()) {
                subtaskOfEpic.add(subtasksList.get(subtaskId));
            }
            return subtaskOfEpic;
        }
        return Collections.emptyList();
    }
}
