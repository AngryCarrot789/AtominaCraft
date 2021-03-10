package atominacraft.client.graphics.shaders;

import atominacraft.maths.Matrix4;
import org.lwjgl.opengl.GL20;

import java.io.File;

public class TextureShader extends Shader {
    public int vertexId;
    public int fragmentId;

    public int mvId;
    public int mvpId;

    public TextureShader(File vertexFile, File fragmentFile) {
        if (!vertexFile.exists()) {
            System.err.println("Vertex shader file doesn't exist");
            return;
        }
        if (!fragmentFile.exists()) {
            System.err.println("Fragment shader file doesn't exist");
            return;
        }

        vertexId = loadShader(vertexFile, GL20.GL_VERTEX_SHADER);
        fragmentId = loadShader(fragmentFile, GL20.GL_FRAGMENT_SHADER);
        if (vertexId == -1 || fragmentId == -1) {
            return;
        }

        createProgram();
        GL20.glAttachShader(this.programId, this.vertexId);
        GL20.glAttachShader(this.programId, this.fragmentId);

        GL20.glBindAttribLocation(this.programId, 0, "mvp");
        GL20.glBindAttribLocation(this.programId, 1, "mv");
        GL20.glBindAttribLocation(this.programId, 2, "in_pos");
        GL20.glBindAttribLocation(this.programId, 3, "in_uv");
        GL20.glBindAttribLocation(this.programId, 4, "in_normal");

        GL20.glLinkProgram(this.programId);

        if (GL20.glGetProgrami(this.programId, GL20.GL_LINK_STATUS) < 1) {
            System.err.println("Failed to link shaders to program: ");
            System.err.println(GL20.glGetProgramInfoLog(this.programId));
            return;
        }

        mvpId = GL20.glGetUniformLocation(this.programId, "mvp");
        mvId = GL20.glGetUniformLocation(this.programId, "mv");

        GL20.glDetachShader(this.programId, this.vertexId);
        GL20.glDetachShader(this.programId, this.fragmentId);
        GL20.glDeleteShader(this.vertexId);
        GL20.glDeleteShader(this.fragmentId);
    }

    public void use() {
        GL20.glUseProgram(this.programId);
    }

    public void setMatrix(Matrix4 mvp, Matrix4 mv) {
        if (mvpId >= 0 && mvp != null) {
            GL20.glUniformMatrix4fv(mvpId, true, mvp.m);
        }
        if (mvId >= 0 && mv != null) {
            GL20.glUniformMatrix4fv(mvId, true, mv.m);
        }
    }
}
