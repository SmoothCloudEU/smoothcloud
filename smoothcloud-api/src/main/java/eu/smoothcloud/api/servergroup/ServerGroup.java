package eu.smoothcloud.api.servergroup;

import eu.smoothcloud.api.servergroup.template.ServerGroupTemplate;

public interface ServerGroup {

    String getName();
    ServerGroupTemplate getTemplateName();
    String getVersion();
    String getWrapperName();

    int getMaxOnlineCount();
    long getMaxMemory();

    boolean isStaticServices();

    String toHash();
    ServerGroup fromHash();
}
