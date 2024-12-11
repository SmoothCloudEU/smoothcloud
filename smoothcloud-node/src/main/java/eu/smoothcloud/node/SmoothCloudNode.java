package eu.smoothcloud.node;

import eu.smoothcloud.chain.CloudChain;
import eu.smoothcloud.node.configuration.LaunchConfiguration;
import eu.smoothcloud.node.console.Console;
import eu.smoothcloud.node.util.ThreadManager;

public class SmoothCloudNode  {

    public static void main(String[] args) {
        new SmoothCloudNode();
    }

    private LaunchConfiguration launchConfiguration;
    private Console console;

    private CloudChain chain;
    private int maxThreads = Runtime.getRuntime().availableProcessors();

    public SmoothCloudNode() {
        ThreadManager manager = new ThreadManager(maxThreads);
        manager.startTask("Console", this::startConsole);
        manager.startTask("CloudChain", this::startChain);
        print(console);
        this.launchConfiguration = new LaunchConfiguration() {};
        launchConfiguration.fromJson("");
        String jsonString = launchConfiguration.toJson();
    }

    private void startChain() {
        this.chain = new CloudChain();
    }

    private void startConsole() {
        this.console = new Console();
        console.start();
    }

    private void print(Console console) {
        console.print("&eInitialize Cloud");
    }
}
