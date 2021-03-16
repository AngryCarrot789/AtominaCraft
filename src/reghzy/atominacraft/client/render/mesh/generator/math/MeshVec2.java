package reghzy.atominacraft.client.render.mesh.generator.math;

import java.util.List;

public class MeshVec2 {
    public float x;
    public float y;
    public MeshVec2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void writeVertices(List<Float> vertices) {
        vertices.add(x);
        vertices.add(y);
    }
}
