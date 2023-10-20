package net.kamilereon.lylac_system.entity.instance;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Zombie;
import net.kamilereon.lylac_system.entity.EntityWrapper;

public class Dummy extends EntityWrapper {
    public Dummy(Entity controller) {
        super(controller);
    }

    public static Zombie summon(Location location) {
        Zombie skeleton = (Zombie) location.getWorld().spawnEntity(location, EntityType.ZOMBIE);
        Dummy dummy = new Dummy(skeleton);
        dummy.setName("울트라 짱짱 맨");
        dummy.getEntityAttribute().setHealth(300);
        dummy.getEntityAttribute().setMaxHealth(300);
        dummy.init();
        return skeleton;
    }
}
