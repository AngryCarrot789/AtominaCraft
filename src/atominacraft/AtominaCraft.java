package atominacraft;

import atominacraft.client.graphics.DebugDrawing;
import atominacraft.client.graphics.GraphicsLoader;
import atominacraft.client.graphics.shaders.PinkShader;
import atominacraft.entity.Entity;
import atominacraft.entity.viewer.Camera;
import atominacraft.entity.viewer.CameraPlayer;
import atominacraft.maths.Matrix4;
import atominacraft.maths.Vector3;
import atominacraft.time.GameTime;
import atominacraft.client.window.input.Input;
import atominacraft.client.window.Window;
import atominacraft.utils.ResourceLocator;
import atominacraft.world.World;
import atominacraft.world.WorldManager;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

public class AtominaCraft {
    public static AtominaCraft instance;

    private Window mainWindow;

    public WorldManager worldManager;

    public CameraPlayer mainPlayer;

    public AtominaCraft() {
        instance = this;

        initGLFW();
        this.mainWindow = new Window("AtominaCraft", 1280, 720, 100, 100);
        this.mainWindow.show();
        worldManager = new WorldManager();
    }

    private void initGLFW() {
        if (GLFW.glfwInit()) {
            System.out.println("Initialised glfw! Version: " + GLFW.glfwGetVersionString());
        }
        else {
            System.err.println("Failed to init glfw!");
        }
    }

    private PinkShader shader;

    private void init() {
        ResourceLocator.init();
        GraphicsLoader.init();
        GLFW.glfwSetInputMode(this.mainWindow.getWindowId(), GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_DISABLED);

        World earth = new World(0, "earth");
        worldManager.registerWorld(earth);

        Camera camera = new Camera(mainWindow.getWidth(), mainWindow.getHeight(), 0.1f, 100.0f, 75.0f);
        mainPlayer = new CameraPlayer(earth, camera);
        mainPlayer.moveTo(new Vector3(0, 0, 4));

        shader = (PinkShader) GraphicsLoader.getShader("pink");

        //GL11.glClearColor(0.2f, 0.8f, 0.6f, 1.0f);
        //GL11.glEnable(GL11.GL_CULL_FACE);
        //GL11.glCullFace(GL11.GL_BACK);
        //GL11.glEnable(GL11.GL_DEPTH_TEST);
        //GL11.glDepthFunc(GL11.GL_LESS);
        //GL11.glDepthMask(true);
    }

    public void run() {
        GameTime.previousTicks = System.currentTimeMillis();
        init();

        while (!this.mainWindow.shouldClose()) {
            GLFW.glfwPollEvents();
            this.mainWindow.getInput().updatePositions(this.mainWindow);

            if (this.mainWindow.getInput().getKey(GLFW.GLFW_KEY_ESCAPE)) {
                break;
            }

            GameTime.currentTicks = System.currentTimeMillis();
            GameTime.ticksDifference = (GameTime.currentTicks - GameTime.previousTicks);
            GameTime.delta = ((float) GameTime.ticksDifference / 1000.0f) + 0.001f;

            update();

            GameTime.previousTicks = System.currentTimeMillis();

            render();

            GameTime.totalTicks++;
        }

        GLFW.glfwTerminate();
    }

    public void update() {
        mainPlayer.update();
        worldManager.updateWorlds();
    }

    public void render() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glClearColor(0.2f, 0.8f, 0.6f, 1.0f);

        this.mainPlayer.camera.worldView = this.mainPlayer.worldToCamera();
        this.mainPlayer.camera.updateSize(this.mainWindow);

        Matrix4 cam = this.mainPlayer.camera.matrix();
        Matrix4 ltw = Entity.localToWorld(this.mainPlayer);

        shader.use();
        shader.setMatrix(Matrix4.multiply(cam, ltw));

        DebugDrawing.drawCube(this.mainPlayer.camera, Vector3.Zero, Vector3.One);

        GL11.glBegin(GL11.GL_TRIANGLES);
        GL11.glVertex3f( 0.5f, -0.5f, 0.0f);
        GL11.glVertex3f(-0.5f, -0.5f, 0.0f);
        GL11.glVertex3f( 0.0f,  0.5f, 0.0f);
        GL11.glEnd();

        this.mainWindow.swapBuffers();
    }

    public Window mainWindow() {
        return this.mainWindow;
    }

    public Input getInput() {
        return this.mainWindow.getInput();
    }
}
