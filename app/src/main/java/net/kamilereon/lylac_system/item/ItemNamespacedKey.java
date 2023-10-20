package net.kamilereon.lylac_system.item;

import org.bukkit.NamespacedKey;

import net.kamilereon.lylac_system.LylacSystem;

public enum ItemNamespacedKey {
    LYLAC_SERVER_ITEM,
    ITEM_UNIQUE_ID,
    ITEM_TYPE,
    ITEM_NAME,
    ITEM_WEIGHT,
    ITEM_GRADE,
    ITEM_DAMAGE_MIN,
    ITEM_DAMAGE_MAX,
    ;

    public NamespacedKey getNamespacedKey() {
        return new NamespacedKey(LylacSystem.getLylacSystem(), toString());
    }
}
