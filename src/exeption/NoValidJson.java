package exeption;

public class NoValidJson extends RuntimeException {
    public NoValidJson() {
        super("Json пустой или не валидный");
    }
}
