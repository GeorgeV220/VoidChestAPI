---
icon: gear
description: VoidChest Plugin Configuration Documentation
---

# Configuration

### Overview

This document provides a comprehensive guide to the configuration options for the VoidChest plugin. The configuration file is written in YAML format and allows you to customize various aspects of the plugin's behavior. Below, you will find detailed explanations for each configuration option, along with examples and additional information where necessary.

### Configuration File Structure

```yaml
# Configuration file for Options
Options:

  # Debug option
  debug: false

  # Proxy option (currently unused)
  proxy: false

  # Secret option (currently unused)
  secret: "SECRET HERE"

  # Locale option
  locale: "en_US"

  # Load delay option
  load delay: 2

  # Experimental features option
  experimental features: false

  # Metrics option
  metrics: true

  # Discord option (currently unused)
  discord: false

  # Updater option
  updater:
    enabled: true

  # VoidChest command option
  commands:
    voidchest: true

  database:
    type: "File" #Types File, SQLite, MySQL, PostgreSQL, MongoDB

    # SQL database options
    SQL:
      host: "localhost"     # SQL database host
      port: 3306            # SQL database port
      user: "youruser"      # SQL database username
      password: "yourpassword"   # SQL database password
      database: "VoidChest"   # SQL database name
      users table name: "voidchest_users"   # SQL table for users
      voidchests table name: "voidchest_voidchests"   # SQL table for voidchests
      SQLite file name: "voidchest"   # SQLite file name

    # MongoDB options
    MongoDB:
      host: "localhost"     # MongoDB host
      port: 27017           # MongoDB port
      user: "youruser"      # MongoDB username
      password: "yourpassword"   # MongoDB password
      database: "voidchest"   # MongoDB database name
      users collection: "voidchest_users"   # MongoDB collection for users
      voidchest collection: "voidchest_voidchests"   # MongoDB collection for voidchests

  # Time format options
  time format:
    second: second
    seconds: seconds
    minute: minute
    minutes: minutes
    hour: hour
    hours: hours
    day: day
    days: days
    invalid: Invalid

  money format: "##.####"

  # Economy options
  economy:
    charge: "Vault"
    upgrades: "Vault"
    payout: "Vault"

  # Void chest economy mode
  void chest economy mode: "EXP"

  # Stacker option
  stacker: "None"

  # Bank options
  bank: "None"
  bank tnt: "None"

  # Hologram options
  hologram:
    plugin: "VoidChest"
    update interval: 20

  # Ignore item meta option
  ignore item meta: true

  # Single task option
  single task: false

  # Sell interval option
  sell interval: 10

  # Chunk see options
  chunk see:
    Y central: 0
    Y up: 0
    Y down: 0
    effect:
      type: "VILLAGER_HAPPY"
      interval: 20

  # Sell message options
  sell message:
    enabled: false
    interval: 60

    offline:
      enabled: false
      message delay: 50
```

### Detailed Configuration Options

#### `debug`

* **Type:** Boolean
* **Default:** `false`
* **Description:** Enables or disables debug mode. When enabled, the plugin will output detailed debug information to the console, which can be useful for troubleshooting issues.
*   **Example:**

    ```yaml
    debug: true
    ```

#### `proxy`

* **Type:** Boolean (currently unused)
* **Default:** `false`
* **Description:** Enables or disables proxy support. This option is useful if your server is running behind a proxy like BungeeCord.
*   **Example:**

    ```yaml
    proxy: true
    ```

#### `secret`

* **Type:** String (currently unused)
* **Default:** `"SECRET HERE"`
* **Description:** A secret key used for secure communication between the plugin and external services. Replace `"SECRET HERE"` with your actual secret key.
*   **Example:**

    ```yaml
    secret: "my_secret_key"
    ```

#### `locale`

* **Type:** String
* **Default:** `"en_US"`
* **Description:** Specifies the language locale for the plugin's messages. The locale should be in this [format](locales.md).
*   **Example:**

    ```yaml
    locale: "en_US"
    ```

#### `load delay`

* **Type:** Integer
* **Default:** `2`
* **Description:** The number of seconds the plugin should wait before loading data (e.g., void chests, player data). This delay helps ensure that worlds and other plugins are fully loaded before VoidChest attempts to load its data, preventing potential issues.
*   **Example:**

    ```yaml
    load delay: 5
    ```

#### `experimental features`

* **Type:** Boolean
* **Default:** `false`
* **Description:** Enables or disables experimental features. These features are not fully tested and may be unstable. Use at your own risk.
*   **Example:**

    ```yaml
    experimental features: true
    ```

#### `metrics`

* **Type:** Boolean
* **Default:** `true`
* **Description:** Enables or disables the collection of anonymous usage data. This data helps the developers improve the plugin.
*   **Example:**

    ```yaml
    metrics: false
    ```

#### `discord`

* **Type:** Boolean (currently unused)
* **Default:** `false`
* **Description:** Enables or disables Discord integration. This allows the plugin to send notifications to a Discord server.
*   **Example:**

    ```yaml
    discord: true
    ```

#### `updater`

* **Type:** Map
*   **Default:**

    ```yaml
    updater:
      enabled: true
    ```
* **Description:** Configures the plugin updater. When enabled, the plugin will automatically check for updates and notify you if a new version is available.
*   **Example:**

    ```yaml
    updater:
      enabled: false
    ```

#### `commands`

* **Type:** Map
*   **Default:**

    ```yaml
    commands:
      voidchest: true
    ```
* **Description:** Enables or disables specific commands provided by the plugin. Currently, only the `voidchest` command is configurable.
*   **Example:**

    ```yaml
    commands:
      voidchest: false
    ```

#### `database`

* **Type:** Map
*   **Default:**

    ```yaml
    database:
      type: "File"
    ```
* **Description:** Configures the database type and connection details. The plugin supports multiple database types, including `File`, `SQLite`, `MySQL`, `PostgreSQL`, and `MongoDB`.
*   **Example:**

    ```yaml
    database:
      type: "MySQL"
      SQL:
        host: "localhost"
        port: 3306
        user: "admin"
        password: "password123"
        database: "VoidChestDB"
        users table name: "users"
        voidchests table name: "voidchests"
    ```

#### `time format`

* **Type:** Map
*   **Default:**

    ```yaml
    time format:
      second: second
      seconds: seconds
      minute: minute
      minutes: minutes
      hour: hour
      hours: hours
      day: day
      days: days
      invalid: Invalid
    ```
* **Description:** Defines the text format for time units. This affects how time is displayed in various parts of the plugin.
*   **Example:**

    ```yaml
    time format:
      second: sec
      seconds: secs
      minute: min
      minutes: mins
      hour: hr
      hours: hrs
      day: day
      days: days
      invalid: Invalid Time
    ```

#### `money format`

* **Type:** String
* **Default:** `"##.####"`
* **Description:** Specifies the format for displaying monetary values. The format string uses `#` to represent digits.
*   **Example:**

    ```yaml
    money format: "##.##"
    ```

#### `economy`

* **Type:** Map
*   **Default:**

    ```yaml
    economy:
      charge: "Vault"
      upgrades: "Vault"
      payout: "Vault"
    ```
* **Description:** Configures the economy system used by the plugin. The plugin supports multiple economy plugins, including `Vault`, `TheNewEconomy`, `VoidChest`, and `Custom`.
*   **Example:**

    ```yaml
    economy:
      charge: "TheNewEconomy"
      upgrades: "VoidChest"
      payout: "Custom"
    ```

#### `void chest economy mode`

* **Type:** String
* **Default:** `"EXP"`
* **Description:** Defines the economy mode for void chests. Available modes are `EXP` and `LEVELS`.
*   **Example:**

    ```yaml
    void chest economy mode: "LEVELS"
    ```

#### `stacker`

* **Type:** String
* **Default:** `"None"`
* **Description:** Specifies the stacker plugin to use for stacking items. Available options include `WildStacker`, `UltimateStacker`, `SpaceStacker`, `RoseStacker`, `zItemStacker`, `CloudSpawners`, and `None`.
*   **Example:**

    ```yaml
    stacker: "WildStacker"
    ```

#### `bank`

* **Type:** String
* **Default:** `"None"`
* **Description:** Specifies the bank plugin to use for handling bank transactions. Available options include `SuperiorSkyblock2`, `FabledSkyBlock`, `SaberFactions`, `MysqlEconomyBank`, `RoyaleEconomy`, `Custom`, and `None`.
*   **Example:**

    ```yaml
    bank: "SuperiorSkyblock2"
    ```

#### `bank tnt`

* **Type:** String
* **Default:** `"None"`
* **Description:** Specifies the bank plugin to use for handling TNT transactions. Available options include `SaberFactions`, `SupremeFactions`, `FactionsUUID`, `Custom`, and `None`.
*   **Example:**

    ```yaml
    bank tnt: "SaberFactions"
    ```

#### `hologram`

* **Type:** Map
*   **Default:**

    ```yaml
    hologram:
      plugin: "VoidChest"
      update interval: 20
    ```
* **Description:** Configures the hologram plugin used by the VoidChest. The plugin supports multiple hologram plugins, including `VoidChest`, `HolographicDisplays`, `Holograms`, `CMI`, `DecentHolograms`, and `FancyHolograms`.
*   **Example:**

    ```yaml
    hologram:
      plugin: "HolographicDisplays"
      update interval: 10
    ```

#### `ignore item meta`

* **Type:** Boolean
* **Default:** `true`
* **Description:** Determines whether the plugin should ignore item metadata when processing items. If enabled, items with different metadata (e.g., enchantments, lore) will be treated as the same item.
*   **Example:**

    ```yaml
    ignore item meta: false
    ```

#### `single task`

* **Type:** Boolean
* **Default:** `false`
* **Description:** Specifies whether all void chests should share a single sell interval. If set to `false`, each void chest can have its own sell interval.
*   **Example:**

    ```yaml
    single task: true
    ```

#### `sell interval`

* **Type:** Integer
* **Default:** `10`
* **Description:** The interval (in seconds) at which void chests will sell items. This value is used if the interval is not specified in the void chest's configuration file or if `single task` is enabled.
*   **Example:**

    ```yaml
    sell interval: 30
    ```

#### `chunk see`

* **Type:** Map
*   **Default:**

    ```yaml
    chunk see:
      Y central: 0
      Y up: 0
      Y down: 0
      effect:
        type: "VILLAGER_HAPPY"
        interval: 20
    ```
* **Description:** Configures the visibility and effects of chunks. The `Y central`, `Y up`, and `Y down` values determine the vertical range of chunk visibility. The `effect` section configures the visual effect displayed in the chunks.
*   **Example:**

    ```yaml
    chunk see:
      Y central: 10
      Y up: 5
      Y down: 5
      effect:
        type: "FIREWORKS_SPARK"
        interval: 10
    ```

#### `sell message`

* **Type:** Map
*   **Default:**

    ```yaml
    sell message:
      enabled: false
      interval: 60
      offline:
        enabled: false
        message delay: 50
    ```
* **Description:** Configures the messages displayed when void chests sell items. The `enabled` option determines whether sell messages are shown. The `interval` specifies the interval (in seconds) between sell messages. The `offline` section configures messages for offline players.
*   **Example:**

    ```yaml
    sell message:
      enabled: true
      interval: 30
      offline:
        enabled: true
        message delay: 20
    ```

***
