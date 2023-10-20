package net.kamilereon.lylac_system.utils;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

import net.kamilereon.lylac_system.LylacSystemRegistry;
import net.kamilereon.lylac_system.entity.AbstractEntityWrapper;

public class WorldUtil {
    public static Location getRandomPositionInRadius(Location location, double radius) {
        double x = new RangeDouble(-radius, radius).getRandomNumber();
        double yMax = Math.sqrt(radius * radius - x * x);
        double y = new RangeDouble(-yMax, yMax).getRandomNumber();
        double zMax = Math.sqrt(radius * radius - x * x - y * y);
        double z = new RangeDouble(-zMax, zMax).getRandomNumber();
        return location.clone().add(x, y, z);
    }

    public static int nearbyEntitiesForEach(Location location, double radius,
            Consumer<AbstractEntityWrapper> consumer) {
        return nearbyEntitiesForEach(location, radius, consumer, (e) -> true);
    }

    public static int nearbyEntitiesForEach(Location location, double radius,
            Consumer<AbstractEntityWrapper> consumer, Predicate<AbstractEntityWrapper> predicate) {
        int count = 0;
        List<Entity> entities = location.getWorld().getNearbyEntities(location, radius, radius, radius, (e) -> {
            AbstractEntityWrapper abstractEntityWrapper = LylacSystemRegistry.getEntityWrapper(e);
            if (abstractEntityWrapper == null)
                return false;
            return predicate.test(abstractEntityWrapper);
        }).stream().toList();
        for (Entity entity : entities) {
            consumer.accept(LylacSystemRegistry.getEntityWrapper(entity));
            ++count;
        }
        return count;
    }
}
