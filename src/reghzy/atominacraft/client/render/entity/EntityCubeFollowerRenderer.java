package reghzy.atominacraft.client.render.entity;

import reghzy.atominacraft.client.render.Tesselator;
import reghzy.atominacraft.entity.Entity;
import reghzy.atominacraft.entity.EntityCubeFollower;
import reghzy.atominacraft.entity.player.viewer.Camera;
import reghzy.atominacraft.maths.Vector3;

public class EntityCubeFollowerRenderer extends EntityRenderer {
    @Override
    public void render(Camera camera, Entity entity) {
        EntityCubeFollower cubeFollower = (EntityCubeFollower) entity;
        if (entity.getChunk() == null) {
            cubeFollower.position.set(cubeFollower.follower.position);
        }
        else {
            Tesselator.drawCube(camera, entity.position, Vector3.One, 1.0f, 0.4f, 0.2f);
        }
    }
}
