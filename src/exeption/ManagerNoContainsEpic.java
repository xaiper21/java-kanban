package exeption;

public class ManagerNoContainsEpic extends RuntimeException {
    public ManagerNoContainsEpic() {
        super("Менеджер не содержит необходимый эпик для добавления субтаски");
    }
}
