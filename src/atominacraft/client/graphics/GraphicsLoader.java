package atominacraft.client.graphics;

import atominacraft.client.graphics.shaders.PinkShader;
import atominacraft.client.graphics.shaders.Shader;
import atominacraft.client.graphics.shaders.TextureShader;
import atominacraft.utils.ResourceLocator;

import java.io.File;
import java.util.HashMap;

public class GraphicsLoader {
    public static HashMap<String, Shader> shaders;

    public static void init() {
        shaders = new HashMap<>();
        loadShaders();
    }

    public static Shader getShader(String name) {
        return shaders.get(name);
    }

    private static void loadShaders() {
        File textureT = ResourceLocator.getShader("texture_v");
        File textureF = ResourceLocator.getShader("texture_f");
        TextureShader textureShader = new TextureShader(textureT, textureF);
        shaders.put("texture", textureShader);

        File pinkT = ResourceLocator.getShader("pink_v");
        File pinkF = ResourceLocator.getShader("pink_f");
        PinkShader pinkShader = new PinkShader(pinkT, pinkF);
        shaders.put("pink", pinkShader);
    }
}
