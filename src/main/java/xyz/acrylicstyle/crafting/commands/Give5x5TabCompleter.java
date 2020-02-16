package xyz.acrylicstyle.crafting.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import util.ICollectionList;
import xyz.acrylicstyle.crafting.items.CustomItem;
import xyz.acrylicstyle.crafting.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Give5x5TabCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        List<String> emptyList = new ArrayList<>();
        if (args.length == 0 || args.length == 1) return Utils.getOnlinePlayers().map(Player::getName).concat(ICollectionList.asList(Arrays.asList("@a", "@p", "@s", "@r")));
        if (args.length == 2) return Utils.getCustomItemsCached("5x5").map(CustomItem::getId).filter(a -> args[0].startsWith(a));
        if (args.length == 3) return Utils.create64List();
        return emptyList;
    }
}
