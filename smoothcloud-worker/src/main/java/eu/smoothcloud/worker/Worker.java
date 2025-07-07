/*
 * Copyright (c) 2024-2025 SmoothCloud team & contributors
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

package eu.smoothcloud.worker;

import eu.smoothcloud.api.group.IGroup;
import eu.smoothcloud.util.process.ProcessType;
import eu.smoothcloud.worker.process.ProcessBuilder;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class Worker {
    private final ProcessType processType;
    private final List<IGroup> groups;
    private final Map<String, Process> processes; 

    public Worker(ProcessType processType) {
        this.processType = processType;
        this.groups = new ArrayList<>();
        this.processes = new HashMap<>();
    }

    public void addGroup(IGroup group) {
        this.groups.add(group);
    }

    public void removeGroup(IGroup group) {
        this.groups.remove(group);
    }

    public void startService(IGroup group) {
        String name = generateNextName(group);
        try {
            Process process = new ProcessBuilder()
                    .jar("server.jar")
                    .withArgument("--nogui")
                    .workingDirectory(new File("services/" + (group.isStaticServices() ? "static" : "temporary") + "/" + name))
                    .inheritIO(true)
                    .start();
            processes.put(name, process);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void stopService(String name) {

    }

    public void restartService(String name) {

    }

    public String generateNextName(IGroup group) {
        int counter = 1;
        String baseName = group.getName();
        String nextName = baseName + "-" + counter;
        while (processes.containsKey(nextName)) {
            counter++;
            nextName = baseName + "-" + counter;
        }
        return nextName;
    }
}
