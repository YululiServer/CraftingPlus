package xyz.acrylicstyle.crafting.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import xyz.acrylicstyle.crafting.Crafting;
import xyz.acrylicstyle.crafting.gui.CraftingGui5x5;

public class Open5x5 implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command cannot be invoked from console.");
            return true;
        }
        CraftingGui5x5 crafting = new CraftingGui5x5();
        Bukkit.getPluginManager().registerEvents(crafting, Crafting.getInstance());
        ((Player) sender).openInventory(crafting.getInventory());
        return true;
    }
}
