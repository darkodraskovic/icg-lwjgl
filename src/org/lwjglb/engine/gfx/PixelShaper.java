package org.lwjglb.engine.gfx;

import java.util.ArrayList;

import org.joml.Vector2i;

public class PixelShaper {
	public static ArrayList<Vector2i> bresenhamLine(int x1, int y1, int x2, int y2) {
		// delta of exact value and rounded value of the dependent variable
		int d = 0;

		int dx = Math.abs(x2 - x1);
		
		int dy = Math.abs(y2 - y1);

		int dx2 = 2 * dx; // slope scaling factors to
		int dy2 = 2 * dy; // avoid floating point

		int ix = x1 < x2 ? 1 : -1; // increment direction
		int iy = y1 < y2 ? 1 : -1;

		int x = x1;
		int y = y1;

		ArrayList<Vector2i> points = new ArrayList<Vector2i>();
		if (dx >= dy) {
			while (true) {
				points.add(new Vector2i(x, y));
				if (x == x2)
					break;
				x += ix;
				d += dy2;
				if (d > dx) {
					y += iy;
					d -= dx2;
				}
			}
			return points;
		}
		
		while (true) {
			points.add(new Vector2i(x, y));
			if (y == y2)
				break;
			y += iy;
			d += dx2;
			if (d > dy) {
				x += ix;
				d -= dy2;
			}
		}
		return points;
	}

}
