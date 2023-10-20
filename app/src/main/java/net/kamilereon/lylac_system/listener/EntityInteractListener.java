package net.kamilereon.lylac_system.listener;

import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import net.kamilereon.lylac_system.LylacSystemRegistry;
import net.kamilereon.lylac_system.damage.DamageSequencer;
import net.kamilereon.lylac_system.entity.AbstractEntityWrapper;

public class EntityInteractListener implements Listener {

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        Entity target = event.getEntity();
        AbstractEntityWrapper targEntityWrapper = LylacSystemRegistry.getEntityWrapper(target);
        if (targEntityWrapper == null)
            return;

        if (target instanceof LivingEntity livingEntity) {
            livingEntity.setMaximumNoDamageTicks(0);
        }
        event.setCancelled(true);
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        Entity damager = event.getDamager();
        Entity target = event.getEntity();
        AbstractEntityWrapper damagerWrapper = LylacSystemRegistry.getEntityWrapper(damager);
        AbstractEntityWrapper targEntityWrapper = LylacSystemRegistry.getEntityWrapper(target);
        if (damagerWrapper == null || targEntityWrapper == null)
            return;

        DamageSequencer.damageTo(damagerWrapper, targEntityWrapper,
                damagerWrapper.getEntityAttribute().getDamageRange());
    }
}
