package net.kamilereon.lylac_system.entity.event;

import org.bukkit.inventory.EquipmentSlot;

import lombok.Getter;
import lombok.Setter;
import net.kamilereon.lylac_system.entity.AbstractEntityWrapper;
import net.kamilereon.lylac_system.entity.PlayerWrapper;

@Getter
@Setter
public class LylacPlayerRightClickAtEntityEvent extends LylacEvent {
    private AbstractEntityWrapper rightClickedEntity;
    private EquipmentSlot hand;

    public LylacPlayerRightClickAtEntityEvent(PlayerWrapper entityWrapper) {
        super(entityWrapper);
    }

    @Override
    public PlayerWrapper getEntityWrapper() {
        return (PlayerWrapper) super.getEntityWrapper();
    }

}
