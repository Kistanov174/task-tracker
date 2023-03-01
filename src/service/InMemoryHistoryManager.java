package service;

import model.Task;
import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements  HistoryManager {
    private final List<Task> historyWatching;

    public InMemoryHistoryManager() {
        historyWatching = new ArrayList<>();
    }

    @Override
    public void add(Task task) {
        if (historyWatching.size() < 10) {
            historyWatching.add(task);
        } else {
            historyWatching.remove(0);
            add(task);
        }
    }

    @Override
    public List<Task> getHistory() {
        return historyWatching;
    }
}
