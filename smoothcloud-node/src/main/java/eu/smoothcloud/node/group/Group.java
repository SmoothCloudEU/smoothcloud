package eu.smoothcloud.node.group;

import eu.smoothcloud.node.configuration.TomlSerializable;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Group implements TomlSerializable {
    private final GroupType type;
    private final String name;
    private String template;
    private String worker;
    private String software;
    private String version;
    private String permission;
    private String java;
    private int priority;
    private int minimumMemory;
    private int maximumMemory;
    private int minimumOnlineServices;
    private int maximumOnlineServices;
    private int newServicePercent;
    private boolean maintenance;
    private boolean staticServices;

    public Group(GroupType type, String name) {
        this.type = type;
        this.name = name;
    }

    public Group(GroupType type, String name, String template, String worker, String software, String version, String permission, String java, int priority, int minimumMemory, int maximumMemory, int minimumOnlineServices, int maximumOnlineServices, boolean isStaticServices, int newServicePercent, boolean maintenance, boolean staticServices) {
        this.type = type;
        this.name = name;
        this.template = template;
        this.worker = worker;
        this.software = software;
        this.version = version;
        this.permission = permission;
        this.java = java;
        this.priority = priority;
        this.minimumMemory = minimumMemory;
        this.maximumMemory = maximumMemory;
        this.minimumOnlineServices = minimumOnlineServices;
        this.maximumOnlineServices = maximumOnlineServices;
        this.newServicePercent = newServicePercent;
        this.maintenance = maintenance;
        this.staticServices = staticServices;
    }

    public void startNewService() {}
}
