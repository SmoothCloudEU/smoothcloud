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

package eu.smoothcloud.thread;

import eu.smoothcloud.node.console.ConsoleColor;

import java.util.Map;
import java.util.concurrent.*;

public class ThreadManager {

    private static final String PREFIX = ConsoleColor.apply("&eThreadManager &7» ");

    private final Map<String, Future<?>> taskMap;
    private final ExecutorService executorService;

    public ThreadManager(int threadPoolSize) {
        this.taskMap = new ConcurrentHashMap<>();
        this.executorService = Executors.newFixedThreadPool(threadPoolSize);
    }

    public void startTask(String taskName, Runnable task) {
        if (taskMap.containsKey(taskName)) {
            System.out.println(ConsoleColor.apply(PREFIX + "&eTask &b" + taskName + " &eis already running."));
            return;
        }
        Future<?> future = executorService.submit(task);
        taskMap.put(taskName, future);
        System.out.println(ConsoleColor.apply(PREFIX + "&eTask &b" + taskName + " &estarted."));
    }

    public <T> Future<T> submitTask(String taskName, Callable<T> task) {
        if (taskMap.containsKey(taskName)) {
            System.out.println(ConsoleColor.apply(PREFIX + "&eTask &b" + taskName + " &eis already running."));
            return null;
        }
        Future<T> future = executorService.submit(task);
        taskMap.put(taskName, future);
        System.out.println(ConsoleColor.apply(PREFIX + "&eTask &b" + taskName + " &estarted."));
        return future;
    }

    public void stopTask(String taskName) {
        Future<?> future = taskMap.get(taskName);
        if (future != null) {
            future.cancel(true);
            taskMap.remove(taskName);
            System.out.println(ConsoleColor.apply(PREFIX + "&eTask &b" + taskName + " &cstopped."));
            return;
        }
        System.out.println(ConsoleColor.apply(PREFIX + "&eNo task found with name: &b" + taskName));
    }

    public boolean isTaskRunning(String taskName) {
        Future<?> future = taskMap.get(taskName);
        return future != null && !future.isDone() && !future.isCancelled();
    }

    public void shutdown() {
        executorService.shutdownNow();
        taskMap.clear();
        System.out.println(ConsoleColor.apply(PREFIX + "&cshut down."));
    }

    public void listTasks() {
        System.out.println("Running tasks:");
        for (String taskName : taskMap.keySet()) {
            System.out.println(ConsoleColor.apply(PREFIX + "- " + taskName + ""));
        }
    }
}
