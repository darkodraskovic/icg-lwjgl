package algo;

import java.util.ArrayList;

import org.joml.Vector3f;

public class Koch {
	private static ArrayList<Vector3f> points = new ArrayList<Vector3f>();

	// 2D
	static public ArrayList<Vector3f> koch2D(Vector3f a, Vector3f b, int numDivisions) {
		points.clear();

		divide(a, b, numDivisions);
		return points;
	}

	static public void divide(Vector3f a, Vector3f b, int numDivisions) {
		Vector3f delta = b.sub(a, new Vector3f());
		Vector3f c = delta.mul(1f / 3, new Vector3f()).add(a);
		Vector3f d = delta.mul(2f / 3, new Vector3f()).add(a);

		Vector3f e = d.sub(c, new Vector3f());
		e.rotateZ((float) Math.PI / 3).add(c);

		numDivisions--;
		if (numDivisions < 1) {
			points.add(a);
			points.add(c);
			points.add(e);
			points.add(d);
			points.add(b);
			return;
		}

		divide(a, c, numDivisions);
		divide(c, e, numDivisions);
		divide(e, d, numDivisions);
		divide(d, b, numDivisions);
	}
}