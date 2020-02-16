package xyz.acrylicstyle.crafting.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import util.Collection;
import util.CollectionList;

import java.util.Map;

public class CustomItem {
    private String id;
    private Material material;
    private CustomRecipe recipe;
    private String displayName;
    private Collection<Enchantment, Integer> enchantments;
    private CollectionList<Map.Entry<String, Object>> recipesRaw;
    private int resultAmount;
    private boolean unbreakable;
    private ItemStack leather;
    private boolean requiresPermission;

    public CustomItem(String id,
                      Material material,
                      CustomRecipe recipe,
                      String displayName,
                      Collection<Enchantment, Integer> enchantments,
                      CollectionList<Map.Entry<String, Object>> recipesRaw,
                      int resultAmount,
                      boolean unbreakable,
                      ItemStack leather,
                      boolean requiresPermission) {
        this.id = id;
        this.material = material;
        this.recipe = recipe;
        this.displayName = displayName;
        this.enchantments = enchantments;
        this.recipesRaw = recipesRaw;
        this.resultAmount = resultAmount;
        this.unbreakable = unbreakable;
        this.leather = leather;
        this.requiresPermission = requiresPermission;
    }

    public String getId() {
        return id;
    }

    public Material getMaterial() {
        return material;
    }

    public void setRecipe(CustomRecipe recipe) {
        this.recipe = recipe;
    }

    public CustomRecipe getRecipe() {
        return recipe;
    }

    public Collection<Enchantment, Integer> getEnchantments() {
        return enchantments;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public CollectionList<Map.Entry<String, Object>> getRecipesRaw() {
        return recipesRaw;
    }

    public int getResultAmount() {
        return resultAmount;
    }

    public boolean isRequiresPermission() { return requiresPermission; }

    public ItemStack toItemStack() {
        ItemStack item = this.leather != null ? this.leather : new ItemStack(this.material);
        ItemMeta meta = item.getItemMeta();
        assert meta != null;
        if (this.enchantments != null) this.enchantments.forEach((ench, level) -> meta.addEnchant(ench, level, true));
        meta.setUnbreakable(this.unbreakable);
        if (this.displayName != null) meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', this.displayName));
        item.setItemMeta(meta);
        return item;
    }
}
