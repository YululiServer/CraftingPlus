package xyz.acrylicstyle.crafting.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import xyz.acrylicstyle.crafting.items.CustomItem;
import xyz.acrylicstyle.crafting.utils.Utils;
import xyz.acrylicstyle.tomeito_core.utils.TypeUtil;

import java.util.Objects;

public class Give5x5 implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "This command cannot be invoked from console.");
            return true;
        }
        if (!sender.isOp()) {
            sender.sendMessage(ChatColor.RED + "You don't have permission to do this.");
            return true;
        }
        if (args.length < 2) {
            sender.sendMessage(ChatColor.RED + "Usage: /give5x5 <Player> <Item ID> [amount]");
            return true;
        }
        int amount = 1;
        if (args.length >= 3 && !TypeUtil.isInt(args[2])) {
            sender.sendMessage(ChatColor.RED + "有効な数値を指定してください。");
            return true;
        }
        if (args.length >= 3 && TypeUtil.isInt(args[2])) {
            amount = Integer.parseInt(args[2]);
            if (amount < 1) {
                sender.sendMessage(ChatColor.RED + "1以上で数値を指定してください。");
                return true;
            }
        }
        if (Utils.getCustomItemById("5x5", args[1]) == null) {
            sender.sendMessage(ChatColor.RED + "アイテムが見つかりませんでした。");
            return true;
        }
        CustomItem item = Utils.getCustomItemById("5x5", args[1]);
        assert item != null;
        ItemStack itemStack = item.toItemStack().clone();
        itemStack.setAmount(1);
        if (args.length >= 3) itemStack.setAmount(amount);
        sender.sendMessage("プレイヤー に " + ChatColor.AQUA + "[" + Objects.requireNonNull(itemStack.getItemMeta()).getDisplayName() + "]" + ChatColor.RESET + ChatColor.WHITE + " を " + amount + " 個与えました");
        Utils.runPlayer((Player) sender, args[0], player -> player.getInventory().addItem(itemStack));
        return true;
    }
}
