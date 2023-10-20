package net.kamilereon.lylac_system.mutable_data;

import lombok.Getter;
import lombok.Setter;
import net.kamilereon.lylac_system.SchedulerWrapper;

@Getter
@Setter
public class MutableScheduler extends MutableData {
    private SchedulerWrapper schedulerWrapper = new SchedulerWrapper();
    private Runnable onCancel;

    public void cancel() {
        if (onCancel != null)
            onCancel.run();
        schedulerWrapper.cancel();
    }
}
