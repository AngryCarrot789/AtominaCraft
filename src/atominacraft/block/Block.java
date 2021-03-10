package atominacraft.block;

import atominacraft.world.World;

public class Block {
    // template block instances must never be used in-game, only as a "template". when they are to be
    // placed or used... you must copy them. They never hold references, they're only copied
    public static final Block TEMPLATE_AIR = new Block(0, 0);
    public static final Block TEMPLATE_DIRT = new Block(1, 0);

    public int id;
    public int metadata;

    public World world;
    public BlockLocation location;

    public Block(int id, int metadata) {
        this.id = id;
        this.metadata = metadata;
    }

    public Block(World world, BlockLocation location, int id, int metadata) {
        this(id, metadata);
        placeInWorld(world, location);
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
}
