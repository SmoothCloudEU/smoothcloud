package eu.smoothcloud.node.console.modes;

import eu.smoothcloud.node.console.Console;
import eu.smoothcloud.node.console.ConsoleColor;

public class DefaultMode extends Mode {

    private Console console;

    public DefaultMode(Console console) {
        this.console = console;
    }

    @Override
    public String getName() {
        return ConsoleColor.apply("[00FFFF-00BFFF]SmoothCloud &7»");
    }

    @Override
    public void handleCommand(String command) {

    }
}
