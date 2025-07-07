package eu.smoothcloud.api.group;

public interface IGroup {
    void startNewService();
    void stopService(String name);
    void stopServices();
    void restartService(String name);

    String getType();
    String getName();
    String getWorker();
    String getSoftware();
    String getVersion();
    String getPermission();
    String getJava();
    int getPriority();
    int getMiniumMemory();
    int getMaximumMemory();
    int getMiniumOnlineServices();
    int getMaximumOnlineServices();
    int getNewServicePercent();
    boolean isMaintenance();
    boolean isStaticServices();
    String getProcessType();
}
