package com.georgev22.voidchest.api.menu.item.builder;

import com.georgev22.voidchest.api.VoidChestAPI;
import com.georgev22.voidchest.api.menu.MojangApiUtils;
import com.georgev22.voidchest.api.menu.MojangApiUtils.MojangApiException;
import com.georgev22.voidchest.api.utilities.Utils;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.NonNull;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public final class SkullBuilder extends AbstractItemBuilder<SkullBuilder> {

    private String textureValue;
    private UUID profileUUID;
    private String profileName;

    /**
     * Create a {@link SkullBuilder} of a {@link Player Player's} {@link UUID}.
     *
     * @param uuid The {@link UUID} of the skull owner.
     * @throws MojangApiException If the Mojang API returns an error.
     * @throws IOException        If an I/O error occurs.
     * @deprecated Prefer using {@link SkullBuilder#SkullBuilder(HeadTexture)} with a hardcoded texture value instead of querying the Mojang API.
     */
    @Deprecated
    public SkullBuilder(@NonNull UUID uuid) throws MojangApiException, IOException {
        this(HeadTexture.of(uuid));
    }

    /**
     * Create a {@link SkullBuilder} with the {@link Player Player's} username.
     *
     * @param username The username of the skull owner.
     * @throws MojangApiException If the Mojang API returns an error.
     * @throws IOException        If an I/O error occurs.
     * @deprecated Prefer using {@link SkullBuilder#SkullBuilder(HeadTexture)} with a hardcoded texture value instead of querying the Mojang API.
     */
    @Deprecated
    public SkullBuilder(@NonNull String username) throws MojangApiException, IOException {
        this(HeadTexture.of(username));
    }

    /**
     * Create a {@link SkullBuilder} with a {@link HeadTexture}.
     *
     * @param headTexture The {@link HeadTexture} to be applied to the skull.
     */
    public SkullBuilder(@NonNull HeadTexture headTexture) {
        super(Objects.requireNonNull(Material.PLAYER_HEAD));
        this.textureValue = headTexture.getTextureValue();
        this.profileUUID = Utils.generateUUID(VoidChestAPI.getInstance().plugin().getName());
        String cleanName = VoidChestAPI.getInstance().plugin().getName().replaceAll("[^A-Za-z0-9_]", "");
        cleanName = cleanName.length() >= 3 ? cleanName : String.format("%-3s", cleanName).replace(' ', '_');
        this.profileName = cleanName.substring(0, Math.min(16, cleanName.length()));
    }

    @Override
    public @NonNull ItemStack get() {
        ItemStack item = super.get();
        //noinspection deprecation - 1.8.8
        item.setDurability((short) 3);
        SkullMeta meta = (SkullMeta) item.getItemMeta();

        if (textureValue != null) {
            try {
                try {
                    //noinspection deprecation
                    org.bukkit.profile.PlayerProfile profile = Bukkit.createProfile(profileUUID, profileName);

                    org.bukkit.profile.PlayerTextures textures = profile.getTextures();

                    String json = new String(Base64.getDecoder().decode(textureValue), StandardCharsets.UTF_8);

                    JsonObject textureJson = JsonParser.parseString(json).getAsJsonObject();
                    String skinUrl = textureJson
                            .getAsJsonObject("textures")
                            .getAsJsonObject("SKIN")
                            .get("url")
                            .getAsString();

                    textures.setSkin(new URI(skinUrl).toURL());

                    profile.setTextures(textures);
                    //noinspection deprecation
                    meta.setOwnerProfile(profile);
                } catch (Throwable ignored) {
                    applyLegacyProfile(meta);
                }
            } catch (Throwable t) {
                VoidChestAPI.getInstance().plugin().getLogger().log(Level.SEVERE, "Failed to set skull texture", t);
            }
        }

        item.setItemMeta(meta);
        return item;
    }

    private void applyLegacyProfile(@NonNull SkullMeta meta) throws Exception {
        Class<?> skull = meta.getClass();

        try {
            Class<?> resolvable = Class.forName("net.minecraft.world.item.component.ResolvableProfile");
            Constructor<?> ctor = resolvable.getDeclaredConstructor(GameProfile.class);
            ctor.setAccessible(true);
            Object inst = ctor.newInstance(makeGameProfile());
            Method m = skull.getDeclaredMethod("setProfile", resolvable);
            m.setAccessible(true);
            m.invoke(meta, inst);
            return;
        } catch (Throwable ignored) {
        }

        try {
            Method m = skull.getDeclaredMethod("setProfile", GameProfile.class);
            m.setAccessible(true);
            m.invoke(meta, makeGameProfile());
            return;
        } catch (Throwable ignored) {
        }

        Field f = skull.getDeclaredField("profile");
        f.setAccessible(true);
        f.set(meta, makeGameProfile());
    }

    private @NonNull GameProfile makeGameProfile() {
        GameProfile gp = new GameProfile(profileUUID, profileName);
        gp.getProperties().put("textures", new Property("textures", textureValue));
        return gp;
    }

    @Contract("_ -> this")
    @Override
    public @NonNull SkullBuilder setMaterial(@NonNull Material material) {
        return this;
    }

    @Contract(value = "-> new", pure = true)
    @Override
    public @NonNull SkullBuilder clone() {
        SkullBuilder skullBuilder = (SkullBuilder) super.clone();
        skullBuilder.profileName = profileName;
        skullBuilder.textureValue = textureValue;
        skullBuilder.profileUUID = profileUUID;
        return skullBuilder;
    }

    /**
     * Creates a shallow copy of the current object.
     * <p>
     * Only the top-level fields are copied; nested objects are referenced directly.
     *
     * @return a shallow copy of type {@code T}
     */
    @Override
    public @NonNull SkullBuilder shallowCopy() {
        return this.clone();
    }

    /**
     * Creates a deep copy of the current object.
     * <p>
     * Both top-level fields and nested objects are fully copied.
     *
     * @return a deep copy of type {@code T}
     */
    @Override
    public @NonNull SkullBuilder deepCopy() {
        return this.clone();
    }

    /**
     * Contains the texture value for a player head.
     *
     * @see SkullBuilder
     */
    @SuppressWarnings("ClassCanBeRecord")
    public static class HeadTexture implements Serializable {

        private static final Cache<UUID, String> textureCache = CacheBuilder.newBuilder()
                .expireAfterWrite(1, TimeUnit.DAYS)
                .build();

        private static final Cache<String, UUID> uuidCache = CacheBuilder.newBuilder()
                .expireAfterWrite(1, TimeUnit.DAYS)
                .build();

        private final String textureValue;

        /**
         * Creates a new {@link HeadTexture} from the raw texture value.
         *
         * @param textureValue The texture value of this {@link HeadTexture}
         * @see HeadTexture#of(OfflinePlayer)
         * @see HeadTexture#of(UUID)
         * @see HeadTexture#of(String)
         */
        public HeadTexture(@NonNull String textureValue) {
            this.textureValue = textureValue;
        }

        /**
         * Retrieves the {@link HeadTexture} from this {@link OfflinePlayer}
         * Please note that this data might not be pulled from the Mojang API as it might already be cached.
         * Use {@link HeadTexture#invalidateCache()} to invalidate the cache.
         *
         * @param offlinePlayer The skull owner.
         * @return The {@link HeadTexture} of that player.
         * @throws MojangApiException If the Mojang API returns an error.
         * @throws IOException        If an I/O error occurs.
         * @see HeadTexture#of(UUID)
         * @deprecated Prefer using {@link HeadTexture#HeadTexture(String)} with a hardcoded texture value instead of querying the Mojang API.
         */
        @Deprecated
        public static @NonNull HeadTexture of(@NonNull OfflinePlayer offlinePlayer) throws MojangApiException, IOException {
            return of(offlinePlayer.getUniqueId());
        }

        /**
         * Retrieves the {@link HeadTexture} from the username of the skull owner.
         * This will first retrieve the {@link UUID} of the player from either Bukkit's usercache.json file
         * (if the server is in only mode) or from the Mojang API (if the server is in offline mode).
         * <p>
         * Please note that this data might not be pulled from the Mojang API as it might already be cached.
         * Use {@link HeadTexture#invalidateCache()} to invalidate the cache.
         *
         * @param playerName The username of the player.
         * @return The {@link HeadTexture} of that player.
         * @throws MojangApiException If the Mojang API returns an error.
         * @throws IOException        If an I/O error occurs.
         * @see HeadTexture#of(UUID)
         * @deprecated Prefer using {@link HeadTexture#HeadTexture(String)} with a hardcoded texture value instead of querying the Mojang API.
         */
        @Deprecated
        public static @NonNull HeadTexture of(@NonNull String playerName) throws MojangApiException, IOException {
            if (Bukkit.getServer().getOnlineMode()) {
                // if the server is in online mode, the Minecraft UUID cache (usercache.json) can be used
                return of(Bukkit.getOfflinePlayer(playerName).getUniqueId());
            } else {
                // the server isn't in online mode - the UUID has to be retrieved from the Mojang API
                try {
                    return of(uuidCache.get(playerName, () -> MojangApiUtils.getCurrentUuid(playerName)));
                } catch (ExecutionException e) {
                    Throwable cause = e.getCause();
                    if (cause instanceof MojangApiException) {
                        throw (MojangApiException) cause;
                    } else if (cause instanceof IOException) {
                        throw (IOException) cause;
                    } else {
                        throw new RuntimeException(cause);
                    }
                }
            }
        }

        /**
         * Retrieves the {@link HeadTexture} from the {@link UUID} of the skull owner.
         * Please note that this data might not be pulled from the Mojang API as it might already be cached.
         * Use {@link HeadTexture#invalidateCache()} to invalidate the cache.
         *
         * @param uuid The {@link UUID} of the skull owner.
         * @return The {@link HeadTexture} of that player.
         * @throws MojangApiException If the Mojang API returns an error.
         * @throws IOException        If an I/O error occurs.
         * @deprecated Prefer using {@link HeadTexture#HeadTexture(String)} with a hardcoded texture value instead of querying the Mojang API.
         */
        @Deprecated
        public static @NonNull HeadTexture of(@NonNull UUID uuid) throws MojangApiException, IOException {
            try {
                return new HeadTexture(textureCache.get(uuid, () -> MojangApiUtils.getSkinData(uuid, false)[0]));
            } catch (ExecutionException e) {
                Throwable cause = e.getCause();
                if (cause instanceof MojangApiException) {
                    throw (MojangApiException) cause;
                } else if (cause instanceof IOException) {
                    throw (IOException) cause;
                } else {
                    throw new RuntimeException(cause);
                }
            }
        }

        /**
         * Invalidates the uuid and texture value cache.
         * This means that when {@link HeadTexture#of(OfflinePlayer)}, {@link HeadTexture#of(UUID)}
         * and {@link HeadTexture#of(String)} are called, these values will be pulled from the
         * Mojang API again.
         *
         * @deprecated Prefer using {@link HeadTexture#HeadTexture(String)} with a hardcoded texture value instead of querying the Mojang API.
         */
        @Deprecated
        public static void invalidateCache() {
            uuidCache.invalidateAll();
            textureCache.invalidateAll();
        }

        /**
         * Gets the stored texture value.
         *
         * @return The stored texture value.
         */
        public @NonNull String getTextureValue() {
            return textureValue;
        }

    }

}