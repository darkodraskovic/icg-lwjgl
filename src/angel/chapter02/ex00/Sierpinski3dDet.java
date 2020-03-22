package angel.chapter02.ex00;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;

import java.util.ArrayList;
import java.util.HashMap;

import org.joml.Vector3f;

import lib.Entity;
import lib.graph.Mesh;

public class Sierpinski3dDet extends Entity {

	ArrayList<Vector3f> vertices = new ArrayList<Vector3f>();
	ArrayList<Vector3f> colors = new ArrayList<Vector3f>();
	HashMap<String, Vector3f> baseColors;

	public Sierpinski3dDet() throws Exception {
		baseColors = new HashMap<String, Vector3f>();
		baseColors.put("red", new Vector3f(1, 0, 0));
		baseColors.put("green", new Vector3f(0, 1, 0));
		baseColors.put("blue", new Vector3f(0, 0, 1));
		baseColors.put("magenta", new Vector3f(0, 1, 1));

		setShader("/shaders/angel/vertex.vs", "/shaders/angel/fragment.fs");
		mesh = new Mesh(GL_TRIANGLES);

		Vector3f a = new Vector3f(-1, -1, -1);
		Vector3f b = new Vector3f(0, -1, 1);
		Vector3f c = new Vector3f(1, -1, -1);
		Vector3f d = new Vector3f(0, 1, 0);
		divide(a, b, c, d, 4);

		mesh.genArrayBufferv3f(vertices);
		mesh.genArrayBufferv3f(colors);
	}

	private void divide(Vector3f a, Vector3f b, Vector3f c, Vector3f d, int numDivisions) {
		if (numDivisions < 1) {
			vertices.add(a);
			vertices.add(b);
			vertices.add(c);
			colors.add(baseColors.get("red"));
			colors.add(baseColors.get("red"));
			colors.add(baseColors.get("red"));

			vertices.add(a);
			vertices.add(b);
			vertices.add(d);
			colors.add(baseColors.get("green"));
			colors.add(baseColors.get("green"));
			colors.add(baseColors.get("green"));

			vertices.add(a);
			vertices.add(c);
			vertices.add(d);
			colors.add(baseColors.get("blue"));
			colors.add(baseColors.get("blue"));
			colors.add(baseColors.get("blue"));

			vertices.add(b);
			vertices.add(c);
			vertices.add(d);
			colors.add(baseColors.get("magenta"));
			colors.add(baseColors.get("magenta"));
			colors.add(baseColors.get("magenta"));

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

	@Override
	public void update(float interval) {
		float rotSpeed = 45;
		moveRotation(interval * rotSpeed, interval * rotSpeed, 0);
	}
}
