package eu.smoothcloud.api.group;

public interface IGroup {
    void startNewService();
    void stopService(int id);
    void stopServices();
    void restartService(int id);
}
