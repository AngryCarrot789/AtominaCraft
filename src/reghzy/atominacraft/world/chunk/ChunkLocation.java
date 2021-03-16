package reghzy.atominacraft.world.chunk;

import reghzy.atominacraft.utils.HashHelper;

public class ChunkLocation {
    public final int x;
    public final int z;
    public Chunk chunk;

    public ChunkLocation(int x, int z) {
        this.x = x;
        this.z = z;
    }

    public static int hash(int x, int z) {
        return HashHelper.getHash2i(x, z);
    }

    @Override
    public int hashCode() {
        return ChunkLocation.hash(this.x, this.z);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ChunkLocation)) {
            return false;
        }

        ChunkLocation coordinates = ((ChunkLocation) obj);
        return coordinates.x == this.x && coordinates.z == this.z;
    }

    @Override
    public String toString() {
        return "ChunkLocation{" + this.x + "," + this.z + "}";
    }
}
