package com.company.scheduler;


import com.company.model.Task;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.config.TriggerTask;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.ScheduledFuture;

/**
 * Alternative version for DynamicScheduler
 * This one should support everything the basic dynamic scheduler does,
 * and on top of it, you can cancel and re-activate the scheduler.
 */
@Service
public class CancellableScheduler implements SchedulingConfigurer {

    ScheduledTaskRegistrar scheduledTaskRegistrar;

    List<ScheduledFuture> futureTasks;

    //Map<>;


    public CancellableScheduler() {
        futureTasks = new ArrayList<>();
    }

    public TaskScheduler poolScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setThreadNamePrefix("ThreadPoolTaskScheduler");
        scheduler.setPoolSize(1);
        scheduler.initialize();
        return scheduler;
    }


    public void scheduleTask(Task task) {
        if (scheduledTaskRegistrar == null) {
            scheduledTaskRegistrar = new ScheduledTaskRegistrar();
        }
        if (scheduledTaskRegistrar.getScheduler() == null) {
            scheduledTaskRegistrar.setScheduler(poolScheduler());
        }

        futureTasks.add(scheduledTaskRegistrar.getScheduler().schedule(
                () -> {
                    System.out.println("task executed");
                    cancelTask(true, futureTasks.size() - 1);
                }, t -> {
                    Calendar nextExecutionTime = new GregorianCalendar();
                    Date lastActualExecutionTime = t.lastActualExecutionTime();
                    nextExecutionTime.setTime(lastActualExecutionTime != null ? lastActualExecutionTime : new Date());
                    nextExecutionTime.add(Calendar.SECOND, (int) (task.getStartDate().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli() - System.currentTimeMillis()) / 1000);
                    return nextExecutionTime.getTime();
                }));
    }

    // We can have multiple tasks inside the same registrar as we can see below.
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        if (scheduledTaskRegistrar == null) {
            scheduledTaskRegistrar = taskRegistrar;
        }
        if (taskRegistrar.getScheduler() == null) {
            taskRegistrar.setScheduler(poolScheduler());
        }

        List<TriggerTask> triggerTasks = taskRegistrar.getTriggerTaskList();

        for (TriggerTask currentTask : triggerTasks) {
            futureTasks.add(taskRegistrar.getScheduler().schedule(
                    currentTask.getRunnable(), t -> {
                        Calendar nextExecutionTime = new GregorianCalendar();
                        Date lastActualExecutionTime = t.lastActualExecutionTime();
                        nextExecutionTime.setTime(lastActualExecutionTime != null ? lastActualExecutionTime : new Date());
                        nextExecutionTime.add(Calendar.SECOND, 7);
                        return nextExecutionTime.getTime();
                    }));
        }
    }

    /**
     * @param mayInterruptIfRunning {@code true} if the thread executing this task
     *                              should be interrupted; otherwise, in-progress tasks are allowed to complete
     */
    public void cancelTask(boolean mayInterruptIfRunning, int index) {
        futureTasks.get(index).cancel(mayInterruptIfRunning); // set to false if you want the running task to be completed first.
    }

}