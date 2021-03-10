package atominacraft.world.chunk;

import atominacraft.world.World;

public class ChunkCoordinates {
    public World world;
    public final int x;
    public final int z;

    public ChunkCoordinates(int x, int z) {
        this.x = x;
        this.z = z;
    }

    public ChunkCoordinates(World world, int x, int z) {
        this.world = world;
        this.x = x;
        this.z = z;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(this.x) + Integer.hashCode(this.z);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ChunkCoordinates)) {
            return false;
        }

        ChunkCoordinates coordinates = ((ChunkCoordinates) obj);
        return coordinates.x == this.x && coordinates.z == this.z;
    }
}
