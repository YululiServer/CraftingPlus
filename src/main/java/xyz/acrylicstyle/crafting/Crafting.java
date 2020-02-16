package xyz.acrylicstyle.crafting;

import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.acrylicstyle.crafting.commands.*;
import xyz.acrylicstyle.crafting.utils.Utils;
import xyz.acrylicstyle.tomeito_core.utils.Log;

import java.util.Objects;

public class Crafting extends JavaPlugin {
    private static Crafting instance = null;
    public static LuckPerms api;

    public void onLoad() {
        instance = this;
    }

    public void onEnable() {
        Utils.initializeRecipes("4x4");
        Utils.initializeRecipes("5x5");
        Objects.requireNonNull(Bukkit.getPluginCommand("open4x4")).setExecutor(new Open4x4());
        Objects.requireNonNull(Bukkit.getPluginCommand("open5x5")).setExecutor(new Open5x5());
        Objects.requireNonNull(Bukkit.getPluginCommand("recipes4x4")).setExecutor(new Recipes4x4());
        Objects.requireNonNull(Bukkit.getPluginCommand("recipes5x5")).setExecutor(new Recipes5x5());
        Objects.requireNonNull(Bukkit.getPluginCommand("reloadrecipes")).setExecutor(new ReloadCrafting());
        Objects.requireNonNull(Bukkit.getPluginCommand("give5x5")).setExecutor(new Give5x5());
        Objects.requireNonNull(Bukkit.getPluginCommand("give5x5")).setTabCompleter(new Give5x5TabCompleter());
        if (Bukkit.getOnlinePlayers().size() >= 1) initializeLuckPerms();
        new BukkitRunnable() {
            @Override
            public void run() {
                initializeLuckPerms();
            }
        }.runTaskLater(this, 1);
        Log.info("Enabled Crafting.");
    }

    public void initializeLuckPerms() {
        RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            api = provider.getProvider();
        }
    }

    public static Crafting getInstance() {
        return instance;
    }
}
