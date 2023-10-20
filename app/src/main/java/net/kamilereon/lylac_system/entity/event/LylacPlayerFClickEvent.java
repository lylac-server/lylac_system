package net.kamilereon.lylac_system.entity.event;

import org.bukkit.inventory.ItemStack;

import lombok.Getter;
import lombok.Setter;
import net.kamilereon.lylac_system.entity.PlayerWrapper;

@Setter
@Getter
public class LylacPlayerFClickEvent extends LylacEvent {
    private ItemStack mainHandItemStack;
    private ItemStack offHandItemStack;
    private boolean cancelled = false;

    public LylacPlayerFClickEvent(PlayerWrapper entityWrapper) {
        super(entityWrapper);
    }

    @Override
    public PlayerWrapper getEntityWrapper() {
        return (PlayerWrapper) super.getEntityWrapper();
    }

}
