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

package eu.smoothcloud.node.console;

import eu.smoothcloud.node.console.modes.DefaultMode;
import eu.smoothcloud.node.console.modes.Mode;
import eu.smoothcloud.node.console.modes.SetupMode;
import eu.smoothcloud.util.console.ConsoleColor;

import java.util.Scanner;

public class Console {

    private boolean isRunning;
    private boolean isPaused;
    private Mode currentMode;

    public Console() {
        this.isRunning = true;
        this.isPaused = false;
        this.currentMode = new DefaultMode(this);
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);

        while (isRunning) {
            if (isPaused) {
                String input = scanner.nextLine().trim();
                if (input.equalsIgnoreCase("resume")) {
                    isPaused = false;
                }
                continue;
            }

            String input = scanner.nextLine().trim();
            String lowerCaseInput = input.toLowerCase();

            switch (lowerCaseInput) {
                case "exit" -> {
                    isRunning = false;
                }
                case "pause" -> {
                    isPaused = true;
                }
                case "switch" -> {
                    String modeName = input.substring(7).trim();
                    switchMode(modeName);
                }
                default -> {
                    currentMode.handleCommand(lowerCaseInput);
                }
            }
        }
        scanner.close();
    }

    public void switchMode(String modeName) {
        switch (modeName.toLowerCase()) {
            case "setup" -> currentMode = new SetupMode(this);
            case "default" -> currentMode = new DefaultMode(this);
            default -> print("Unknown mode: " + modeName);
        }
    }

    public void print(String message) {
        if (currentMode == null) {
            throw new IllegalStateException("Current mode is not initialized");
        }
        print(message, true);
    }

    public void print(String message, boolean newLine) {
        String prefix = currentMode != null ? currentMode.getName() : "NoMode";
        String coloredMessage = ConsoleColor.apply(prefix + message);
        if (newLine) {
            System.out.println(coloredMessage);
        } else {
            System.out.print(coloredMessage);
        }
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public boolean isRunning() {
        return isRunning;
    }

}
