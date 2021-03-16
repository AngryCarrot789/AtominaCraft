package reghzy.atominacraft.block;

import reghzy.atominacraft.utils.HashHelper;
import reghzy.atominacraft.world.chunk.Chunk;

public class BlockLocation {
    public int x;
    public int y;
    public int z;
    public Chunk chunk;

    public BlockLocation(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Block getBlock() {
        if (chunk == null)
            return null;
        return chunk.getBlockAt(this.x, this.y, this.z);
    }

    @Override
    public String toString() {
        return "BlockLocation{" + this.x + "," + this.y + "," + this.z + "}";
    }

    @Override
    public int hashCode() {
        return HashHelper.getHash3i(x, y, z);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof BlockLocation)) {
            return false;
        }

        BlockLocation location = ((BlockLocation) obj);
        return location.x == this.x && location.y == this.y && location.z == this.z;
    }
}
