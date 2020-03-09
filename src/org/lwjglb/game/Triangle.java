package org.lwjglb.game;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import java.nio.FloatBuffer;

import org.lwjgl.system.MemoryUtil;
import org.lwjglb.engine.graph.Mesh;

public class Triangle extends Mesh {
	
	public Triangle() {
		float[] vertices = new float[] { 0.0f, 0.5f, -1.0f, -0.5f, -0.5f, -1.0f, 0.5f, -0.5f, -1.0f };
		float[] colors = new float[] { 1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f };

		FloatBuffer verticesBuffer = null;
		FloatBuffer colorsBuffer = null;
		
		try {
			// Create the VAO and bind to it
			vao = glGenVertexArrays();
			glBindVertexArray(vao);

			// Create the VBO array
			vbo = new int[2];

			verticesBuffer = MemoryUtil.memAllocFloat(vertices.length);
			verticesBuffer.put(vertices).flip();
			// Create the VBO and bind to it
			vbo[0] = glGenBuffers();
			glBindBuffer(GL_ARRAY_BUFFER, vbo[0]);
			glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);
			// Enable location 0
			glEnableVertexAttribArray(0);
			// Define structure of the data
            glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);

			colorsBuffer = MemoryUtil.memAllocFloat(colors.length);
			colorsBuffer.put(colors).flip();
			// Create the VBO and bind to it
			vbo[1] = glGenBuffers();
			glBindBuffer(GL_ARRAY_BUFFER, vbo[1]);
			glBufferData(GL_ARRAY_BUFFER, colorsBuffer, GL_STATIC_DRAW);
			// Enable location 1
			glEnableVertexAttribArray(1);
			// Define structure of the data
            glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);
            
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

	@Override
	public void draw() {
		drawArrays(GL_TRIANGLES, 0, 3);
	}
}
