package org.lwjglb.game;

import static org.lwjgl.opengl.GL11.GL_LINES;

import org.lwjglb.engine.Entity;
import org.lwjglb.engine.graph.Mesh;
import org.lwjglb.engine.graph.Shader;

public class Triangle extends Entity {
	public Triangle() throws Exception {
		super();

		Shader shader = new Shader("/shaders/vertex.vs", "/shaders/fragment.fs");
		setShader(shader);

		Mesh mesh = new Mesh();
		mesh.setMode(GL_LINES);
		float[] vertices = new float[] { 0.0f, 0.5f, -1.0f, -0.5f, -0.5f, -1.0f, 0.5f, -0.5f, -1.0f };
		float[] colors = new float[] { 1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f };
		mesh.genArrayBufferf(vertices, 0, 3);
		mesh.genArrayBufferf(colors, 1, 3);
		mesh.genElementBuffer(new int[] { 0, 1, 1, 2, 2, 0 });
		setMesh(mesh);
	}

	@Override
	public void update(float interval) {
		moveRotation(0, 0, interval * 45);
	}
}
