package reghzy.atominacraft.client.render.entity;

import reghzy.atominacraft.entity.Entity;
import reghzy.atominacraft.entity.player.viewer.Camera;

public abstract class EntityRenderer {
    public abstract void render(Camera camera, Entity entity);
}
