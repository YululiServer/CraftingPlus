# CraftingPlus
Adds 4x4 and 5x5 crafting recipes into your minecraft server.

## How to use
- Install this plugin
- Create directory `./plugins/CraftingPlus/items4x4` and `./plugins/CraftingPlus/items5x5`
- Create a file like `[item name].yml`
```yaml
id: heavy_diamond_pickaxe # Item ID
material: STONE # -> Material.STONE
displayName: "&9Heavy Diamond Pickaxe" # Item name, color codes supported
recipe:
  recipe:
    - DIAMOND: 16 # Item(or ID^): Amount
    - DIAMOND: 16
    - DIAMOND: 16
    - DIAMOND: 16
    - DIAMOND: 16

    - DIAMOND: 16
    - DIAMOND: 16
    - DIAMOND: 16
    - DIAMOND: 16
    - DIAMOND: 16

    - "null": 1
    - OAK_LOG: 64
    - OAK_LOG: 64
    - OAK_LOG: 64
    - "null": 1

    - "null": 1
    - OAK_LOG: 64
    - OAK_LOG: 64
    - OAK_LOG: 64
    - "null": 1

    - "null": 1
    - OAK_LOG: 64
    - OAK_LOG: 64
    - OAK_LOG: 64
    - "null": 1
enchantments:
  unbreaking: 5 # minecraft:unbreaking, level 5
  efficiency: 8 # minecraft:efficiency, level 8
```