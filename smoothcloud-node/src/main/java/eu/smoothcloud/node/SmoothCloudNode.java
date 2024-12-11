package eu.smoothcloud.node;

import eu.smoothcloud.node.configuration.LaunchConfiguration;
import eu.smoothcloud.node.console.Console;
import eu.smoothcloud.node.util.ThreadManager;

public class SmoothCloudNode  {

    public static void main(String[] args) {
        new SmoothCloudNode();
    }

    private LaunchConfiguration launchConfiguration;
    private Console console;

    public SmoothCloudNode() {
        ThreadManager manager = new ThreadManager(12);
        System.out.println("DEBUG: Before console.start()");
        manager.startTask("Console", this::startConsole);
        print(console);
    }

    private void startConsole() {
        this.console = new Console();
        console.start();
    }

    private void print(Console console) {
        console.print("&eInitialize Cloud");
    }
}
