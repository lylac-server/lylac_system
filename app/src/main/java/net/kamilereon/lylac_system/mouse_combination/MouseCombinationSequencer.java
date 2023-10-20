package net.kamilereon.lylac_system.mouse_combination;

import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;

import lombok.Getter;
import net.kamilereon.lylac_system.SchedulerWrapper;
import net.kamilereon.lylac_system.entity.PlayerWrapper;
import net.kamilereon.lylac_system.mouse_combination.CombinationResult.CombinationStatus;
import net.kamilereon.lylac_system.mutable_data.MutableScheduler;
import net.kamilereon.lylac_system.utils.ItemUtil;
import net.kamilereon.lylac_system.utils.SoundUtil;
import net.kyori.adventure.text.Component;

@Getter
public class MouseCombinationSequencer {
    public final static String COMBINATION_SEQUENCE_MUTABLE_SCHEDULER = "COMBINATION_SEQUENCE/MUTABLE_SCHDULER";
    private final static int COMBINATION_WAIT_TICK = 20;

    private MouseCombination mouseCombination = new MouseCombination();
    private PlayerWrapper playerWrapper;

    public MouseCombinationSequencer(PlayerWrapper playerWrapper) {
        this.playerWrapper = playerWrapper;
    }

    public void sequence(Type type) {
        CombinationResult result = mouseCombination.pressMouseKey(type);
        CombinationStatus status = result.getCombinationStatus();

        if (isCombinationComplete(status)) {
            cancelMutableScheduler();
            initializeCombination();
            playerWrapper.getSpellSequencer().sequence(result.getCombinationType());

        } else if (isCombinationChanged(status)) {
            int heldItemSlot = playerWrapper.getController().getInventory().getHeldItemSlot();
            cancelMutableScheduler();
            sendCombinationTextComponent(heldItemSlot,
                    getTextComponentAppliedItemStack());
            restoreOriginTextComponentAfter(COMBINATION_WAIT_TICK);
            playCombinationChangeEffect(type);
        }
    }

    private boolean isCombinationComplete(CombinationStatus status) {
        return status == CombinationStatus.COMPLETE;
    }

    private boolean isCombinationChanged(CombinationStatus status) {
        return status == CombinationStatus.CHANGED;
    }

    private boolean isCombinationNotChanged(CombinationStatus status) {
        return status == CombinationStatus.NOT_CHANGED;
    }

    private void sendCombinationTextComponent(int slot, ItemStack convertTo) {
        PacketContainer packetContainer = new PacketContainer(PacketType.Play.Server.SET_SLOT);
        packetContainer.getModifier().writeDefaults();
        packetContainer.getIntegers().write(0, 0);
        packetContainer.getIntegers().write(1, 0);
        packetContainer.getIntegers().write(2, slot + 36);
        packetContainer.getItemModifier().write(0, convertTo);

        ProtocolLibrary.getProtocolManager().sendServerPacket(playerWrapper.getController(), packetContainer);
    }

    private ItemStack getTextComponentAppliedItemStack() {
        Component component = mouseCombination.convertToTextComponent();
        ItemStack cloned = playerWrapper.getController().getInventory().getItemInMainHand().clone();
        ItemMeta itemMeta = cloned.getItemMeta();
        itemMeta.displayName(component);
        cloned.setItemMeta(itemMeta);
        return cloned;
    }

    public void cancelMutableScheduler() {
        MutableScheduler mutableScheduler = (MutableScheduler) playerWrapper
                .getMutableData(COMBINATION_SEQUENCE_MUTABLE_SCHEDULER);
        if (mutableScheduler == null)
            return;
        mutableScheduler.cancel();
    }

    public void initializeCombination() {
        mouseCombination.initializeCombination();
    }

    private void restoreOriginTextComponentAfter(int tick) {
        final ItemStack origin = playerWrapper.getController().getInventory().getItemInMainHand();
        MutableScheduler mutableScheduler = new MutableScheduler();
        mutableScheduler.getSchedulerWrapper().runLater(tick, () -> {
            restoreOriginTextComponent(origin);
            mouseCombination.initializeCombination();
            playerWrapper.removeMutableData(COMBINATION_SEQUENCE_MUTABLE_SCHEDULER);
        });
        mutableScheduler.setOnCancel(() -> {
            restoreOriginTextComponent(origin);
            playerWrapper.removeMutableData(COMBINATION_SEQUENCE_MUTABLE_SCHEDULER);
        });
        playerWrapper.setMutableData(COMBINATION_SEQUENCE_MUTABLE_SCHEDULER, mutableScheduler);
    }

    private void restoreOriginTextComponent(ItemStack origin) {
        int slot = ItemUtil.findExactItemSlot(playerWrapper.getController().getInventory(), origin);
        if (slot == -1)
            return;
        sendCombinationTextComponent(slot, origin);
    }

    private void playCombinationChangeEffect(Type type) {
        if (type == Type.R) {
            SoundUtil.playSound(playerWrapper, Sound.UI_BUTTON_CLICK, 1f, 1.3f);
        } else if (type == Type.L) {
            SoundUtil.playSound(playerWrapper, Sound.UI_BUTTON_CLICK, 1f, 1.1f);
        }
    }
}
