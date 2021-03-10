package atominacraft.entity;

import atominacraft.block.grid.GridLatch;
import atominacraft.maths.Matrix4;
import atominacraft.maths.Vector3;
import atominacraft.time.GameTime;
import atominacraft.world.World;
import atominacraft.world.chunk.Chunk;

public class Entity {
    public static int nextId = 0;
    public int id;

    public Vector3 position;
    public Vector3 previousPosition;
    public Vector3 velocity;
    public Vector3 rotation;

    public World world;

    public boolean isDead;

    public Entity(World world) {
        this.id = Entity.nextId + 1;
        Entity.nextId += 2;

        this.world = world;
        this.position = new Vector3();
        this.previousPosition = new Vector3();
        this.velocity = new Vector3();
        this.rotation = new Vector3();
    }

    public void update() {
        this.previousPosition.set(this.position);
        float drag = 1.0f - 0.05f;
        this.velocity.set(velocity.x * drag,
                          velocity.y * drag,
                          velocity.z * drag);
        this.position.set(position.x + (velocity.x * GameTime.delta),
                          position.y + (velocity.y * GameTime.delta),
                          position.z + (velocity.z * GameTime.delta));
    }

    public void moveTo(Vector3 position) {
        this.previousPosition.set(this.position);
        this.position.set(position);
    }

    public Chunk getChunk() {
        //return this.world.getChunkAt(GridLatch.MTWGetChunk(position));
        return null;
    }

    public static Matrix4 worldToLocal(Entity entity) {
        return Matrix4.worldToLocal(entity.position, entity.rotation, Vector3.One);
    }

    public static Matrix4 localToWorld(Entity entity) {
        return Matrix4.localToWorld(entity.position, entity.rotation, Vector3.One);
    }
}
