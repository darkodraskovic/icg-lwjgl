package org.lwjglb.game;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;

import org.lwjglb.engine.graph.Mesh;

public class Triangle extends Mesh {

	public Triangle() {
		float[] vertices = new float[] { 0.0f, 0.5f, -1.0f, -0.5f, -0.5f, -1.0f, 0.5f, -0.5f, -1.0f };
		float[] colors = new float[] { 1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f };

		genArrayBufferf(vertices, 0, 3);
		genArrayBufferf(colors, 1, 3);
	}

	@Override
	public void draw() {
		drawArrays(GL_TRIANGLES, 0, 3);
	}
}
