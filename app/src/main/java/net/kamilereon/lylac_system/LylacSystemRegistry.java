package net.kamilereon.lylac_system;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import net.kamilereon.lylac_system.entity.AbstractEntityWrapper;
import net.kamilereon.lylac_system.entity.PlayerWrapper;

public class LylacSystemRegistry {
    private static final ConcurrentHashMap<Entity, AbstractEntityWrapper> entityRegistries = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<Player, PlayerWrapper> playerRegistries = new ConcurrentHashMap<>();

    public static Collection<PlayerWrapper> getPlayerWrappers() {
        return playerRegistries.values();
    }

    public static PlayerWrapper getOrCreatePlayerWrapper(Player player) {
        PlayerWrapper playerWrapper = playerRegistries.get(player);
        if (playerWrapper != null)
            return playerWrapper;
        playerWrapper = new PlayerWrapper(player);
        playerWrapper.init();
        playerRegistries.put(player, playerWrapper);
        return playerWrapper;
    }

    public static void removePlayerWrapper(Player player) {
        playerRegistries.remove(player);
    }

    public static AbstractEntityWrapper getEntityWrapper(Entity entity) {
        if (entityRegistries.containsKey(entity))
            return entityRegistries.get(entity);
        if (playerRegistries.containsKey(entity))
            return playerRegistries.get(entity);
        return null;
    }

    public static void registerWrapperEntity(AbstractEntityWrapper entityWrapper) {
        entityRegistries.put(entityWrapper.getController(), entityWrapper);
    }

    public static void removeWrapperEntity(Entity entity) {
        entityRegistries.remove(entity);
    }
}
