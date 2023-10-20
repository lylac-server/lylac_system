package net.kamilereon.lylac_system.utils;

import org.bukkit.inventory.ItemStack;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;

import net.kamilereon.lylac_system.entity.PlayerWrapper;

public class ProtocolUtil {
    public static void packetServerSetSlot(PlayerWrapper playerWrapper, ItemStack itemStack, int slot) {
        PacketContainer packetContainer = new PacketContainer(PacketType.Play.Server.SET_SLOT);

        packetContainer.getModifier().writeDefaults();
        packetContainer.getIntegers().write(0, 0);
        packetContainer.getIntegers().write(1, 0);
        packetContainer.getIntegers().write(2, slot + 36);
        packetContainer.getItemModifier().write(0, itemStack);

        ProtocolLibrary.getProtocolManager().sendServerPacket(playerWrapper.getController(), packetContainer);
    }
}
