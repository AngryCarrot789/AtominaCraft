package reghzy.atominacraft.client.render.mesh;

import reghzy.atominacraft.block.BlockLocation;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL30;

import java.util.ArrayList;

public class BlockMesh {
    public int vao;
    public int[] vbos;

    public float[] vertices;
    public float[] uvs;
    public float[] normals;
    public BlockLocation location;

    public BlockMesh(ArrayList<Float> vertices, ArrayList<Float> uvs, ArrayList<Float> normals) {
        this.vertices = listToArray(vertices);
        this.uvs = listToArray(uvs);
        this.normals = listToArray(normals);

        vao = GL30.glGenVertexArrays();
        vbos = new int[3];

        vbos[0] = GL30.glGenBuffers();
        {
            GL30.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbos[0]);
            GL30.glBufferData(GL15.GL_ARRAY_BUFFER, this.vertices, GL30.GL_STATIC_DRAW);
            GL30.glEnableVertexAttribArray(0);
            GL30.glVertexAttribPointer(0, 3, GL30.GL_FLOAT, false, 0, 0);
        }
        vbos[1] = GL30.glGenBuffers();
        {
            GL30.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbos[1]);
            GL30.glBufferData(GL15.GL_ARRAY_BUFFER, this.uvs, GL30.GL_STATIC_DRAW);
            GL30.glEnableVertexAttribArray(1);
            GL30.glVertexAttribPointer(1, 2, GL30.GL_FLOAT, false, 0, 0);
        }
        vbos[2] = GL30.glGenBuffers();
        {
            GL30.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbos[2]);
            GL30.glBufferData(GL15.GL_ARRAY_BUFFER, this.normals, GL30.GL_STATIC_DRAW);
            GL30.glEnableVertexAttribArray(2);
            GL30.glVertexAttribPointer(1, 3, GL30.GL_FLOAT, false, 0, 0);
        }
    }

    public void draw() {
        GL30.glBindVertexArray(vao);
        GL30.glDrawArrays(GL11.GL_TRIANGLES, 0, vertices.length / 3);
    }

    public void dispose() {
        GL30.glDeleteBuffers(vbos);
        GL30.glDeleteVertexArrays(vao);
    }

    public static float[] listToArray(ArrayList<Float> floats) {
        float[] buffer = new float[floats.size()];
        for (int i = 0; i < floats.size(); i++) {
            buffer[i] = floats.get(i);
        }
        return buffer;
    }
}
