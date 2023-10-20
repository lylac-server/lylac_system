package net.kamilereon.lylac_system.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.EquipmentSlot;

import io.papermc.paper.event.player.PlayerInventorySlotChangeEvent;
import net.kamilereon.lylac_system.LylacSystemRegistry;
import net.kamilereon.lylac_system.entity.AbstractEntityWrapper;
import net.kamilereon.lylac_system.entity.PlayerWrapper;
import net.kamilereon.lylac_system.entity.event.LylacPlayerFClickEvent;
import net.kamilereon.lylac_system.entity.event.LylacPlayerHeldItemChangeEvent;
import net.kamilereon.lylac_system.entity.event.LylacPlayerLeftClickEvent;
import net.kamilereon.lylac_system.entity.event.LylacPlayerRightClickAtEntityEvent;
import net.kamilereon.lylac_system.entity.event.LylacPlayerRightClickEvent;

public class PlayerListener implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        LylacSystemRegistry.getOrCreatePlayerWrapper(player);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        PlayerWrapper playerWrapper = LylacSystemRegistry.getOrCreatePlayerWrapper(player);
        playerWrapper.fin();
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        PlayerWrapper playerWrapper = LylacSystemRegistry.getOrCreatePlayerWrapper(player);
        playerWrapper.respawn();
    }

    @EventHandler
    public void onInteract(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        PlayerWrapper playerWrapper = LylacSystemRegistry.getOrCreatePlayerWrapper(player);
        AbstractEntityWrapper rightClicked = LylacSystemRegistry.getEntityWrapper(event.getRightClicked());

        LylacPlayerRightClickAtEntityEvent lylacPlayerRightClickEvent = new LylacPlayerRightClickAtEntityEvent(
                playerWrapper);
        lylacPlayerRightClickEvent.setRightClickedEntity(rightClicked);
        lylacPlayerRightClickEvent.setHand(event.getHand());

        playerWrapper.onRightClickAtEntity(lylacPlayerRightClickEvent);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        PlayerWrapper playerWrapper = LylacSystemRegistry.getOrCreatePlayerWrapper(player);

        Action action = event.getAction();
        if (action.isLeftClick()) {
            LylacPlayerLeftClickEvent lylacPlayerLeftClickEvent = new LylacPlayerLeftClickEvent(playerWrapper);
            lylacPlayerLeftClickEvent.setInteractionPoint(event.getInteractionPoint());
            lylacPlayerLeftClickEvent.setRightClickedBlock(event.getClickedBlock());
            lylacPlayerLeftClickEvent.setHand(event.getHand());

            playerWrapper.onLeftClick(lylacPlayerLeftClickEvent);
        }
        if (action.isRightClick()) {
            LylacPlayerRightClickEvent lylacPlayerRightClickEvent = new LylacPlayerRightClickEvent(playerWrapper);
            lylacPlayerRightClickEvent.setInteractionPoint(event.getInteractionPoint());
            lylacPlayerRightClickEvent.setRightClickedBlock(event.getClickedBlock());
            lylacPlayerRightClickEvent.setHand(event.getHand());

            playerWrapper.onRightClick(lylacPlayerRightClickEvent);
        }
    }

    @EventHandler
    public void onInteract(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player player))
            return;
        PlayerWrapper playerWrapper = LylacSystemRegistry.getOrCreatePlayerWrapper(player);
        LylacPlayerLeftClickEvent lylacPlayerLeftClickEvent = new LylacPlayerLeftClickEvent(playerWrapper);
        lylacPlayerLeftClickEvent.setInteractionPoint(event.getEntity().getLocation());
        lylacPlayerLeftClickEvent.setHand(EquipmentSlot.HAND);

        playerWrapper.onLeftClick(lylacPlayerLeftClickEvent);
    }

    @EventHandler
    public void onInteract(PlayerSwapHandItemsEvent event) {
        Player player = event.getPlayer();
        PlayerWrapper playerWrapper = LylacSystemRegistry.getOrCreatePlayerWrapper(player);

        LylacPlayerFClickEvent lylacPlayerFClickEvent = new LylacPlayerFClickEvent(playerWrapper);
        lylacPlayerFClickEvent.setMainHandItemStack(event.getMainHandItem());
        lylacPlayerFClickEvent.setOffHandItemStack(event.getOffHandItem());

        playerWrapper.onFClick(lylacPlayerFClickEvent);
        if (lylacPlayerFClickEvent.isCancelled())
            event.setCancelled(true);
    }

    @EventHandler
    public void onSetHeldItem(PlayerItemHeldEvent event) {
        Player player = event.getPlayer();
        PlayerWrapper playerWrapper = LylacSystemRegistry.getOrCreatePlayerWrapper(player);

        playerWrapper.onHeldItemChange(new LylacPlayerHeldItemChangeEvent(playerWrapper));
    }
}
