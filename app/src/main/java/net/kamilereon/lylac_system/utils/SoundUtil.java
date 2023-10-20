package net.kamilereon.lylac_system.utils;

import org.bukkit.Sound;

import net.kamilereon.lylac_system.entity.PlayerWrapper;

public class SoundUtil {
    public static void playSound(PlayerWrapper playerWrapper, Sound sound, float volume, float pitch) {
        playerWrapper.getController().playSound(playerWrapper.getController(), sound, volume, pitch);
    }
}
