package atominacraft.block;

import atominacraft.utils.HashHelper;

import java.util.Objects;

public class BlockLocation {
    public final int x;
    public final int y;
    public final int z;

    public BlockLocation(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
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
