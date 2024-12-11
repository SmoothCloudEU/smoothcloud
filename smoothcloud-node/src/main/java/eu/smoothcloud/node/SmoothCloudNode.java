package eu.smoothcloud.node;

import eu.smoothcloud.chain.CloudChain;
import eu.smoothcloud.node.configuration.LaunchConfiguration;
import eu.smoothcloud.node.console.Console;
import eu.smoothcloud.node.util.ThreadBound;
import eu.smoothcloud.node.util.ThreadManager;

import java.util.concurrent.Future;

public class SmoothCloudNode  {

    public static void main(String[] args) {
        new SmoothCloudNode();
    }

    private LaunchConfiguration launchConfiguration;
    private Console console;
    private int threads = Runtime.getRuntime().availableProcessors();

    private CloudChain chain;

    public SmoothCloudNode() {
        ThreadManager manager = new ThreadManager(launchConfiguration.getThreads() > 0 ? launchConfiguration.getThreads() : threads);
        manager.startTask("Console", this::startConsole);
        print(console);

        ThreadBound<CloudChain> cloudChain = new ThreadBound<>(manager, "CloudChain", new CloudChain());
        cloudChain.runOnThread(CloudChain::initialize);
        Future<String> result = cloudChain.callOnThread(CloudChain::getSomething);
        try {
            System.out.println("Result: " + result.get());
        } catch (Exception e) {
            e.fillInStackTrace();
        }
    }

    private void startCloudChain() {
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
