package atominacraft.client.graphics.texture;

import javafx.scene.effect.ImageInput;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;

public class Texture {
    public int textureId;

    public Texture(File file) {
        if (!file.exists()) {
            System.err.println("Texture file doesn't exist");
            return;
        }
    }
}
