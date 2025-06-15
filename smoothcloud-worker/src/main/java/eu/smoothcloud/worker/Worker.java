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
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class Worker {
    private final List<IGroup> groups;

    public Worker() {
        this.groups = new ArrayList<>();
    }

    public void addGroup(IGroup group) {
        this.groups.add(group);
    }

    public void removeGroup(IGroup group) {
        this.groups.remove(group);
    }

    public void startService() {

    }

    public void stopService(int id) {

    }

    public void restartService(int id) {

    }
}
