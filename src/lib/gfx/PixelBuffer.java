package lib.gfx;

import java.util.ArrayList;
import java.util.Iterator;

import org.joml.Vector2i;
import org.joml.Vector3f;

import lib.graph.Mesh;

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
		genArrayBufferv3f(points, 0);
		update();
	}

	public void update() {
		genArrayBufferv3f(pixels, 1);
	}

	public void writePixels(ArrayList<Vector2i> pixels, Vector3f color) {
		for (Iterator<Vector2i> iterator = pixels.iterator(); iterator.hasNext();) {
			Vector2i point = iterator.next();
			writePixel(point.x, point.y, color);
		}
	}

	public void writePixel(int x, int y, Vector3f color) {
		pixels.set(y * width + x, color);
	}

	public Vector3f readPixel(int x, int y) {
		return pixels.get(y * width + x);
	}
}