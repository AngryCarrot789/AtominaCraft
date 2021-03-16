package reghzy.atominacraft.client.render.mesh.generator;

import reghzy.atominacraft.client.render.mesh.generator.math.MeshVec3;

import java.util.ArrayList;
import java.util.List;

public class CubeBuilder {
    public BlockFace top, front, left, bottom, right, back;

    public boolean topVisible;
    public boolean frontVisible;
    public boolean leftVisible;
    public boolean bottomVisible;
    public boolean rightVisible;
    public boolean backVisible;

    public void writeVertices(ArrayList<Float> vertices) {
        if (topVisible) top.writeVertices(vertices);
        if (frontVisible) front.writeVertices(vertices);
        if (leftVisible) left.writeVertices(vertices);
        if (bottomVisible) bottom.writeVertices(vertices);
        if (rightVisible) right.writeVertices(vertices);
        if (backVisible) back.writeVertices(vertices);
    }

    public void writeUvs(ArrayList<Float> uvs) {
        if (topVisible) top.writeTextureCoords(uvs);
        if (frontVisible) front.writeTextureCoords(uvs);
        if (leftVisible) left.writeTextureCoords(uvs);
        if (bottomVisible) bottom.writeTextureCoords(uvs);
        if (rightVisible) right.writeTextureCoords(uvs);
        if (backVisible) back.writeTextureCoords(uvs);
    }

    public void writeNormals(List<Float> normals, List<Float> vertices) {
        for (int i = 0; i < vertices.size(); i += 9) {
            MeshVec3 vertex1 = new MeshVec3(vertices.get(i + 0), vertices.get(i + 1), vertices.get(i + 2));
            MeshVec3 vertex2 = new MeshVec3(vertices.get(i + 3), vertices.get(i + 4), vertices.get(i + 5));
            MeshVec3 vertex3 = new MeshVec3(vertices.get(i + 6), vertices.get(i + 7), vertices.get(i + 8));
            MeshVec3 normal = (vertex2.subtract(vertex1).cross(vertex3.subtract(vertex1))).normalised();
            if (Float.isNaN(normal.x)){
                normal.x = 0;
            }
            if (Float.isNaN(normal.y)) {
                normal.y = 0;
            }
            if (Float.isNaN(normal.z)) {
                normal.z = 0;
            }
            normals.add(normal.x);
            normals.add(normal.y);
            normals.add(normal.z);
            normals.add(normal.x);
            normals.add(normal.y);
            normals.add(normal.z);
            normals.add(normal.x);
            normals.add(normal.y);
            normals.add(normal.z);
        }
    }

    public CubeBuilder(boolean top, boolean fro, boolean lef, boolean bot, boolean rig, boolean bac) {
        if (top) {
            this.topVisible = true;
            this.top = BlockFace.TOP.clone();
        }
        if (fro) {
            this.frontVisible = true;
            this.front = BlockFace.FRONT.clone();
        }
        if (lef) {
            this.leftVisible = true;
            this.left = BlockFace.LEFT.clone();
        }
        if (bot) {
            this.bottomVisible = true;
            this.bottom = BlockFace.BOTTOM.clone();
        }
        if (rig) {
            this.rightVisible = true;
            this.right = BlockFace.RIGHT.clone();
        }
        if (bac) {
            this.backVisible = true;
            this.back = BlockFace.BACK.clone();
        }
    }
}
