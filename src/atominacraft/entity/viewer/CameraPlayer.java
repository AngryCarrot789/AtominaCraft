package atominacraft.entity.viewer;

import atominacraft.AtominaCraft;
import atominacraft.client.GameSettings;
import atominacraft.client.window.input.Input;
import atominacraft.entity.Entity;
import atominacraft.entity.EntityPlayer;
import atominacraft.maths.Maths;
import atominacraft.maths.Matrix4;
import atominacraft.maths.Vector3;
import atominacraft.time.GameTime;
import atominacraft.world.World;
import org.lwjgl.glfw.GLFW;
import sun.security.x509.DeltaCRLIndicatorExtension;

public class CameraPlayer extends EntityPlayer {
    public Camera camera;
    public Input input;

    public float rotationX;
    public float rotationY;

    public CameraPlayer(World world, Camera camera) {
        super(world);
        this.input = AtominaCraft.instance.getInput();
        this.camera = camera;
    }

    @Override
    public void update() {
        double differenceX = input.getMouseChangeX();
        double differenceY = input.getMouseChangeY();

        increaseLook((float) differenceX, (float) differenceY);

        float back = 0.0f;
        float right = 0.0f;
        float up = 0.0f;
        if (input.getKey(GLFW.GLFW_KEY_W)) {
            back -= 1.0f;
        }
        if (input.getKey(GLFW.GLFW_KEY_S)) {
            back += 1.0f;
        }
        if (input.getKey(GLFW.GLFW_KEY_A)) {
            right -= 1.0f;
        }
        if (input.getKey(GLFW.GLFW_KEY_D)) {
            right += 1.0f;
        }
        if (input.getKey(GLFW.GLFW_KEY_SPACE)) {
            up += 1.0f;
        }
        if (input.getMouseButton(GLFW.GLFW_MOUSE_BUTTON_5)) {
            up -= 1.0f;
        }

        increaseMove(back, right, up);
    }

    public void increaseMove(float back, float right, float up) {
        Matrix4 cameraToWorld = Matrix4.multiply(Matrix4.localToWorld(position, rotation, Vector3.One), Matrix4.RotationY(rotationY));
        Vector3 lookDirection = Vector3.multiplyDirection(cameraToWorld, new Vector3(right, up, back).normalised());
        Vector3 movement = Vector3.multiply(lookDirection, GameSettings.WALK_SPEED);

        this.velocity.set((velocity.x + movement.x) * GameTime.delta,
                          (velocity.y + movement.y) * GameTime.delta,
                          (velocity.z + movement.z) * GameTime.delta);

        this.position.set(this.position.x + this.velocity.x,
                          this.position.y + this.velocity.y,
                          this.position.z + this.velocity.z);
    }

    public void increaseLook(float x, float y) {
        rotationX -= y * GameSettings.MOUSE_SENSITIVITY;
        if (rotationX > Maths.PI_HALF) {
            rotationX = Maths.PI_HALF;
        }
        else if (rotationX < Maths.PI_HALF_NEGATIVE) {
            rotationX = Maths.PI_HALF_NEGATIVE;
        }
        rotationY -= x * GameSettings.MOUSE_SENSITIVITY;
        if (rotationY > Maths.PI) {
            rotationY -= Maths.PI_DOUBLE;
        }
        else if (rotationY < Maths.PI_NEGATIVE) {
            rotationY += Maths.PI_DOUBLE;
        }
    }

    public Matrix4 worldToCamera() {
        return Matrix4.multiply(
                Matrix4.multiply(
                        Matrix4.RotationX(-rotationX),
                        Matrix4.RotationY(-rotationY)),
                Entity.worldToLocal(this));
    }
}
