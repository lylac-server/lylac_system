package net.kamilereon.lylac_system.entity;

import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;

import com.destroystokyo.paper.ParticleBuilder;

import lombok.Getter;
import lombok.Setter;
import net.kamilereon.lylac_system.bossbar.HealthFocusBossbar;
import net.kamilereon.lylac_system.damage.DamageDTO;
import net.kamilereon.lylac_system.damage.DamageResult;
import net.kamilereon.lylac_system.entity.event.LylacHealthChangeEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

@Setter
@Getter
public class EntityWrapper extends AbstractEntityWrapper {
    private String name = "temp";

    public EntityWrapper(Entity controller) {
        super(controller);
    }

    public void init() {
        super.init();

        setHealthFocusBossbar(new HealthFocusBossbar(getCustomName()));
        showNameToFirstLine();
    }

    private void showNameToFirstLine() {
        getController().customName(getCustomName());
        getController().setCustomNameVisible(true);
    }

    public Component getCustomName() {
        return Component.text("[E]").color(TextColor.color(255, 255, 0))
                .appendSpace()
                .append(Component.text(name).color(TextColor.color(255, 0, 0)));
    }

    private void setDoubleLineWhenEntityHit() {

    }

    public void fin() {
        getController().remove();
        super.fin();
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
        playDamagedEffect();
        return null;
    }

    private void playDamagedEffect() {
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

    @Override
    public void onDead() {
        fin();
    }

    public void onHealthChanged(LylacHealthChangeEvent event) {
        float scale = (float) getEntityAttribute().getHealth() / getEntityAttribute().getMaxHealth();
        getHealthFocusBossbar().update(scale > 0 ? scale : 0);
    }
}
