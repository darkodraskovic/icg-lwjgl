package angel.chapter02.ex00;

import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;

import java.util.ArrayList;

import org.joml.Vector3f;

import lib.Entity;
import lib.graph.Mesh;

public class Sierpinski2dDetMnt extends Entity {

	ArrayList<Vector3f> vertices = new ArrayList<Vector3f>();

	public Sierpinski2dDetMnt() throws Exception {
		setShader("/shaders/angel/vertex.vs", "/shaders/angel/fragment-color.fs");
		mesh = new Mesh(GL_LINES);
//		mesh = new Mesh(GL_TRIANGLES);

		Vector3f a = new Vector3f(-1, -1, 0);
		Vector3f b = new Vector3f(1, -1, 0);
		Vector3f c = new Vector3f(0, 1, 0);
		divide(a, b, c, 5);

		mesh.genArrayBufferv3f(vertices);
	}

	private void strokeTriangle(Vector3f a, Vector3f b, Vector3f c) {
		vertices.add(a);
		vertices.add(b);
		vertices.add(b);
		vertices.add(c);
		vertices.add(c);
		vertices.add(a);
	}

	private void fillTriangle(Vector3f a, Vector3f b, Vector3f c) {
		vertices.add(a);
		vertices.add(b);
		vertices.add(c);
	}

	private void divide(Vector3f a, Vector3f b, Vector3f c, int numDivisions) {
		if (numDivisions < 1) {
			if (mesh.mode == GL_LINES) {
				strokeTriangle(a, b, c);
				return;
			} else if (mesh.mode == GL_TRIANGLES) {
				fillTriangle(a, b, c);
				return;
			}
		}

		Vector3f ab = a.add(b, new Vector3f()).mul(0.5f);
		Vector3f ac = a.add(c, new Vector3f()).mul(0.5f);
		Vector3f bc = b.add(c, new Vector3f()).mul(0.5f);

		float scl = 12;
		randomize(ab, scl);
		randomize(ac, scl);
		randomize(bc, scl);

		numDivisions--;
		divide(a, ab, ac, numDivisions);
		divide(ab, b, bc, numDivisions);
		divide(ac, bc, c, numDivisions);
	}

	private void randomize(Vector3f vec, float scl) {
		float rndX = (float) Math.random() / scl - 1 / scl / 2;
		float rndY = (float) Math.random() / scl - 1 / scl / 2;
		vec.add(rndX, rndY, 0);
	}

	@Override
	public void update(float interval) {
		// TODO Auto-generated method stub

	}
}
