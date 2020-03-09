package org.lwjglb.engine;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjglb.engine.graph.Mesh;
import org.lwjglb.engine.graph.ShaderProgram;

public class GameItem {

	private final Mesh mesh;
	private final ShaderProgram shaderProgram;

	private final Vector3f position;

	private float scale;

	private final Vector3f rotation;

	public GameItem(Mesh mesh, ShaderProgram shaderProgram) {
		this.mesh = mesh;
		this.shaderProgram = shaderProgram;

		position = new Vector3f();
		scale = 1;
		rotation = new Vector3f();
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(float x, float y, float z) {
		this.position.x = x;
		this.position.y = y;
		this.position.z = z;
	}

	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
	}

	public Vector3f getRotation() {
		return rotation;
	}

	public void setRotation(float x, float y, float z) {
		this.rotation.x = x;
		this.rotation.y = y;
		this.rotation.z = z;
	}

	public void draw(Matrix4f projectionMatrix, Matrix4f viewMatrix, Matrix4f modelViewMatrix) {
		shaderProgram.bind();
		shaderProgram.setUniform("projectionMatrix", projectionMatrix);
		shaderProgram.setUniform("viewMatrix", viewMatrix);
		shaderProgram.setUniform("modelViewMatrix", modelViewMatrix);
		mesh.draw();
		shaderProgram.unbind();
	};

	public Mesh getMesh() {
		return mesh;
	}

	public void cleanup() {
		if (shaderProgram != null) {
			shaderProgram.cleanup();
		}

		if (mesh != null) {
			mesh.cleanup();
		}
	}
}