import httpserver.HttpTaskServer;
import managers.InMemoryTaskManager;

public class Main {

    public static void main(String[] args) {
        HttpTaskServer server = new HttpTaskServer(new InMemoryTaskManager());
        server.start();

    }
}
