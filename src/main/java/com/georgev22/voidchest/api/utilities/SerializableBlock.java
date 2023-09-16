package com.georgev22.voidchest.api.utilities;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.Serial;
import java.io.Serializable;

public class SerializableBlock implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private String worldName;
    private int x;
    private int y;
    private int z;

    public SerializableBlock(@NotNull Block block) {
        Location location = block.getLocation();
        this.worldName = location.getWorld().getName();
        this.x = location.getBlockX();
        this.y = location.getBlockY();
        this.z = location.getBlockZ();
    }

    @Contract("_ -> new")
    public static @NotNull SerializableBlock fromBlock(@NotNull Block block) {
        return new SerializableBlock(block);
    }

    public @NotNull String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        Block block = this.toBlock();
        Location location = block.getLocation().clone();
        stringBuilder
                .append(location.getWorld().getName())
                .append(":")
                .append(location.getBlockX())
                .append(":")
                .append(location.getBlockY())
                .append(":")
                .append(location.getBlockZ());

        return stringBuilder.toString();
    }

    public static @Nullable SerializableBlock fromString(@NotNull String string) {
        if (string == null || string.trim().isEmpty()) {
            return null;
        }
        String[] parts = string.split(":");
        World world = Bukkit.getServer().getWorld(parts[0]);
        int x = Integer.parseInt(parts[1]);
        int y = Integer.parseInt(parts[2]);
        int z = Integer.parseInt(parts[3]);
        if (world != null) {
            return new SerializableBlock(world.getBlockAt(x, y, z));
        }
        return null;
    }

    public static @NotNull SerializableBlock fromLocation(@NotNull Location location) {
        if (location == null) {
            return null;
        }
        return new SerializableBlock(location.getBlock());
    }

    public Block toBlock() {
        World world = Bukkit.getWorld(worldName);
        if (world != null) {
            return world.getBlockAt(x, y, z);
        }
        return null;
    }

    public String getWorldName() {
        return worldName;
    }

    public void setWorldName(String worldName) {
        this.worldName = worldName;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }
}
