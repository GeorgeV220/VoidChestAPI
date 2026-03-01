package com.georgev22.voidchest.api.utilities;

import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

/**
 * Utility class for safely playing sounds using {@link NamespacedKey}.
 *
 * <p>
 * Expected sound format: {@code minecraft:anvil.break}
 * </p>
 */
public final class SoundUtil {

    private SoundUtil() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated.");
    }

    /**
     * Plays a sound to a player at their current location.
     *
     * @param player   the target player
     * @param soundKey the namespaced sound key
     * @param volume   the volume (1.0 = normal)
     * @param pitch    the pitch (1.0 = normal)
     */
    public static void play(@Nullable Player player,
                            @Nullable String soundKey,
                            float volume,
                            float pitch) {

        if (player == null)
            return;

        play(player.getLocation(), soundKey, volume, pitch);
    }

    /**
     * Plays a sound at a specific location.
     *
     * @param location the location to play the sound at
     * @param soundKey the namespaced sound key
     * @param volume   the volume (1.0 = normal)
     * @param pitch    the pitch (1.0 = normal)
     */
    public static void play(@Nullable Location location,
                            @Nullable String soundKey,
                            float volume,
                            float pitch) {

        if (location == null || soundKey == null || soundKey.isBlank())
            return;

        World world = location.getWorld();
        if (world == null)
            return;

        Sound sound = resolve(soundKey);
        if (sound == null)
            return;

        world.playSound(location, sound, volume, pitch);
    }

    /**
     * Resolves a {@link Sound} from a namespaced key string.
     *
     * @param soundKey the namespaced key
     * @return the resolved {@link Sound}, or null if invalid
     */
    @Nullable
    private static Sound resolve(String soundKey) {
        try {
            NamespacedKey key = NamespacedKey.fromString(soundKey);
            if (key == null)
                return null;

            return Registry.SOUND_EVENT.get(key);
        } catch (Exception ignored) {
            return null;
        }
    }
}