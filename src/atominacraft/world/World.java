package atominacraft.world;

import atominacraft.world.chunk.Chunk;
import atominacraft.world.chunk.ChunkCoordinates;

import java.util.HashMap;
import java.util.HashSet;

public class World {
    public int worldId;
    public String name;


    /**
     * Holds all of the loaded chunks, where the key is the hash code and the value is the chunk
     *
     * The key (hashcode) is the combination of the X and Z coordinates (for fast and easy access to chunks)
     */
    private final HashMap<Integer, Chunk> chunks;

    public World(int id, String name) {
        this.worldId = id;
        this.name = name;

        chunks = new HashMap<>();
    }

    public void update() {
        updateChunks();
    }

    public void updateChunks() {
        for(Chunk chunk : chunks.values()) {
            chunk.update();
        }
    }

    public Chunk createChunk(int x, int z) {
        ChunkCoordinates coordinates = new ChunkCoordinates(x, z);
        Chunk chunk = new Chunk(coordinates);
        chunks.put(coordinates.hashCode(), chunk);
        return chunk;
    }

    public Chunk getChunkAt(int x, int z) {
        int hash = Integer.hashCode(x) + Integer.hashCode(z);
        return chunks.get(hash);
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(this.worldId);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof World)) {
            return false;
        }

        return ((World)obj).worldId == this.worldId;
    }
}
