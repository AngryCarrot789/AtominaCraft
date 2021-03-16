package reghzy.atominacraft.client.render.mesh.generator.math;

import java.util.List;

public class MeshVec3 {
    public float x;
    public float y;
    public float z;

    public MeshVec3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void writeVertices(List<Float> vertices) {
        vertices.add(x);
        vertices.add(y);
        vertices.add(z);
    }

    public MeshVec3 subtract(MeshVec3 v) {
        return new MeshVec3(this.x - v.x, this.y - v.y, this.z - v.z);
    }

    public MeshVec3 cross(MeshVec3 v) {
        return new MeshVec3(this.y * v.z - this.z * this.y, this.z * v.x - this.x * this.z, this.x * v.y - this.y * this.x);
    }

    public MeshVec3 normalised() {
        float magSquare = x * x + y * y + z * z;
        if (Float.isNaN(magSquare)) {
            return new MeshVec3(0, 0, 0);
        }
        float mag = (float) Math.sqrt(magSquare);
        if (Float.isNaN(mag)) {
            return new MeshVec3(0, 0, 0);
        }
        return new MeshVec3(x / mag, y / mag, z / mag);
    }
}
