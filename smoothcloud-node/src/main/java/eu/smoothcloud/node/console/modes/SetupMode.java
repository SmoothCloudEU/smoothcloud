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
