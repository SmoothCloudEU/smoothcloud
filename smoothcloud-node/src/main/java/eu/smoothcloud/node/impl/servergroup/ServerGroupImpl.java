package eu.smoothcloud.node.impl.servergroup;

import eu.smoothcloud.api.servergroup.ServerGroup;
import eu.smoothcloud.api.servergroup.template.ServerGroupTemplate;
import eu.smoothcloud.api.wrapper.Wrapper;

public class ServerGroupImpl implements ServerGroup {

    private String name;
    private String version;
    private Wrapper wrapper;
    private ServerGroupTemplate template;
    private int maxOnlineCount;
    private long maxMemory;
    private boolean staticService;

    public ServerGroupImpl(String groupName) {
        this.name = groupName;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public ServerGroupTemplate getTemplateName() {
        return template;
    }

    @Override
    public String getVersion() {
        return version;
    }

    @Override
    public String getWrapperName() {
        return wrapper.getName();
    }

    @Override
    public int getMaxOnlineCount() {
        return maxOnlineCount;
    }

    @Override
    public long getMaxMemory() {
        return maxMemory;
    }

    @Override
    public boolean isStaticServices() {
        return staticService;
    }

    @Override
    public String toHash() {
        return null;
    }

    @Override
    public ServerGroup fromHash() {
        return null;
    }
}
