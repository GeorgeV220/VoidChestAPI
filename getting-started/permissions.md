---
icon: square-terminal
---

# Permissions

***
5.3.0 and up:

| **Permission**                   | **Description**                                                                                                                |
| -------------------------------- | ------------------------------------------------------------------------------------------------------------------------------ |
| `voidchest.admin.help`           | Grants access to the **/vcadmin help** command, showing help information for admin-specific VoidChest commands.                |
| `voidchest.admin.reload`         | Allows reloading VoidChest plugin configuration files with the **/vcadmin reload** command (some changes may require a restart).|
| `voidchest.admin.list`           | Grants permission to use the **/vcadmin list** command to view all players' VoidChests.                                        |
| `voidchest.admin.boost`          | Allows applying a boost to a player using the **/vcadmin boost** command.                                                      |
| `voidchest.admin.developer`      | Enables developer information and tools via the **/vcadmin mode** command.                                                     |
| `voidchest.admin.give`           | Permits giving a VoidChest to a player with the **/vcadmin give** command.                                                     |
| `voidchest.admin.filters`        | Grants access to edit VoidChest system filters using the **/vcadmin filters** command.                                         |
| `voidchest.admin.shop`           | Allows modification of the VoidChest shop through the **/vcadmin shop** command.                                               |
| `voidchest.help`                 | Enables the use of the **/vc help** command to display a help page for VoidChest features and commands.                        |
| `voidchest.list`                 | Grants access to the **/vc list** command, which opens the player's VoidChest list GUI.                                        |
| `voidchest.ignore`               | Permits toggling sell messages with the **/vc toggle** or **/vc ignore** commands.                                             |
| `voidchest.locate`               | Allows the use of **/vc locate** to find VoidChests.                                                                            |
| `voidchest.locate.others`        | Extends **/vc locate** to locate VoidChests owned by other players.                                                            |
| `voidchest.chunk`                | Enables viewing chunk borders or VoidStorage bounding boxes with the **/vc chunk** or **/vc boundingbox** commands.            |
| `voidchest.stats`                | Grants access to view a player's VoidChest statistics using the **/vc stats** command.                                         |
| `voidchest.stats.other`          | Permits viewing another player's VoidChest statistics with the **/vc stats [player]** command.                                 |
| `voidchest.whitelist`            | Allows opening and managing whitelist filters for a specific VoidChest type with the **/vc whitelist** command.                |
| `voidchest.blacklist`            | Allows opening and managing blacklist filters for a specific VoidChest type with the **/vc blacklist** command.                |
| `voidchest.whitelist.others`   | Allows a player to edit the whitelist of another player's VoidChest, managing third-party access.                                  |
| `voidchest.blacklist.others`   | Allows a player to edit the blacklist of another player's VoidChest, managing third-party restrictions.                            |
| `voidchest.link`                 | Enables linking a container to a VoidChest via the **/vc link** command.                                                         |
| `voidchest.link.others`          | Enables linking a container to a VoidChest that does not belong to the player via the **/vc link** command.                      |
| `voidchest.links`                | Grants access to the **/vc lists** command, which lists all links associated with the player's VoidChests.                       |
| `voidchest.links.others`       | Grants access to see other players' links                                                                                          |
| `voidchest.interact-other`     | Allows a player to interact with VoidChests owned by other players.                                                                |
| `voidchest.creative.bypass`    | Enables interaction with VoidChests while in creative mode, bypassing usual restrictions.                                          |
| `voidchest.break.<voidchest>`  | Allows breaking a specified type of VoidChest. Replace `<voidchest>` with the specific VoidChest type name.                        |
| `voidchest.break.*`            | Grants permission to break all types of VoidChests.                                                                                |
| `voidchest.place.<voidchest>`  | Allows placing a specified type of VoidChest. Replace `<voidchest>` with the specific VoidChest type name.                         |
| `voidchest.place.*`            | Grants permission to place any type of VoidChest.                                                                                  |
| `voidchest.limit.<limit_type>` | Sets a placement limit for a specific VoidChest type, defined by `<limit_type>`.                                                   |
| `voidchest.limit.*`            | Allows a player to bypass any placement limits for VoidChests, ignoring the set cap.                                               |
| `voidchest.break-other.bypass` | Permits breaking VoidChests owned by other players, overriding usual ownership restrictions.                                       |

Pre 5.3.0:
| **Permission**                 | **Description**                                                                                                                    |
| ------------------------------ | ---------------------------------------------------------------------------------------------------------------------------------- |
| `voidchest.developer`          | Grants access to developer-specific features for the VoidChest plugin.                                                             |
| `voidchest.interact-other`     | Allows a player to interact with VoidChests owned by other players.                                                                |
| `voidchest.chunk`              | Grants permission to use the **/voidchest chunk** command, showing information or performing actions related to the current chunk. |
| `voidchest.give`               | Allows access to the **/voidchest give** command, enabling the distribution of VoidChests to players.                              |
| `voidchest.boost`              | Grants access to the **/voidchest boost** command, which can apply temporary benefits or boosts to a VoidChest.                    |
| `voidchest.help`               | Allows a player to use the **/voidchest help** command, displaying available commands and their descriptions.                      |
| `voidchest.list`               | Grants permission to use the **/voidchest list** command, listing all owned or accessible VoidChests for a player.                 |
| `voidchest.reload`             | Allows reloading the VoidChest plugin configuration with the **/voidchest reload** command, applying any new settings.             |
| `voidchest.stats`              | Grants access to view VoidChest statistics with the **/voidchest stats** command.                                                  |
| `voidchest.stats.other`        | Allows a player to view another player's VoidChest statistics with the **/voidchest stats** command.                               |
| `voidchest.toggle/ignore`      | Grants access to toggle VoidChest features or ignore settings using the **/voidchest toggle** command.                             |
| `voidchest.locate`             | Allows the use of **/voidchest locate** to find the nearest VoidChest or a specific VoidChest.                                     |
| `voidchest.creative.bypass`    | Enables interaction with VoidChests while in creative mode, bypassing usual restrictions.                                          |
| `voidchest.break.<voidchest>`  | Allows breaking a specified type of VoidChest. Replace `<voidchest>` with the specific VoidChest type name.                        |
| `voidchest.break.*`            | Grants permission to break all types of VoidChests.                                                                                |
| `voidchest.place.<voidchest>`  | Allows placing a specified type of VoidChest. Replace `<voidchest>` with the specific VoidChest type name.                         |
| `voidchest.place.*`            | Grants permission to place any type of VoidChest.                                                                                  |
| `voidchest.limit.<limit_type>` | Sets a placement limit for a specific VoidChest type, defined by `<limit_type>`.                                                   |
| `voidchest.limit.*`            | Allows a player to bypass any placement limits for VoidChests, ignoring the set cap.                                               |
| `voidchest.break-other.bypass` | Permits breaking VoidChests owned by other players, overriding usual ownership restrictions.                                       |
| `voidchest.whitelist`          | Enables editing of the whitelist for a VoidChest, controlling who can interact with it.                                            |
| `voidchest.blacklist`          | Enables editing of the blacklist for a VoidChest, controlling who is restricted from interacting with it.                          |
| `voidchest.whitelist.others`   | Allows a player to edit the whitelist of another player's VoidChest, managing third-party access.                                  |
| `voidchest.blacklist.others`   | Allows a player to edit the blacklist of another player's VoidChest, managing third-party restrictions.                            |

***
