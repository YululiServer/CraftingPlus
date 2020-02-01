package xyz.acrylicstyle.crafting.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import util.StringCollection;
import xyz.acrylicstyle.crafting.utils.Utils;

public class ReloadCrafting implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Utils.setCustomItems(new StringCollection<>());
        Utils.initializeRecipes("4x4");
        Utils.initializeRecipes("5x5");
        sender.sendMessage(ChatColor.GREEN + "Reloaded recipes.");
        return true;
    }
}
