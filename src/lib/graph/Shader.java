package lib.graph;

import static org.lwjgl.opengl.GL20.GL_COMPILE_STATUS;
import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_LINK_STATUS;
import static org.lwjgl.opengl.GL20.GL_VALIDATE_STATUS;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;
import static org.lwjgl.opengl.GL20.glAttachShader;
import static org.lwjgl.opengl.GL20.glCompileShader;
import static org.lwjgl.opengl.GL20.glCreateProgram;
import static org.lwjgl.opengl.GL20.glCreateShader;
import static org.lwjgl.opengl.GL20.glDeleteProgram;
import static org.lwjgl.opengl.GL20.glDetachShader;
import static org.lwjgl.opengl.GL20.glGetAttribLocation;
import static org.lwjgl.opengl.GL20.glGetProgramInfoLog;
import static org.lwjgl.opengl.GL20.glGetProgrami;
import static org.lwjgl.opengl.GL20.glGetShaderInfoLog;
import static org.lwjgl.opengl.GL20.glGetShaderi;
import static org.lwjgl.opengl.GL20.glGetUniformLocation;
import static org.lwjgl.opengl.GL20.glLinkProgram;
import static org.lwjgl.opengl.GL20.glShaderSource;
import static org.lwjgl.opengl.GL20.glUniform1i;
import static org.lwjgl.opengl.GL20.glUniform1f;
import static org.lwjgl.opengl.GL20.glUniformMatrix4fv;
import static org.lwjgl.opengl.GL20.glUseProgram;
import static org.lwjgl.opengl.GL20.glValidateProgram;

import java.util.HashMap;
import java.util.Map;

import org.joml.Matrix4f;
import org.lwjgl.system.MemoryStack;

import lib.Utils;

public class Shader {

	private final int programId;

	private final Exception createProgramException = new Exception("Could not create Shader");

	private int vertexShaderId;

	private int fragmentShaderId;
	
	private final Map<String, Integer> uniforms = new HashMap<>();

	// CONSTRUCTORS
	public Shader() throws Exception {
		programId = glCreateProgram();
		if (programId == 0) {
			throw createProgramException;
		}
	}

	public Shader(String vertex, String fragment) throws Exception {
		programId = glCreateProgram();
		if (programId == 0) {
			throw createProgramException;
		}

		createVertexShader(Utils.loadResource(vertex));
		createFragmentShader(Utils.loadResource(fragment));
		link();
	}

	// CREATION
	public void createVertexShader(String shaderCode) throws Exception {
		vertexShaderId = createShader(shaderCode, GL_VERTEX_SHADER);
	}

	public void createFragmentShader(String shaderCode) throws Exception {
		fragmentShaderId = createShader(shaderCode, GL_FRAGMENT_SHADER);
	}

	protected int createShader(String shaderCode, int shaderType) throws Exception {
		int shaderId = glCreateShader(shaderType);
		if (shaderId == 0) {
			throw new Exception("Error creating shader. Type: " + shaderType);
		}

		glShaderSource(shaderId, shaderCode);
		glCompileShader(shaderId);

		if (glGetShaderi(shaderId, GL_COMPILE_STATUS) == 0) {
			throw new Exception("Error compiling Shader code: " + glGetShaderInfoLog(shaderId, 1024));
		}

		glAttachShader(programId, shaderId);

		return shaderId;
	}

	public void link() throws Exception {
		glLinkProgram(programId);
		if (glGetProgrami(programId, GL_LINK_STATUS) == 0) {
			throw new Exception("Error linking Shader code: " + glGetProgramInfoLog(programId, 1024));
		}

		if (vertexShaderId != 0) {
			glDetachShader(programId, vertexShaderId);
		}
		if (fragmentShaderId != 0) {
			glDetachShader(programId, fragmentShaderId);
		}

		glValidateProgram(programId);
		if (glGetProgrami(programId, GL_VALIDATE_STATUS) == 0) {
			System.err.println("Warning validating Shader code: " + glGetProgramInfoLog(programId, 1024));
		}
	}

	// ATTRIBUTES
	public int getAttribLocation(String attributeName) {
		return glGetAttribLocation(programId, attributeName);
	}

	// UNIFORMS
	public int getUniformLocation(String uniformName) {
		return glGetUniformLocation(programId, uniformName);
	}

	public void createUniform(String uniformName) throws Exception {
		int uniformLocation = glGetUniformLocation(programId, uniformName);
		if (uniformLocation < 0) {
			// throw new Exception("Could not find uniform:" + uniformName);
		}
		uniforms.put(uniformName, uniformLocation);
	}

	// set uniforms
	public void setUniform(String uniformName, int value) {
		glUniform1i(uniforms.get(uniformName), value);
	}

	public void setUniform(String uniformName, float value) {
		glUniform1f(uniforms.get(uniformName), value);
	}

	public void setUniform(String uniformName, Matrix4f value) {
		// Dump the matrix into a float buffer
		try (MemoryStack stack = MemoryStack.stackPush()) {
			glUniformMatrix4fv(uniforms.get(uniformName), false, value.get(stack.mallocFloat(16)));
		}
	}

	// (UN)BIND
	public void bind() {
		glUseProgram(programId);
	}

	public void unbind() {
		glUseProgram(0);
	}

	// CLEANUP
	public void cleanup() {
		unbind();
		if (programId != 0) {
			glDeleteProgram(programId);
		}
	}
}
