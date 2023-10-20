package net.kamilereon.lylac_system.item;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class WearableItem extends Item {
    private int weight = 0;
    private Grade grade = Grade.NORMAL;

    @Override
    public void updatePersistentDataContainer() {
        super.updatePersistentDataContainer();
        storeIntegerDataToItemStack(ItemNamespacedKey.ITEM_WEIGHT, weight);
        storeStringDataToItemStack(ItemNamespacedKey.ITEM_GRADE, grade.toString());
    }
}
