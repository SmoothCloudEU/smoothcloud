/*
 * Copyright (c) 2025 SmoothCloud team & contributors
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package eu.smoothcloud.node.configuration;

import org.tomlj.Toml;
import org.tomlj.TomlArray;
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
                } else if (value instanceof String[] array) {
                    tomlBuilder.append("[");
                    for (int i = 0; i < array.length; i++) {
                        tomlBuilder.append("\"").append(array[i]).append("\"");
                        if (i < array.length - 1) {
                            tomlBuilder.append(", ");
                        }
                    }
                    tomlBuilder.append("]");
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
                    if (field.getType().equals(String[].class) && value instanceof TomlArray array) {
                        String[] stringArray = new String[array.size()];
                        for (int i = 0; i < array.size(); i++) {
                            stringArray[i] = array.getString(i);
                        }
                        field.set(instance, stringArray);
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
