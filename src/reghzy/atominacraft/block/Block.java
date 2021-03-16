package reghzy.atominacraft.block;

import reghzy.atominacraft.utils.HashHelper;
import reghzy.atominacraft.world.World;
import reghzy.atominacraft.world.chunk.Chunk;

public class Block {
    // template block instances must never be used in-game, only as a "template". when they are to be
    // placed or used... you must copy them. They never hold references, they're only copied
    public static final Block TEMPLATE_AIR = new Block(0, 0);
    public static final Block TEMPLATE_DIRT = new Block(1, 0);
    public static final Block TEMPLATE_SNOW = new Block(10, 0);

    public int id;
    public int metadata;
    public boolean transparent;

    public World world;
    public Chunk chunk;
    public BlockLocation location;

    public Block(int id, int metadata) {
        this.id = id;
        this.metadata = metadata;
    }

    public Block(World world, BlockLocation location, int id, int metadata) {
        this(id, metadata);
        this.chunk = world.getChunkAt(this.location.x >> 4, this.location.z >> 4);
        placeInWorld(world, location);
    }

    public boolean isEmpty() {
        return this.id == 0;
    }

    public void placeInWorld(World world, BlockLocation location) {
        this.world = world;
        this.location = location;
        onPlaced();
    }

    public void onPlaced() {

    }

    public void onDestroyed() {

    }

    public Block copyTemplate() {
        Block copied = new Block(this.id, this.metadata);
        // custom data for the block
        return copied;
    }

    @Override
    public int hashCode() {
        return location != null ? location.hashCode() : HashHelper.getHash2i(this.id, this.metadata);
    }
}
