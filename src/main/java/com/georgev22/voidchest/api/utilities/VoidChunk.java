package com.georgev22.voidchest.api.utilities;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.Objects;
import java.util.Optional;


public class VoidChunk implements Serializable {
    @Serial
    private static final long serialVersionUID = 2L;

    private String worldName;
    private int x;
    private int z;

    /**
     * Creates a new VoidChunk instance.
     *
     * @param worldName The name of the world.
     * @param x         The X coordinate of the chunk.
     * @param z         The Z coordinate of the chunk.
     */
    public VoidChunk(String worldName, int x, int z) {
        this.worldName = worldName;
        this.x = x;
        this.z = z;
    }

    /**
     * Returns the name of the world.
     *
     * @return The name of the world.
     */
    public String getWorldName() {
        return worldName;
    }

    /**
     * Returns the X coordinate of the chunk.
     *
     * @return The X coordinate of the chunk.
     */
    public int getX() {
        return x;
    }

    /**
     * Returns the Z coordinate of the chunk.
     *
     * @return The Z coordinate of the chunk.
     */
    public int getZ() {
        return z;
    }

    /**
     * Converts the VoidChunk to a Bukkit Chunk.
     *
     * @return An Optional containing the Bukkit Chunk, or an empty Optional if the world is not found.
     */
    public Optional<Chunk> toBukkitChunk() {
        World world = Bukkit.getWorld(worldName);
        if (world != null) {
            return Optional.of(world.getChunkAt(x, z));
        }
        return Optional.empty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VoidChunk voidChunk)) return false;
        return x == voidChunk.x && z == voidChunk.z && Objects.equals(worldName, voidChunk.worldName);
    }

    @Override
    public int hashCode() {
        int result = worldName != null ? worldName.hashCode() : 0;
        result = 31 * result + Integer.hashCode(x);
        result = 31 * result + Integer.hashCode(z);
        return result;
    }

    /**
     * Custom serialization method to write the object to an ObjectOutputStream.
     *
     * @param out The ObjectOutputStream to write to.
     * @throws IOException If an I/O error occurs during serialization.
     */
    @Serial
    private void writeObject(@NotNull ObjectOutputStream out) throws IOException {
        out.writeUTF(worldName);
        out.writeInt(x);
        out.writeInt(z);
    }

    /**
     * Custom deserialization method to read the object from an ObjectInputStream.
     *
     * @param in The ObjectInputStream to read from.
     * @throws IOException            If an I/O error occurs during deserialization.
     * @throws ClassNotFoundException If the class of the serialized object cannot be found.
     */
    @Serial
    private void readObject(@NotNull ObjectInputStream in) throws IOException, ClassNotFoundException {
        this.worldName = in.readUTF();
        this.x = in.readInt();
        this.z = in.readInt();
    }

}
