package reghzy.atominacraft.client.render.mesh.generator;

import reghzy.atominacraft.block.Block;
import reghzy.atominacraft.block.BlockLocation;
import reghzy.atominacraft.client.render.WorldMeshMap;
import reghzy.atominacraft.client.render.mesh.BlockMesh;
import reghzy.atominacraft.world.chunk.Chunk;

import java.util.ArrayList;
import java.util.HashMap;

public class ChunkMeshGenerator {
    public static class BlockFaceVisibility {
        public boolean top, front, left, bottom, right, back;

        public BlockFaceVisibility() { }

        public BlockFaceVisibility(boolean b) {
            this(b, b, b, b, b, b);
        }

        public BlockFaceVisibility(boolean top, boolean front, boolean left, boolean bottom, boolean right, boolean back) {
            this.top = top;
            this.front = front;
            this.left = left;
            this.bottom = bottom;
            this.right = right;
            this.back = back;
        }
    }

    public static void generateChunk(Chunk chunk) {
        HashMap<Block, BlockMesh> blockMeshMap = new HashMap<>(chunk.blocks.size());
        ArrayList<Float> vertices = new ArrayList<>();
        ArrayList<Float> normals = new ArrayList<>();
        ArrayList<Float> uvs = new ArrayList<>();

        for (int y = 0; y < 256; y++) {
            for (int x = 0; x < 16; x++) {
                for (int z = 0; z < 16; z++) {
                    Block block = chunk.getBlockAt(x, y, z);
                    if (block == null)
                        continue;

                    CubeBuilder builder = generateCube(getVisibleFaces(block));
                    builder.writeVertices(vertices);
                    builder.writeUvs(uvs);
                    builder.writeNormals(normals, vertices);

                    BlockMesh mesh = new BlockMesh(vertices, uvs, normals);
                    blockMeshMap.put(block, mesh);

                    vertices.clear();
                    uvs.clear();
                    normals.clear();
                }
            }
        }

        WorldMeshMap.addChunkCubeMeshMap(chunk, blockMeshMap);
    }

    public static CubeBuilder generateCube(BlockFaceVisibility visibility) {
        return new CubeBuilder(visibility.top, visibility.front, visibility.left, visibility.bottom, visibility.right, visibility.back);
    }

    //public HashMap<Block, BlockFaceVisibility> generateBlockFaces(Chunk chunk) {
    //    ArrayList<BlockFaceVisibility> visibilitiesZ = new ArrayList<>(16);
    //    for(int y = 0; y < 255; y++) {
    //        for (int x = 0; x < 16; x++) {
    //            Block a = chunk.blocks.get(HashHelper.getHash3i(x, y, 0));
    //            Block b = chunk.blocks.get(HashHelper.getHash3i(x, y, 1));
    //            Block c = chunk.blocks.get(HashHelper.getHash3i(x, y, 2));
    //            Block d = chunk.blocks.get(HashHelper.getHash3i(x, y, 3));
    //            Block e = chunk.blocks.get(HashHelper.getHash3i(x, y, 4));
    //            Block f = chunk.blocks.get(HashHelper.getHash3i(x, y, 5));
    //            Block g = chunk.blocks.get(HashHelper.getHash3i(x, y, 6));
    //            Block h = chunk.blocks.get(HashHelper.getHash3i(x, y, 7));
    //            Block i = chunk.blocks.get(HashHelper.getHash3i(x, y, 8));
    //            Block j = chunk.blocks.get(HashHelper.getHash3i(x, y, 9));
    //            Block k = chunk.blocks.get(HashHelper.getHash3i(x, y, 10));
    //            Block l = chunk.blocks.get(HashHelper.getHash3i(x, y, 11));
    //            Block m = chunk.blocks.get(HashHelper.getHash3i(x, y, 12));
    //            Block n = chunk.blocks.get(HashHelper.getHash3i(x, y, 13));
    //            Block o = chunk.blocks.get(HashHelper.getHash3i(x, y, 14));
    //            Block p = chunk.blocks.get(HashHelper.getHash3i(x, y, 15));
    //            BlockFaceVisibility[] visibilities = new BlockFaceVisibility[16];
    //            getVisible(a, b, c, d, e, f, g, h, i, j, k, l, m, n, o, p, visibilities);
    //        }
    //    }
    //}
    //public void getVisible(Block a, Block b, Block c, Block d,
    //                                      Block e, Block f, Block g, Block h,
    //                                      Block i, Block j, Block k, Block l,
    //                                      Block m, Block n, Block o, Block p,
    //                                      BlockFaceVisibility[] visibilities) {
    //    visibilities[0 ] = getZvisible(null, a, b);
    //    visibilities[1 ] = getZvisible(a, b, c);
    //    visibilities[2 ] = getZvisible(b, c, d);
    //    visibilities[3 ] = getZvisible(c, d, e);
    //    visibilities[4 ] = getZvisible(d, e, f);
    //    visibilities[5 ] = getZvisible(e, f, g);
    //    visibilities[6 ] = getZvisible(f, g, h);
    //    visibilities[7 ] = getZvisible(g, h, i);
    //    visibilities[8 ] = getZvisible(h, i, j);
    //    visibilities[9 ] = getZvisible(i, j, k);
    //    visibilities[10] = getZvisible(j, k, l);
    //    visibilities[11] = getZvisible(k, l, m);
    //    visibilities[12] = getZvisible(l, m, n);
    //    visibilities[13] = getZvisible(m, n, o);
    //    visibilities[14] = getZvisible(n, o, p);
    //    visibilities[15] = getZvisible(o, p, null);
    //}
    //public BlockFaceVisibility getZvisible(Block front, Block center, Block back) {
    //    if (center == null)
    //        return new BlockFaceVisibility(false);
    //    return new BlockFaceVisibility(false, isBlockVisible(front), false, false, false, isBlockVisible(back));
    //}
    //public boolean isBlockVisible(Block block) {
    //    return block != null || block.isEmpty() || block.transparent;
    //}

    public static BlockFaceVisibility getVisibleFaces(Block block) {
        BlockFaceVisibility visibility = new BlockFaceVisibility();
        Chunk chunk = block.location.chunk;
        BlockLocation location = block.location;
        Block left = chunk.getBlockAt(location.x - 1, location.y, location.z);
        Block righ = chunk.getBlockAt(location.x + 1, location.y, location.z);
        Block topp = chunk.getBlockAt(location.x, location.y + 1, location.z);
        Block botm = chunk.getBlockAt(location.x, location.y - 1, location.z);
        Block back = chunk.getBlockAt(location.x, location.y, location.z - 1);
        Block frnt = chunk.getBlockAt(location.x, location.y, location.z + 1);
        visibility.left = (left == null || left.isEmpty() || left.transparent);
        visibility.right = (righ == null || righ.isEmpty() || righ.transparent);
        visibility.top = (topp == null || topp.isEmpty() || topp.transparent);
        visibility.bottom = (botm == null || botm.isEmpty() || botm.transparent);
        visibility.back = (back == null || back.isEmpty() || back.transparent);
        visibility.front = (frnt == null || frnt.isEmpty() || frnt.transparent);
        return visibility;
    }
}