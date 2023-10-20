package net.kamilereon.lylac_system.spell;

import java.util.Map;

import org.bukkit.Sound;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.checkerframework.checker.units.qual.s;

import lombok.Getter;
import net.kamilereon.lylac_system.entity.PlayerWrapper;
import net.kamilereon.lylac_system.mouse_combination.CombinationType;
import net.kamilereon.lylac_system.mouse_combination.MouseCombinationSequencer;
import net.kamilereon.lylac_system.mutable_data.MutableScheduler;
import net.kamilereon.lylac_system.utils.ItemUtil;
import net.kamilereon.lylac_system.utils.ProtocolUtil;
import net.kamilereon.lylac_system.utils.SoundUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.format.TextDecoration.State;

@Getter
public class SpellSequencer {
    private static final Component SPELL_NOT_EXIST = Component.text("할당된 스펠이 없습니다", NamedTextColor.RED)
            .decorations(Map.of(TextDecoration.ITALIC, State.FALSE));
    private static final int SPELL_SEQUENCE_WAIT_TICK = 20;

    private SpellInventory spellInventory = new SpellInventory();
    private PlayerWrapper playerWrapper;

    public SpellSequencer(PlayerWrapper playerWrapper) {
        this.playerWrapper = playerWrapper;
    }

    public void sequence(CombinationType type) {
        Spell spell = spellInventory.getSpell(type);
        if (spell == null) {
            cancelMutableScheduler();
            restoreOriginItemStackAfter(SPELL_SEQUENCE_WAIT_TICK);
            playSpellNotExistEffect();
            return;
        }

        cancelMutableScheduler();
        restoreOriginItemStackAfter(SPELL_SEQUENCE_WAIT_TICK);
        playSpellExecuteEffect(spell);
        spell.execute(playerWrapper);
    }

    private void cancelMutableScheduler() {
        MutableScheduler mutableScheduler = (MutableScheduler) playerWrapper
                .getMutableData(MouseCombinationSequencer.COMBINATION_SEQUENCE_MUTABLE_SCHEDULER);
        if (mutableScheduler == null)
            return;
        mutableScheduler.cancel();
    }

    private void playSpellNotExistEffect() {
        SoundUtil.playSound(playerWrapper, Sound.BLOCK_ANVIL_PLACE, 1f, 1f);
        ProtocolUtil.packetServerSetSlot(playerWrapper, getComponentApplied(SPELL_NOT_EXIST),
                playerWrapper.getController().getInventory().getHeldItemSlot());
    }

    private void restoreOriginItemStackAfter(int tick) {
        final ItemStack origin = playerWrapper.getController().getInventory().getItemInMainHand();
        MutableScheduler mutableScheduler = new MutableScheduler();
        mutableScheduler.getSchedulerWrapper().runLater(tick, () -> {
            restoreOriginItemStack(origin);
            playerWrapper.removeMutableData(MouseCombinationSequencer.COMBINATION_SEQUENCE_MUTABLE_SCHEDULER);
        });
        mutableScheduler.setOnCancel(() -> {
            restoreOriginItemStack(origin);
            playerWrapper.removeMutableData(MouseCombinationSequencer.COMBINATION_SEQUENCE_MUTABLE_SCHEDULER);
        });
        playerWrapper.setMutableData(MouseCombinationSequencer.COMBINATION_SEQUENCE_MUTABLE_SCHEDULER,
                mutableScheduler);
    }

    private ItemStack getComponentApplied(Component component) {
        ItemStack itemStack = playerWrapper.getController().getInventory().getItemInMainHand().clone();
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.displayName(component);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    private void playSpellExecuteEffect(Spell spell) {
        SoundUtil.playSound(playerWrapper, Sound.ENTITY_ARROW_HIT_PLAYER, 1f, 1f);
        ProtocolUtil.packetServerSetSlot(playerWrapper, getComponentApplied(spell.getSpellUsingComponent()),
                playerWrapper.getController().getInventory().getHeldItemSlot());
    }

    private void restoreOriginItemStack(ItemStack origin) {
        int response = ItemUtil.findExactItemSlot(playerWrapper.getController().getInventory(), origin);
        if (response == -1)
            return;
        ProtocolUtil.packetServerSetSlot(playerWrapper, origin, response);
    }
}
