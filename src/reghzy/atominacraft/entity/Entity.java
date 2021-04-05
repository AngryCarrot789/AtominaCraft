package reghzy.atominacraft.entity;

import reghzy.atominacraft.block.Block;
import reghzy.atominacraft.block.grid.GridLatch;
import reghzy.atominacraft.collision.AxisAlignedBB;
import reghzy.atominacraft.maths.Maths;
import reghzy.atominacraft.maths.Matrix4;
import reghzy.atominacraft.maths.Vector3;
import reghzy.atominacraft.world.World;
import reghzy.atominacraft.world.chunk.Chunk;
import reghzy.atominacraft.world.chunk.ChunkLocation;

public class Entity {
    public static int nextId = 0;
    public int id;

    /**
     * <h1>
     * The euler rotation.
     * </h1>
     * With eulers, the rotation axis are locked. E.g, if you want to look horizontally then vertically,
     * you cant just edit the Y and X rotation, you have to play with the Z rotation too
     * <p>
     * To work around this, use Quaternions
     * </p>
     */
    public Vector3 euler;
    public Vector3 scale;
    public Vector3 velocity;
    public Vector3 position;
    public Vector3 previousPosition;
    public Vector3 positionDifference;

    public AxisAlignedBB boundingBox;

    public World world;
    private Chunk chunk;

    /**
     * The number of ticks the entity has existed for. This value increases by (atleast most the time) by 200 times per second
     * <p>
     *     200 ticks means the entity has existed for 1 second. 1000 ticks means its existed for 5 seconds
     * </p>
     */
    public int ticksExisted;

    public boolean isDead;

    public Entity(World world) {
        this.id = Entity.nextId + 1;
        Entity.nextId += 2;

        this.world = world;
        this.position = new Vector3();
        this.previousPosition = new Vector3();
        this.positionDifference = new Vector3();
        this.velocity = new Vector3();
        this.euler = new Vector3();
        this.scale = new Vector3();
        this.boundingBox = AxisAlignedBB.fromCenterScale(position, scale);

        this.ticksExisted = 0;
    }

    public void update() {
        ticksExisted++;

        this.positionDifference.set(this.position.x - this.previousPosition.x,
                                    this.position.y - this.previousPosition.y,
                                    this.position.z - this.previousPosition.z);
        this.previousPosition.set(this.position);

        this.chunk = this.world.getChunkAt(ChunkLocation.hash(GridLatch.MTWGetChunk(this.position.x), GridLatch.MTWGetChunk(this.position.z)));
    }

    public void updateCollision() {
        boundingBox.repositionFromCenterAndScale(this.position, this.scale);

        if (this.getChunk() != null) {
            Chunk chunk = getChunk();

            for (Block block : chunk.blocks.values()) {
                if (this.boundingBox.intersectsAABB(block.boundingBox)) {
                    Vector3 intersection = this.boundingBox.getIntersectAmount(block.boundingBox);//.clampPositive().invertUnit();
                    Vector3 direction = this.boundingBox.intersectDirection(block.boundingBox);
                    this.position.subtract(intersection.multiply(direction));
                }
            }
        }
    }

    public void moveTo(Vector3 position) {
        this.previousPosition.set(this.position);
        this.position.set(position);
    }

    public void moveTowards(float amountX, float amountY, float amountZ) {
        this.previousPosition.set(this.position);
        this.position.add(amountX, amountY, amountZ);
    }

    public void moveTowards(Vector3 v) {
        this.previousPosition.set(this.position);
        this.position.add(v);
    }

    public void moveTowardsEntity(Entity entity, float amountX, float amountY, float amountZ) {
        moveTowardsPosition(entity.position, amountX, amountY, amountZ);
    }

    /**
     * moves towards the given position by the given amounts.
     * @param position
     * @param amountX
     * @param amountY
     * @param amountZ
     */
    public void moveTowardsPosition(Vector3 position, float amountX, float amountY, float amountZ) {
        if (this.position.x < position.x) {
            this.position.x += amountX;
        }
        else if (this.position.x > position.x) {
            this.position.x -= amountX;
        }
        if (this.position.y < position.y) {
            this.position.y += amountY;
        }
        else if (this.position.y > position.y) {
            this.position.y -= amountY;
        }
        if (this.position.z < position.z) {
            this.position.z += amountZ;
        }
        else if (this.position.z > position.z) {
            this.position.z -= amountZ;
        }
    }

    public float distanceFromEntity(Entity entity) {
        return Maths.DistanceBetween(this.position, entity.position);
    }

    public Chunk getChunk() {
        if (this.chunk == null) {
            this.chunk = this.world.getChunkAt(ChunkLocation.hash(GridLatch.MTWGetChunk(this.position.x), GridLatch.MTWGetChunk(this.position.z)));
        }
        return chunk;
    }

    /**
     * Checks if the given entity is close to this entity in the upper X Y Z coordinates
     */
    public boolean isEntityNearUpper(Entity entity, float distX, float distY, float distZ) {
        return (position.x + distX) < entity.position.x ||
               (position.y + distY) < entity.position.y ||
               (position.z + distZ) < entity.position.z;
    }

    public boolean isEntityNearLower(Entity entity, float distX, float distY, float distZ) {
        return (position.x - distX) > entity.position.x ||
               (position.y - distY) > entity.position.y ||
               (position.z - distZ) > entity.position.z;
    }

    public boolean isEntityNear(Entity entity, float nearX, float nearY, float nearZ) {
        return isEntityNearLower(entity, nearX,nearY, nearZ) ||
               isEntityNearUpper(entity, nearX, nearY, nearZ);
    }

    public static Matrix4 worldToLocal(Entity entity) {
        return Matrix4.worldToLocal(entity.position, entity.euler, Vector3.One);
    }

    public static Matrix4 localToWorld(Entity entity) {
        return Matrix4.localToWorld(entity.position, entity.euler, Vector3.One);
    }
}
