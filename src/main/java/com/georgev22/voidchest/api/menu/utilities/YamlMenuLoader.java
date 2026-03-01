package com.georgev22.voidchest.api.menu.utilities;

import com.georgev22.voidchest.api.animation.engine.AnimationRequestOptions;
import com.georgev22.voidchest.api.animation.type.AnimationType;
import com.georgev22.voidchest.api.menu.Menu;
import com.georgev22.voidchest.api.menu.actions.Action;
import com.georgev22.voidchest.api.menu.item.ItemProvider;
import com.georgev22.voidchest.api.menu.item.builder.AbstractItemBuilder;
import com.georgev22.voidchest.api.menu.item.builder.ItemBuilder;
import com.georgev22.voidchest.api.menu.item.builder.SkullBuilder;
import com.georgev22.voidchest.api.menu.item.items.MenuFrameItem;
import com.georgev22.voidchest.api.menu.item.items.MenuItem;
import com.georgev22.voidchest.api.menu.item.items.StatefulMenuItem;
import com.georgev22.voidchest.api.menu.state.StateCondition;
import com.georgev22.voidchest.api.registry.Registries;
import com.georgev22.voidchest.api.utilities.EnumUtils;
import com.georgev22.voidchest.api.utilities.MessageBuilder;
import com.georgev22.voidchest.api.utilities.color.Color;
import io.papermc.paper.registry.RegistryAccess;
import io.papermc.paper.registry.RegistryKey;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.jspecify.annotations.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class YamlMenuLoader {
    private static final Random RANDOM = new Random();
    private static final List<Material> VALID_MATERIALS = Arrays.stream(Material.values())
            .filter(Material::isItem).toList();

    private final FileConfiguration config;

    public YamlMenuLoader(FileConfiguration config) {
        this.config = config;
    }

    public static @NonNull List<Color> generateRandomColors() {
        int count = 3 + RANDOM.nextInt(8);
        List<Color> colors = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            String hex = String.format("#%06X", RANDOM.nextInt(0xFFFFFF + 1));
            colors.add(Color.from(hex));
        }
        return colors;
    }

    public static Material getRandomItemMaterial() {
        return VALID_MATERIALS.get(RANDOM.nextInt(VALID_MATERIALS.size()));
    }

    public static List<Material> getValidMaterials() {
        return VALID_MATERIALS;
    }

    public Menu loadMenu(String path) {
        ConfigurationSection section = config.getConfigurationSection(path);
        if (section == null) throw new IllegalArgumentException("Section not found: " + path);

        String title = section.getString("title", "Menu");
        int rows = section.getInt("rows", 6);
        boolean isAnimated = section.getBoolean("animation", false);
        String defaultActionKey = section.getString("defaultAction", "voidchest:nothing");
        String bottomInventoryActionKey = section.getString("bottomInventoryAction", "voidchest:nothing");
        String[] structure = section.getStringList("structure").toArray(String[]::new);

        MenuBuilder builder = new MenuBuilder(MessageBuilder.builder().appendMiniMessage(title).build());
        builder.setAnimated(isAnimated);
        if (structure.length != 0) {
            builder.setStructure(new Structure(structure));
        } else {
            builder.setRows(rows);
        }

        Registries.MENU_ACTION.get(NamespacedKey.fromString(defaultActionKey)).ifPresent(builder::setDefaultAction);
        Registries.MENU_ACTION.get(NamespacedKey.fromString(bottomInventoryActionKey)).ifPresent(builder::setBottomInventoryAction);

        ConfigurationSection itemsSection = section.getConfigurationSection("items");
        if (itemsSection != null) {
            for (String key : itemsSection.getKeys(false)) {
                ConfigurationSection itemSection = itemsSection.getConfigurationSection(key);
                if (itemSection == null) continue;

                MenuItem menuItem = buildMenuItem(itemSection, isAnimated);
                menuItem.setFrames(buildFrames(itemSection, menuItem));

                if (Arrays.stream(structure).anyMatch(row -> row.contains(key))) {
                    builder.mapChar(key.charAt(0), menuItem.clone());
                } else {
                    if (itemSection.contains("slot")) {
                        if (itemSection.isList("slot")) {
                            List<Integer> slots = itemSection.getIntegerList("slot");
                            for (int slot : slots) {
                                MenuItem slotItem = menuItem.clone();
                                slotItem.setSlot(slot);
                                builder.setReservedItem(slot, slotItem);
                            }
                        } else {
                            int slot = itemSection.getInt("slot");
                            builder.setReservedItem(slot, menuItem);
                        }
                    } else {
                        builder.addItem(menuItem);
                    }
                }
            }
        }
        return builder.build();
    }

    @SuppressWarnings("DuplicatedCode")
    private @NonNull MenuItem buildMenuItem(@NonNull ConfigurationSection itemSection,
                                            boolean isAnimated) {
        if (itemSection.contains("states")) {
            ConfigurationSection statesSection = itemSection.getConfigurationSection("states");
            if (statesSection != null) {
                ConfigurationSection state0 = statesSection.getConfigurationSection("0");
                if (state0 == null)
                    throw new IllegalArgumentException("State 0 must be defined for item: " + itemSection.getName());

                ItemProvider itemProvider = finalItem(state0);
                String actionKey = state0.getString("action", "voidchest:nothing");
                Action defaultAction = Registries.MENU_ACTION.get(NamespacedKey.fromString(actionKey)).orElse(null);

                String conditionKey = itemSection.getString("stateCondition", "voidchest:nothing");
                StateCondition condition = Registries.MENU_STATE_CONDITION
                        .get(NamespacedKey.fromString(conditionKey))
                        .orElse(null);

                List<Color> colors = state0.getStringList("colors").stream().map(Color::from).toList();
                if (isAnimated && colors.size() <= 1) {
                    colors = generateRandomColors();
                }

                String type = state0.getString("animation-options.type", "voidchest:none");
                boolean bold = state0.getBoolean("animation-options.bold", false);
                int ticksPerFrame = state0.getInt("animation-options.ticks-per-frame", 10);

                AnimationRequestOptions animationRequestOptions = new AnimationRequestOptions(bold, colors, ticksPerFrame);

                AnimationType animationType = Registries.ANIMATION_TYPE
                        .get(NamespacedKey.fromString(type)).orElse(null);

                StatefulMenuItem statefulItem = new StatefulMenuItem(itemProvider, defaultAction);
                statefulItem.setColors(colors);
                statefulItem.setAnimationType(animationType);
                statefulItem.setAnimationRequestOptions(animationRequestOptions);
                statefulItem.setStateCondition(condition);

                for (String stateKey : statesSection.getKeys(false)) {
                    ConfigurationSection stateSection = statesSection.getConfigurationSection(stateKey);
                    if (stateSection == null) continue;

                    ItemProvider provider = finalItem(stateSection);
                    String stateActionKey = stateSection.getString("action", "voidchest:nothing");
                    Action action = Registries.MENU_ACTION.get(NamespacedKey.fromString(stateActionKey)).orElse(null);

                    statefulItem.setStateProvider(Integer.parseInt(stateKey), provider, action);
                }

                return statefulItem;
            }
        }

        ItemProvider itemProvider = finalItem(itemSection);

        String actionKey = itemSection.getString("action", "voidchest:nothing");
        Action action = Registries.MENU_ACTION.get(NamespacedKey.fromString(actionKey)).orElse(null);

        List<Color> colors = itemSection.getStringList("colors").stream().map(Color::from).toList();
        if (isAnimated && colors.size() <= 1) {
            colors = generateRandomColors();
        }
        String type = itemSection.getString("animation-options.type", "voidchest:none");
        boolean bold = itemSection.getBoolean("animation-options.bold", false);
        int ticksPerFrame = itemSection.getInt("animation-options.ticks-per-frame", 10);

        AnimationType animation = Registries.ANIMATION_TYPE
                .get(NamespacedKey.fromString(type)).orElse(null);

        AnimationRequestOptions animationRequestOptions = new AnimationRequestOptions(bold, colors, ticksPerFrame);

        return new MenuItem(itemProvider, action, colors, null, animation, animationRequestOptions);
    }

    private @NonNull List<MenuFrameItem> buildFrames(
            @NonNull ConfigurationSection itemSection,
            MenuItem baseItem) {
        List<MenuFrameItem> frames = new ArrayList<>();
        ConfigurationSection framesSection = itemSection.getConfigurationSection("frames");
        if (framesSection == null) return frames;

        for (String key : framesSection.getKeys(false)) {
            ConfigurationSection frameSec = framesSection.getConfigurationSection(key);
            if (frameSec == null) continue;

            frames.add(new MenuFrameItem(baseItem, finalItem(frameSec)));
        }
        return frames;
    }

    private @NonNull ItemProvider finalItem(@NonNull ConfigurationSection itemSection) {
        String itemType = itemSection.getString("item", "PAPER");
        String skullProperty = itemSection.getString("skullProperty");
        Material material = EnumUtils.valueOfIgnoreCase(Material.class, itemType).orElse(Material.PAPER);
        //noinspection ExtractMethodRecommender
        AbstractItemBuilder<?> itemBuilder;
        if (material.equals(Material.PLAYER_HEAD) && skullProperty != null) {
            itemBuilder = new SkullBuilder(new SkullBuilder.HeadTexture(skullProperty));
        } else {
            ItemStack parsedItemStack = new ItemStack(material);
            //noinspection ConstantValue
            if (parsedItemStack == null) throw new IllegalArgumentException("Invalid item type: " + itemType);
            itemBuilder = new ItemBuilder(parsedItemStack);
        }

        itemBuilder.setAmount(itemSection.getInt("amount", 1));

        if (itemSection.contains("name")) {
            itemBuilder.setDisplayName(itemSection.getString("name"));
        }

        if (itemSection.contains("lore")) {
            itemBuilder.setLegacyLore(itemSection.getStringList("lore"));
        }

        if (itemSection.contains("customModelData")) {
            itemBuilder.setCustomModelData(itemSection.getInt("customModelData"));
        }

        if (itemSection.contains("glow")) {
            itemBuilder.setGlow(itemSection.getBoolean("glow"));
        }

        for (String enchantmentStr : itemSection.getStringList("enchantments")) {
            String[] split = enchantmentStr.split(":");
            if (split.length != 2) continue;
            NamespacedKey key = NamespacedKey.fromString(split[0]);
            if (key == null) continue;
            Enchantment enchantment = RegistryAccess.registryAccess().getRegistry(RegistryKey.ENCHANTMENT).get(key);
            if (enchantment == null) continue;
            int level = Integer.parseInt(split[1]);
            itemBuilder.addEnchantment(enchantment, level, true);
        }

        return itemBuilder;
    }

}
