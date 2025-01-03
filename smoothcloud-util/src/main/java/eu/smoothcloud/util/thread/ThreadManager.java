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

package eu.smoothcloud.util.thread;

import java.util.Map;
import java.util.concurrent.*;

public class ThreadManager {

    private final Map<String, Future<?>> taskMap;
    private final ThreadPoolExecutor threadPoolExecutor;

    public ThreadManager(int threadPoolSize) {
        this.taskMap = new ConcurrentHashMap<>();
        this.threadPoolExecutor = new ThreadPoolExecutor(threadPoolSize, threadPoolSize, 20, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
    }

    public synchronized void startTask(String taskName, Runnable task) {
        if (taskMap.containsKey(taskName)) {
            return;
        }
        Future<?> future = threadPoolExecutor.submit(task);
        taskMap.put(taskName, future);
    }

    public synchronized <T> Future<T> submitTask(String taskName, Callable<T> task) {
        if (taskMap.containsKey(taskName)) {
            return null;
        }
        Future<T> future = threadPoolExecutor.submit(task);
        taskMap.put(taskName, future);
        return future;
    }

    public synchronized void stopTask(String taskName) {
        Future<?> future = taskMap.get(taskName);
        if (future != null) {
            future.cancel(true);
            taskMap.remove(taskName);
            return;
        }
    }

    public boolean isTaskRunning(String taskName) {
        Future<?> future = taskMap.get(taskName);
        return future != null && !future.isDone() && !future.isCancelled();
    }

    public synchronized void updateMaxThreads(int maxPoolSize) {
        threadPoolExecutor.setMaximumPoolSize(maxPoolSize);
    }

    public int getActiveThreadCount() {
        return threadPoolExecutor.getActiveCount();
    }

    public void shutdown() {
        threadPoolExecutor.shutdownNow();
        taskMap.clear();
    }
}
