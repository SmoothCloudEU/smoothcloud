/*
 * Copyright (c) 2024 SmoothCloud team & contributors
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package eu.smoothcloud.node.console.modes;

import eu.smoothcloud.node.console.JLineConsole;
import eu.smoothcloud.util.console.ConsoleColor;

public class DefaultMode extends Mode {
    private final JLineConsole console;

    public DefaultMode(JLineConsole console) {
        this.console = console;
    }

    @Override
    public String getName() {
        return "default";
    }

    @Override
    public String getPrefix() {
        return ConsoleColor.apply("[00FFFF-00BFFF]SmoothCloud &7» ");
    }

    @Override
    public void handleCommand(String command, String[] args) {
        switch (command) {
            case "help" -> {
                this.console.print("&7--------------&bHelp&7--------------");
                this.console.print(" &bexit &7- &fShutdown the cloud");
                this.console.print(" &bhelp &7- &fShow this help menu.");
                this.console.print(" &binfo version &7- &fDisplays the current version of the cloud.");
                this.console.print(" &binfo group <group> &7- &fDisplays information about the group. ");
                this.console.print(" &binfo service <service> &7- &fDisplays information about the service. ");
                this.console.print("&7--------------&bHelp&7--------------");
            }
            case "clear" -> {
                this.console.clear();
            }
            default -> this.console.print("[FF3333]The command &b" + command + " [FF3333]can not be executed by the console.");
        }
    }
}
