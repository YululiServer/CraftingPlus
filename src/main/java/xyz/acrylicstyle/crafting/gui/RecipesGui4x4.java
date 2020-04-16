package xyz.acrylicstyle.crafting.gui;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import util.CollectionList;
import xyz.acrylicstyle.crafting.utils.Utils;

import java.util.concurrent.atomic.AtomicInteger;

public class RecipesGui4x4 implements InventoryHolder, Listener {
    private final CollectionList<Inventory> inventoryList = new CollectionList<>();
    private int page = 0;

    public RecipesGui4x4() {
        ItemStack blackGlass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemStack barrier = new ItemStack(Material.BARRIER);
        ItemMeta meta2 = barrier.getItemMeta();
        assert meta2 != null;
        meta2.setDisplayName(" ");
        barrier.setItemMeta(meta2);
        AtomicInteger index = new AtomicInteger(1);
        Utils.recipesRaw.get("4x4").forEach((matrix, result) -> {
            Inventory inventory = Bukkit.createInventory(this, 54, "Recipes (4x4) - Page " + index.getAndIncrement());
            inventory.setItem(0, blackGlass);
            inventory.setItem(1, blackGlass);
            inventory.setItem(2, blackGlass);
            inventory.setItem(3, blackGlass);
            inventory.setItem(4, blackGlass);
            inventory.setItem(5, blackGlass);
            inventory.setItem(6, blackGlass);
            inventory.setItem(7, blackGlass);
            inventory.setItem(8, blackGlass);
            // ---
            inventory.setItem(9, blackGlass);
            inventory.setItem(10, matrix[0]);
            inventory.setItem(11, matrix[1]);
            inventory.setItem(12, matrix[2]);
            inventory.setItem(13, matrix[3]);
            inventory.setItem(14, blackGlass);
            inventory.setItem(15, blackGlass);
            inventory.setItem(16, blackGlass);
            inventory.setItem(17, blackGlass);
            // ---
            inventory.setItem(18, blackGlass);
            inventory.setItem(19, matrix[4]);
            inventory.setItem(20, matrix[5]);
            inventory.setItem(21, matrix[6]);
            inventory.setItem(22, matrix[7]);
            inventory.setItem(23, blackGlass);
            inventory.setItem(24, blackGlass);
            inventory.setItem(25, result);
            inventory.setItem(26, blackGlass);
            // ---
            inventory.setItem(27, blackGlass);
            inventory.setItem(28, matrix[8]);
            inventory.setItem(29, matrix[9]);
            inventory.setItem(30, matrix[10]);
            inventory.setItem(31, matrix[11]);
            inventory.setItem(32, blackGlass);
            inventory.setItem(33, blackGlass);
            inventory.setItem(34, blackGlass);
            inventory.setItem(35, blackGlass);
            // ---
            inventory.setItem(36, blackGlass);
            inventory.setItem(37, matrix[12]);
            inventory.setItem(38, matrix[13]);
            inventory.setItem(39, matrix[14]);
            inventory.setItem(40, matrix[15]);
            inventory.setItem(41, blackGlass);
            inventory.setItem(42, blackGlass);
            inventory.setItem(43, blackGlass);
            inventory.setItem(44, blackGlass);
            inventory.setItem(45, arrowItem(ChatColor.GREEN + "← Back"));
            inventory.setItem(46, blackGlass);
            inventory.setItem(47, blackGlass);
            inventory.setItem(48, blackGlass);
            inventory.setItem(49, blackGlass);
            inventory.setItem(50, blackGlass);
            inventory.setItem(51, blackGlass);
            inventory.setItem(52, blackGlass);
            inventory.setItem(53, arrowItem(ChatColor.GREEN + "Next →"));
            inventoryList.add(inventory);
        });
    }

    private ItemStack arrowItem(String name) {
        ItemStack arrow = new ItemStack(Material.ARROW);
        ItemMeta meta = arrow.getItemMeta();
        assert meta != null;
        meta.setDisplayName(name);
        arrow.setItemMeta(meta);
        return arrow;
    }

    private Inventory getInventory(int page) {
        return this.inventoryList.get(page);
    }

    @Override
    public Inventory getInventory() {
        return getInventory(0);
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getInventory().getHolder() != this) return;
        if (e.getClickedInventory() == null || e.getClickedInventory().getHolder() != this) {
            e.setCancelled(true);
            return;
        }
        if (e.getSlot() == 45) {
            if (page-1 < 0) {
                e.setCancelled(true);
                return;
            }
            e.getWhoClicked().openInventory(getInventory(--page));
            return;
        }
        if (e.getSlot() == 53) {
            if (page+1 >= inventoryList.size()) {
                e.setCancelled(true);
                return;
            }
            e.getWhoClicked().openInventory(getInventory(++page));
            return;
        }
        e.setCancelled(true);
    }
}
