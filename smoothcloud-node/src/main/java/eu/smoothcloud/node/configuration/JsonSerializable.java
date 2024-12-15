/*
 * Copyright (c) 2024 SmoothCloud team & contributors
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

import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;

public interface JsonSerializable {

    default JSONObject toJSONObject() {
        JSONObject jsonObject = new JSONObject();
        var fields = this.getClass().getDeclaredFields();
        Arrays.sort(fields, Comparator.comparing(Field::getName));
        for (var field : fields) {
            field.setAccessible(true);
            try {
                jsonObject.put(field.getName(), field.get(this));
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Fehler beim Zugriff auf das Feld: " + field.getName(), e);
            }
        }
        return jsonObject;
    }

    default void saveToFile(String path, String fileName) {
        try {
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
            FileWriter writer = new FileWriter(new File(file, fileName));
            writer.write(this.toJSONObject().toString(4));
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException("Fehler beim Speichern der Datei: " + fileName, e);
        }
    }

    static <T extends JsonSerializable> T loadFromFile(String path, String fileName, Class<T> clazz) {
        try {
            String filePath = Paths.get(path, fileName).toString();
            String content = Files.readString(Paths.get(filePath));
            JSONObject jsonObject = new JSONObject(content);
            return fromJSONObject(jsonObject, clazz);
        } catch (IOException e) {
            throw new RuntimeException("Fehler beim Lesen der Datei: " + fileName, e);
        }
    }

    static <T extends JsonSerializable> T fromJSONObject(JSONObject jsonObject, Class<T> clazz) {
        try {
            T instance = clazz.getDeclaredConstructor().newInstance();
            var fields = clazz.getDeclaredFields();
            for (var field : fields) {
                field.setAccessible(true);
                if (jsonObject.has(field.getName())) {
                    Object value = jsonObject.get(field.getName());
                    field.set(instance, value);
                }
            }
            return instance;
        } catch (Exception e) {
            throw new RuntimeException("Fehler beim Deserialisieren von JSON", e);
        }
    }
}
