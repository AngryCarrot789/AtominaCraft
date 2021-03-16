package reghzy.atominacraft.client.window.input;

import reghzy.atominacraft.client.window.Window;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.*;

import java.nio.DoubleBuffer;

public class Input {
    private final GLFWKeyCallback keyCallback;
    //private final GLFWCursorPosCallback cursorCallback;
    private final GLFWMouseButtonCallback buttonCallback;

    private final boolean[] keysDown = new boolean[GLFW.GLFW_KEY_LAST];
    private final boolean[] mouseButtons = new boolean[GLFW.GLFW_MOUSE_BUTTON_LAST];
    private double previousMouseX;
    private double previousMouseY;
    private double mouseX;
    private double mouseY;

    private DoubleBuffer mouseBufferX;
    private DoubleBuffer mouseBufferY;

    public Input() {
        mouseBufferX = BufferUtils.createDoubleBuffer(1);
        mouseBufferY = BufferUtils.createDoubleBuffer(1);

        keyCallback = new GLFWKeyCallback() {
            @Override
            public void invoke(long window, int key, int scancode, int action, int mods) {
                if (key < keysDown.length && key > -1) {
                    keysDown[key] = (action != GLFW.GLFW_RELEASE);
                }
            }
        };

        //cursorCallback = new GLFWCursorPosCallback() {
        //    @Override
        //    public void invoke(long window, double posX, double posY) {
        //        setPreviousMouseX(getMouseX());
        //        setPreviousMouseY(getMouseY());
        //        setMouseX(posX);
        //        setMouseY(posY);
        //    }
        //};

        buttonCallback = new GLFWMouseButtonCallback() {
            @Override
            public void invoke(long window, int button, int action, int mods) {
                mouseButtons[button] = (action != GLFW.GLFW_RELEASE);
            }
        };
    }

    public void destroy() {
        keyCallback.free();
        //cursorCallback.free();
        buttonCallback.free();
    }

    public boolean getKey(int key) {
        return this.keysDown[key];
    }

    public boolean getMouseButton(int button) {
        return this.mouseButtons[button];
    }

    public void updatePositions(Window window) {
        GLFW.glfwGetCursorPos(window.getWindowId(), this.mouseBufferX, this.mouseBufferY);
        this.previousMouseX = this.mouseX;
        this.previousMouseY = this.mouseY;
        this.mouseX = mouseBufferX.get(0);
        this.mouseY = mouseBufferY.get(0);
    }

    public double getMouseX() {
        return this.mouseX;
    }

    public double getMouseY() {
        return this.mouseY;
    }

    public double getPreviousMouseX() {
        return this.previousMouseX;
    }

    public double getPreviousMouseY() {
        return this.previousMouseY;
    }

    public double getMouseChangeX() {
        return this.mouseX - this.previousMouseX;
    }

    public double getMouseChangeY() {
        return this.mouseY - this.previousMouseY;
    }

    private void setMouseX(double value) {
        this.mouseX = value;
    }

    private void setMouseY(double value) {
        this.mouseY = value;
    }

    private void setPreviousMouseX(double value) {
        this.previousMouseX = value;
    }

    private void setPreviousMouseY(double value) {
        this.previousMouseY = value;
    }

    public GLFWKeyCallback getKeyCallback() {
        return keyCallback;
    }

    //public GLFWCursorPosCallback getCursorCallback() {
    //    return cursorCallback;
    //}

    public GLFWMouseButtonCallback getButtonCallback() {
        return buttonCallback;
    }
}
