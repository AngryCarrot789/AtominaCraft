package reghzy.atominacraft.client.render;

import reghzy.atominacraft.block.grid.GridLatch;
import reghzy.atominacraft.collision.AxisAlignedBB;
import reghzy.atominacraft.entity.player.viewer.Camera;
import reghzy.atominacraft.maths.Matrix4;
import reghzy.atominacraft.maths.Vector3;
import reghzy.atominacraft.maths.Vector4;
import reghzy.atominacraft.world.chunk.Chunk;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public class Tesselator {
    public static Vector4 CubePart1 = new Vector4( 1.0f,  1.0f, -1.0f, 1.0f);
    public static Vector4 CubePart2 = new Vector4( 1.0f, -1.0f, -1.0f, 1.0f);
    public static Vector4 CubePart3 = new Vector4(-1.0f, -1.0f, -1.0f, 1.0f);
    public static Vector4 CubePart4 = new Vector4(-1.0f,  1.0f, -1.0f, 1.0f);
    public static Vector4 CubePart5 = new Vector4(-1.0f,  1.0f,  1.0f, 1.0f);
    public static Vector4 CubePart6 = new Vector4(-1.0f, -1.0f,  1.0f, 1.0f);
    public static Vector4 CubePart7 = new Vector4( 1.0f, -1.0f,  1.0f, 1.0f);
    public static Vector4 CubePart8 = new Vector4( 1.0f,  1.0f,  1.0f, 1.0f);

    public static void vertex4f(Vector4 v) {
        GL11.glVertex4f(v.x, v.y, v.z, v.w);
    }

    public static void drawCube(Camera camera, Vector3 position, Vector3 scale, float r, float g, float b) {
        GL11.glColor3f(r, g, b);
        drawCube(camera, position, scale);
    }

    public static void drawChunkOutlines(Camera camera, Chunk chunk) {
        drawCube(camera, GridLatch.WTMGetChunkWorld(chunk.coordinates), GridLatch.ChunkScaleV, 1.0f, 0.1f, 0.1f);
    }

    public static void drawChunkCenterOutline(Camera camera, Chunk chunk, float r, float g, float b) {
        Vector3 position = GridLatch.WTMGetChunkWorld(chunk.coordinates);
        Matrix4 mvp = Matrix4.multiply(camera.matrix(), Matrix4.localToWorld(position, Vector3.Zero, GridLatch.ChunkScaleV));
        Vector4 v1 = Matrix4.multiply(mvp, new Vector4( 1.0f, -1.0f,  0.0f, 1.0f));
        Vector4 v2 = Matrix4.multiply(mvp, new Vector4( 1.0f,  1.0f,  0.0f, 1.0f));
        Vector4 v3 = Matrix4.multiply(mvp, new Vector4( 0.0f, -1.0f, -1.0f, 1.0f));
        Vector4 v4 = Matrix4.multiply(mvp, new Vector4( 0.0f,  1.0f, -1.0f, 1.0f));
        Vector4 v5 = Matrix4.multiply(mvp, new Vector4(-1.0f, -1.0f,  0.0f, 1.0f));
        Vector4 v6 = Matrix4.multiply(mvp, new Vector4(-1.0f,  1.0f,  0.0f, 1.0f));
        Vector4 v7 = Matrix4.multiply(mvp, new Vector4( 0.0f, -1.0f,  1.0f, 1.0f));
        Vector4 v8 = Matrix4.multiply(mvp, new Vector4( 0.0f,  1.0f,  1.0f, 1.0f));

        GL11.glDepthFunc(GL11.GL_LEQUAL);
        GL20.glUseProgram(0);

        GL11.glLineWidth(2);

        GL11.glColor3f(r, g, b);
        GL11.glBegin(GL11.GL_LINE_LOOP);
        vertex4f(v1);
        vertex4f(v2);
        GL11.glEnd();

        GL11.glBegin(GL11.GL_LINE_LOOP);
        vertex4f(v3);
        vertex4f(v4);
        GL11.glEnd();

        GL11.glBegin(GL11.GL_LINE_LOOP);
        vertex4f(v5);
        vertex4f(v6);
        GL11.glEnd();

        GL11.glBegin(GL11.GL_LINE_LOOP);
        vertex4f(v7);
        vertex4f(v8);
        GL11.glEnd();

        GL11.glDepthFunc(GL11.GL_LESS);
        GL11.glLineWidth(1);
    }

    public static void drawCube(Camera camera, Vector3 position, Vector3 scale) {
        Matrix4 mvp = Matrix4.multiply(camera.matrix(), Matrix4.localToWorld(position, Vector3.Zero, scale));
        Vector4 v1 = Matrix4.multiply(mvp, CubePart1);
        Vector4 v2 = Matrix4.multiply(mvp, CubePart2);
        Vector4 v3 = Matrix4.multiply(mvp, CubePart3);
        Vector4 v4 = Matrix4.multiply(mvp, CubePart4);
        Vector4 v5 = Matrix4.multiply(mvp, CubePart5);
        Vector4 v6 = Matrix4.multiply(mvp, CubePart6);
        Vector4 v7 = Matrix4.multiply(mvp, CubePart7);
        Vector4 v8 = Matrix4.multiply(mvp, CubePart8);

        GL11.glDepthFunc(GL11.GL_LEQUAL);
        GL20.glUseProgram(0);
        GL11.glLineWidth(2);

        GL11.glBegin(GL11.GL_LINE_LOOP);
        vertex4f(v1);
        vertex4f(v2);
        vertex4f(v3);
        vertex4f(v4);
        GL11.glEnd();

        GL11.glBegin(GL11.GL_LINE_LOOP);
        vertex4f(v4);
        vertex4f(v5);
        vertex4f(v6);
        vertex4f(v3);
        GL11.glEnd();

        GL11.glBegin(GL11.GL_LINE_LOOP);
        vertex4f(v6);
        vertex4f(v5);
        vertex4f(v8);
        vertex4f(v7);
        GL11.glEnd();

        GL11.glBegin(GL11.GL_LINE_LOOP);
        vertex4f(v8);
        vertex4f(v7);
        vertex4f(v2);
        vertex4f(v1);
        GL11.glEnd();

        GL11.glDepthFunc(GL11.GL_LESS);
        GL11.glLineWidth(1);
    }

    public static void drawCubeOpen() {
        Vector4 v1 = CubePart1;
        Vector4 v2 = CubePart2;
        Vector4 v3 = CubePart3;
        Vector4 v4 = CubePart4;
        Vector4 v5 = CubePart5;
        Vector4 v6 = CubePart6;
        Vector4 v7 = CubePart7;
        Vector4 v8 = CubePart8;

        GL11.glLineWidth(2);

        GL11.glBegin(GL11.GL_LINE_LOOP);
        vertex4f(v1);
        vertex4f(v2);
        vertex4f(v3);
        vertex4f(v4);
        GL11.glEnd();

        GL11.glBegin(GL11.GL_LINE_LOOP);
        vertex4f(v4);
        vertex4f(v5);
        vertex4f(v6);
        vertex4f(v3);
        GL11.glEnd();

        GL11.glBegin(GL11.GL_LINE_LOOP);
        vertex4f(v6);
        vertex4f(v5);
        vertex4f(v8);
        vertex4f(v7);
        GL11.glEnd();

        GL11.glBegin(GL11.GL_LINE_LOOP);
        vertex4f(v8);
        vertex4f(v7);
        vertex4f(v2);
        vertex4f(v1);
        GL11.glEnd();

        GL11.glLineWidth(1);
    }

    public static void drawBoundingBox(Camera camera, AxisAlignedBB aabb) {
        Vector3 position = aabb.getCenter();
        Vector3 scale = aabb.getScale();
        Matrix4 mvp = Matrix4.multiply(camera.matrix(), Matrix4.localToWorld(position, Vector3.Zero, scale));
        Vector4 v1 = Matrix4.multiply(mvp, CubePart1);
        Vector4 v2 = Matrix4.multiply(mvp, CubePart2);
        Vector4 v3 = Matrix4.multiply(mvp, CubePart3);
        Vector4 v4 = Matrix4.multiply(mvp, CubePart4);
        Vector4 v5 = Matrix4.multiply(mvp, CubePart5);
        Vector4 v6 = Matrix4.multiply(mvp, CubePart6);
        Vector4 v7 = Matrix4.multiply(mvp, CubePart7);
        Vector4 v8 = Matrix4.multiply(mvp, CubePart8);

        GL11.glDepthFunc(GL11.GL_LEQUAL);
        GL20.glUseProgram(0);
        GL11.glLineWidth(3);

        GL11.glBegin(GL11.GL_LINE_LOOP);
        GL11.glColor3f(0.2f, 0.3f, 0.8f);
        vertex4f(v1);
        vertex4f(v2);
        vertex4f(v3);
        vertex4f(v4);
        GL11.glEnd();

        GL11.glBegin(GL11.GL_LINE_LOOP);
        GL11.glColor3f(0.2f, 0.3f, 0.8f);
        vertex4f(v4);
        vertex4f(v5);
        vertex4f(v6);
        vertex4f(v3);
        GL11.glEnd();

        GL11.glBegin(GL11.GL_LINE_LOOP);
        GL11.glColor3f(0.2f, 0.3f, 0.8f);
        vertex4f(v6);
        vertex4f(v5);
        vertex4f(v8);
        vertex4f(v7);
        GL11.glEnd();

        GL11.glBegin(GL11.GL_LINE_LOOP);
        GL11.glColor3f(0.2f, 0.3f, 0.8f);
        vertex4f(v8);
        vertex4f(v7);
        vertex4f(v2);
        vertex4f(v1);
        GL11.glEnd();

        GL11.glDepthFunc(GL11.GL_LESS);
        GL11.glLineWidth(1);
    }
}
