package httpserver.handlers.taskshandlers.gson_adapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.time.Duration;

public class DurationAdapter extends TypeAdapter<Duration> {

    @Override
    public void write(JsonWriter jsonWriter, Duration duration) throws IOException {
        if (duration != null) {
            jsonWriter.value(duration.toMinutes());
        } else {
            jsonWriter.value("null");
        }
    }

    @Override
    public Duration read(JsonReader jsonReader) throws IOException {
        String value = jsonReader.nextString();
        if (value.equals("null")) return null;
        return Duration.ofMinutes(Integer.parseInt(value));
    }
}
