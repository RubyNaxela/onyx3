package com.rubynaxela.onyx.io;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * JSON serialization and deserialization utility.
 */
public final class S31N {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final ObjectWriter WRITER = MAPPER.writerWithDefaultPrettyPrinter();
    private static final ObjectReader READER = MAPPER.reader();

    private S31N() {
    }

    public static void serialize(@NotNull Object object, @NotNull File file) {
        try {
            WRITER.writeValue(file, object);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T deserialize(@NotNull Class<T> type, @NotNull File file) {
        try {
            return READER.readValue(file, type);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T deserialize(@NotNull Class<T> type, @NotNull InputStream stream) {
        try {
            return READER.readValue(stream, type);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
