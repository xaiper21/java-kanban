package managers;

import exeption.ManagerSaveException;
import tasks.TaskStatus;
import tasks.TaskType;
import tasks.epic.Epic;
import tasks.subtask.Subtask;
import tasks.task.Task;

import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;

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
            bw.write("id,type,name,status,description,startTime,duration(Minutes),epic\n");
            for (Task task : super.getListAllTasks()) {
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
        final int NUM_ID = 0;
        final int NUM_TYPE = 1;
        final int NUM_NAME = 2;
        final int NUM_STATUS = 3;
        final int NUM_DESCRIPTION = 4;
        final int NUM_START_TIME = 5;
        final int NUM_DURATION = 6;
        final int NUM_EPIC = 7;

        int id = Integer.parseInt(valuesString[NUM_ID]);
        TaskType type = getTypeFromString(valuesString[NUM_TYPE]);
        String name = valuesString[NUM_NAME];
        TaskStatus status = getStatusFromString(valuesString[NUM_STATUS]);
        String description = valuesString[NUM_DESCRIPTION];
        Integer idEpic = getIdMyEpicFromString(valuesString[NUM_EPIC], type);
        LocalDateTime startTime = getDateTimeFromString(valuesString[NUM_START_TIME]);
        Duration duration = getDurationFromString(valuesString[NUM_DURATION]);
        Task task;
        if (type == TaskType.Subtask) {
            task = new Subtask(status, name, description, idEpic);
        } else if (type == TaskType.Task) {
            task = new Task(status, name, description);
        } else {
            task = new Epic(name, description);
        }
        task.setId(id);
        task.setDuration(duration);
        task.setStartTime(startTime);
        return task;
    }

    private Duration getDurationFromString(String value) {
        if (value.equals("null")) return null;
        return Duration.ofMinutes(Long.parseLong(value));
    }

    private LocalDateTime getDateTimeFromString(String value) {
        if (value.equals("null")) return null;
        return LocalDateTime.parse(value);
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

    private Integer getIdMyEpicFromString(String value, TaskType type) {
        if (type != TaskType.Subtask) return null;
        return Integer.parseInt(value);
    }

    @Override
    public void addTask(Task task) {
        super.addTask(task);
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
