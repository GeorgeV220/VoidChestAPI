---
icon: up
---

# Upgrades

#### Upgrades Menu

This section defines the layout and navigation options within the upgrade menu for the VoidChest plugin. The menu is structured into slots, each representing a button or display for navigation and information.

**Values:**

* **title**: The title of the upgrades menu, which supports dynamic placeholders like `%currentPage%` to show the current page of the menu.
* **navigation**: A section where each number (0-8) represents a slot in the player's inventory. Each slot defines:
  * **name**: The name that will appear when hovering over the slot.
  * **item**: The item that will appear in the slot (e.g., "PAPER", "PLAYER\_HEAD").
  * **skull property**: Used if the item is a player head, specifying the texture of the head.
  * **action**: Defines the action when clicked, such as `NOTHING`, `PREVIOUS_PAGE`, `NEXT_PAGE`, or `CLOSE`.
  * **lores**: A list of lines displayed as the item’s description when hovered over.

**Configuration Example:**

```yaml
Upgrades Menu:
  title: "&b&lUpgrades Menu - Page (&a&l%currentPage%&b&l)"
  navigation:
    '0': 
      name: "&a&lInformation"
      item: "PAPER"
      action: "NOTHING"
      lores:
        - "&7To upgrade just click on an item."
    '1': 
      name: "&a&lBack"
      item: "PLAYER_HEAD"
      skull property: "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7..."
      action: "PREVIOUS_PAGE"
      lores:
        - "&7Go back to the previous page."
    '4': 
      name: "&c&lClose Menu"
      item: "PLAYER_HEAD"
      skull property: "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7..."
      action: "CLOSE"
      lores:
        - "&7Close the filter menu."
    '8': 
      name: "&a&lNext"
      item: "PLAYER_HEAD"
      skull property: "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7..."
      action: "NEXT_PAGE"
      lores:
        - "&7Go to the next page."
```

***

#### BoundingBox Upgrade

This upgrade enhances the collection range of the VoidChest, defining the area within which items are collected.

**Values:**

* **item**:
  * **type**: The type of block used to represent this upgrade level (e.g., DIRT, GRASS\_BLOCK, STONE).
  * **name**: The display name for the upgrade.
  * **lore**: Describes the current and next level of the upgrade, including collection range information (Max/Min X, Y, Z values).
* **price**: The cost to upgrade to this level.
* **minX, minY, minZ**: The minimum coordinates (relative to the VoidChest) for item collection.
* **maxX, maxY, maxZ**: The maximum coordinates (relative to the VoidChest) for item collection.

**Configuration Example:**

```yaml
BoundingBox:
  1:
    item:
      type: DIRT
      name: "&b&lCollection Range"
      lore:
        - "&7"
        - "Current Level: &a&l%level%&7"
        - "Next Level: &a&l%nextLevel%&7"
        - "Next Level Price: &a&l%nextLevelPrice%&7"
        - "&7"
        - "Max X: &a&l%maxX%&7"
        - "Max Y: &a&l%maxY%&7"
        - "Max Z: &a&l%maxZ%&7"
        - "Min X: &a&l%minX%&7"
        - "Min Y: &a&l%minY%&7"
        - "Min Z: &a&l%minZ%&7"
        - "&7"
    price: 0
    minX: -5
    minY: -5
    minZ: -5
    maxX: 5
    maxY: 5
    maxZ: 5
```

***

#### BoostSell Upgrade

This upgrade increases the sell boost of the VoidChest, meaning the chest will sell items for a higher profit.

**Values:**

* **item**:
  * **type**: The type of item used to represent this level (e.g., COAL, IRON\_INGOT, GOLD\_INGOT).
  * **name**: The display name for the upgrade.
  * **lore**: Describes the current and next level, including the sell boost percentage.
* **price**: The cost to upgrade to this level.
* **value**: The percentage increase in sell prices. A value of 1.0 corresponds to a 100% boost in sell prices.

**Configuration Example:**

```yaml
BoostSell:
  1:
    item:
      type: COAL
      name: "&b&lSell Boost"
      lore:
        - "&7"
        - "Current Level: &a&l%level%&7"
        - "Next Level: &a&l%nextLevel%&7"
        - "Next Level Price: &a&l%nextLevelPrice%&7"
        - "&7"
        - "Boost: &a&l%boost%&7"
        - "&7"
    price: 0
    value: 0.0
  2:
    item:
      type: IRON_INGOT
      name: "&b&lSell Boost"
      lore:
        - "&7"
        - "Current Level: &a&l%level%&7"
        - "Next Level: &a&l%nextLevel%&7"
        - "Next Level Price: &a&l%nextLevelPrice%&7"
        - "&7"
        - "Boost: &a&l%boost%&7"
        - "&7"
    price: 10000
    value: 0.5
  3:
    item:
      type: GOLD_INGOT
      name: "&b&lSell Boost"
      lore:
        - "&7"
        - "Current Level: &a&l%level%&7"
        - "Next Level: &c&lMax"
        - "&7"
        - "Boost: &a&l%boost%&7"
        - "&7"
    price: 20000
    value: 1.0
```

***

#### Links Upgrade

This upgrade allows the VoidChest to link with other VoidChests, enabling shared item collection between multiple chests.

**Values:**

* **item**:
  * **type**: The type of chest used to represent this level (e.g., TRAPPED\_CHEST, CHEST, ENDER\_CHEST).
  * **name**: The display name for the upgrade.
  * **lore**: Describes the current and next level, including the number of links to other VoidChests.
* **price**: The cost to upgrade to this level.
* **value**: The number of other VoidChests this chest can link with.

**Configuration Example:**

```yaml
Links:
  1:
    item:
      type: TRAPPED_CHEST
      name: "&b&lLinks"
      lore:
        - "&7"
        - "Current Level: &a&l%level%&7"
        - "Next Level: &a&l%nextLevel%&7"
        - "Next Level Price: &a&l%nextLevelPrice%&7"
        - "&7"
        - "Links: &a&l%links%&7"
        - "&7"
    price: 0
    value: 0
  2:
    item:
      type: CHEST
      name: "&b&lLinks"
      lore:
        - "&7"
        - "Current Level: &a&l%level%&7"
        - "Next Level: &a&l%nextLevel%&7"
        - "Next Level Price: &a&l%nextLevelPrice%&7"
        - "&7"
        - "Links: &a&l%links%&7"
        - "&7"
    price: 10000
    value: 2
  3:
    item:
      type: ENDER_CHEST
      name: "&b&lLinks"
      lore:
        - "&7"
        - "Current Level: &a&l%level%&7"
        - "Next Level: &c&lMax"
        - "&7"
        - "Links: &a&l%links%&7"
        - "&7"
    price: 20000
    value: 3
```

***
