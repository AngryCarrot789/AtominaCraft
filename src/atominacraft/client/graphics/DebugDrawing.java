package atominacraft.client.graphics;

import atominacraft.entity.viewer.Camera;
import atominacraft.maths.Matrix4;
import atominacraft.maths.Vector3;
import atominacraft.maths.Vector4;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public class DebugDrawing {
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
        GL11.glColor3f(0.2f, 0.4f, 0.8f);

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
}
