package org.lwjglb.engine.graph;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glBindTexture;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL13.glActiveTexture;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glDeleteBuffers;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glDeleteVertexArrays;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.Iterator;

import org.lwjgl.system.MemoryUtil;

public class Mesh {
	private int vao = glGenVertexArrays();
	private ArrayList<Integer> vbos = new ArrayList<Integer>();
	private int[] indices;

	/**
	 * The kind of primitives being constructed.
	 */
	private int mode;

	/**
	 * The total number of vertices.
	 */
	private int count;

	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	// BUFFERS
	/**
	 * @param attribArray The vertex attribute float array.
	 * @param index       The index of the generic vertex attribute to be modified.
	 * @param size        The number of values per vertex that are stored in the
	 *                    array.
	 */
	public void genArrayBufferf(float[] attribArray, int index, int size) {
		count = attribArray.length / size;

		int vbo;
		FloatBuffer buffer = null;

		try {
			buffer = MemoryUtil.memAllocFloat(attribArray.length);
			buffer.put(attribArray).flip();

			glBindVertexArray(vao);

			// Create the VBO and bind to it
			vbo = glGenBuffers();
			glBindBuffer(GL_ARRAY_BUFFER, vbo);
			// Transfer data from float buffer to VBO
			glBufferData(GL_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);
			// Enable attribute location specified by index
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
	
	public void genElementBuffer(int[] indices) {
		int vbo;
		IntBuffer buffer = null;

		try {
			buffer = MemoryUtil.memAllocInt(indices.length);
			buffer.put(indices).flip();

			glBindVertexArray(vao);

			// Create the VBO and bind to it
			vbo = glGenBuffers();
			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, vbo);
			glBufferData(GL_ELEMENT_ARRAY_BUFFER, buffer, GL_STATIC_DRAW);

			// Unbind the VBO
			glBindBuffer(GL_ARRAY_BUFFER, 0);
			// Unbind the VAO
			glBindVertexArray(0);

			this.indices = indices;
		} finally {
			if (buffer != null) {
				MemoryUtil.memFree(buffer);
			}
		}

		vbos.add(vbo);
	}

	// DRAW
	public void draw() {
		if (indices != null) {
			drawElements();
		} else {
			drawArrays();
		}
	};

	protected void setTexture2D(int texture, int target) {
		glActiveTexture(texture);
		glBindTexture(GL_TEXTURE_2D, target);
	}

	private void drawArrays() {
		// Bind to the VAO
		glBindVertexArray(vao);

		// Draw the vertices
		glDrawArrays(mode, 0, count);

		// Restore state
		glBindVertexArray(0);
	}

	private void drawElements() {
		// Bind to the VAO
		glBindVertexArray(vao);

		// Draw the vertices
		glDrawElements(mode, indices.length, GL_UNSIGNED_INT, 0);

		// Restore state
		glBindVertexArray(0);
	}

	// CLEANUP
	public void cleanup() {
		// glDisableVertexAttribArray(0);

		// Delete the VBO
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		for (Iterator<Integer> iterator = vbos.iterator(); iterator.hasNext();) {
			Integer integer = (Integer) iterator.next();
			glDeleteBuffers(integer);
		}

		// Delete the VAO
		glBindVertexArray(0);
		glDeleteVertexArrays(vao);
	}
}
