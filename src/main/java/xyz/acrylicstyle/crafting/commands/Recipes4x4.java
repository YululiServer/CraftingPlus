package xyz.acrylicstyle.crafting.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import xyz.acrylicstyle.crafting.Crafting;
import xyz.acrylicstyle.crafting.gui.RecipesGui4x4;

public class Recipes4x4 implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command cannot be invoked from console.");
            return true;
        }
        RecipesGui4x4 crafting = new RecipesGui4x4();
        Bukkit.getPluginManager().registerEvents(crafting, Crafting.getInstance());
        ((Player) sender).openInventory(crafting.getInventory());
        return true;
    }
}
