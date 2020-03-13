package angel.chapter02.ex00;

import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;

import java.util.ArrayList;
import java.util.HashMap;

import org.joml.Vector3f;
import org.lwjglb.engine.Entity;
import org.lwjglb.engine.graph.Mesh;

public class Sierpinski3dDetMnt extends Entity {

	ArrayList<Vector3f> vertices = new ArrayList<Vector3f>();
	ArrayList<Vector3f> colors = new ArrayList<Vector3f>();
	HashMap<String, Vector3f> baseColors;

	public Sierpinski3dDetMnt() throws Exception {
		baseColors = new HashMap<String, Vector3f>();
		baseColors.put("red", new Vector3f(1, 0, 0));
		baseColors.put("green", new Vector3f(0, 1, 0));
		baseColors.put("blue", new Vector3f(0, 0, 1));
		baseColors.put("yellow", new Vector3f(1, 1, 0));
		baseColors.put("magenta", new Vector3f(1, 0, 1));
		baseColors.put("cyan", new Vector3f(0, 1, 1));

		setShader("/shaders/angel/vertex.vs", "/shaders/angel/fragment.fs");
		mesh = new Mesh(GL_LINES);
//		mesh = new Mesh(GL_TRIANGLES);

		Vector3f a = new Vector3f(-1, -1, -1);
		Vector3f b = new Vector3f(0, -1, 1);
		Vector3f c = new Vector3f(1, -1, -1);
		Vector3f d = new Vector3f(0, 1, 0);
		divide(a, b, c, d, 4);

		mesh.genArrayBufferv3f(vertices);
		mesh.genArrayBufferv3f(colors);
	}

	private void strokeTriangle(Vector3f a, Vector3f b, Vector3f c, Vector3f d) {
		vertices.add(a);
		vertices.add(b);
		setColor(baseColors.get("red"), 2);

		vertices.add(b);
		vertices.add(c);
		setColor(baseColors.get("green"), 2);

		vertices.add(c);
		vertices.add(a);
		setColor(baseColors.get("blue"), 2);

		vertices.add(a);
		vertices.add(d);
		setColor(baseColors.get("magenta"), 2);

		vertices.add(b);
		vertices.add(d);
		setColor(baseColors.get("yellow"), 2);

		vertices.add(c);
		vertices.add(d);
		setColor(baseColors.get("cyan"), 2);
	}

	private void fillTriangle(Vector3f a, Vector3f b, Vector3f c, Vector3f d) {
		vertices.add(a);
		vertices.add(b);
		vertices.add(c);
		setColor(baseColors.get("red"), 3);

		vertices.add(a);
		vertices.add(b);
		vertices.add(d);
		setColor(baseColors.get("green"), 3);

		vertices.add(a);
		vertices.add(c);
		vertices.add(d);
		setColor(baseColors.get("blue"), 3);

		vertices.add(b);
		vertices.add(c);
		vertices.add(d);
		setColor(baseColors.get("yellow"), 3);
	}

	private void setColor(Vector3f color, int countVertices) {
		for (int i = 0; i < countVertices; i++) {
			colors.add(color);
		}
	}

	private void divide(Vector3f a, Vector3f b, Vector3f c, Vector3f d, int numDivisions) {
		if (numDivisions < 1) {
			if (mesh.mode == GL_LINES) {
				strokeTriangle(a, b, c, d);
				return;
			} else if (mesh.mode == GL_TRIANGLES) {
				fillTriangle(a, b, c, d);
				return;
			}
		}

		Vector3f ab = a.add(b, new Vector3f()).mul(0.5f);
		Vector3f ac = a.add(c, new Vector3f()).mul(0.5f);
		Vector3f ad = a.add(d, new Vector3f()).mul(0.5f);
		Vector3f bc = b.add(c, new Vector3f()).mul(0.5f);
		Vector3f bd = b.add(d, new Vector3f()).mul(0.5f);
		Vector3f cd = c.add(d, new Vector3f()).mul(0.5f);

		float scl = 7;
		randomize(ab, scl);
		randomize(ac, scl);
		randomize(ad, scl);
		randomize(bc, scl);
		randomize(bd, scl);
		randomize(cd, scl);

		numDivisions--;
		divide(a, ab, ac, ad, numDivisions);
		divide(ab, b, bc, bd, numDivisions);
		divide(ac, bc, c, cd, numDivisions);
		divide(ad, bd, cd, d, numDivisions);
	}

	private void randomize(Vector3f vec, float scl) {
		float rndX = (float) Math.random() / scl - 1 / scl / 2;
		float rndY = (float) Math.random() / scl - 1 / scl / 2;
		float rndZ = (float) Math.random() / scl - 1 / scl / 2;
		vec.add(rndX, rndY, rndZ);
	}

	@Override
	public void update(float interval) {
		float rotSpeed = 10;
		moveRotation(0, interval * rotSpeed, 0);
	}
}
