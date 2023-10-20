package net.kamilereon.lylac_system.entity.event;

import net.kamilereon.lylac_system.entity.AbstractEntityWrapper;
import net.kamilereon.lylac_system.entity.PlayerWrapper;

public class LylacPlayerHeldItemChangeEvent extends LylacEvent {
    public LylacPlayerHeldItemChangeEvent(PlayerWrapper entityWrapper) {
        super(entityWrapper);
    }

    @Override
    public PlayerWrapper getEntityWrapper() {
        return (PlayerWrapper) super.getEntityWrapper();
    }

}
