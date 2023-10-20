package net.kamilereon.lylac_system.entity;

import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import lombok.Getter;
import lombok.Setter;
import net.kamilereon.lylac_system.SchedulerWrapper;
import net.kamilereon.lylac_system.actionbar.PlayerStatusActionBar;
import net.kamilereon.lylac_system.attribute.PlayerAttribute;
import net.kamilereon.lylac_system.bossbar.HealthFocusBossbar;
import net.kamilereon.lylac_system.entity.event.LylacEntityDamageEntityEvent;
import net.kamilereon.lylac_system.entity.event.LylacHealthChangeEvent;
import net.kamilereon.lylac_system.entity.event.LylacPlayerFClickEvent;
import net.kamilereon.lylac_system.entity.event.LylacPlayerHeldItemChangeEvent;
import net.kamilereon.lylac_system.entity.event.LylacPlayerLeftClickEvent;
import net.kamilereon.lylac_system.entity.event.LylacPlayerRightClickAtEntityEvent;
import net.kamilereon.lylac_system.entity.event.LylacPlayerRightClickEvent;
import net.kamilereon.lylac_system.item.Item;
import net.kamilereon.lylac_system.item.ItemParser;
import net.kamilereon.lylac_system.item.ScepterItem;
import net.kamilereon.lylac_system.mouse_combination.MouseCombinationSequencer;
import net.kamilereon.lylac_system.mouse_combination.Type;
import net.kamilereon.lylac_system.spell.SpellSequencer;
import net.kamilereon.lylac_system.spell.instance.BlackAurora;
import net.kamilereon.lylac_system.spell.instance.FrostBolt;
import net.kamilereon.lylac_system.utils.ItemUtil;

@Getter
@Setter
public class PlayerWrapper extends AbstractEntityWrapper {
    private static final int FOCUS_BOSSBAR_REMAIN_TICK = 60;

    private HealthFocusBossbar currentFocusedBossBar;
    private SpellSequencer spellSequencer = new SpellSequencer(this);
    private MouseCombinationSequencer mouseCombinationSequencer = new MouseCombinationSequencer(this);

    private SchedulerWrapper focusBossBarScheduler = new SchedulerWrapper();
    private SchedulerWrapper actionBarScheduler = new SchedulerWrapper();

    public PlayerWrapper(Entity controller) {
        super(controller);
        setEntityAttribute(new PlayerAttribute());
    }

    @Override
    public void init() {
        spellSequencer.getSpellInventory().setSpell(0, new FrostBolt());
        spellSequencer.getSpellInventory().setSpell(1, new BlackAurora());
        onStartLifeCycle();
    }

    @Override
    public void fin() {
        super.fin();
    }

    @Override
    public Player getController() {
        return (Player) super.getController();
    }

    @Override
    public PlayerAttribute getEntityAttribute() {
        return (PlayerAttribute) super.getEntityAttribute();
    }

    @Override
    public void onDead() {
    }

    public void respawn() {
        getEntityAttribute().setHealth(getEntityAttribute().getMaxHealth());
        getController().setHealth(20);
    }

    @Override
    public void onHealthChanged(LylacHealthChangeEvent event) {
        int maxHealth = getEntityAttribute().getMaxHealth();
        int currentHealth = event.getCurrentHealth();
        double scale = getHealthScale(currentHealth, maxHealth);
        if (scale > 0) {
            getController().setHealth(20 * scale);
        } else
            getController().setHealth(0);
    }

    private double getHealthScale(int cur, int max) {
        return (double) cur / (double) max;
    }

    @Override
    public void onDamagedToEntity(LylacEntityDamageEntityEvent event) {
        if (!(event.getTarget() instanceof PlayerWrapper)) {
            focusBossBar(event.getTarget());
        }
        playEffectOnHit();
    }

    private void playEffectOnHit() {
        getController().playSound(getController(), Sound.ENTITY_ARROW_HIT_PLAYER, 0.5f, 2);
    }

    public void focusBossBar(AbstractEntityWrapper entityWrapper) {
        if (currentFocusedBossBar != null) {
            currentFocusedBossBar.removeViewer(this);
            focusBossBarScheduler.cancel();
        }

        currentFocusedBossBar = entityWrapper.getHealthFocusBossbar();
        currentFocusedBossBar.showBossbar(this);
        focusBossBarScheduler.runLater(FOCUS_BOSSBAR_REMAIN_TICK, () -> {
            currentFocusedBossBar.removeViewer(this);
        });
    }

    @Override
    public void onStartLifeCycle() {
        actionBarScheduler.runEvery(0, 2, () -> {
            PlayerStatusActionBar.execute(this);
        });
    }

    @Override
    public void onStopLifeCycle() {
        actionBarScheduler.cancel();
    }

    public void onRightClickAtEntity(LylacPlayerRightClickAtEntityEvent event) {

    }

    public void onRightClick(LylacPlayerRightClickEvent event) {
        if (event.getHand() == EquipmentSlot.HAND) {
            ItemParser parser = new ItemParser(getController().getInventory().getItemInMainHand());
            Item item = parser.parse();
            if (item instanceof ScepterItem)
                mouseCombinationSequencer.sequence(Type.R);
        }
    }

    public void onLeftClick(LylacPlayerLeftClickEvent event) {
        if (event.getHand() == EquipmentSlot.HAND) {
            ItemParser parser = new ItemParser(getController().getInventory().getItemInMainHand());
            Item item = parser.parse();
            if (item instanceof ScepterItem)
                mouseCombinationSequencer.sequence(Type.L);
        }
    }

    public void onFClick(LylacPlayerFClickEvent event) {
        ItemStack itemStack = getController().getInventory().getItemInMainHand();
        if (ItemUtil.isServerItem(itemStack))
            event.setCancelled(true);
        ItemParser parser = new ItemParser(itemStack);
        Item item = parser.parse();
        if (item instanceof ScepterItem)
            mouseCombinationSequencer.sequence(Type.F);
    }

    public void onHeldItemChange(LylacPlayerHeldItemChangeEvent event) {
        mouseCombinationSequencer.cancelMutableScheduler();
        mouseCombinationSequencer.initializeCombination();
    }
}
