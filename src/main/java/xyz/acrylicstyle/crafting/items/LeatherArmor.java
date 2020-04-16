package xyz.acrylicstyle.crafting.items;

import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

@SuppressWarnings("unused")
public enum LeatherArmor {
    HELMET(Material.LEATHER_HELMET),
    CHESTPLATE(Material.LEATHER_CHESTPLATE),
    LEGGINGS(Material.LEATHER_LEGGINGS),
    BOOTS(Material.LEATHER_BOOTS);

    private final Material material;

    LeatherArmor(Material material) {
        this.material = material;
    }

    public Material getMaterial() {
        return material;
    }

    public ItemStack toItemStack() {
        return new ItemStack(material);
    }

    public ItemStack toItemStack(String displayName) {
        if (displayName == null) return toItemStack();
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName));
        item.setItemMeta(meta);
        return item;
    }

    public ItemStack toItemStack(String displayName, String hexColor) {
        if (hexColor == null) return toItemStack(displayName);
        ItemStack item = new ItemStack(material);
        LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
        assert meta != null;
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', displayName));
        meta.setColor(Color.fromRGB(Integer.parseInt(hexColor, 16)));
        item.setItemMeta(meta);
        return item;
    }
}
