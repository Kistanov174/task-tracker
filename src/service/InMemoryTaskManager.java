package service;

import java.util.*;
import model.*;

public class InMemoryTaskManager implements TaskManager {
    private int counter;
    private final Map<Integer, Task> tasks;
    private final Map<Integer, Epic> epics;
    private final Map<Integer, Subtask> subtasks;
    private final HistoryManager historyManager;

    public InMemoryTaskManager() {
        counter = 0;
        tasks = new HashMap<>();
        epics = new HashMap<>();
        subtasks = new HashMap<>();
        historyManager = Managers.getDefaultHistory();
    }

    @Override
    public int getId() {
        return ++counter;
    }

    @Override
    public List<Task> getAllTasks() {
        if (!tasks.isEmpty()) {
            return new ArrayList<>(tasks.values());
        }
        return Collections.emptyList();
    }

    @Override
    public List<Epic> getAllEpics() {
        if (!epics.isEmpty()) {
            return new ArrayList<>(epics.values());
        }
        return Collections.emptyList();
    }

    @Override
    public List<Subtask> getAllSubtasks() {
        if (!subtasks.isEmpty()) {
            return new ArrayList<>(subtasks.values());
        }
        return Collections.emptyList();
    }

    @Override
    public void removeAllTasks() {
        tasks.clear();
    }

    @Override
    public void removeAllEpics() {
        removeAllSubTasks();
        epics.clear();
    }

    @Override
    public void removeAllSubTasks() {
        subtasks.clear();
        for (Epic epic : epics.values()) {
            updateEpic(epic);
        }
    }

    @Override
    public Task getTaskById(Integer id) {
        historyManager.add(tasks.get(id));
        return tasks.get(id);
    }

    @Override
    public Epic getEpicById(Integer id) {
        historyManager.add(epics.get(id));
        return epics.get(id);
    }

    @Override
    public Subtask getSubTaskById(Integer id) {
        historyManager.add(subtasks.get(id));
        return subtasks.get(id);
    }

    @Override
    public void createTask(Task task) {
        task.setId();
        tasks.put(task.getId(), task);
    }

    @Override
    public void createEpic(Epic epic) {
        epic.setId();
        epics.put(epic.getId(), epic);
    }

    @Override
    public void createSubtask(Subtask subtask) {
        if (epics.containsKey(subtask.epicId)) {
            subtask.setId();
            subtasks.put(subtask.getId(), subtask);
            epics.get(subtask.epicId).addSubtaskId(subtask.getId());
            updateEpic(epics.get(subtask.epicId));
        }
    }

    @Override
    public void removeTaskById(Integer id) {
        tasks.remove(id);
    }

    @Override
    public void removeEpicById(Integer id) {
        for (Integer idOfSubtask : epics.get(id).getSubtasksId()) {
            removeSubtaskById(idOfSubtask);
        }
        epics.remove(id);
    }

    @Override
    public void removeSubtaskById(Integer id) {
        Integer epicId = subtasks.get(id).epicId;
        epics.get(epicId).removeSubtaskId(id);
        subtasks.remove(id);
        updateEpic(epics.get(epicId));
    }

    @Override
    public void updateTask(Task task) {
        if (tasks.containsKey(task.getId())) {
            tasks.put(task.getId(), task);
        }
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        if (subtasks.containsKey(subtask.getId())) {
            subtasks.put(subtask.getId(), subtask);
            updateEpic(epics.get(subtask.epicId));
        }
    }

    @Override
    public void updateEpic(Epic epic) {
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

    @Override
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

    @Override
    public HistoryManager getHistoryManager() {
        return historyManager;
    }
}
