package net.kamilereon.lylac_system.utils;

import com.destroystokyo.paper.ParticleBuilder;

import net.kamilereon.lylac_system.entity.AbstractEntityWrapper;

public class ParticleUtil {
    public static void playParticle(AbstractEntityWrapper entityWrapper, ParticleBuilder builder) {
        builder.spawn();
    }

    public static void playParticle(ParticleBuilder builder) {
        builder.spawn();
    }
}
