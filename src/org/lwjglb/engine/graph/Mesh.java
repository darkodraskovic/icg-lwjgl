package org.lwjglb.engine.graph;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL13.glActiveTexture;
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
import java.util.ArrayList;
import java.util.Iterator;

import org.lwjgl.system.MemoryUtil;

public class Mesh {
	private int vao  = glGenVertexArrays();
	private ArrayList<Integer> vbos  = new ArrayList<Integer>();
	private long[] indices;
	private int mode;
	private int count;

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}


	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public void genArrayBufferf(float[] vertices, int index, int size) {
		int vbo;
		FloatBuffer buffer = null;

		try {
			buffer = MemoryUtil.memAllocFloat(vertices.length);
			buffer.put(vertices).flip();

			glBindVertexArray(vao);

			// Create the VBO and bind to it
			vbo = glGenBuffers();
			glBindBuffer(GL_ARRAY_BUFFER, vbo);
			glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
			// Enable location 0
			glEnableVertexAttribArray(index);
			// Define structure of the data
			glVertexAttribPointer(index, size, GL_FLOAT, false, 0, 0);

			// Unbind the VBO
			glBindBuffer(GL_ARRAY_BUFFER, 0);

			// Unbind the VAO
			glBindVertexArray(0);
		} finally {
			if (buffer != null) {
				MemoryUtil.memFree(buffer);
			}
		}

		vbos.add(vbo);
	}

	public void draw() {
		if(indices != null) {
			drawElements();
		}
		else {
			drawArrays();
		}
	};

	protected void setTexture2D(int texture, int target) {
		glActiveTexture(texture);
		glBindTexture(GL_TEXTURE_2D, target);
	}

	protected void drawArrays() {
		// Bind to the VAO
		glBindVertexArray(vao);

		// Draw the vertices
		glDrawArrays(mode, 0, count);

		// Restore state
		glBindVertexArray(0);
	}

	protected void drawElements() {
		// Bind to the VAO
		glBindVertexArray(vao);

		// Draw the vertices
		glDrawElements(mode, count, GL_UNSIGNED_INT, 0);

		// Restore state
		glBindVertexArray(0);
	}

	public void cleanup() {
		glDisableVertexAttribArray(0);

		// Delete the VBO
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		for (Iterator<Integer> iterator = vbos.iterator(); iterator.hasNext();) {
			Integer integer = (Integer) iterator.next();
			glDeleteBuffers(integer);
		}

		// Delete the VAO
		glBindVertexArray(0);
		glDeleteVertexArrays(vao);
	}
}
