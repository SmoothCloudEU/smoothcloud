package eu.smoothcloud.node.configuration;

import org.tomlj.Toml;
import org.tomlj.TomlTable;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;

public interface TomlSerializable {

    default String toTomlString() {
        StringBuilder tomlBuilder = new StringBuilder();
        var fields = this.getClass().getDeclaredFields();
        Arrays.sort(fields, Comparator.comparing(Field::getName));
        for (var field : fields) {
            field.setAccessible(true);
            try {
                Object value = field.get(this);
                tomlBuilder.append(field.getName()).append(" = ");
                if (value instanceof String) {
                    tomlBuilder.append("\"").append(value).append("\"");
                } else {
                    tomlBuilder.append(value);
                }
                tomlBuilder.append("\n");
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Fehler beim Zugriff auf das Feld: " + field.getName(), e);
            }
        }
        return tomlBuilder.toString();
    }

    default void saveToFile(String path, String fileName) {
        try {
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
            FileWriter writer = new FileWriter(new File(file, fileName));
            writer.write(this.toTomlString());
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException("Fehler beim Speichern der Datei: " + fileName, e);
        }
    }

    static <T extends TomlSerializable> T loadFromFile(String path, String fileName, Class<T> clazz) {
        try {
            String filePath = Paths.get(path, fileName).toString();
            String content = Files.readString(Paths.get(filePath));
            TomlTable toml = Toml.parse(content);
            return fromTomlTable(toml, clazz);
        } catch (IOException e) {
            return null;
        }
    }

    static <T extends TomlSerializable> T fromTomlTable(TomlTable toml, Class<T> clazz) {
        try {
            T instance = clazz.getDeclaredConstructor().newInstance();
            var fields = clazz.getDeclaredFields();
            for (var field : fields) {
                field.setAccessible(true);
                if (toml.contains(field.getName())) {
                    Object value = toml.get(field.getName());
                    if (field.getType().equals(int.class) && value instanceof Long) {
                        field.set(instance, ((Long) value).intValue());
                        continue;
                    }
                    field.set(instance, value);
                }
            }
            return instance;
        } catch (Exception e) {
            throw new RuntimeException("Fehler beim Deserialisieren von TOML", e);
        }
    }
}
