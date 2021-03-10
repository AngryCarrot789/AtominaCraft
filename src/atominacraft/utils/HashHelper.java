package atominacraft.utils;

import atominacraft.block.BlockLocation;
import atominacraft.world.chunk.ChunkLocation;

public class HashHelper {
    public static int getBlockHash(BlockLocation location) {
        return getHash3i(location.x, location.y, location.z);
    }

    public static int getChunkHash(ChunkLocation location) {
        return getHash2i(location.x, location.z);
    }

    public static int getHash1i(int a) {
        return a;
    }

    public static int getHash2i(int a, int b) {
        return a + (b << 8);
    }

    public static int getHash3i(int a, int b, int c) {
        return a + (b << 8) + (c << 16);
        //return (a | (b << 8)) | (b | (c << 16));
    }

    public static int getHash4i(int a, int b, int c, int d) {
        return a + (b << 8) + (c << 16) + (d << 24);
    }

    public static int getHash1f(float a) {
        return Float.hashCode(a);
    }

    public static int getHash2f(float a, float b) {
        return Float.hashCode(a) + (Float.hashCode(b) << 8);
    }

    public static int getHash3f(float a, float b, float c) {
        return Float.hashCode(a) + (Float.hashCode(b) << 8) + (Float.hashCode(c) << 16);
    }

    public static int getHash4f(float a, float b, float c, float d) {
        return Float.hashCode(a) + (Float.hashCode(b) << 8) + (Float.hashCode(c) << 16) + (Float.hashCode(d) << 24);
    }
}
