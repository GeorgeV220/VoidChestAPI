---
description: VoidChest Menu Configuration Documentation
icon: computer-mouse
---

# Menus



This document provides a detailed explanation of the configuration file for the VoidChest menu named `default.yml`. This file is located in the `/plugins/VoidChest/inventories/` directory. Each VoidChest has its own configuration file named `<voidchestname>.yml` in the same directory.

### Table of Contents

1. [General Configuration](menus.md#general-configuration)
2. [Animation Configuration](menus.md#animation-configuration)
3. [Items Configuration](menus.md#items-configuration)
   * [Item Properties](menus.md#item-properties)
   * [Item Actions](menus.md#item-actions)
4. [Examples](menus.md#examples)

### General Configuration

#### `name`

* **Description**: The name of the inventory menu.
* **Format**: String with color codes.
* **Example**: `"&a&l%voidchest% VoidChest Inventory"`

#### `size`

* **Description**: The size of the inventory menu in slots.
* **Format**: Integer (must be a multiple of 9).
* **Example**: `54`

### Animation Configuration

#### `animation`

* **Description**: Configuration for the inventory animation.
* **Properties**:
  * `enabled`: Whether the animation is enabled.
    * **Format**: Boolean.
    * **Example**: `true`

### Items Configuration

#### Item Properties

Each item in the inventory is defined by a set of properties:

* **`item`**: The type of item.
  * **Format**: Minecraft item identifier.
  * **Example**: `GOLD_INGOT`
* **`title`**: The display name of the item.
  * **Format**: String with color codes.
  * **Example**: `"&a&lAuto Sell"`
* **`lores`**: The lore lines of the item.
  * **Format**: List of strings with color codes.
  *   **Example**:

      ```yaml
      lores:
        - "&7Should this voidchest sell contents?"
        - " "
        - "&7Status %status_auto_sell%"
      ```
* **`glow`**: Whether the item should glow.
  * **Format**: Boolean.
  * **Example**: `false`
* **`show attributes`**: Whether to show item attributes.
  * **Format**: Boolean.
  * **Example**: `false`
* **`amount`**: The amount of the item.
  * **Format**: Integer.
  * **Example**: `1`
* **`animated`**: Whether the item is animated.
  * **Format**: Boolean.
  * **Example**: `true`
* **`animation`**: The type of animation.
  * **Format**: String.
  * **Example**: `"wave"`
* **`colors`**: The colors used in the animation.
  * **Format**: List of hex color codes.
  *   **Example**:

      ```yaml
      colors:
        - "#F3F8FA"
        - "#A2CCE3"
        - "#68A4C4"
        - "#32627B"
        - "#1E4356"
      ```
* **`action`**: The action performed when the item is clicked.
  * **Format**: String.
  * **Example**: `TOGGLE_AUTO_SELL`
* **`commands`**: Commands executed when the item is clicked.
  * **Format**: List of strings.
  * **Example**: `[ ]`
* **`commands cooldown`**: Cooldown for commands executed on different mouse clicks.
  * **Format**: Map with keys `RIGHT`, `LEFT`, `MIDDLE` and values as integers.
  *   **Example**:

      ```yaml
      commands cooldown:
        RIGHT: 0
        LEFT: 0
        MIDDLE: 0
      ```

#### Item Actions

* **`TOGGLE_AUTO_SELL`**: Toggles the auto-sell feature.
* **`TOGGLE_CHUNK_COLLECTOR`**: Toggles the chunk collector feature.
* **`TOGGLE_PURGE`**: Toggles the purge feature.
* **`TOGGLE_INSTANT_COLLECTOR`**: Toggles the instant collector feature.
* **`TOGGLE_HOLOGRAM`**: Toggles the hologram feature.
* **`ADD_CHARGE`**: Adds charge time to the VoidChest.
* **`TOGGLE_TRANSFER_NON_SELLABLES`**: Toggles the transfer of non-sellable items.
* **`OPEN_CHEST`**: Opens the chest inventory.
* **`TOGGLE_BANK`**: Toggles the bank feature.
* **`NOTHING`**: No action is performed.
* **`EXIT`**: Exits the menu.

### Examples

#### Example 1: Auto Sell Item

```yaml
'11':
  item: GOLD_INGOT
  title: "&a&lAuto Sell"
  lores:
    - "&7Should this voidchest sell contents?"
    - " "
    - "&7Status %status_auto_sell%"
    - " "
    - "&7Click to &a%!status_auto_sell%&7 this setting."
  glow: false
  show attributes: false
  amount: 1
  animated: true
  animation: "wave"
  colors:
    - "#F3F8FA"
    - "#A2CCE3"
    - "#68A4C4"
    - "#32627B"
    - "#1E4356"
  action: TOGGLE_AUTO_SELL
  commands: [ ]
  commands cooldown:
    RIGHT: 0
    LEFT: 0
    MIDDLE: 0
```

#### Example 2: Exit Item

```yaml
'49':
  item: PLAYER_HEAD
  title: "&8&l» &e&lExit &8&l«"
  skull property: "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDQ2NjRlZGVhNzAxOTMzMjZhYTMxZjhmNTBmODBjMjkzN2I1YThmMDczNThhNWIwODQ5ZGRmNWI1YjJjOGMzNiJ9fX0="
  amount: 1
  lores: [ ]
  show all attributes: false
  glow: true
  slot: 4
  animated: true
  animation: "wave"
  frames:
    '0':
      item: PLAYER_HEAD
      skull property: "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzY2ZmU5NzM4YTY5M2NlMjlkZWY1MmU3OTQ0OTNhZTAwMGVhYmE3MWJhNjYwNTY5MDY1ZWE2NjI4NTEzNTQxZiJ9fX0="
  colors:
    - "#48D1CC"
    - "#778899"
    - "#55FF55"
    - "#FF1493"
  action: EXIT
  commands: [ ]
  commands cooldown:
    RIGHT: 0
    LEFT: 0
    MIDDLE: 0
```

This documentation should help you understand and modify the `default.yml` file for the VoidChest menu. Each VoidChest can have its own configuration file named `<voidchestname>.yml` in the same directory.

***
