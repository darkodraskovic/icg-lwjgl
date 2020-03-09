package org.lwjglb.game;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glViewport;

import java.util.ArrayList;
import java.util.Iterator;

import org.joml.Matrix4f;
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

	private ArrayList<Mesh> meshes;

	public Renderer() {
		transformation = new Transformation();
		meshes = new ArrayList<Mesh>();
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

	public void init(Window window) throws Exception {
		initShader();
		meshes.add(new Triangle(shaderProgram));
	}

	public void render(Window window, Camera camera) {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		if (window.isResized()) {
			glViewport(0, 0, window.getWidth(), window.getHeight());
			window.setResized(false);
		}

		shaderProgram.bind();

		setUniforms(window, camera);

		for (Iterator<Mesh> iterator = meshes.iterator(); iterator.hasNext();) {
			Mesh iMesh = (Mesh) iterator.next();
			iMesh.draw();
		}

		shaderProgram.unbind();
	}

	public void cleanup() {
		cleanupShader();
		
		for (Iterator<Mesh> iterator = meshes.iterator(); iterator.hasNext();) {
			Mesh iMesh = (Mesh) iterator.next();
			iMesh.cleanup();
		}
	}
}
