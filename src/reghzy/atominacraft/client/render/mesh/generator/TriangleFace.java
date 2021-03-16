package reghzy.atominacraft.client.render.mesh.generator;

import reghzy.atominacraft.client.render.mesh.generator.math.MeshVec2;
import reghzy.atominacraft.client.render.mesh.generator.math.MeshVec3;

import java.util.List;

public class TriangleFace {
    public MeshVec3 V1;
    public MeshVec3 V2;
    public MeshVec3 V3;
    public MeshVec2 VT1;
    public MeshVec2 VT2;
    public MeshVec2 VT3;

    public TriangleFace(MeshVec3 v1, MeshVec3 v2, MeshVec3 v3, MeshVec2 vt1, MeshVec2 vt2, MeshVec2 vt3) {
        this.V1 = v1;
        this.V2 = v2;
        this.V3 = v3;
        this.VT1 = vt1;
        this.VT2 = vt2;
        this.VT3 = vt3;
    }

    public void writeVertices(List<Float> vertices) {
        V1.writeVertices(vertices);
        V2.writeVertices(vertices);
        V3.writeVertices(vertices);
    }

    public void writeTextureCoordinates(List<Float> uvs) {
        VT1.writeVertices(uvs);
        VT2.writeVertices(uvs);
        VT3.writeVertices(uvs);
    }
}
