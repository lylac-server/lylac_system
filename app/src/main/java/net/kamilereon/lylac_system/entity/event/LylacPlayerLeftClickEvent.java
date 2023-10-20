package net.kamilereon.lylac_system.entity.event;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.inventory.EquipmentSlot;

import lombok.Getter;
import lombok.Setter;
import net.kamilereon.lylac_system.entity.PlayerWrapper;

@Setter
@Getter
public class LylacPlayerLeftClickEvent extends LylacEvent {
    private Block rightClickedBlock;
    private Location interactionPoint;
    private EquipmentSlot hand;

    public LylacPlayerLeftClickEvent(PlayerWrapper entityWrapper) {
        super(entityWrapper);
    }

    @Override
    public PlayerWrapper getEntityWrapper() {
        return (PlayerWrapper) super.getEntityWrapper();
    }

}
