package atominacraft.utils;

import java.io.File;

public class ResourceLocator {
    public static File directory;

    public static void init() {
        directory = new File("resources");
    }

    public static File getFile(String path) {
        return new File(directory, path);
    }

    public static File getShader(String name) {
        return getFile("shaders/" + name + ".glsl");
    }
}
