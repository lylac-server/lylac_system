package net.kamilereon.lylac_system.item;

import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import lombok.Getter;
import lombok.Setter;
import net.kamilereon.lylac_system.persistence.UUIDTagType;
import net.kyori.adventure.text.Component;

@Setter
@Getter
public abstract class Item {
    private ItemStack itemStack;
    private ItemMeta itemMeta;
    private UUID uuid = UUID.randomUUID();
    private Material material = Material.STICK;
    private int amount = 1;
    private String name = "item";
    private Type type = Type.SCEPTER;

    public ItemStack build() {
        itemStack = new ItemStack(material, amount);
        itemMeta = itemStack.getItemMeta();
        updatePersistentDataContainer();
        updateCustomName();
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public void updatePersistentDataContainer() {
        storeStringDataToItemStack(ItemNamespacedKey.LYLAC_SERVER_ITEM, "0.0.1v");
        storeUUIDDataToItemSTack(ItemNamespacedKey.ITEM_UNIQUE_ID, uuid);
        storeStringDataToItemStack(ItemNamespacedKey.ITEM_TYPE, type.toString());
        storeStringDataToItemStack(ItemNamespacedKey.ITEM_NAME, name);
    }

    public void updateCustomName() {
        itemMeta.displayName(Component.text(name));
    }

    public void storeStringDataToItemStack(ItemNamespacedKey key, String value) {
        PersistentDataContainer container = getPersistentDataContainer();
        container.set(key.getNamespacedKey(), PersistentDataType.STRING, value);
    }

    public void storeIntegerDataToItemStack(ItemNamespacedKey key, int value) {
        PersistentDataContainer container = getPersistentDataContainer();
        container.set(key.getNamespacedKey(), PersistentDataType.INTEGER, value);
    }

    public void storeUUIDDataToItemSTack(ItemNamespacedKey key, UUID uuid) {
        PersistentDataContainer container = getPersistentDataContainer();
        container.set(key.getNamespacedKey(), new UUIDTagType(), uuid);
    }

    public <T, Z> void storeDataToItemStack(ItemNamespacedKey key, PersistentDataType<T, Z> type, Z value) {
        PersistentDataContainer container = getPersistentDataContainer();
        container.set(key.getNamespacedKey(), type, value);
    }

    private PersistentDataContainer getPersistentDataContainer() {
        return itemMeta.getPersistentDataContainer();
    }
}
