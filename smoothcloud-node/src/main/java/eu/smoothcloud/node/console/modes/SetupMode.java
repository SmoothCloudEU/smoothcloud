package eu.smoothcloud.node.console.modes;

import eu.smoothcloud.node.console.Console;
import eu.smoothcloud.node.console.ConsoleColor;

public class SetupMode extends Mode {

    private Console console;

    public SetupMode(Console console) {
        this.console = console;
    }

    @Override
    public String getName() {
        return "Setup";
    }

    @Override
    public void handleCommand(String command) {
        String prefix = ConsoleColor.apply("&e" + getName() + " &7- ");
    }
}
