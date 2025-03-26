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
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            int maxId = 0;
            if (br.ready()) {
                br.readLine();
            }
            while (br.ready()) {
                Task task = manager.fromString(br.readLine());
                if (maxId < task.getId()) maxId = task.getId();
                manager.addTask(task, task.getId());
            }
            manager.setIdentifier(maxId);
            return manager;
        } catch (Exception e) {
            throw new ManagerSaveException(e.getMessage());
        }
    }

    public void save() throws ManagerSaveException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file.getName()))) {
            bw.write("id,type,name,status,description,epic\n");
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
            throw new ManagerSaveException("Ошибка при сохранении тасок");
        }
    }

    private Task fromString(String value) {
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
    public void addTask(Task task) {
        super.addTask(task, task.getId());
        save();
    }

    @Override
    public void addEpic(Epic epic) {
        super.addEpic(epic);
        save();
    }

    @Override
    public void addSubtask(Subtask subtask) {
        super.addSubtask(subtask);
        save();
    }


    @Override
    public void removeAllTasks() {
        super.removeAllTasks();
        save();
    }

    @Override
    public void removeAllEpics() {
        super.removeAllEpics();
        save();
    }

    @Override
    public void removeAllSubtasks() {
        super.removeAllSubtasks();
        save();
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void updateSubtask(Subtask subtask) {
        super.updateSubtask(subtask);
        save();
    }

    @Override
    public void removeSubtaskById(int id) {
        super.removeSubtaskById(id);
        save();
    }

    @Override
    public void removeTaskById(int id) {
        super.removeTaskById(id);
        save();
    }

    @Override
    public void removeEpicById(int id) {
        super.removeEpicById(id);
        save();
    }
}
