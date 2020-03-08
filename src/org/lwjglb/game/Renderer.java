package org.lwjglb.game;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glViewport;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import java.nio.FloatBuffer;

import org.joml.Matrix4f;
import org.lwjgl.system.MemoryUtil;
import org.lwjglb.engine.Utils;
import org.lwjglb.engine.Window;
import org.lwjglb.engine.graph.Camera;
import org.lwjglb.engine.graph.ShaderProgram;
import org.lwjglb.engine.graph.Transformation;

public class Renderer {

	/**
	 * Field of View in Radians
	 */
	private static final float FOV = (float) Math.toRadians(60.0f);

	private static final float Z_NEAR = 0.01f;

	private static final float Z_FAR = 1000.f;

	private final Transformation transformation;

	private ShaderProgram shaderProgram;

	private int vbo;
	private int vao;

	public Renderer() {
		transformation = new Transformation();
	}

	private void initShader() throws Exception {
		// Create shader
		shaderProgram = new ShaderProgram();
		shaderProgram.createVertexShader(Utils.loadResource("/shaders/vertex.vs"));
		shaderProgram.createFragmentShader(Utils.loadResource("/shaders/fragment.fs"));
		shaderProgram.link();

		// Create uniforms for view and projection matrices and texture
		shaderProgram.createUniform("projectionMatrix");
		shaderProgram.createUniform("viewMatrix");
	}

	private void setUniforms(Window window, Camera camera) {
		// Update projection Matrix
		Matrix4f projectionMatrix = transformation.getProjectionMatrix(FOV, window.getWidth(), window.getHeight(),
				Z_NEAR, Z_FAR);
		shaderProgram.setUniform("projectionMatrix", projectionMatrix);

		// Update view Matrix
		Matrix4f viewMatrix = transformation.getViewMatrix(camera);
		shaderProgram.setUniform("viewMatrix", viewMatrix);		
	}
	
	private void cleanupShader() {
		if (shaderProgram != null) {
			shaderProgram.cleanup();
		}
	}

	private void initObjects() {
		float[] vertices = new float[] { 0.0f, 0.5f, -1.0f, -0.5f, -0.5f, -1.0f, 0.5f, -0.5f, -1.0f };

		FloatBuffer verticesBuffer = null;
		try {
			verticesBuffer = MemoryUtil.memAllocFloat(vertices.length);
			verticesBuffer.put(vertices).flip();

			// Create the VAO and bind to it
			vao = glGenVertexArrays();
			glBindVertexArray(vao);

			// Create the VBO and bind to it
			vbo = glGenBuffers();
			glBindBuffer(GL_ARRAY_BUFFER, vbo);
			glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);
			// Enable location 0
			glEnableVertexAttribArray(0);
			// Define structure of the data
			int posAttrib = shaderProgram.getAttribLocation("vPosition");
			glVertexAttribPointer(posAttrib, 3, GL_FLOAT, false, 0, 0);

			// Unbind the VBO
			glBindBuffer(GL_ARRAY_BUFFER, 0);

			// Unbind the VAO
			glBindVertexArray(0);
		} finally {
			if (verticesBuffer != null) {
				MemoryUtil.memFree(verticesBuffer);
			}
		}
	}

	private void drawObjects() {
		// Bind to the VAO
		glBindVertexArray(vao);

		// Draw the vertices
		glDrawArrays(GL_TRIANGLES, 0, 3);

		// Restore state
		glBindVertexArray(0);
	}

	private void cleanupObjects() {
		glDisableVertexAttribArray(0);

		// Delete the VBO
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glDeleteBuffers(vbo);

		// Delete the VAO
		glBindVertexArray(0);
		glDeleteVertexArrays(vao);
	}

	public void init(Window window) throws Exception {
		initShader();
		initObjects();
	}

	public void render(Window window, Camera camera) {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		if (window.isResized()) {
			glViewport(0, 0, window.getWidth(), window.getHeight());
			window.setResized(false);
		}

		shaderProgram.bind();

		setUniforms(window, camera);

		drawObjects();

		shaderProgram.unbind();
	}

	public void cleanup() {
		cleanupShader();
		cleanupObjects();
	}
}
