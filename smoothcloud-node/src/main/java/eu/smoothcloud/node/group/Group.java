package eu.smoothcloud.node.group;

import eu.smoothcloud.api.group.IGroup;
import eu.smoothcloud.api.service.IService;
import eu.smoothcloud.node.configuration.TomlIgnore;
import eu.smoothcloud.node.configuration.TomlSerializable;
import eu.smoothcloud.worker.Worker;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class Group implements IGroup, TomlSerializable {
    private final String type;
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
    @TomlIgnore
    private Worker cloudWorker;
    @TomlIgnore
    private List<IService> services;

    public Group(GroupType type, String name) {
        this(type, name, null, null, null, null, null, null, 0, 0, 0, 0, 0, false, 0, false, false);
    }

    public Group(GroupType type, String name, String template, String worker, String software, String version, String permission, String java, int priority, int minimumMemory, int maximumMemory, int minimumOnlineServices, int maximumOnlineServices, boolean isStaticServices, int newServicePercent, boolean maintenance, boolean staticServices) {
        this.type = type.name();
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
        this.cloudWorker = new Worker();
        this.services = new ArrayList<>();
    }

    @Override
    public void startNewService() {
        this.cloudWorker.startService();
    }

    @Override
    public void stopService(int id) {
        this.cloudWorker.stopService(id);
    }

    @Override
    public void stopServices() {
        for (IService service : this.services) {
            this.stopService(service.getId());
        }
    }

    @Override
    public void restartService(int id) {
        this.cloudWorker.restartService(id);
    }
}
