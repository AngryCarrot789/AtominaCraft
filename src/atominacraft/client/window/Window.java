package atominacraft.client.window;

import atominacraft.client.window.input.Input;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.glfw.GLFWWindowPosCallback;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

public class Window {
    private String title;
    private int width;
    private int height;
    private int positionX;
    private int positionY;
    private final long windowId;
    private GLFWVidMode vidMode;
    private Input input;

    private GLFWWindowSizeCallback resizeCallback;
    private GLFWWindowPosCallback posCallback;

    public Window(String title, int width, int height, int x, int y) {
        this.windowId = GLFW.glfwCreateWindow(width, height, title, 0, 0);
        this.title = title;
        this.width = width;
        this.height = height;
        setPosition(x, y);

        if (this.windowId == 0) {
            System.err.println("Failed to create window");
            return;
        }

        this.input = new Input();
        GLFW.glfwSetKeyCallback(this.windowId, this.input.getKeyCallback());
        //GLFW.glfwSetCursorPosCallback(this.windowId, this.input.getCursorCallback());
        GLFW.glfwSetMouseButtonCallback(this.windowId, this.input.getButtonCallback());

        resizeCallback = new GLFWWindowSizeCallback() {
            @Override
            public void invoke(long window, int width, int height) {
                setWidth(width);
                setHeight(height);
                useViewport();
            }
        };

        posCallback = new GLFWWindowPosCallback() {
            @Override
            public void invoke(long window, int posX, int posY) {
                setPositionX(posX);
                setPositionY(posY);
            }
        };

        GLFW.glfwSetWindowSizeCallback(this.windowId, this.resizeCallback);
        GLFW.glfwSetWindowPosCallback(this.windowId, this.posCallback);

        this.vidMode = GLFW.glfwGetVideoMode(GLFW.glfwGetPrimaryMonitor());

        GLFW.glfwMakeContextCurrent(this.windowId);
        GL.createCapabilities();
    }

    public void swapBuffers() {
        GLFW.glfwSwapBuffers(this.windowId);
    }

    public void useViewport() {
        GL11.glViewport(0, 0, this.width, this.height);
    }

    public void show() {
        GLFW.glfwShowWindow(this.windowId);
    }

    public boolean shouldClose() {
        return GLFW.glfwWindowShouldClose(this.getWindowId());
    }

    public long getWindowId() {
        return this.windowId;
    }

    public void setTitle(String title) {
        GLFW.glfwSetWindowTitle(this.windowId, title);
        this.title = title;
    }

    public String getTitle() {
        return this.title;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    private void setWidth(int width) {
        this.width = width;
    }

    private void setHeight(int height) {
        this.height = height;
    }

    private void setPositionX(int x) {
        this.positionX = x;
    }

    private void setPositionY(int y) {
        this.positionY = y;
    }

    public GLFWVidMode getVideoMode() {
        return vidMode;
    }

    public Input getInput() {
        return this.input;
    }

    public void setSize(int width, int height) {
        GLFW.glfwSetWindowSize(this.windowId, width, height);
        this.width = width;
        this.height = height;
    }

    public void setPosition(int x, int y) {
        GLFW.glfwSetWindowPos(this.windowId, x, y);
        this.positionX = x;
        this.positionY = y;
    }

    public void destroy() {
        this.input.destroy();
        GLFW.glfwWindowShouldClose(this.windowId);
        GLFW.glfwDestroyWindow(this.windowId);
    }
}
