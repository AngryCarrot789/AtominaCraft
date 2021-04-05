package reghzy.atominacraft.utils;

import java.io.File;

public class ResourceLocator {
    public static final File directory;

    public static void init() {

    }

    public static File getFile(String path) {
        return new File(directory, path);
    }

    public static File getShader(String name) {
        return getFile("shaders/" + name + ".glsl");
    }

    public static File getAssets() {
        return getFile("assets");
    }

    static {
        directory = new File("resources");
    }
}
