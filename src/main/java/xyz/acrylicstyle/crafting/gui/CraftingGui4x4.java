package xyz.acrylicstyle.crafting.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.acrylicstyle.crafting.Crafting;
import xyz.acrylicstyle.crafting.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class CraftingGui4x4 implements InventoryHolder, Listener {
    private final Inventory inventory;
    private final List<Integer> blockedSlots = new ArrayList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 14, 15, 16, 17, 18, 23, 24, 26, 27, 32, 33, 34, 35, 36, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53));
    private final ItemStack barrier;

    public CraftingGui4x4() {
        ItemStack blackGlass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        barrier = new ItemStack(Material.BARRIER);
        ItemMeta meta2 = barrier.getItemMeta();
        assert meta2 != null;
        meta2.setDisplayName(" ");
        barrier.setItemMeta(meta2);
        inventory = Bukkit.createInventory(this, 54, "Crafting (4x4)");
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
        // 10 (Crafting slot #0)
        // 11 (Crafting slot #1)
        // 12 (Crafting slot #2)
        // 13 (Crafting slot #3)
        inventory.setItem(14, blackGlass);
        inventory.setItem(15, blackGlass);
        inventory.setItem(16, blackGlass);
        inventory.setItem(17, blackGlass);
        // ---
        inventory.setItem(18, blackGlass);
        // 19 (Crafting slot #4)
        // 20 (Crafting slot #5)
        // 21 (Crafting slot #6)
        // 22 (Crafting slot #7)
        inventory.setItem(23, blackGlass);
        inventory.setItem(24, blackGlass);
        inventory.setItem(25, barrier); // (Result item slot)
        inventory.setItem(26, blackGlass);
        // ---
        inventory.setItem(27, blackGlass);
        // 28 (Crafting slot #8)
        // 29 (Crafting slot #9)
        // 30 (Crafting slot #10)
        // 31 (Crafting slot #11)
        inventory.setItem(32, blackGlass);
        inventory.setItem(33, blackGlass);
        inventory.setItem(34, blackGlass);
        inventory.setItem(35, blackGlass);
        // ---
        inventory.setItem(36, blackGlass);
        // 37 (Crafting slot #12)
        // 38 (Crafting slot #13)
        // 39 (Crafting slot #14)
        // 40 (Crafting slot #15)
        inventory.setItem(41, blackGlass);
        inventory.setItem(42, blackGlass);
        inventory.setItem(43, blackGlass);
        inventory.setItem(44, blackGlass);
        // ---
        inventory.setItem(45, blackGlass);
        inventory.setItem(46, blackGlass);
        inventory.setItem(47, blackGlass);
        inventory.setItem(48, blackGlass);
        inventory.setItem(49, blackGlass);
        inventory.setItem(50, blackGlass);
        inventory.setItem(51, blackGlass);
        inventory.setItem(52, blackGlass);
        inventory.setItem(53, blackGlass);
    }

    @Override
    public Inventory getInventory() {
        return this.inventory;
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent e) {
        if (e.getInventory().getHolder() != this) return;
        checkRecipe(e.getInventory());
        e.getInventorySlots().forEach(i -> {
            if (blockedSlots.contains(i)) e.setCancelled(true);
        });
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getInventory().getHolder() != this) return;
        if (e.getClick() == ClickType.DOUBLE_CLICK) {
            e.setCancelled(true);
            return;
        }
        if (e.getClickedInventory() == null || e.getCurrentItem() == null) return;
        if ((e.getClickedInventory().getHolder() == this && blockedSlots.contains(e.getSlot())) || e.getCurrentItem().getType() == Material.BARRIER) {
            e.setCancelled(true);
            return;
        }
        if (e.getAction() == InventoryAction.PLACE_SOME && e.getSlot() == 25) {
            e.setCancelled(true);
            return;
        }
        if (e.getClickedInventory().getHolder() == this && e.getSlot() == 25 && e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.AIR) {
            e.setCancelled(true);
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (!Utils.hasFullInventory(e.getWhoClicked().getInventory())) {
                        e.getWhoClicked().getInventory().addItem(e.getInventory().getItem(25));
                        e.getInventory().setItem(25, barrier);
                    } else return;
                    e.getInventory().setItem(10, new ItemStack(Material.AIR));
                    e.getInventory().setItem(11, new ItemStack(Material.AIR));
                    e.getInventory().setItem(12, new ItemStack(Material.AIR));
                    e.getInventory().setItem(13, new ItemStack(Material.AIR));
                    e.getInventory().setItem(19, new ItemStack(Material.AIR));
                    e.getInventory().setItem(20, new ItemStack(Material.AIR));
                    e.getInventory().setItem(21, new ItemStack(Material.AIR));
                    e.getInventory().setItem(22, new ItemStack(Material.AIR));
                    e.getInventory().setItem(28, new ItemStack(Material.AIR));
                    e.getInventory().setItem(29, new ItemStack(Material.AIR));
                    e.getInventory().setItem(30, new ItemStack(Material.AIR));
                    e.getInventory().setItem(31, new ItemStack(Material.AIR));
                    e.getInventory().setItem(37, new ItemStack(Material.AIR));
                    e.getInventory().setItem(38, new ItemStack(Material.AIR));
                    e.getInventory().setItem(39, new ItemStack(Material.AIR));
                    e.getInventory().setItem(40, new ItemStack(Material.AIR));
                    e.getInventory().setItem(25, barrier);
                }
            }.runTaskLater(Crafting.getInstance(), 2);
            return;
        }
        checkRecipe(e.getInventory());
    }

    private void checkRecipe(Inventory inventory) {
        new BukkitRunnable() {
            @Override
            public void run() {
                ItemStack[] matrix = new ItemStack[16];
                matrix[0] = inventory.getItem(10);
                matrix[1] = inventory.getItem(11);
                matrix[2] = inventory.getItem(12);
                matrix[3] = inventory.getItem(13);
                // ---
                matrix[4] = inventory.getItem(19);
                matrix[5] = inventory.getItem(20);
                matrix[6] = inventory.getItem(21);
                matrix[7] = inventory.getItem(22);
                // ---
                matrix[8] = inventory.getItem(28);
                matrix[9] = inventory.getItem(29);
                matrix[10] = inventory.getItem(30);
                matrix[11] = inventory.getItem(31);
                // ---
                matrix[12] = inventory.getItem(37);
                matrix[13] = inventory.getItem(38);
                matrix[14] = inventory.getItem(39);
                matrix[15] = inventory.getItem(40);
                ItemStack result = Utils.recipes.get("4x4").get(Arrays.toString(matrix));
                if (result == null) {
                    inventory.setItem(25, barrier);
                    return;
                }
                inventory.setItem(25, result);
            }
        }.runTaskLater(Crafting.getInstance(), 10);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        if (e.getInventory().getHolder() != this) return;
        collectItem(e, 10);
        collectItem(e, 11);
        collectItem(e, 12);
        collectItem(e, 13);
        // ---
        collectItem(e, 19);
        collectItem(e, 20);
        collectItem(e, 21);
        collectItem(e, 22);
        // ---
        collectItem(e, 28);
        collectItem(e, 29);
        collectItem(e, 30);
        collectItem(e, 31);
        // ---
        collectItem(e, 37);
        collectItem(e, 38);
        collectItem(e, 39);
        collectItem(e, 40);
    }

    private void collectItem(InventoryCloseEvent e, int index) {
        if (e.getInventory().getItem(index) != null && Objects.requireNonNull(e.getInventory().getItem(index)).getType() != Material.AIR)
            e.getPlayer().getInventory().addItem(e.getInventory().getItem(index));
    }
}