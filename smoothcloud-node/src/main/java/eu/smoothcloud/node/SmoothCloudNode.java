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
import eu.smoothcloud.node.console.JLineConsole;
import eu.smoothcloud.util.thread.ThreadBound;
import eu.smoothcloud.util.thread.ThreadManager;

public class SmoothCloudNode {

    public static void main(String[] args) {
        new SmoothCloudNode();
    }

    private final int threads;
    private final LaunchConfiguration launchConfiguration;
    private JLineConsole console;

    private final ThreadManager threadManager;
    private final ThreadBound<CloudChain> cloudChainThreadBound;

    public SmoothCloudNode() {
        this.threads = Runtime.getRuntime().availableProcessors();
        this.launchConfiguration = JsonSerializable.loadFromFile(".", "config.json", LaunchConfiguration.class);
        this.threadManager = new ThreadManager(this.launchConfiguration == null || this.launchConfiguration.getThreads() <= 0 ? this.threads : this.launchConfiguration.getThreads());
        this.threadManager.startTask("console", this::startConsole);
        this.cloudChainThreadBound = new ThreadBound<>(this.threadManager, "cloud-chain", new CloudChain());
        this.cloudChainThreadBound.runOnThread(CloudChain::initialize);
        if (this.threadManager.isTaskRunning("console")) {
            try {
                Thread.sleep(1000);
                this.initializeConsole();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void startConsole() {
        this.console = new JLineConsole();
        this.console.start();
    }

    private void initializeConsole() {
        this.console.print("&aInitialize Cloud");
        if (this.launchConfiguration == null) {
            this.console.switchMode("setup");
            this.console.print("Switched to setup.");
            this.console.print("Which language do you want to use? (en_US, de_DE)");
        }
        this.console.print(this.console.prefix(), false);
    }

    public ThreadBound<CloudChain> getCloudChainThreadBound() {
        return cloudChainThreadBound;
    }

    public ThreadManager getThreadManager() {
        return threadManager;
    }

    public JLineConsole getConsole() {
        return console;
    }

    public LaunchConfiguration getLaunchConfiguration() {
        return launchConfiguration;
    }

    public int getThreads() {
        return threads;
    }
}
