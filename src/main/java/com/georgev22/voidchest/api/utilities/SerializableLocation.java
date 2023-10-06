package com.georgev22.voidchest.api.utilities;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serial;
import java.io.Serializable;

public class SerializableLocation implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private transient Location location;
    private final String worldName;
    private final double x;
    private final double y;
    private final double z;
    private final float yaw;
    private final float pitch;

    public SerializableLocation(@NotNull Location location) {
        this.location = location;
        this.worldName = location.getWorld().getName();
        this.x = location.getX();
        this.y = location.getY();
        this.z = location.getZ();
        this.yaw = location.getYaw();
        this.pitch = location.getPitch();
    }

    @Contract("_ -> new")
    public static @NotNull SerializableLocation fromLocation(Location location) {
        return new SerializableLocation(location);
    }

    public @NotNull String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        Location loc = location.clone();
        stringBuilder
                .append(loc.getWorld().getName())
                .append(":")
                .append(loc.getX())
                .append(":")
                .append(loc.getY())
                .append(":")
                .append(loc.getZ())
                .append(":")
                .append(loc.getPitch())
                .append(":")
                .append(loc.getYaw());

        return stringBuilder.toString();
    }

    public static @Nullable Location fromString(@NotNull String string) {
        if (string.trim().isEmpty()) {
            return null;
        }
        String[] parts = string.split(":");
        World world = Bukkit.getServer().getWorld(parts[0]);
        double x = Double.parseDouble(parts[1]);
        double y = Double.parseDouble(parts[2]);
        double z = Double.parseDouble(parts[3]);
        float pitch = Float.parseFloat(parts[4]);
        float yaw = Float.parseFloat(parts[5]);
        if (world != null) {
            return new Location(world, x, y, z, pitch, yaw);
        }
        return null;
    }

    public Location toLocation() {
        World world = Bukkit.getWorld(worldName);
        if (world != null) {
            location = new Location(world, x, y, z, yaw, pitch);
        }
        return location;
    }

    @Serial
    private void writeObject(java.io.@NotNull ObjectOutputStream out) throws java.io.IOException {
        out.defaultWriteObject();
    }

    @Serial
    private void readObject(java.io.@NotNull ObjectInputStream in) throws java.io.IOException, ClassNotFoundException {
        in.defaultReadObject();
        toLocation();
    }
}