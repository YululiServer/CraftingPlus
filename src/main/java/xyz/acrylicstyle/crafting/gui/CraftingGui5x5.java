package xyz.acrylicstyle.crafting.gui;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import xyz.acrylicstyle.crafting.Crafting;
import xyz.acrylicstyle.crafting.items.CustomItem;
import xyz.acrylicstyle.crafting.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class CraftingGui5x5 implements InventoryHolder, Listener {
    private final Inventory inventory;
    private final List<Integer> blockedSlots = new ArrayList<>(Arrays.asList(0, 6, 7, 8, 9, 15, 16, 17, 18, 24, 26, 27, 33, 34, 35, 36, 42, 43, 44));
    private final ItemStack barrier;

    public CraftingGui5x5() {
        ItemStack blackGlass = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        barrier = new ItemStack(Material.BARRIER);
        ItemMeta meta2 = barrier.getItemMeta();
        assert meta2 != null;
        meta2.setDisplayName(" ");
        barrier.setItemMeta(meta2);
        inventory = Bukkit.createInventory(this, 45, "Crafting (5x5)");
        inventory.setItem(0, blackGlass);
        // 1 (Crafting slot #0)
        // 2 (Crafting slot #1)
        // 3 (Crafting slot #2)
        // 4 (Crafting slot #3)
        // 5 (Crafting slot #4)
        inventory.setItem(6, blackGlass);
        inventory.setItem(7, blackGlass);
        inventory.setItem(8, blackGlass);
        // ---
        inventory.setItem(9, blackGlass);
        // 10 (Crafting slot #5)
        // 11 (Crafting slot #6)
        // 12 (Crafting slot #7)
        // 13 (Crafting slot #8)
        // 14 (Crafting slot #9)
        inventory.setItem(15, blackGlass);
        inventory.setItem(16, blackGlass);
        inventory.setItem(17, blackGlass);
        // ---
        inventory.setItem(18, blackGlass);
        // 19 (Crafting slot #10)
        // 20 (Crafting slot #11)
        // 21 (Crafting slot #12)
        // 22 (Crafting slot #13)
        // 23 (Crafting slot #14)
        inventory.setItem(24, blackGlass);
        inventory.setItem(25, barrier); // (Result item slot)
        inventory.setItem(26, blackGlass);
        // ---
        inventory.setItem(27, blackGlass);
        // 28 (Crafting slot #15)
        // 29 (Crafting slot #16)
        // 30 (Crafting slot #17)
        // 31 (Crafting slot #18)
        // 32 (Crafting slot #19)
        inventory.setItem(33, blackGlass);
        inventory.setItem(34, blackGlass);
        inventory.setItem(35, blackGlass);
        // ---
        inventory.setItem(36, blackGlass);
        // 37 (Crafting slot #20)
        // 38 (Crafting slot #21)
        // 39 (Crafting slot #22)
        // 40 (Crafting slot #23)
        // 41 (Crafting slot #24)
        inventory.setItem(42, blackGlass);
        inventory.setItem(43, blackGlass);
        inventory.setItem(44, blackGlass);
    }

    @Override
    public @NotNull Inventory getInventory() {
        return this.inventory;
    }

    @EventHandler
    public void onInventoryDrag(InventoryDragEvent e) {
        if (e.getInventory().getHolder() != this) return;
        checkRecipe(e.getWhoClicked(), e.getInventory());
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
        if (e.getClickedInventory().getHolder() == this && e.getSlot() != 25) checkRecipe(e.getWhoClicked(), e.getInventory());
        if (e.getClickedInventory().getHolder() == this && e.getSlot() == 25 && e.getCurrentItem() != null && e.getCurrentItem().getType() != Material.AIR) {
            e.setCancelled(true);
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (!checkRecipeCommon(inventory)) return;
                    if (!Utils.hasFullInventory(e.getWhoClicked().getInventory())) {
                        e.getWhoClicked().getInventory().addItem(Objects.requireNonNull(e.getInventory().getItem(25)));
                        e.getInventory().setItem(25, barrier);
                    } else return;
                    e.getInventory().setItem(1, new ItemStack(Material.AIR));
                    e.getInventory().setItem(2, new ItemStack(Material.AIR));
                    e.getInventory().setItem(3, new ItemStack(Material.AIR));
                    e.getInventory().setItem(4, new ItemStack(Material.AIR));
                    e.getInventory().setItem(5, new ItemStack(Material.AIR));
                    // ---
                    e.getInventory().setItem(10, new ItemStack(Material.AIR));
                    e.getInventory().setItem(11, new ItemStack(Material.AIR));
                    e.getInventory().setItem(12, new ItemStack(Material.AIR));
                    e.getInventory().setItem(13, new ItemStack(Material.AIR));
                    e.getInventory().setItem(14, new ItemStack(Material.AIR));
                    // ---
                    e.getInventory().setItem(19, new ItemStack(Material.AIR));
                    e.getInventory().setItem(20, new ItemStack(Material.AIR));
                    e.getInventory().setItem(21, new ItemStack(Material.AIR));
                    e.getInventory().setItem(22, new ItemStack(Material.AIR));
                    e.getInventory().setItem(23, new ItemStack(Material.AIR));
                    // ---
                    e.getInventory().setItem(28, new ItemStack(Material.AIR));
                    e.getInventory().setItem(29, new ItemStack(Material.AIR));
                    e.getInventory().setItem(30, new ItemStack(Material.AIR));
                    e.getInventory().setItem(31, new ItemStack(Material.AIR));
                    e.getInventory().setItem(32, new ItemStack(Material.AIR));
                    // ---
                    e.getInventory().setItem(37, new ItemStack(Material.AIR));
                    e.getInventory().setItem(38, new ItemStack(Material.AIR));
                    e.getInventory().setItem(39, new ItemStack(Material.AIR));
                    e.getInventory().setItem(40, new ItemStack(Material.AIR));
                    e.getInventory().setItem(41, new ItemStack(Material.AIR));

                    e.getInventory().setItem(25, barrier);
                }
            }.runTaskLater(Crafting.getInstance(), 2);
        }
    }

    private ItemStack[] getMatrix(Inventory inventory) {
        ItemStack[] matrix = new ItemStack[25];
        matrix[0] = inventory.getItem(1);
        matrix[1] = inventory.getItem(2);
        matrix[2] = inventory.getItem(3);
        matrix[3] = inventory.getItem(4);
        matrix[4] = inventory.getItem(5);
        // ---
        matrix[5] = inventory.getItem(10);
        matrix[6] = inventory.getItem(11);
        matrix[7] = inventory.getItem(12);
        matrix[8] = inventory.getItem(13);
        matrix[9] = inventory.getItem(14);
        // ---
        matrix[10] = inventory.getItem(19);
        matrix[11] = inventory.getItem(20);
        matrix[12] = inventory.getItem(21);
        matrix[13] = inventory.getItem(22);
        matrix[14] = inventory.getItem(23);
        // ---
        matrix[15] = inventory.getItem(28);
        matrix[16] = inventory.getItem(29);
        matrix[17] = inventory.getItem(30);
        matrix[18] = inventory.getItem(31);
        matrix[19] = inventory.getItem(32);
        // ---
        matrix[20] = inventory.getItem(37);
        matrix[21] = inventory.getItem(38);
        matrix[22] = inventory.getItem(39);
        matrix[23] = inventory.getItem(40);
        matrix[24] = inventory.getItem(41);
        return matrix;
    }

    private void checkRecipe(HumanEntity entity, Inventory inventory) {
        new BukkitRunnable() {
            @Override
            public void run() {
                checkRecipeCommon(inventory);
                ((Player) entity).updateInventory();
            }
        }.runTaskLater(Crafting.getInstance(), 2);
    }

    private boolean checkRecipeCommon(Inventory inventory) {
        ItemStack[] matrix = getMatrix(inventory);
        ItemStack result = Utils.recipes.get("5x5").get(Arrays.toString(matrix));
        CustomItem item = Utils.customItem.get("5x5").get(Arrays.toString(matrix));
        if (item == null) {
            inventory.setItem(25, barrier);
            return false;
        }
        if (item.isRequiresPermission()) {
            if (!inventory.getViewers().get(0).hasPermission("craftingplus.recipes." + item.getId())) {
                inventory.setItem(25, barrier);
                return false;
            }
        }
        if (result == null) {
            inventory.setItem(25, barrier);
            return false;
        }
        inventory.setItem(25, result);
        return true;
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        if (e.getInventory().getHolder() != this) return;
        new BukkitRunnable() {
            @Override
            public void run() {
                collectItem(e, 1);
                collectItem(e, 2);
                collectItem(e, 3);
                collectItem(e, 4);
                collectItem(e, 5);
                // ---
                collectItem(e, 10);
                collectItem(e, 11);
                collectItem(e, 12);
                collectItem(e, 13);
                collectItem(e, 14);
                // ---
                collectItem(e, 19);
                collectItem(e, 20);
                collectItem(e, 21);
                collectItem(e, 22);
                collectItem(e, 23);
                // ---
                collectItem(e, 28);
                collectItem(e, 29);
                collectItem(e, 30);
                collectItem(e, 31);
                collectItem(e, 32);
                // ---
                collectItem(e, 37);
                collectItem(e, 38);
                collectItem(e, 39);
                collectItem(e, 40);
                collectItem(e, 41);
                e.getPlayer().getInventory().remove(Material.BARRIER);
            }
        }.runTaskLater(Crafting.getInstance(), 3);
    }

    private void collectItem(InventoryCloseEvent e, int index) {
        if (e.getInventory().getItem(index) != null && Objects.requireNonNull(e.getInventory().getItem(index)).getType() != Material.AIR)
            e.getPlayer().getInventory().addItem(Objects.requireNonNull(e.getInventory().getItem(index)));
    }
}