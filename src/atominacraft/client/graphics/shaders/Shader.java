package atominacraft.client.graphics.shaders;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Shader {
    public int programId;

    public void createProgram() {
        this.programId = GL20.glCreateProgram();
    }

    public void destroyProgram() {
        GL20.glDeleteProgram(this.programId);
    }

    protected int loadShader(File file, int type) {
        int shaderId = GL20.glCreateShader(type);
        try {
            StringBuilder sources = new StringBuilder((int) file.length());
            BufferedReader fileReader = new BufferedReader(new FileReader(file));
            String line = fileReader.readLine();
            while (line != null) {
                sources.append(line).append('\n');
                line = fileReader.readLine();
            }
            fileReader.close();
            GL20.glShaderSource(shaderId, sources.toString());
        }
        catch (Exception e) {
            return -1;
        }

        GL20.glCompileShader(shaderId);
        int compileStatus = GL20.glGetShaderi(shaderId, GL20.GL_COMPILE_STATUS);
        if (compileStatus == GL11.GL_FALSE) {
            System.err.println("Failed to compile shader. Error: ");
            System.err.println(GL20.glGetShaderInfoLog(shaderId));
            return -1;
        }

        return shaderId;
    }

    @Override
    protected void finalize() throws Throwable {
        this.destroyProgram();
        super.finalize();
    }
}
