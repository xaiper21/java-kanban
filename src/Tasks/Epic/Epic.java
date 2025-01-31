package Tasks.Epic;

import Tasks.Subtask.Subtask;
import Tasks.Task.Task;
import Tasks.TaskStatus;

import java.util.ArrayList;
import java.util.HashMap;

public class Epic extends Task {
    HashMap<Integer, Subtask> subtasks;

    public Epic() {
        subtasks = new HashMap<>();
    }

    public HashMap<Integer, Subtask> getSubtasks() {
        return subtasks;
    }

    public void setSubtasks(HashMap<Integer, Subtask> subtasks) {
        this.subtasks = subtasks;
    }

    public void addSubtask(Subtask subtask) {
        subtasks.put(subtask.getId(), subtask);
    }

    private TaskStatus calculateStatus() {
        if (subtasks.isEmpty()) {
            return TaskStatus.NEW;
        }
        boolean isProgress = false;
        boolean isNew = false;
        boolean isDone = false;
        for (Subtask subtask : subtasks.values()) {
            TaskStatus statusSubtask = subtask.getStatus();
            switch (statusSubtask) {
                case TaskStatus.DONE -> isDone = true;
                // case NEW -> isNew = true;
                case TaskStatus.IN_PROGRESS -> isProgress = true;
                case null, default -> isNew = true;
            }
        }
        if (!isProgress && !isDone) {
            return TaskStatus.NEW;
        } else if (!isProgress && !isNew) {
            return TaskStatus.DONE;
        } else return TaskStatus.IN_PROGRESS;
    }

    public void updateStatus() {
        setStatus(calculateStatus());
    }

    public void removeAllSubtasks(){
        subtasks.clear();
    }

    public Subtask getSubtaskById(int id){
        if (subtasks.containsKey(id)){
            return subtasks.get(id);
        }else return null;
    }

    public boolean isContainsSubtaskId(int id){
        return subtasks.containsKey(id);
    }

    public void removeSubtaskById(int id){
        subtasks.remove(id);
    }

    public ArrayList<Subtask> getArrayListSubtasks(){
        ArrayList<Subtask> subtasks = new ArrayList<>();
        subtasks.addAll(this.subtasks.values());
        return subtasks;
    }

}
