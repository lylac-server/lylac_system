package net.kamilereon.lylac_system.item;

import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import net.kamilereon.lylac_system.utils.ItemUtil;

public class ItemParser {
    private ItemStack itemStack;

    public ItemParser(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    /**
     * 
     * @return 서버 아이템이 아니라면 null 반환
     */
    public Item parse() {
        if (!isValidItem() || !isServerItem())
            return null;

        Item parsed;
        if (isItemType(Type.SCEPTER)) {
            parsed = new ScepterItem();
        } else {
            parsed = new ScepterItem();
        }

        if (parsed instanceof WearableItem wearableItem) {
            wearableItem.setWeight(getIntegerDataFromItemStack(ItemNamespacedKey.ITEM_WEIGHT));
            wearableItem.setGrade(Grade.valueOf(getStringDataFromItemStack(ItemNamespacedKey.ITEM_GRADE)));
        }

        parsed.setMaterial(itemStack.getType());
        parsed.setName(getStringDataFromItemStack(ItemNamespacedKey.ITEM_NAME));
        parsed.setType(Type.valueOf(getStringDataFromItemStack(ItemNamespacedKey.ITEM_TYPE)));

        return parsed;
    }

    private boolean isValidItem() {
        return ItemUtil.isValidItem(itemStack);
    }

    private boolean isServerItem() {
        return ItemUtil.isServerItem(itemStack);
    }

    private boolean isItemType(Type type) {
        Type res = Type.valueOf(getStringDataFromItemStack(ItemNamespacedKey.ITEM_TYPE));
        return res == null ? false : res == type;
    }

    private String getStringDataFromItemStack(ItemNamespacedKey key) {
        PersistentDataContainer container = getPersistentDataContainer();
        return container.get(key.getNamespacedKey(), PersistentDataType.STRING);
    }

    private int getIntegerDataFromItemStack(ItemNamespacedKey key) {
        PersistentDataContainer container = getPersistentDataContainer();
        return container.get(key.getNamespacedKey(), PersistentDataType.INTEGER);
    }

    private PersistentDataContainer getPersistentDataContainer() {
        return itemStack.getItemMeta().getPersistentDataContainer();
    }
}
