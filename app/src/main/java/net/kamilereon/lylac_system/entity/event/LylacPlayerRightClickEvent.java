package net.kamilereon.lylac_system.entity.event;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.inventory.EquipmentSlot;

import lombok.Getter;
import lombok.Setter;
import net.kamilereon.lylac_system.entity.AbstractEntityWrapper;
import net.kamilereon.lylac_system.entity.PlayerWrapper;

@Setter
@Getter
public class LylacPlayerRightClickEvent extends LylacEvent {
    private AbstractEntityWrapper rightClickedEntity;
    private Block rightClickedBlock;
    private Location interactionPoint;
    private EquipmentSlot hand;

    public LylacPlayerRightClickEvent(PlayerWrapper entityWrapper) {
        super(entityWrapper);
    }

    @Override
    public PlayerWrapper getEntityWrapper() {
        return (PlayerWrapper) super.getEntityWrapper();
    }

}
