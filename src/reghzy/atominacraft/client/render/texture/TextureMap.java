package reghzy.atominacraft.client.render.texture;

import reghzy.atominacraft.utils.ResourceLocator;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;

public class TextureMap {
    public static HashMap<String, Texture> textures;
    public static HashMap<Integer, Texture> textureBlocks;

    private static File texturesDirectory;

    public static void init() throws FileNotFoundException {
        textures = new HashMap<>(4);
        textureBlocks = new HashMap<>(4);
        texturesDirectory = new File(ResourceLocator.getAssets(), "reghzy/atominacraft");
        if (!texturesDirectory.exists()) {
            throw new FileNotFoundException("AtominaCraft assets directory not found");
        }
    }

    public static void loadDefaultTextures() {
        File blocksDirectory = new File(texturesDirectory, "textures\\blocks");

        File[] files = blocksDirectory.listFiles();
        if (files == null) {
            System.err.println("Block textures folder was null");
        }
        else if (files.length == 0) {
            System.err.println("There are no block textures");
        }
        else {
            registerTexture("dirt", blocksDirectory);
            registerTexture("grass_side", blocksDirectory);

            linkTextureBlockId(1, "dirt");
            linkTextureBlockId(10, "grass_side");
        }
    }

    public static Texture createTexture(String name, File file) {
        if (file == null || !file.exists()) {
            System.err.println("File does not exist! Cannot load " + name);
            return null;
        }

        String path = file.getPath();
        Texture texture = new Texture(file);
        texture.load();
        System.out.println("Loaded texture '" + name + "' at '" + path + "'");
        return texture;
    }

    public static void registerTexture(String textureName, File directory) {
        addTexture(textureName, createTexture(textureName, new File(directory, textureName + ".png")));
    }

    public static boolean linkTextureBlockId(int id, String textureName) {
        Texture texture = textures.get(textureName);
        if (texture == null) {
            return false;
        }
        textureBlocks.put(id, texture);
        return true;
    }

    public static Texture getTextureFromBlock(int id) {
        return textureBlocks.get(id);
    }

    private static void addTexture(String name, Texture texture) {
        textures.put(name, texture);
    }

    public Texture getTexture(String name) {
        return textures.get(name);
    }
}
