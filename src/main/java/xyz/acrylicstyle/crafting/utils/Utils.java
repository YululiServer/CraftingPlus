package xyz.acrylicstyle.crafting.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import util.Collection;
import util.*;
import xyz.acrylicstyle.crafting.items.CustomItem;
import xyz.acrylicstyle.crafting.items.CustomRecipe;
import xyz.acrylicstyle.crafting.items.LeatherArmor;
import xyz.acrylicstyle.tomeito_api.providers.ConfigProvider;
import xyz.acrylicstyle.tomeito_api.utils.Log;

import java.io.File;
import java.util.*;
import java.util.function.Consumer;

public final class Utils {
    private Utils() {}

    public static void runPlayer(Player sender, String selector, Consumer<Player> consumer) {
        switch (selector) {
            case "@a":
                getOnlinePlayers().forEach(consumer);
                break;
            case "@p":
            case "@s":
                consumer.accept(sender);
                break;
            case "@r":
                consumer.accept(getOnlinePlayers().shuffle().first());
                break;
            default:
                Player player = Bukkit.getPlayerExact(selector);
                consumer.accept(player);
                break;
        }
    }

    public static CollectionList<Player> getOnlinePlayers() {
        CollectionList<Player> players = new CollectionList<>();
        players.addAll(Bukkit.getOnlinePlayers());
        return players;
    }

    public static CollectionList<String> getItemDefinitionStringFiles(String type) {
        File itemsDir = new File("./plugins/CraftingPlus/items" + type + "/");
        String[] itemsArray = itemsDir.list();
        if (itemsArray == null) return new CollectionList<>();
        return ICollectionList.asList(itemsArray);
    }

    private static StringCollection<CollectionList<CustomItem>> customItems = new StringCollection<>();

    public static CollectionList<CustomItem> getCustomItems(String type) {
        ICollectionList<String> files = getItemDefinitionStringFiles(type);
        return (CollectionList<CustomItem>) files.map(file -> {
            Log.debug("Processing file: " + file);
            ConfigProvider config;
            try {
                config = new ConfigProvider("./plugins/CraftingPlus/items" + type + "/" + file);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
            String id = config.getString("id");
            if (id == null) throw new NullPointerException("Item ID must be specified.");
            Material material = Material.valueOf(config.getString("material", "STONE"));
            boolean leatherArmor = material.name().startsWith("LEATHER_");
            String displayName = config.getString("displayName", null);
            ItemStack leather = null;
            if (leatherArmor) {
                String color = Objects.requireNonNull(config.getString("color", "000000")).replaceAll("#", "");
                leather = LeatherArmor.valueOf(material.name().replaceAll("LEATHER_(.*)", "$1")).toItemStack(displayName, color);
            }
            Collection<Enchantment, Integer> enchantments = config.get("enchantments") == null ? null : ICollection.asCollection(config.getConfigSectionValue("enchantments", true))
                    .map((k, v) -> Enchantment.getByKey(NamespacedKey.minecraft(k)), (k, v) -> (int) v);
            List<Map<?, ?>> recipesRawRaw = config.getMapList("recipe.recipe");
            CollectionList<Map.Entry<String, Object>> recipesRaw = ICollectionList.asList(recipesRawRaw)
                    .map(map -> new HashMap.SimpleEntry<>((String) map.keySet().toArray()[0], map.values().toArray()[0]));
            int amount = config.getInt("recipe.result.amount", 1);
            boolean unbreakable = config.getBoolean("unbreakable", false);
            boolean requiresPermission = config.getBoolean("requiresPermission", false);
            return new CustomItem(id, material, null, displayName, enchantments, recipesRaw, amount, unbreakable, leather, requiresPermission);
        });
    }

    public static StringCollection<CollectionList<CustomItem>> getCustomItems() {
        return customItems;
    }

    public static void setCustomItems(StringCollection<CollectionList<CustomItem>> items) {
        customItems = items;
    }

    public static CollectionList<CustomItem> getCustomItemsCached(String type) {
        if (!Utils.customItems.containsKey(type)) Utils.customItems.add(type, Utils.getCustomItems(type));
        return Utils.customItems.get(type);
    }

    /**
     * Gets custom item by ID.
     * @param id Custom item ID. Case-sensitive.
     * @return Custom item if found, null otherwise
     */
    public static CustomItem getCustomItemById(String type, String id) {
        try {
            return Utils.getCustomItemsCached(type).filter(custom -> custom.getId().equals(id)).first();
        } catch (Exception ignored) {
            return null;
        }
    }

    public static StringCollection<StringCollection<ItemStack>> recipes = new StringCollection<>();
    public static StringCollection<StringCollection<CustomItem>> customItem = new StringCollection<>();
    public static StringCollection<Collection<ItemStack[], ItemStack>> recipesRaw = new StringCollection<>();

    public static void initializeRecipes(String type) {
        Log.debug("Registering recipe for: " + type);
        StringCollection<ItemStack> recipesList = new StringCollection<>();
        StringCollection<CustomItem> itemsList = new StringCollection<>();
        Collection<ItemStack[], ItemStack> recipesListRaw = new Collection<>();
        Utils.getCustomItemsCached(type).clone().forEach(item -> {
            Log.debug("Processing recipe for item: " + item.getId());
            ItemStack[] matrix = new ItemStack[(type.equalsIgnoreCase("5x5") ? 25 : 16)];
            if (item.getRecipesRaw().size() != 0) {
                item.getRecipesRaw().foreach((map, index) -> {
                    String id = map.getKey();
                    CustomItem recipeItem = getCustomItemById(type, id);
                    int amount = (int) map.getValue();
                    if (recipeItem == null) {
                        if (id.equalsIgnoreCase("null")) {
                            matrix[index] = null;
                        } else
                            matrix[index] = new ItemStack(Material.valueOf(id), amount);
                    } else {
                        ItemStack recipeItem2 = recipeItem.toItemStack().clone();
                        recipeItem2.setAmount(amount);
                        matrix[index] = recipeItem2;
                    }
                });
                int resultAmount = item.getResultAmount();
                ItemStack resultItem2 = item.toItemStack().clone();
                resultItem2.setAmount(resultAmount);
                item.setRecipe(new CustomRecipe(matrix, resultItem2));
                Log.debug("Registering item: " + item.getId());
                itemsList.add(Arrays.toString(matrix), item);
                recipesList.add(Arrays.toString(matrix), resultItem2);
                recipesListRaw.add(matrix, resultItem2);
            } else {
                Log.debug("Matrix size is 0, ignoring: " + item.getId());
            }
        });
        customItem.add(type, itemsList);
        recipesRaw.add(type, recipesListRaw);
        recipes.add(type, recipesList);
    }

    public static boolean hasFullInventory(Inventory inventory) {
        return inventory.firstEmpty() == -1;
    }

    public static List<String> create64List() {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 64; i++) list.add(Integer.toString(i));
        return list;
    }
}
