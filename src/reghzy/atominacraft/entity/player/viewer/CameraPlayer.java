package reghzy.atominacraft.entity.player.viewer;

import reghzy.atominacraft.AtominaCraft;
import reghzy.atominacraft.client.GameSettings;
import reghzy.atominacraft.client.window.input.Input;
import reghzy.atominacraft.entity.Entity;
import reghzy.atominacraft.entity.player.EntityPlayer;
import reghzy.atominacraft.maths.Maths;
import reghzy.atominacraft.maths.Matrix4;
import reghzy.atominacraft.maths.Vector3;
import reghzy.atominacraft.time.GameTime;
import reghzy.atominacraft.world.World;
import org.lwjgl.glfw.GLFW;

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

        super.update();
        increaseMove(back, right, up);
    }

    public void increaseMove(float back, float right, float up) {
        Matrix4 cameraToWorld = Matrix4.multiply(Matrix4.localToWorld(position, euler, Vector3.One), Matrix4.RotationY(rotationY));
        Vector3 lookDirection = Vector3.multiplyDirection(cameraToWorld, new Vector3(right, up, back).normalised());
        Vector3 movement = lookDirection.multiply(GameSettings.WALK_SPEED, GameSettings.FLY_SPEED, GameSettings.WALK_SPEED);

        this.velocity.set((velocity.x + movement.x) * GameTime.delta,
                          (velocity.y + movement.y) * GameTime.delta,
                          (velocity.z + movement.z) * GameTime.delta);

        this.position.add(this.velocity);
    }

    public void increaseLook(float x, float y) {
        rotationX -= y * (GameSettings.MOUSE_SENSITIVITY * (GameSettings.MOUSE_SENSITIVITY - GameTime.delta));
        if (rotationX > Maths.PI_HALF) {
            rotationX = Maths.PI_HALF;
        }
        else if (rotationX < Maths.PI_HALF_NEGATIVE) {
            rotationX = Maths.PI_HALF_NEGATIVE;
        }
        rotationY -= x * (GameSettings.MOUSE_SENSITIVITY * (GameSettings.MOUSE_SENSITIVITY - GameTime.delta));
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
