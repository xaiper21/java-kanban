package http_server.handlers.tasks_handlers.gson_adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeAdapter extends TypeAdapter<LocalDateTime> {

    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy");

    @Override
    public void write(JsonWriter jsonWriter, LocalDateTime localDateTime) throws IOException {
        if (localDateTime != null) {
            jsonWriter.value(localDateTime.format(dtf));
        } else {
            jsonWriter.value("null");
        }
    }

    @Override
    public LocalDateTime read(JsonReader jsonReader) throws IOException {
        String value = jsonReader.nextString();
        if (value.equals("null")) return null;
        return LocalDateTime.parse(value,dtf);
    }
}
