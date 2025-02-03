package eu.smoothcloud.node.console.modes;

import eu.smoothcloud.node.console.JLineConsole;
import eu.smoothcloud.node.template.TemplateManager;
import eu.smoothcloud.util.console.ConsoleColor;

public class GroupSetupMode extends Mode {
    private final JLineConsole console;
    private final TemplateManager templateManager;

    public GroupSetupMode(JLineConsole console, TemplateManager templateManager) {
        this.console = console;
        this.templateManager = templateManager;
    }

    @Override
    public String getName() {
        return "group-setup";
    }

    @Override
    public String getPrefix() {
        return ConsoleColor.apply("&eGroup-Setup&7@&b%hostname &7» ");
    }

    @Override
    public void handleCommand(String command, String[] args) {

    }
}
