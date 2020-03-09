package org.lwjglb.engine.graph;

import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL20.glDisableVertexAttribArray;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;

abstract public class Mesh {
	protected int vao;
	protected int[] vbo;

	abstract public void draw();

	protected void drawArrays(int mode, int first, int count) {
		// Bind to the VAO
		glBindVertexArray(vao);

		// Draw the vertices
		glDrawArrays(mode, first, count);

		// Restore state
		glBindVertexArray(0);
	}

	public void cleanup() {		
		glDisableVertexAttribArray(0);

		// Delete the VBO
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		for (int i = 0; i < vbo.length; i++) {
			glDeleteBuffers(vbo[i]);
		}

		// Delete the VAO
		glBindVertexArray(0);
		glDeleteVertexArrays(vao);
	}
}
