package eu.smoothcloud.lib.json;

import eu.smoothcloud.lib.json.annotations.JsonField;
import eu.smoothcloud.lib.json.annotations.JsonIgnore;
import eu.smoothcloud.lib.json.reflection.ReflectionUtil;
import org.json.JSONObject;
import java.lang.reflect.Field;

public class Json {

    public String toJson() {
        try {
            JSONObject jsonObject = new JSONObject();
            for (Field field : this.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(JsonIgnore.class)) continue;

                String jsonKey = field.getName();
                if (field.isAnnotationPresent(JsonField.class)) {
                    JsonField annotation = field.getAnnotation(JsonField.class);
                    if (!annotation.value().isEmpty()) {
                        jsonKey = annotation.value();
                    }
                }

                Object value = ReflectionUtil.invokeGetter(this, field.getName());
                jsonObject.put(jsonKey, value);
            }
            return jsonObject.toString(4);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void fromJson(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            for (Field field : this.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(JsonIgnore.class)) continue;

                String jsonKey = field.getName();
                if (field.isAnnotationPresent(JsonField.class)) {
                    JsonField annotation = field.getAnnotation(JsonField.class);
                    if (!annotation.value().isEmpty()) {
                        jsonKey = annotation.value();
                    }
                }

                if (jsonObject.has(jsonKey)) {
                    ReflectionUtil.invokeSetter(this, field.getName(), jsonObject.get(jsonKey));
                }
            }
        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }

    public Object dynamicGet(String fieldName) throws Exception {
        return ReflectionUtil.invokeGetter(this, fieldName);
    }

    public void dynamicSet(String fieldName, Object value) throws Exception {
        ReflectionUtil.invokeSetter(this, fieldName, value);
    }
}
