package reghzy.atominacraft.client.render;

import reghzy.atominacraft.client.render.entity.EntityCubeFollowerRenderer;
import reghzy.atominacraft.client.render.entity.EntityRenderer;
import reghzy.atominacraft.entity.Entity;
import reghzy.atominacraft.entity.EntityCubeFollower;
import reghzy.atominacraft.entity.player.viewer.Camera;

import java.util.HashMap;

public class RenderingManager {
    public static HashMap<Class<? extends Entity>, EntityRenderer> entityRenderers;

    public static void init() {
        entityRenderers = new HashMap<>();
    }

    public static void setupDefaultEntityRenderers() {
        entityRenderers.put(EntityCubeFollower.class, new EntityCubeFollowerRenderer());
    }

    public static void renderEntity(Camera camera, Entity entity) {
        EntityRenderer renderer = entityRenderers.get(entity.getClass());
        if (renderer != null) {
            renderer.render(camera, entity);
        }
    }
}
