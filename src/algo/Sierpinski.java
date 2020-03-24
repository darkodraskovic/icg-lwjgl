package algo;

import java.util.ArrayList;

import org.joml.Vector3f;

public class Sierpinski {
	private static ArrayList<Vector3f> points = new ArrayList<Vector3f>();

	// 2D
	static public ArrayList<Vector3f> sierpinski2D(Vector3f a, Vector3f b, Vector3f c, int numDivisions) {
		points.clear();

		divide(a, b, c, numDivisions);

		return points;
	}

	static public void divide(Vector3f a, Vector3f b, Vector3f c, int numDivisions) {
		if (numDivisions < 1) {
			points.add(a);
			points.add(b);
			points.add(c);
			return;
		}

		Vector3f ab = a.add(b, new Vector3f()).mul(0.5f);
		Vector3f ac = a.add(c, new Vector3f()).mul(0.5f);
		Vector3f bc = b.add(c, new Vector3f()).mul(0.5f);

		numDivisions--;
		divide(a, ab, ac, numDivisions);
		divide(ab, b, bc, numDivisions);
		divide(ac, bc, c, numDivisions);
	}

	// 3D
	static public ArrayList<Vector3f> sierpinski3D(int numDivisions) {
		points = new ArrayList<Vector3f>();

		Vector3f a = new Vector3f(-1, -1, -1);
		Vector3f b = new Vector3f(0, -1, 1);
		Vector3f c = new Vector3f(1, -1, -1);
		Vector3f d = new Vector3f(0, 1, 0);
		divide(a, b, c, d, numDivisions);

		return points;
	}

	static private void divide(Vector3f a, Vector3f b, Vector3f c, Vector3f d, int numDivisions) {
		if (numDivisions < 1) {
			points.add(a);
			points.add(b);
			points.add(c);

			points.add(a);
			points.add(b);
			points.add(d);

			points.add(a);
			points.add(c);
			points.add(d);

			points.add(b);
			points.add(c);
			points.add(d);
			return;
		}

		Vector3f ab = a.add(b, new Vector3f()).mul(0.5f);
		Vector3f ac = a.add(c, new Vector3f()).mul(0.5f);
		Vector3f ad = a.add(d, new Vector3f()).mul(0.5f);
		Vector3f bc = b.add(c, new Vector3f()).mul(0.5f);
		Vector3f bd = b.add(d, new Vector3f()).mul(0.5f);
		Vector3f cd = c.add(d, new Vector3f()).mul(0.5f);

		numDivisions--;
		divide(a, ab, ac, ad, numDivisions);
		divide(ab, b, bc, bd, numDivisions);
		divide(ac, bc, c, cd, numDivisions);
		divide(ad, bd, cd, d, numDivisions);
	}
}
