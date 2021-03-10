package atominacraft.world.chunk;

import atominacraft.utils.HashHelper;

public class ChunkLocation {
    public final int x;
    public final int z;

    public ChunkLocation(int x, int z) {
        this.x = x;
        this.z = z;
    }

    @Override
    public int hashCode() {
        return HashHelper.getHashi(this.x, this.z);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ChunkLocation)) {
            return false;
        }

        ChunkLocation coordinates = ((ChunkLocation) obj);
        return coordinates.x == this.x && coordinates.z == this.z;
    }
}
