package eu.smoothcloud.node;

import eu.smoothcloud.node.console.Console;

public class SmoothCloudNode  {

    public static void main(String[] args) {
        new SmoothCloudNode();
    }

    public SmoothCloudNode() {
        Console console = new Console();
        console.start();

    }

}
