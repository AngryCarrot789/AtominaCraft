package reghzy.atominacraft.entity.player.viewer;

import reghzy.atominacraft.client.window.Window;
import reghzy.atominacraft.maths.Maths;
import reghzy.atominacraft.maths.Matrix4;
import org.lwjgl.opengl.GL11;

public class Camera {
    public float viewportWidth;
    public float viewportHeight;
    public float near;
    public float far;
    public float fov;

    public Matrix4 projection;
    public Matrix4 worldView;

    public Camera(float width, float height, float near, float far, float fov) {
        projection = new Matrix4();
        worldView = new Matrix4();
        set(width, height, near, far, fov);
    }

    public void set(float width, float height, float near, float far, float fov) {
        this.viewportWidth = width;
        this.viewportHeight = height;
        this.near = near;
        this.far = far;
        this.fov = fov;

        float fovRadians = 1.0f / (float) Math.tan(fov * Maths.PI / 360.0f);
        float aspect = Math.max(4, height) / Math.max(4, width);
        float diff = near - far;

        projection.m[0]  = fovRadians * aspect;
        projection.m[1]  = 0.0f;
        projection.m[2]  = 0.0f;
        projection.m[3]  = 0.0f;

        projection.m[4]  = 0.0f;
        projection.m[5]  = fovRadians;
        projection.m[6]  = 0.0f;
        projection.m[7]  = 0.0f;

        projection.m[8]  = 0.0f;
        projection.m[9]  = 0.0f;
        projection.m[10] = (near + far) / diff;
        projection.m[11] = (2 * near * far) / diff;

        projection.m[12] = 0.0f;
        projection.m[13] = 0.0f;
        projection.m[14] = -1.0f;
        projection.m[15] = 0.0f;
    }

    public void updateSize(Window window) {
        this.set(window.getWidth(), window.getHeight(), this.near, this.far, this.fov);
    }

    public Matrix4 matrix() {
        return Matrix4.multiply(projection, worldView);
    }

    public void useViewport() {
        GL11.glViewport(0, 0, (int) Math.floor(this.viewportWidth), (int) Math.floor(this.viewportHeight));
    }
}
