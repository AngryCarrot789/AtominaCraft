package reghzy.atominacraft.world.chunk;

import reghzy.atominacraft.block.Block;
import reghzy.atominacraft.block.BlockLocation;
import reghzy.atominacraft.utils.HashHelper;
import reghzy.atominacraft.world.World;

import java.util.HashMap;

public class Chunk {
    public ChunkLocation coordinates;

    public HashMap<Integer, Block> blocks;

    public World world;

    public Chunk(World world, ChunkLocation coordinates) {
        this.world = world;
        this.coordinates = coordinates;
        this.coordinates.chunk = this;
        blocks = new HashMap<>(256);
    }

    public void setBlock(Block block, int x, int y, int z) {
        if (x > 15 || z > 15 || y > 255) {
            return;
        }

        if (block.location == null) {
            block.location = new BlockLocation(x, y, z);
            block.location.chunk = this;
        }
        Block old = blocks.put(block.location.hashCode(), block);
        if (old != null) {
            old.onDestroyed();
            System.out.println("Block Duplicate! at: " + x + ", " + y + ", " + z + "! Old at: " + old.location.toString());
        }
    }

    public Block getBlockAt(int x, int y, int z) {
        if (x > 15 || z > 15 || y > 255) {
            return getBlockInWorld(x, y, z);
        }
        return blocks.get(HashHelper.getHash3i(x, y, z));
    }

    public Block getBlockInWorld(int worldX, int worldY, int worldZ) {
        Chunk chunk = this.world.getChunkAt(this.coordinates.hashCode());
        if (chunk == null)
            return null;
        return chunk.getBlockAt(worldX & 15, worldY & 255, worldZ & 15);
    }

    public void update() {

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
