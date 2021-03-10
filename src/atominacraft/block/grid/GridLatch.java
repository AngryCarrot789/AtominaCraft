package atominacraft.block.grid;

import atominacraft.block.BlockLocation;
import atominacraft.maths.Vector3;
import atominacraft.world.chunk.ChunkLocation;

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

    public static int GetBlockInWorld(int blockCoordinate, int chunkCoordinate) {
        return (chunkCoordinate * ChunkWidth) + blockCoordinate;
    }

    public static int GetBlockInChunk(int blockLocation) {
        return blockLocation & ChunkWidthIndices;
    }

    public static int GetChunkFromBlock(int block) {
        return block >> ChunkWidthRBitshift;
    }

    public static ChunkLocation GetChunkFromBlock(BlockLocation blockLocation) {
        return new ChunkLocation(GetChunkFromBlock(blockLocation.x),
                                 GetChunkFromBlock(blockLocation.z));
    }

    public static Vector3 WTMGetBlock(int x, int y, int z) {
        return new Vector3(x + BlockScale,
                           y + BlockScale,
                           z + BlockScale);
    }

    public static Vector3 WTMGetBlock(BlockLocation location) {
        return WTMGetBlock(location.x, location.y, location.z);
    }

    public static Vector3 WTMGetChunk(int x, int z) {
        return new Vector3(((float)x * (float)ChunkWidth) + ((float) ChunkWidthHalf),
                           ChunkScaleHeight,
                           ((float)z * (float)ChunkWidth) + ((float) ChunkWidthHalf));
    }

    public static Vector3 WTMGetChunk(ChunkLocation location) {
        return WTMGetChunk(location.x, location.z);
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
        return a > 0 ? ((int) Math.floor(a) >> ChunkWidthRBitshift) : (((int) Math.ceil(a) - ChunkWidth) >> ChunkWidthRBitshift);
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
