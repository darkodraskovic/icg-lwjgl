package org.lwjglb.engine.graph;

import java.util.ArrayList;

import org.joml.Vector3f;

public class PixelBuffer extends Mesh {
	private int width;
	private ArrayList<Vector3f> pixels = new ArrayList<Vector3f>();

	public PixelBuffer(int width, int height) {
		this.width = width;
		ArrayList<Vector3f> points = new ArrayList<Vector3f>();
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				points.add(new Vector3f(x, y, 0));
				pixels.add(new Vector3f(1, 1, 1));
			}
		}
		genArrayBufferv3f(points);
		update();
	}

	public void update() {
		genArrayBufferv3f(pixels, 1);
	}

	public void writePixel(int x, int y, Vector3f color) {
		pixels.set(y * width + x, color);
	}

	public Vector3f readPixel(int x, int y) {
		return pixels.get(y * width + x);
	}
}