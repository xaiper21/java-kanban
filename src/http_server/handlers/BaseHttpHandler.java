package http_server.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public abstract class BaseHttpHandler implements HttpHandler {

    protected void sendText(HttpExchange exchange, String text) throws IOException {
        final int responseCode = 200;
        byte[] response = text.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        exchange.sendResponseHeaders(responseCode, response.length);
        exchange.getResponseBody().write(response);
        exchange.close();
    }

    protected void sendNotFound(HttpExchange exchange, String text) throws IOException {
        send(exchange, text, 404);
    }

    protected void sendHasInteractions(HttpExchange exchange) throws IOException {
        send(exchange, "Задача пересекается с существующей", 406);
    }

    protected void send(HttpExchange exchange, String message, int code) throws IOException {
        exchange.sendResponseHeaders(code, 0);
        exchange.getResponseBody().write(message.getBytes(StandardCharsets.UTF_8));
        exchange.close();
    }

    public void sendErrorValid(HttpExchange exchange) throws IOException {
        send(exchange, "Ошибка валидации", 400);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            String[] patch = exchange.getRequestURI().getPath().split("/");
            String method = exchange.getRequestMethod();
            switch (method) {
                case "GET" -> {
                    processGet(patch, exchange);
                }
                case "POST" -> {
                    processPost(patch, exchange);
                }
                case "DELETE" -> {
                    processDelete(patch, exchange);
                }
                default -> {
                    processDefault(method, exchange);
                }
            }
        } catch (NumberFormatException e) {
            sendErrorValid(exchange);
        }

    }

    protected void processDefault(String method, HttpExchange exchange) throws IOException {
        sendNotFound(exchange, "Метод: " + method + " не реализован");
    }

    protected abstract void processGet(String[] patch, HttpExchange exchange) throws IOException;

    protected abstract void processPost(String[] patch, HttpExchange exchange) throws IOException;

    protected abstract void processDelete(String[] patch, HttpExchange exchange) throws IOException;
}