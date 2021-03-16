package reghzy.atominacraft.world;

import reghzy.atominacraft.block.Block;
import reghzy.atominacraft.entity.Entity;
import reghzy.atominacraft.utils.HashHelper;
import reghzy.atominacraft.world.chunk.Chunk;
import reghzy.atominacraft.world.chunk.ChunkLocation;

import java.util.HashMap;
import java.util.HashSet;

public class World {
    public int worldId;
    public String name;

    private final HashSet<Entity> activeEntities;
    private final HashSet<Entity> toBeRemovedEntities;

    /**
     * Holds all of the loaded chunks, where the key is the hashcode of the X and Z coordinates and the value is the chunk
     * The key (hashcode) is the combination of the X and Z coordinates (for fast and easy access to chunks)
     */
    public final HashMap<Integer, Chunk> chunks;

    public World(int id, String name) {
        this.worldId = id;
        this.name = name;

        activeEntities = new HashSet<>();
        toBeRemovedEntities = new HashSet<>();
        chunks = new HashMap<>();
    }

    public void update() {
        if (toBeRemovedEntities.size() > 0) {
            activeEntities.removeAll(toBeRemovedEntities);
        }

        for (Entity entity : activeEntities) {
            entity.update();

            if (entity.isDead) {
                toBeRemovedEntities.add(entity);
            }
            //else {
            //    moveEntityToNewChunkIfOutside(entity);
            //}
        }

        updateChunks();
    }

    public void updateChunks() {
        for(Chunk chunk : chunks.values()) {
            chunk.update();
        }
    }

    public void spawnEntity(Entity entity) {
        activeEntities.add(entity);
    }

    public HashSet<Entity> getEntities() {
        return this.activeEntities;
    }

    public Chunk createChunk(int x, int z) {
        ChunkLocation coordinates = new ChunkLocation(x, z);
        Chunk chunk = new Chunk(this, coordinates);
        chunks.put(coordinates.hashCode(), chunk);
        return chunk;
    }

    public Chunk getChunkAt(int x, int z) {
        return chunks.get(HashHelper.getHash2i(x, z));
    }

    public Chunk getChunkAt(int hash) {
        return chunks.get(hash);
    }

    public Block getBlockAt(int worldX, int worldY, int worldZ) {
        Chunk chunk = this.getChunkAt(worldX >> 4, worldZ >> 4);
        if (chunk == null)
            return null;
        return chunk.getBlockAt(worldX & 15, worldY & 255, worldZ & 15);
    }

    @Override
    public int hashCode() {
        return this.worldId;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof World)) {
            return false;
        }

        return ((World)obj).worldId == this.worldId;
    }
}
