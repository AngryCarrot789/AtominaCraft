package reghzy.atominacraft;

import reghzy.atominacraft.block.Block;
import reghzy.atominacraft.block.BlockLocation;
import reghzy.atominacraft.block.grid.GridLatch;
import reghzy.atominacraft.client.GameSettings;
import reghzy.atominacraft.client.render.RenderingManager;
import reghzy.atominacraft.client.render.Tesselator;
import reghzy.atominacraft.client.render.GraphicsLoader;
import reghzy.atominacraft.client.render.WorldMeshMap;
import reghzy.atominacraft.client.render.shader.PinkShader;
import reghzy.atominacraft.client.render.texture.Texture;
import reghzy.atominacraft.client.render.texture.TextureMap;
import reghzy.atominacraft.collision.AxisAlignedBB;
import reghzy.atominacraft.entity.Entity;
import reghzy.atominacraft.entity.player.viewer.Camera;
import reghzy.atominacraft.entity.player.viewer.CameraPlayer;
import reghzy.atominacraft.maths.Matrix4;
import reghzy.atominacraft.maths.Vector3;
import reghzy.atominacraft.time.GameTime;
import reghzy.atominacraft.client.window.input.Input;
import reghzy.atominacraft.client.window.Window;
import reghzy.atominacraft.utils.ResourceLocator;
import reghzy.atominacraft.world.World;
import reghzy.atominacraft.world.WorldManager;
import reghzy.atominacraft.world.chunk.Chunk;
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
    private Texture texture;
    private Vector3 position;

    private boolean init() {
        ResourceLocator.init();
        GraphicsLoader.init();
        try {
            TextureMap.init();
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        TextureMap.loadDefaultTextures();
        RenderingManager.initEntityRenderer();
        WorldMeshMap.init();

        GLFW.glfwSetInputMode(this.mainWindow.getWindowId(), GLFW.GLFW_CURSOR, GLFW.GLFW_CURSOR_DISABLED);

        World earth = new World(0, "earth");
        worldManager.registerWorld(earth);

        Camera camera = new Camera(mainWindow.getWidth(), mainWindow.getHeight(), 0.1f, 500.0f, 75.0f);
        mainPlayer = new CameraPlayer(earth, camera);
        mainPlayer.moveTo(new Vector3(0, 0, 4));

        Chunk chunk = earth.createChunk(0, 0);

        for(int x = 0; x < 16; x++) {
            for (int z = 0; z < 16; z++) {
                for (int y = 0; y < 5; y++) {
                    Block block = Block.TEMPLATE_DIRT.copyTemplate();
                    chunk.setBlock(block, x, y, z);
                    block.boundingBox.setMin(block.location.x, block.location.y, block.location.z);
                    block.boundingBox.setMax(block.location.x + GridLatch.BlockWidth,
                                             block.location.y + GridLatch.BlockWidth,
                                             block.location.z + GridLatch.BlockWidth);
                }
            }
        }

        Block block = Block.TEMPLATE_DIRT.copyTemplate();
        chunk.setBlock(block, 8, 10, 8);
        block.boundingBox.setMin(block.location.x, block.location.y, block.location.z);
        block.boundingBox.setMax(block.location.x + GridLatch.BlockWidth,
                                 block.location.y + GridLatch.BlockWidth,
                                 block.location.z + GridLatch.BlockWidth);

        //for(int x = -5; x <= 5; x++) {
        //    for (int z = -5; z <= 5; z++) {
        //        Chunk chunk = earth.createChunk(x, z);

        //        for (int _x = 0; _x < 4; _x++) {
        //            for (int _z = 0; _z < 6; _z++) {
        //                int y = x + 5;
        //                Block block = Block.TEMPLATE_DIRT.copyTemplate();
        //                block.boundingBox = new AxisAlignedBB(GridLatch.WTMGetBlockInWorldFromChunk(chunk.coordinates, new BlockLocation(x, y, z)), GridLatch.BlockScaleV);
        //                chunk.setBlock(block, _x, y, _z);
        //            }
        //        }
        //    }
        //}

        //earth.activeEntities.add(new EntityCubeFollower(this.mainPlayer, earth));

        //for(int y = 0; y < 1; y++) {
        //    for (int x = 0; x < 16; x++) {
        //        for (int z = 0; z < 16; z++) {
        //            center.setBlock(Block.TEMPLATE_DIRT.copyTemplate(), x, y, z);
        //        }
        //    }
        //}

        //for(Chunk chunk : earth.chunks.values()) {
        //    ChunkMeshGenerator.generateChunk(chunk);
        //}

        shader = (PinkShader) GraphicsLoader.getShader("pink");
        texture = TextureMap.getTextureFromBlock(1);
        position = new Vector3();

        GL11.glClearColor(0.2f, 0.8f, 0.6f, 1.0f);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDepthFunc(GL11.GL_LESS);
        GL11.glDepthMask(true);
        return true;
    }

    public void run() {
        GameTime.update_previousTicks = System.currentTimeMillis();
        if (!init()){
            GLFW.glfwTerminate();
            return;
        }

        while (!this.mainWindow.shouldClose()) {
            GLFW.glfwPollEvents();
            this.mainWindow.getInput().updatePositions(this.mainWindow);

            if (this.mainWindow.getInput().getKey(GLFW.GLFW_KEY_ESCAPE)) {
                break;
            }

            GameTime.update_currentTicks = System.currentTimeMillis();
            GameTime.update_ticksDifference = (GameTime.update_currentTicks - GameTime.update_previousTicks);

            if (GameTime.update_ticksDifference >= GameSettings.updatesMillis) {
                GameTime.delta = ((float) GameTime.update_ticksDifference / 1000.0f) + 0.0001f;
                GameTime.update_currentTicks = System.currentTimeMillis();
                update();
                GameTime.update_previousTicks = System.currentTimeMillis();
            }
            //GameTime.render_currentTicks = System.currentTimeMillis();
            //GameTime.render_ticksDifference = (GameTime.render_currentTicks - GameTime.render_previousTicks);
            //if (GameTime.render_ticksDifference > GameSettings.renderMillis) {
            //    GameTime.render_currentTicks = System.currentTimeMillis();
            render();
            //    GameTime.render_previousTicks = System.currentTimeMillis();
            //}

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
        World world = mainPlayer.world;

        for (Entity entity : world.getEntities()) {
            RenderingManager.renderEntity(this.mainPlayer.camera, entity);
        }

        for(Chunk chunk : mainPlayer.world.chunks.values()) {
            Tesselator.drawChunkCenterOutline(this.mainPlayer.camera, chunk, 0.4f, 0.3f, 0.7f);
            if (mainPlayer.getChunk() != null) {
                Tesselator.drawChunkOutlines(this.mainPlayer.camera, this.mainPlayer.getChunk());
            }

            for (Block block : chunk.blocks.values()) {
                Tesselator.drawBoundingBox(this.mainPlayer.camera, block.boundingBox);

                Texture texture = TextureMap.getTextureFromBlock(block.id);
                if (texture != null) {
                    texture.use();
                }
                // getting position of the block
                Vector3 pos = GridLatch.WTMGetBlockInWorldFromChunk(chunk.coordinates, block.location);
                Matrix4 ltw = Matrix4.localToWorld(pos, Vector3.Zero, GridLatch.BlockScaleV);
                // getting the world view projection of the block
                //Matrix4 mv = Matrix4.worldToLocal(pos, Vector3.Zero, GridLatch.BlockScaleV);
                Matrix4 mvp = Matrix4.multiply(cam, ltw);
                shader.use();
                // giving it to the shader
                shader.setMatrix(mvp);
                // drawing it
                Tesselator.drawCubeOpen();
            }
        }

        this.mainWindow.swapBuffers();
    }

    public Window mainWindow() {
        return this.mainWindow;
    }

    public Input getInput() {
        return this.mainWindow.getInput();
    }
}
