package reghzy.atominacraft.client.render.texture;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Texture {
    public BufferedImage bufferedImage;
    public int textureId;

    public Texture(File file) {
        if (!file.exists()) {
            System.err.println("Texture file doesn't exist");
            return;
        }

        try {
            this.bufferedImage = ImageIO.read(file);
        }
        catch (IOException e) {
            System.err.println("Failed to read image");
            e.printStackTrace();
            return;
        }

        genTexture();
    }

    public void load() {
        int w = bufferedImage.getWidth();
        int h = bufferedImage.getHeight();
        int offsetX = 0;
        int offsetY = 0;

        BufferedImage image = bufferedImage;
        TextureUtil.bindPrepare(textureId, w, h);
        TextureUtil.bindTexture(textureId);

        int[] pixels = new int[w * h * 3];

        for (int row = 0; row < h; row++) {
            image.getRGB(0, row, w, 1, pixels, 0, w);
            //GL11.glTexSubImage2D(GL11.GL_TEXTURE_2D, 0, offsetX, offsetY + row, w, 1, GL12.GL_BGRA, GL12.GL_UNSIGNED_INT_8_8_8_8_REV, subBuffer);
        }

        GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
        GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, w, h, 0, GL12.GL_BGRA, GL12.GL_UNSIGNED_INT_8_8_8_8_REV, pixels);

        //image.flush();
    }

    public void genTexture() {
        this.textureId = GL11.glGenTextures();
    }

    public void use() {
        TextureUtil.bindTexture(this.textureId);
    }

    public void dispose() {
        GL11.glDeleteTextures(this.textureId);
    }

    @Override
    public int hashCode() {
        return this.textureId;
    }
}
