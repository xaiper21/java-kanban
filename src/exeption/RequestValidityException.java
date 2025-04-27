package exeption;

public class RequestValidityException extends RuntimeException {
    public RequestValidityException(String message) {
        super(message);
    }
}
