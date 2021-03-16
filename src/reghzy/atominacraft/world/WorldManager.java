package reghzy.atominacraft.world;

import java.util.HashMap;

public class WorldManager {
    public HashMap<Integer, World> worlds;

    public WorldManager() {
        worlds = new HashMap<>();
    }

    public void registerWorld(World world) {
        worlds.put(world.worldId, world);
    }

    public void updateWorlds() {
        for(World world : worlds.values()) {
            world.update();
        }
    }
}
