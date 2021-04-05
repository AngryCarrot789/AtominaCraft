package reghzy.atominacraft.client.render;

import reghzy.atominacraft.client.render.entity.EntityCubeFollowerRenderer;
import reghzy.atominacraft.client.render.entity.EntityRenderer;
import reghzy.atominacraft.entity.Entity;
import reghzy.atominacraft.entity.EntityCubeFollower;
import reghzy.atominacraft.entity.player.viewer.Camera;

import java.util.HashMap;

public class RenderingManager {
    private static final HashMap<Class<? extends Entity>, EntityRenderer> entityRenderers;

    public static void renderEntity(Camera camera, Entity entity) {
        EntityRenderer renderer = entityRenderers.get(entity.getClass());
        if (renderer != null) {
            renderer.render(camera, entity);
        }
    }

    public static void registerRenderer(Class<? extends Entity> entityClass, EntityRenderer renderer) {
        entityRenderers.put(entityClass, renderer);
    }

    public static EntityRenderer unregisterRenderer(Class<? extends Entity> entityClass) {
        return entityRenderers.remove(entityClass);
    }

    public static void initEntityRenderer() {
        registerRenderer(EntityCubeFollower.class, new EntityCubeFollowerRenderer());
    }

    static {
        entityRenderers = new HashMap<>();
    }
}
