package org.lwjglb.engine.pixel;

import java.util.ArrayList;

import org.joml.Vector2i;

public class Shaper {
	public static ArrayList<Vector2i> line(int x1, int y1, int x2, int y2) {
		if (x2 < x1) {
			int x = x1;
			x1 = x2;
			x2 = x;
		}
		if (y2 < y1) {
			int y = y1;
			y1 = y2;
			y2 = y;
		}
		int deltaX = x2 - x1;
		int deltaY = y2 - y1;

		ArrayList<Vector2i> points = new ArrayList<Vector2i>();

		if (deltaX > deltaY) {
			float m = (float) deltaY / (float) deltaX;
			for (int i = 0; i <= deltaX; i++) {
				points.add(new Vector2i(x1 + i, Math.round(y1 + i * m)));
			}
		} else {
			float m = (float) deltaX / (float) deltaY;
			for (int i = 0; i <= deltaY; i++) {
				points.add(new Vector2i(Math.round(x1 + i * m), y1 + i));
			}
		}

		return points;
	}
}
