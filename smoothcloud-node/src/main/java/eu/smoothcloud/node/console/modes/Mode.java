package eu.smoothcloud.node.console.modes;

public abstract class Mode {

    public abstract String getName();
    public abstract void handleCommand(String command);
}
