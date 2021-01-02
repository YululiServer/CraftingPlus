package xyz.acrylicstyle.crafting.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import xyz.acrylicstyle.crafting.Crafting;
import xyz.acrylicstyle.crafting.gui.RecipesGui5x5;

public class Recipes5x5 implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command cannot be invoked from console.");
            return true;
        }
        RecipesGui5x5 crafting = new RecipesGui5x5();
        Bukkit.getPluginManager().registerEvents(crafting, Crafting.getInstance());
        ((Player) sender).openInventory(crafting.getInventory());
        return true;
    }
}
