package eu.smoothcloud.node.group;

import eu.smoothcloud.api.group.IGroup;
import eu.smoothcloud.api.service.IService;
import eu.smoothcloud.node.SmoothCloudNode;
import eu.smoothcloud.node.configuration.TomlIgnore;
import eu.smoothcloud.node.configuration.TomlSerializable;
import eu.smoothcloud.util.process.ProcessType;
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
    private String processType;
    @TomlIgnore
    private Worker cloudWorker;
    @TomlIgnore
    private List<IService> services;

    public Group(GroupType type, String name) {
        this(type, name, null, null, null, null, null, null, 0, 0, 0, 0, 0, false, 0, false, false, ProcessType.PROCESS.name());
    }

    public Group(GroupType type, String name, String template, String worker, String software, String version, String permission, String java, int priority, int minimumMemory, int maximumMemory, int minimumOnlineServices, int maximumOnlineServices, boolean isStaticServices, int newServicePercent, boolean maintenance, boolean staticServices, String processType) {
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
        this.processType = processType;
        try {
            this.cloudWorker = new Worker(ProcessType.valueOf(this.processType.toUpperCase()));
        }  catch (IllegalArgumentException e) {
            SmoothCloudNode.getInstance().getConsole().print(e.getMessage());
        }
        this.services = new ArrayList<>();
    }

    @Override
    public void startNewService() {
        this.cloudWorker.startService(this);
    }

    @Override
    public void stopService(String name) {
        this.cloudWorker.stopService(name);
    }

    @Override
    public void stopServices() {
        for (IService service : this.services) {
            this.stopService(service.getName());
        }
    }

    @Override
    public void restartService(String name) {
        this.cloudWorker.restartService(name);
    }

    @Override
    public String getProcessType() {
        return processType;
    }

    @Override
    public boolean isStaticServices() {
        return staticServices;
    }

    @Override
    public boolean isMaintenance() {
        return maintenance;
    }

    @Override
    public int getNewServicePercent() {
        return newServicePercent;
    }

    @Override
    public int getMaximumOnlineServices() {
        return maximumOnlineServices;
    }

    @Override
    public int getMiniumOnlineServices() {
        return minimumOnlineServices;
    }

    @Override
    public int getMaximumMemory() {
        return maximumMemory;
    }


    @Override
    public int getMiniumMemory() {
        return minimumMemory;
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public String getJava() {
        return java;
    }

    @Override
    public String getPermission() {
        return permission;
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public String getSoftware() {
        return software;
    }

    @Override
    public String getWorker() {
        return worker;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getType() {
        return type;
    }
}
