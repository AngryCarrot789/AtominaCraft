package atominacraft.world.chunk;

import atominacraft.block.Block;
import atominacraft.block.BlockLocation;
import atominacraft.entity.Entity;
import atominacraft.utils.HashHelper;

import java.util.HashMap;
import java.util.HashSet;

public class Chunk {
    public ChunkLocation coordinates;
    public HashSet<Entity> activeEntities;
    public HashSet<Entity> toBeRemovedEntities;

    public HashMap<Integer, Block> blocks;

    public Chunk(ChunkLocation coordinates) {
        this.coordinates = coordinates;

        activeEntities = new HashSet<>(32);
        toBeRemovedEntities = new HashSet<>(32);
        blocks = new HashMap<>(256);
    }

    public void setBlock(Block block, int x, int y, int z) {
        if (block.location == null) {
            block.location = new BlockLocation(x, y, z);
        }
        int hash = HashHelper.getHashi(x, y, z);
        Block old = blocks.put(hash, block);
        if (old != null) {
            old.onDestroyed();
        }
    }

    public void update() {
        if (toBeRemovedEntities.size() > 0) {
            activeEntities.removeAll(toBeRemovedEntities);
        }

        for(Entity entity : activeEntities) {
            entity.update();
        }
    }

    @Override
    public int hashCode() {
        return this.coordinates.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Chunk)) {
            return false;
        }
        return ((Chunk)obj).coordinates.equals(this.coordinates);
    }
}
