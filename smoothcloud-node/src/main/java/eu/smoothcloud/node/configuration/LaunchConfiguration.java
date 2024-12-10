package eu.smoothcloud.node.configuration;

import org.json.JSONException;
import org.json.JSONObject;

public class LaunchConfiguration {
    private final JSONObject jsonObject;

    public LaunchConfiguration() {
        this.jsonObject = new JSONObject("launcher.json");
    }

    public void setLanguage(String language) {
        this.setValue("language", language);
    }

    public void setHost(String host) {
        this.setValue("host", host);
    }

    public void setPort(int port) {
        this.setValue("port", port);
    }

    public void setMemory(String memory) {
        this.setValue("memory", memory);
    }

    public String getLanguage() {
        return (String) this.getValue("language");
    }

    public String getHost() {
        return (String) this.getValue("host");
    }

    public int getPort() {
        return (int) this.getValue("port");
    }

    public String getMemory() {
        return (String) this.getValue("memory");
    }

    private boolean setValue(String key, Object value) {
        try {
            this.jsonObject.put(key, value);
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    private Object getValue(String key) {
        try {
            return this.jsonObject.get(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
