package net.kamilereon.lylac_system.entity.event;

import lombok.Getter;
import lombok.Setter;
import net.kamilereon.lylac_system.entity.AbstractEntityWrapper;

@Getter
@Setter
public class LylacHealthChangeEvent extends LylacEvent {
    private int prevHealth;
    private int currentHealth;

    public LylacHealthChangeEvent(int prevHealth, int currentHealth, AbstractEntityWrapper entityWrapper) {
        super(entityWrapper);
        this.prevHealth = prevHealth;
        this.currentHealth = currentHealth;
    }

}
