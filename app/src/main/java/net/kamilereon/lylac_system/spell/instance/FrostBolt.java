package net.kamilereon.lylac_system.spell.instance;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;

import com.destroystokyo.paper.ParticleBuilder;

import net.kamilereon.lylac_system.SchedulerWrapper;
import net.kamilereon.lylac_system.damage.DamageRange;
import net.kamilereon.lylac_system.damage.DamageSequencer;
import net.kamilereon.lylac_system.entity.AbstractEntityWrapper;
import net.kamilereon.lylac_system.spell.Spell;
import net.kamilereon.lylac_system.spell.SpellCountWrapper;
import net.kamilereon.lylac_system.utils.ParticleUtil;
import net.kamilereon.lylac_system.utils.RangeInt;
import net.kamilereon.lylac_system.utils.WorldUtil;

public class FrostBolt extends Spell {
    private final DamageRange damageRange = new DamageRange(new RangeInt(10, 30));

    public FrostBolt() {
        super("프로스트 볼트");
    }

    @Override
    public void execute(AbstractEntityWrapper entityWrapper) {
        Entity controller = entityWrapper.getController();
        if (!(controller instanceof LivingEntity livingEntity))
            return;
        Location eyeLoc = livingEntity.getEyeLocation().clone();
        Vector dir = eyeLoc.getDirection().clone().multiply(0.3);

        SpellCountWrapper offset = new SpellCountWrapper();

        SchedulerWrapper schedulerWrapper = new SchedulerWrapper();

        schedulerWrapper.runEvery(0, 1, () -> {
            ParticleBuilder builder = new ParticleBuilder(Particle.ELECTRIC_SPARK).count(1).extra(0)
                    .location(eyeLoc).offset(0, 0, 0).allPlayers();
            eyeLoc.add(dir);
            ParticleUtil.playParticle(entityWrapper, builder);
            eyeLoc.add(dir);
            ParticleUtil.playParticle(entityWrapper, builder);
            eyeLoc.add(dir);
            ParticleUtil.playParticle(entityWrapper, builder);
            offset.increase();

            int detected = WorldUtil.nearbyEntitiesForEach(eyeLoc, 1,
                    (e) -> DamageSequencer.damageTo(entityWrapper, e, damageRange), (e) -> !entityWrapper.equals(e));

            if (detected >= 1 || offset.getValue() >= 20)
                schedulerWrapper.cancel();
        });
    }

    @Override
    public void cancel() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'cancel'");
    }
}
