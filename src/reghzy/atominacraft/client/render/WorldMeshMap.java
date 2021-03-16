package reghzy.atominacraft.client.render;

import reghzy.atominacraft.block.Block;
import reghzy.atominacraft.client.render.mesh.BlockMesh;
import reghzy.atominacraft.client.render.mesh.generator.ChunkMeshGenerator;
import reghzy.atominacraft.world.chunk.Chunk;

import java.util.HashMap;

public class WorldMeshMap {
    public static HashMap<Chunk, HashMap<Block, BlockMesh>> chunkBlockMeshMap;

    public static void init() {
        chunkBlockMeshMap = new HashMap<>(512);
    }

    public static void regenerateChunk(Chunk chunk) {
        HashMap<Block, BlockMesh> blockBlockMeshMap = getBlockMeshMap(chunk);
        if (blockBlockMeshMap == null) {
            return;
        }

        for(BlockMesh blockMesh : blockBlockMeshMap.values()) {
            blockMesh.dispose();
        }
        blockBlockMeshMap.clear();
        chunkBlockMeshMap.remove(chunk);
        ChunkMeshGenerator.generateChunk(chunk);
    }

    public static HashMap<Block, BlockMesh> getBlockMeshMap(Chunk chunk) {
        return chunkBlockMeshMap.get(chunk);
    }

    public static void addChunkCubeMeshMap(Chunk chunk, HashMap<Block, BlockMesh> blockMeshMap) {
        chunkBlockMeshMap.put(chunk, blockMeshMap);
    }
}
