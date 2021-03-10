package atominacraft.world.chunk;

import atominacraft.entity.Entity;

import java.util.HashSet;

public class Chunk {
    public ChunkCoordinates coordinates;
    public HashSet<Entity> activeEntities;
    public HashSet<Entity> toBeRemovedEntities;

    public Chunk(ChunkCoordinates coordinates) {
        this.coordinates = coordinates;

        activeEntities = new HashSet<>(32);
        toBeRemovedEntities = new HashSet<>(32);
    }

    public void update() {
        if (toBeRemovedEntities.size() > 0) {
            activeEntities.removeAll(toBeRemovedEntities);
        }

        for(Entity entity : activeEntities) {
            entity.update();
        }
    }

    @Override
    public int hashCode() {
        return this.coordinates.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Chunk)) {
            return false;
        }
        return ((Chunk)obj).coordinates.equals(this.coordinates);
    }
}
