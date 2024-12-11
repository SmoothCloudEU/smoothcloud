package eu.smoothcloud.node.configuration;

import eu.smoothcloud.lib.json.Json;
import eu.smoothcloud.lib.json.annotations.JsonField;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class LaunchConfiguration extends Json {

    @JsonField(value = "language")
    private String language;
    @JsonField(value = "host")
    private String host;
    @JsonField(value = "port")
    private int port;
    @JsonField(value = "memory")
    private int memory;
    @JsonField(value = "threads")
    private int threads;

    /*private final JSONObject jsonObject;

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

    public void setThreads(int threads) { this.setValue("threads", threads); }

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

    public int getThreads() { return (int) this.getValue("threads"); }

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
    }*/
}
