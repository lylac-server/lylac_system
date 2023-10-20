package net.kamilereon.lylac_system.utils;

import java.util.ListIterator;

import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.jetbrains.annotations.NotNull;

import net.kamilereon.lylac_system.item.ItemNamespacedKey;

public class ItemUtil {
    public static boolean isServerItem(ItemStack itemStack) {
        if (!isValidItem(itemStack))
            return false;

        ItemMeta itemMeta = itemStack.getItemMeta();
        PersistentDataContainer container = itemMeta.getPersistentDataContainer();
        return container.has(ItemNamespacedKey.LYLAC_SERVER_ITEM.getNamespacedKey());
    }

    public static boolean isValidItem(ItemStack itemStack) {
        return itemStack != null && itemStack.getItemMeta() != null;
    }

    /**
     *
     * @param inventory 검색할 인벤토리
     * @param itemStack 찾고자 하는 아이템
     * @return 아이템이 존재하지 않는 경우 -1을 반환
     */
    public static int findExactItemSlot(Inventory inventory, @NotNull ItemStack itemStack) {
        ListIterator<ItemStack> iter = inventory.iterator();
        while (iter.hasNext()) {
            int currentIndex = iter.nextIndex();
            ItemStack item = iter.next();
            if (item == null)
                continue;
            if (item.equals(itemStack)) {
                return currentIndex;
            }
        }
        return -1;
    }
}
