package org.lwjglb.engine;

import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.lwjglb.engine.graph.Mesh;
import org.lwjglb.engine.graph.Shader;

public class Entity {

	private Mesh mesh;

	private Shader shader;

	private final Vector3f position = new Vector3f();

	private float scale = 1;

	private final Vector3f rotation = new Vector3f();

	public Shader getShader() {
		return shader;
	}

	public void setShader(Shader shader) throws Exception{
		this.shader = shader;
		
		shader.createUniform("projectionMatrix");
		shader.createUniform("viewMatrix");
		shader.createUniform("modelViewMatrix");
	}

	public void setMesh(Mesh mesh) {
		this.mesh = mesh;
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