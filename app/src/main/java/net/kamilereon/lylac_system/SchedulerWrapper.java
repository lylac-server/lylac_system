package net.kamilereon.lylac_system;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitTask;

public class SchedulerWrapper {
    private BukkitTask task;

    public SchedulerWrapper() {

    }

    public SchedulerWrapper(BukkitTask task) {
        this.task = task;
    }

    public SchedulerWrapper runEvery(int delay, int tick, Runnable runnable) {
        if (task != null)
            task.cancel();
        task = Bukkit.getScheduler().runTaskTimer(LylacSystem.getLylacSystem(), runnable, delay, tick);
        return this;
    }

    public SchedulerWrapper runLater(int tick, Runnable runnable) {
        if (task != null)
            task.cancel();
        task = Bukkit.getScheduler().runTaskLater(LylacSystem.getLylacSystem(), runnable, tick);
        return this;
    }

    public void cancel() {
        if (task != null)
            task.cancel();
        task = null;
    }
}
