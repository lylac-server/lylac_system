package net.kamilereon.lylac_system.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import net.kamilereon.lylac_system.LylacSystemRegistry;
import net.kamilereon.lylac_system.entity.PlayerWrapper;
import net.kamilereon.lylac_system.entity.instance.Dummy;
import net.kamilereon.lylac_system.item.instance.ArcaneStaffBuilder;

public class LylacCommandListener implements CommandExecutor {

    public boolean execute(PlayerWrapper playerWrapper, String args[]) {
        int length = args.length;
        if (length == 0)
            return false;

        String type = args[0];
        switch (type) {
            case "dummy" -> {
                Dummy.summon(playerWrapper.getController().getLocation());
                return true;
            }
            case "staff" -> {
                ArcaneStaffBuilder arcaneStaffBuilder = new ArcaneStaffBuilder();
                playerWrapper.getController().getInventory().addItem(arcaneStaffBuilder.build());
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            @NotNull String[] args) {
        if (sender instanceof Player player) {
            return execute(LylacSystemRegistry.getOrCreatePlayerWrapper(player), args);
        }

        return false;
    }

}
