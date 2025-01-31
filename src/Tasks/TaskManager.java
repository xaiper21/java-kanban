package Tasks;

import Tasks.Epic.Epic;
import Tasks.Subtask.Subtask;
import Tasks.Task.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    private static int identifier = 0;

    public static int getIdentifier() {
        return identifier++;
    }

    private HashMap<Integer, Task> taskTable;
    private HashMap<Integer, Epic> epicTable;

    public TaskManager() {
        taskTable = new HashMap<>();
        epicTable = new HashMap<>();
    }

    public ArrayList<Task> getListAllTalks(){
        ArrayList<Task> tasks = new ArrayList<>();
        for (Task task : taskTable.values()){
            tasks.add(task);
        }
        return tasks;
    }

    public ArrayList<Epic> getListAllEpics(){
        ArrayList<Epic> epics = new ArrayList<>();
        for (Epic epic : epicTable.values()){
            epics.add(epic);
        }
        return epics;
    }

    public ArrayList<Subtask> getListAllSubtasks(){
        ArrayList<Subtask> subtasks = new ArrayList<>();
        for (Epic epic : epicTable.values()){
            for (Subtask subtask : epic.getSubtasks().values()){
                subtasks.add(subtask);
            }
        }
        return subtasks;
    }

    public void removeAllTasks(){
        taskTable.clear();
    }

    public void removeAllEpics(){
        epicTable.clear();
    }

    public void removeAllSubtasks(){
        for (Epic epic : epicTable.values()){
            epic.removeAllSubtasks();
        }
    }

    public Task getTaskById(int id){
        if(taskTable.containsKey(id)){
            return taskTable.get(id);
        }
        return null;
    }

    public Epic getEpicById(int id){
        if (epicTable.containsKey(id)){
            return epicTable.get(id);
        } else return null;
    }

    public Subtask getSubtaskById(int id){
        for (Epic epic : epicTable.values()){
            if (epic.isContainsSubtaskId(id)){
                return epic.getSubtaskById(id);
            }
        }
        return null;
    }

    public void addTask(Task task){
        taskTable.put(task.getId(),task);
    }

    public void addEpic(Epic epic){
        epicTable.put(epic.getId(),epic);
    }

    public void addSubtask(Subtask subtask){
        if (subtask == null) return;
        epicTable.get(subtask.getIdMyEpic()).addSubtask(subtask);
        epicTable.get(subtask.getIdMyEpic()).updateStatus();
    }

    public void updateTask(Task task){
        taskTable.put(task.getId(),task);
    }

    public void updateEpic(Epic epic){
        if (epic == null) return;
        epic.updateStatus();
        epicTable.put(epic.getId(),epic);
    }

    public void updateSubtask(Subtask subtask){
        addSubtask(subtask);
    }

    public void removeSubtsakById(int id){
        for (Epic epic : epicTable.values()){
            if (epic.isContainsSubtaskId(id)){
                epic.removeSubtaskById(id);
                epic.updateStatus();
                return;
            }
        }
    }

    public void removeTastById(int id){
        if (taskTable.containsKey(id)) taskTable.remove(id);
    }

    public void removeEpicById(int id){
        if (epicTable.containsKey(id)) epicTable.remove(id);
    }

    public ArrayList<Subtask> getListSubtasksByIdEpic(int id){
        if (epicTable.containsKey(id)){
            return epicTable.get(id).getArrayListSubtasks();
        } else return null;
    }


}
