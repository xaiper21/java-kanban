package exeption;

public class IntersectionCheckFromPrioritizedTasksException extends RuntimeException {
    public IntersectionCheckFromPrioritizedTasksException() {
        super("задачи пересекаются по времени");
    }
}
