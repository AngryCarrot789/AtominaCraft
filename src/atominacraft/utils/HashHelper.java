package atominacraft.utils;

import atominacraft.block.BlockLocation;
import atominacraft.world.chunk.ChunkLocation;

public class HashHelper {
    public static int getBlockHash(BlockLocation location) {
        return getHashi(location.x, location.y, location.z);
    }

    public static int getChunkHash(ChunkLocation location) {
        return getHashi(location.x, location.z);
    }

    public static int getHashi(int a, int b) {
        int result = 1;
        result = 31 * result + a;
        result = 31 * result + b;
        return result;
    }

    public static int getHashi(int a, int b, int c) {
        int result = 1;
        result = 31 * result + a;
        result = 31 * result + b;
        result = 31 * result + c;
        return result;
    }

    public static int getHashi(int a, int b, int c, int d) {
        int result = 1;
        result = 31 * result + a;
        result = 31 * result + b;
        result = 31 * result + c;
        result = 31 * result + d;
        return result;
    }

    public static int getHashf(float a, float b) {
        int result = 1;
        result = 31 * result + Float.floatToIntBits(a);
        result = 31 * result + Float.floatToIntBits(b);
        return result;
    }

    public static int getHashf(float a, float b, float c) {
        int result = 1;
        result = 31 * result + Float.floatToIntBits(a);
        result = 31 * result + Float.floatToIntBits(b);
        result = 31 * result + Float.floatToIntBits(c);
        return result;
    }

    public static int getHashf(float a, float b, float c, float d) {
        int result = 1;
        result = 31 * result + Float.floatToIntBits(a);
        result = 31 * result + Float.floatToIntBits(b);
        result = 31 * result + Float.floatToIntBits(c);
        result = 31 * result + Float.floatToIntBits(d);
        return result;
    }
}
