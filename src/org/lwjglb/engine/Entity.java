package org.lwjglb.engine;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjglb.engine.graph.Mesh;
import org.lwjglb.engine.graph.Shader;

public class Entity {

	private final Mesh mesh;
	private final Shader shader;

	private final Vector3f position;

	private float scale;

	private final Vector3f rotation;

	public Entity(Mesh mesh, Shader shaderProgram) {
		this.mesh = mesh;
		this.shader = shaderProgram;

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
		shader.bind();
		shader.setUniform("projectionMatrix", projectionMatrix);
		shader.setUniform("viewMatrix", viewMatrix);
		shader.setUniform("modelViewMatrix", modelViewMatrix);
		mesh.draw();
		shader.unbind();
	};

	public Mesh getMesh() {
		return mesh;
	}

	public void cleanup() {
		if (shader != null) {
			shader.cleanup();
		}

		if (mesh != null) {
			mesh.cleanup();
		}
	}
}