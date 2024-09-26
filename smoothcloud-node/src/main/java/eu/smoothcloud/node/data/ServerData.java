package eu.smoothcloud.node.data;

import me.syntaxjason.config.JsonConfiguration;

import java.io.IOException;

public class ServerData extends JsonConfiguration {

    private String name;
    private String version;
    private String host;

    private String group;

    private int port;
    private int maxMemory;
    private int maxPlayerCount;

    public ServerData(String filePath) throws IOException {
        super(filePath);
    }

    public void setName(String name) {
        this.name = name;
        setConfigValue("name", name);
    }

    public void setVersion(String version) {
        this.version = version;
        setConfigValue("version", version);
    }

    public void setHost(String host) {
        this.host = host;
        setConfigValue("host", host);
    }

    public void setGroup(String group) {
        this.group = group;
        setConfigValue("group", group);
    }

    public void setPort(int port) {
        this.port = port;
        setConfigValue("port", port);
    }

    public void setMaxMemory(int maxMemory) {
        this.maxMemory = maxMemory;
        setConfigValue("maxMem", maxMemory);
    }

    public void setMaxPlayerCount(int maxPlayerCount) {
        this.maxPlayerCount = maxPlayerCount;
        setConfigValue("maxPlayer", maxPlayerCount);
    }

    public String getName() {
        if(name == null) {
            name = getConfigValue("name", String.class);
        }
        return name;
    }

    public String getVersion() {
        if(version == null) {
            version = getConfigValue("version", String.class);
        }
        return version;
    }

    public String getHost() {
        if(host == null) {
            host = getConfigValue("host", String.class);
        }
        return host;
    }

    public String getGroup() {
        if(group == null) {
            group = getConfigValue("group", String.class);
        }
        return group;
    }

    public int getPort() {
        return port;
    }

    public int getMaxMemory() {
        return maxMemory;
    }

    public int getMaxPlayerCount() {
        return maxPlayerCount;
    }
}
