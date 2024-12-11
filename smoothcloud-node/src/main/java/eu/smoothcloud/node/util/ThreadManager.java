package eu.smoothcloud.node.util;

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
        if(taskMap.containsKey(taskName)) {
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
