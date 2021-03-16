package reghzy.atominacraft.client.render.mesh.generator;

import reghzy.atominacraft.client.render.mesh.generator.math.MeshVec2;
import reghzy.atominacraft.client.render.mesh.generator.math.MeshVec3;

import java.util.ArrayList;

public class BlockFace {
    public static final MeshVec3 V1 = new MeshVec3(1.0f, 1.0f, -1.0f);
    public static final MeshVec3 V2 = new MeshVec3(1.0f, -1.0f, -1.0f);
    public static final MeshVec3 V3 = new MeshVec3(1.0f, 1.0f, 1.0f);
    public static final MeshVec3 V4 = new MeshVec3(1.0f, -1.0f, 1.0f);
    public static final MeshVec3 V5 = new MeshVec3(-1.0f, 1.0f, -1.0f);
    public static final MeshVec3 V6 = new MeshVec3(-1.0f, -1.0f, -1.0f);
    public static final MeshVec3 V7 = new MeshVec3(-1.0f, 1.0f, 1.0f);
    public static final MeshVec3 V8 = new MeshVec3(-1.0f, -1.0f, 1.0f);

    public static final MeshVec2 VT1 = new MeshVec2(2.0f, 0.0f);
    public static final MeshVec2 VT2 = new MeshVec2(1.0f, 1.0f);
    public static final MeshVec2 VT3 = new MeshVec2(1.0f, 0.0f);
    public static final MeshVec2 VT4 = new MeshVec2(0.0f, 2.0f);
    public static final MeshVec2 VT5 = new MeshVec2(0.0f, 1.0f);
    public static final MeshVec2 VT6 = new MeshVec2(1.0f, -2.0f);
    public static final MeshVec2 VT7 = new MeshVec2(0.0f, -1.0f);
    public static final MeshVec2 VT8 = new MeshVec2(0.0f, -2.0f);
    public static final MeshVec2 VT9 = new MeshVec2(0.0f, 0.0f);
    public static final MeshVec2 VT10 = new MeshVec2(-1.0f, 1.0f);
    public static final MeshVec2 VT11 = new MeshVec2(-1.0f, 0.0f);
    public static final MeshVec2 VT12 = new MeshVec2(1.0f, -1.0f);
    public static final MeshVec2 VT13 = new MeshVec2(2.0f, 1.0f);
    public static final MeshVec2 VT14 = new MeshVec2(1.0f, 2.0f);

    // ----------------------------------------------------------------------------------------------|
    //                                                                                               |
    // Triangle Faces. there's 12 on each cube, and 2 faces per cube face, and each face contains    |
    //       3 vector3s making 9 floats, total of 18 verts per cube face, 108 total per cube         |
    //                                                                                               |
    // ----------------------------------------------------------------------------------------------|
    public static final TriangleFace T1 = new TriangleFace(V5, V3, V1, VT1, VT2, VT3);  // Top 1     |
    public static final TriangleFace T2 = new TriangleFace(V5, V7, V3, VT1, VT13, VT2);  // Top 2    |
    // ----------------------------------------------------------------------------------------------|
    public static final TriangleFace F1 = new TriangleFace(V3, V8, V4, VT2, VT4, VT5);  // Front 1   |
    public static final TriangleFace F2 = new TriangleFace(V3, V7, V8, VT2, VT14, VT4);  // Front 2  |
    // ----------------------------------------------------------------------------------------------|
    public static final TriangleFace L1 = new TriangleFace(V7, V6, V8, VT6, VT7, VT8);  // Left 1    |
    public static final TriangleFace L2 = new TriangleFace(V7, V5, V6, VT6, VT12, VT7);  // Left 2   |
    // ----------------------------------------------------------------------------------------------|
    public static final TriangleFace B1 = new TriangleFace(V2, V8, V6, VT9, VT10, VT11); // Bottom 1 |
    public static final TriangleFace B2 = new TriangleFace(V2, V4, V8, VT9, VT5, VT10); // Bottom 2  |
    // ----------------------------------------------------------------------------------------------|
    public static final TriangleFace R1 = new TriangleFace(V1, V4, V2, VT3, VT5, VT9);  // Right 1   |
    public static final TriangleFace R2 = new TriangleFace(V1, V3, V4, VT3, VT2, VT5);  // Right 2   |
    // ----------------------------------------------------------------------------------------------|
    public static final TriangleFace BK1 = new TriangleFace(V5, V2, V6, VT12, VT9, VT7);  // Back 1  |
    public static final TriangleFace BK2 = new TriangleFace(V5, V1, V2, VT12, VT3, VT9);  // Back 2  |
    // ----------------------------------------------------------------------------------------------|

    public static BlockFace FRONT = new BlockFace(F1, F2);
    public static BlockFace BACK = new BlockFace(BK1, BK2);
    public static BlockFace TOP = new BlockFace(T1, T2);
    public static BlockFace BOTTOM = new BlockFace(B1, B2);
    public static BlockFace LEFT = new BlockFace(L1, L2);
    public static BlockFace RIGHT = new BlockFace(R1, R2);

    public TriangleFace f1, f2;
    private BlockFace(TriangleFace f1, TriangleFace f2) {
        this.f1 = f1;
        this.f2 = f2;
    }

    public void writeVertices(ArrayList<Float> vertices) {
        f1.writeVertices(vertices);
        f2.writeVertices(vertices);
    }

    public void writeTextureCoords(ArrayList<Float> uvs) {
        f1.writeTextureCoordinates(uvs);
        f2.writeTextureCoordinates(uvs);
    }

    @Override
    public BlockFace clone()  {
        return new BlockFace(this.f1, this.f2);
    }
}
