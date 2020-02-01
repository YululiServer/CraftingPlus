package xyz.acrylicstyle.crafting;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.acrylicstyle.crafting.commands.*;
import xyz.acrylicstyle.crafting.utils.Utils;
import xyz.acrylicstyle.tomeito_core.utils.Log;

import java.util.Objects;

public class Crafting extends JavaPlugin {
    private static Crafting instance = null;

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
        Log.info("Enabled Crafting.");
    }

    public static Crafting getInstance() {
        return instance;
    }
}
