package reghzy.atominacraft.entity;

import reghzy.atominacraft.maths.Vector3;
import reghzy.atominacraft.time.GameTime;
import reghzy.atominacraft.world.World;

public class EntityCubeFollower extends Entity {
    public Entity follower;
    public Vector3 targetPositon;

    public EntityCubeFollower(Entity follower, World world) {
        super(world);
        this.follower = follower;
        this.targetPositon = new Vector3();
    }

    public void update() {
        if (this.ticksExisted % 1000 == 0) {
            this.targetPositon.set(this.follower.position);
        }
        else {
            float move = 2.0f * GameTime.delta;
            this.moveTowardsPosition(this.targetPositon, move, move, move);
        }
        super.update();
    }
}
