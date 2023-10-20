package net.kamilereon.lylac_system.item;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ScepterItem extends WearableItem {
    private int minDamage = 2;
    private int maxDamage = 5;

    public ScepterItem() {
        setType(Type.SCEPTER);
    }

    @Override
    public void updatePersistentDataContainer() {
        super.updatePersistentDataContainer();
        storeIntegerDataToItemStack(ItemNamespacedKey.ITEM_DAMAGE_MIN, getMinDamage());
        storeIntegerDataToItemStack(ItemNamespacedKey.ITEM_DAMAGE_MAX, getMaxDamage());
    }
}
