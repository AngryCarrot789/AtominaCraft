package reghzy.atominacraft.block.grid;

import reghzy.atominacraft.block.BlockLocation;
import reghzy.atominacraft.entity.Entity;
import reghzy.atominacraft.maths.Vector3;
import reghzy.atominacraft.world.chunk.Chunk;
import reghzy.atominacraft.world.chunk.ChunkLocation;

/**
 * <h1>
 *     GridLatch
 * </h1>
 * <h3>
 *     Converts between Block Coordinates and 3D-World / Floating-point coordinates
 * </h3>
 * <p>
 *     WTM means 'World to Matrix', and its used for translating (usually) blocks into 3d world coordinates; ints to vectors and floats basically.
 * </p>
 * <p>
 *     MTW means 'Matrix to World', and its does the opposite; "Latches" vectors and floats/decimals to integers (hence the class name, grid latch)
 * </p>
 * <p>
 *     WTM calculations are only really done when rendering to convert a block's world location to a vector that is offset by the location and scale of a block
 * </p>
 * <p>
 *     MTW calculations are normally used with entities and stuff, e.g. getting the block or chunk position of an entity
 * </p>
 */
public class GridLatch {
    // these numbers are simply used as constants and for me to actually remember things lmao...
    // modifying these wont nessesarily affect the world, and if it does then lmao
    public static final float BlockScale = 0.5f;
    public static final float BlockWidth = 1.0f;
    public static final int BlockWidthI = 1;

    public static final float ChunkScaleWidth = 8.0f;
    public static final float ChunkScaleHeight = 128.0f;
    public static final int ChunkHeight = 256;
    public static final int ChunkWidth = 16;
    public static final int ChunkWidthHalf = 8;
    public static final int ChunkHeightIndices = 255;
    public static final int ChunkWidthIndices = 15;
    public static final int ChunkWidthRBitshift = 4;

    public static Vector3 BlockScaleV = new Vector3(BlockScale, BlockScale, BlockScale);
    public static Vector3 ChunkScaleV = new Vector3(ChunkScaleWidth, ChunkScaleHeight, ChunkScaleWidth);

    // block in world from chunk
    // means you're getting the block coordinate within the world, using a chunk coordinate.
    // in means within something
    // from means using something
    // if in and from are the same, just use in.
    // GetBlockInChunkFromChunk... no, GetBlockInChunk

    public static int GetBlockInWorldFromChunk(int blockCoordinate, int chunkCoordinate) {
        return (chunkCoordinate * ChunkWidth) + blockCoordinate;
    }

    public static int GetBlockFromWorldInChunk(int blockWorldLocation) {
        return blockWorldLocation & ChunkWidthIndices;
    }

    public static int GetChunkFromBlockInWorld(int blockWorldCoordinate) {
        return blockWorldCoordinate >> ChunkWidthRBitshift;
    }

    public static Vector3 WTMGetBlockInChunk(int x, int y, int z) {
        return new Vector3(x + BlockScale,
                           y + BlockScale,
                           z + BlockScale);
    }

    public static Vector3 WTMGetBlockInChunk(BlockLocation location) {
        return WTMGetBlockInChunk(location.x, location.y, location.z);
    }

    public static Vector3 WTMGetBlockInWorldFromChunk(ChunkLocation chunkLocation, BlockLocation location) {
        return WTMGetChunkWorld(chunkLocation).add(WTMGetBlockInChunk(location)).subtract(ChunkScaleV);
    }

    public static Vector3 WTMGetEntityInWorld(Entity entity) {
        Chunk chunk = entity.getChunk();
        return WTMGetChunkWorld(chunk.coordinates).add(entity.position);
    }

    public static Vector3 WTMGetChunkWorld(int chunkX, int chunkZ) {
        return new Vector3(((float)chunkX * (float)ChunkWidth) + ((float) ChunkWidthHalf),
                           ChunkScaleHeight,
                           ((float)chunkZ * (float)ChunkWidth) + ((float) ChunkWidthHalf));
    }

    public static Vector3 WTMGetChunkWorld(ChunkLocation location) {
        return WTMGetChunkWorld(location.x, location.z);
    }

    public static BlockLocation MTWGetBlock(float x, float y, float z) {
        return new BlockLocation(
                x > 0 ? (int) MTWPositive(x) : ((int) MTWNegative(x) - BlockWidthI),
                y > 0 ? (int) MTWPositive(y) : ((int) MTWNegative(y) - BlockWidthI),
                z > 0 ? (int) MTWPositive(z) : ((int) MTWNegative(z) - BlockWidthI));
    }

    public static BlockLocation MTWGetBlock(Vector3 v) {
        return MTWGetBlock(v.x, v.y, v.z);
    }

    public static ChunkLocation MTWGetChunk(float x, float z) {
        // i could've used the helper MTWGetChunk(float) function here.... but this looks cooler ;))
        // and is probably faster for the JVM too...
        return new ChunkLocation(
                x > 0 ? ((int) Math.floor(x) >> ChunkWidthRBitshift) : (((int) Math.ceil(x) - ChunkWidth) >> ChunkWidthRBitshift),
                x > 0 ? ((int) Math.floor(x) >> ChunkWidthRBitshift) : (((int) Math.ceil(x) - ChunkWidth) >> ChunkWidthRBitshift));
    }

    public static int MTWGetChunk(float a) {
        if (a > 0)
            return (int) Math.floor(a) >> ChunkWidthRBitshift;
        else
            return ((int) Math.ceil(a) - ChunkWidth) >> ChunkWidthRBitshift;
    }

    public static ChunkLocation MTWGetChunk(Vector3 v) {
        return MTWGetChunk(v.x, v.z);
    }

    public static float MTWChunkPositive(float a) {
        return (float) ((int) MTWPositive(a) >> ChunkWidthRBitshift);
    }

    public static float MTWChunkNegative(float a) {
        return (float) ((int) (MTWNegative(a) - ChunkWidth) >> ChunkWidthRBitshift);
    }

    public static float MTWPositive(float a) {
        return (float) Math.floor(a);
    }

    public static float MTWNegative(float a) {
        return (float) Math.ceil(a);
    }
}
