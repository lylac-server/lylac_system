package net.kamilereon.lylac_system.entity;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import com.destroystokyo.paper.ParticleBuilder;

import lombok.Getter;
import lombok.Setter;
import net.kamilereon.lylac_system.LylacSystemRegistry;
import net.kamilereon.lylac_system.attribute.EntityAttribute;
import net.kamilereon.lylac_system.bossbar.HealthFocusBossbar;
import net.kamilereon.lylac_system.damage.DamageDTO;
import net.kamilereon.lylac_system.damage.DamageResult;
import net.kamilereon.lylac_system.entity.event.LylacEntityDamageEntityEvent;
import net.kamilereon.lylac_system.entity.event.LylacHealthChangeEvent;
import net.kamilereon.lylac_system.mutable_data.MutableData;

@Setter
@Getter
public abstract class AbstractEntityWrapper {
    private ConcurrentHashMap<String, MutableData> mutableDataStore = new ConcurrentHashMap<>();
    private EntityAttribute entityAttribute = new EntityAttribute();
    private HealthFocusBossbar healthFocusBossbar;
    private Entity controller;

    public AbstractEntityWrapper(Entity controller) {
        this.controller = controller;
    }

    public void init() {
        LylacSystemRegistry.registerWrapperEntity(this);
        onStartLifeCycle();
    }

    public void fin() {
        onStopLifeCycle();
        LylacSystemRegistry.removeWrapperEntity(getController());
    }

    public Entity getController() {
        return controller;
    }

    public EntityAttribute getEntityAttribute() {
        return entityAttribute;
    }

    public boolean hasMutableData(String key) {
        return mutableDataStore.containsKey(key);
    }

    public MutableData getMutableData(String key) {
        return mutableDataStore.get(key);
    }

    public MutableData setMutableData(String key, MutableData data) {
        return mutableDataStore.put(key, data);
    }

    public void removeMutableData(String key) {
        mutableDataStore.remove(key);
    }

    public DamageResult damage(DamageDTO damage, AbstractEntityWrapper source) {
        damage(damage);
        return null;
    }

    public DamageResult damage(DamageDTO damage) {
        int value = damage.getDamage();
        int currentHealth = getEntityAttribute().getHealth();
        int nextHealth = getEntityAttribute().getHealth() - value;
        if (nextHealth < 0) {
            onDead();
            onHealthChanged(new LylacHealthChangeEvent(currentHealth, nextHealth, this));
        } else {
            getEntityAttribute().setHealth(nextHealth);
            onHealthChanged(new LylacHealthChangeEvent(currentHealth, nextHealth, this));
        }
        playEffectWhenDamaged();
        return null;
    }

    protected void playEffectWhenDamaged() {
        if (getController() instanceof LivingEntity livingEntity) {
            livingEntity.playHurtAnimation(1);
        }

        ParticleBuilder particleBuilder = new ParticleBuilder(Particle.BLOCK_CRACK);
        particleBuilder.location(getController().getBoundingBox().getCenter().toLocation(getController().getWorld()))
                .offset(0.3, 0.3, 0.3)
                .count(10)
                .extra(0)
                .data(Material.REDSTONE_BLOCK.createBlockData()).allPlayers().spawn();
    }

    public void onDead() {
        fin();
    }

    public void onDamagedToEntity(LylacEntityDamageEntityEvent event) {
    }

    public void onHealthChanged(LylacHealthChangeEvent event) {
    }

    public void onStartLifeCycle() {
    }

    public void onStopLifeCycle() {
    }
}
