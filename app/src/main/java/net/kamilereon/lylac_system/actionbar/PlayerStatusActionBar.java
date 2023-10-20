package net.kamilereon.lylac_system.actionbar;

import org.bukkit.entity.Player;

import net.kamilereon.lylac_system.attribute.PlayerAttribute;
import net.kamilereon.lylac_system.entity.PlayerWrapper;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

public class PlayerStatusActionBar {
    public static void execute(PlayerWrapper playerWrapper) {
        Player player = playerWrapper.getController();
        PlayerAttribute playerAttribute = playerWrapper.getEntityAttribute();
        int maxHealth = playerAttribute.getMaxHealth();
        int curHealth = playerAttribute.getHealth();
        int maxMana = playerAttribute.getMaxMana();
        int curMana = playerAttribute.getMana();
        player.sendActionBar(
                Component.text("♥ " + curHealth + " / " + maxHealth).color(TextColor.color(227, 54, 63))
                        .appendSpace().append(
                                Component.text(curMana + " / " + maxMana).color(TextColor.color(54, 141, 227))));
    }
}
