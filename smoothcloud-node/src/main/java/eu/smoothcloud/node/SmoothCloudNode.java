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

package eu.smoothcloud.node;

import eu.smoothcloud.chain.CloudChain;
import eu.smoothcloud.node.configuration.JsonSerializable;
import eu.smoothcloud.node.configuration.LaunchConfiguration;
import eu.smoothcloud.node.console.Console;
import eu.smoothcloud.util.thread.ThreadBound;
import eu.smoothcloud.util.thread.ThreadManager;

public class SmoothCloudNode {

    public static void main(String[] args) {
        new SmoothCloudNode();
    }

    private final int threads;
    private final LaunchConfiguration launchConfiguration;
    private Console console;

    private final ThreadManager threadManager;
    private final ThreadBound<CloudChain> cloudChainThreadBound;

    public SmoothCloudNode() {
        this.threads = Runtime.getRuntime().availableProcessors();
        this.launchConfiguration = JsonSerializable.loadFromFile("cloud", "config.json", LaunchConfiguration.class);
        this.threadManager = new ThreadManager(this.launchConfiguration == null || this.launchConfiguration.getThreads() <= 0 ? this.threads : this.launchConfiguration.getThreads());
        this.threadManager.startTask("console", this::startConsole);
        this.cloudChainThreadBound = new ThreadBound<>(this.threadManager, "cloudchain", new CloudChain());
        this.cloudChainThreadBound.runOnThread(CloudChain::initialize);
    }

    private void startConsole() {
        System.out.println("Initializing Console");
        this.console = new Console();
        System.out.println("Starting Console");
        this.console.start();
        System.out.println("Console initialized");
        this.print(this.console);
        System.out.println("Printing console output");
        if (this.launchConfiguration == null) {
            System.out.println("Launch configuration is null, switch to setup");
            this.console.switchMode("setup");
            System.out.println("Switched to setup");
        }
        System.out.println("End of line");
    }

    private void print(Console console) {
        console.print("&eInitialize Cloud");
    }
}
