package managers;

import exeption.ManagerSaveException;
import tasks.TaskStatus;
import tasks.TaskType;
import tasks.epic.Epic;
import tasks.subtask.Subtask;
import tasks.task.Task;

import java.io.*;

public class FileBackedTaskManager extends InMemoryTaskManager {
    File file;

    public FileBackedTaskManager(File file) {
        super();
        this.file = file;
    }

    public static FileBackedTaskManager loadFromFile(File file) {
        FileBackedTaskManager manager = new FileBackedTaskManager(file);
        try (BufferedReader br = new BufferedReader(new FileReader(file.getName()))) {
            while (br.ready()) {
                Task task = manager.fromString(br.readLine());
                manager.addTask(task, task.getId());
            }
            return manager;
        } catch (Exception e) {
            return manager;
        }
    }

    public void save() throws ManagerSaveException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file.getName(), true))) {
            for (Task task : super.getListAllTalks()) {
                bw.write(task.toString() + "\n");
            }
            for (Task task : super.getListAllEpics()) {
                bw.write(task.toString() + "\n");
            }
            for (Task task : super.getListAllSubtasks()) {
                bw.write(task.toString() + "\n");
            }
        } catch (IOException e) {
            throw new ManagerSaveException();
        }
    }

    public Task fromString(String value) {
        String[] valuesString = value.split(",");
        int id = Integer.parseInt(valuesString[0]);
        TaskType type = getTypeFromString(valuesString[1]);
        String name = valuesString[2];
        TaskStatus status = getStatusFromString(valuesString[3]);
        String description = valuesString[4];
        Integer idEpic = getIdMyEpicFromString(valuesString, type);
        Task task;
        if (type == TaskType.Subtask) {
            task = new Subtask(status, name, description, idEpic);
        } else if (type == TaskType.Task) {
            task = new Task(status, name, description);
        } else {
            task = new Epic(name, description);
        }
        task.setId(id);
        return task;
    }


    private TaskType getTypeFromString(String value) {
        if (value.equals("Subtask")) {
            return TaskType.Subtask;
        } else if (value.equals("Epic")) {
            return TaskType.Epic;
        } else {
            return TaskType.Task;
        }
    }

    private TaskStatus getStatusFromString(String value) {
        if (value.equals("IN_PROGRESS")) {
            return TaskStatus.IN_PROGRESS;
        } else if (value.equals("DONE")) {
            return TaskStatus.DONE;
        } else {
            return TaskStatus.NEW;
        }
    }

    private Integer getIdMyEpicFromString(String[] value, TaskType type) {
        if (type != TaskType.Subtask) return null;
        return Integer.parseInt(value[5]);
    }

    @Override
    public void addTask(Task task, int id) {
        try {
            super.addTask(task, id);
            save();
        } catch (ManagerSaveException e) {
            //
        }
    }

    private void saveTask(Task task) {

    }

}
