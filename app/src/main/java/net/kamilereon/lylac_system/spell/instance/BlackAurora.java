package net.kamilereon.lylac_system.spell.instance;

import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;

import com.destroystokyo.paper.ParticleBuilder;

import net.kamilereon.lylac_system.SchedulerWrapper;
import net.kamilereon.lylac_system.entity.AbstractEntityWrapper;
import net.kamilereon.lylac_system.spell.Spell;
import net.kamilereon.lylac_system.spell.SpellCountWrapper;
import net.kamilereon.lylac_system.utils.ParticleUtil;

public class BlackAurora extends Spell {
    private static final int DURATION = 30;
    private SchedulerWrapper counterScheduler = new SchedulerWrapper();
    private SchedulerWrapper spreadScheduler = new SchedulerWrapper();
    private SchedulerWrapper areaScheduler = new SchedulerWrapper();
    private SpellCountWrapper counter = new SpellCountWrapper();

    public BlackAurora() {
        super("TESTTESTTSETSETESTSE");
    }

    @Override
    public void execute(AbstractEntityWrapper entityWrapper) {
        Entity controller = entityWrapper.getController();
        if (!(controller instanceof LivingEntity livingEntity))
            return;

        World world = controller.getWorld();
        RayTraceResult rayTraceResult = livingEntity.rayTraceBlocks(15);
        if (rayTraceResult == null)
            return;
        Location loc = rayTraceResult.getHitPosition().toLocation(world);
        playCounter();
        playSpreadParticle(loc);
        playAreaParticle(loc);
    }

    private void playCounter() {
        counterScheduler.runEvery(0, 1, () -> {
            counter.increase();

            if (counter.getValue() >= DURATION) {
                cancel();
            }
        });
    }

    private void playSpreadParticle(Location location) {
        Vector basis = new Vector(0, 0, 1);
        spreadScheduler.runEvery(0, 5, () -> {
            ParticleBuilder builder = new ParticleBuilder(Particle.SMOKE_NORMAL).count(0).location(location).extra(0.5)
                    .allPlayers();
            for (int i = 0; i < 120; i++) {
                basis.rotateAroundY(Math.toRadians(3));
                builder.offset(basis.getX(), basis.getY(), basis.getZ());
                ParticleUtil.playParticle(builder);
            }
        });
    }

    private void playAreaParticle(Location location) {
        Vector basis = new Vector(0, 0, 3.5);
        areaScheduler.runEvery(0, 3, () -> {
            ParticleBuilder builder = new ParticleBuilder(Particle.SMOKE_LARGE).count(1).offset(0, 0, 0).extra(0)
                    .allPlayers();
            for (int i = 0; i < 120; i++) {
                basis.rotateAroundY(Math.toRadians(3));
                builder.location(location.clone().add(basis));
                ParticleUtil.playParticle(builder);
            }
        });
    }

    @Override
    public void cancel() {
        counterScheduler.cancel();
        spreadScheduler.cancel();
        areaScheduler.cancel();
        counter.initalize();
    }
}
