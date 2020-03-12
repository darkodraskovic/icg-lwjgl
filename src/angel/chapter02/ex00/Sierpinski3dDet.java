package angel.chapter02.ex00;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;

import java.util.ArrayList;

import org.joml.Vector3f;
import org.lwjglb.engine.Entity;
import org.lwjglb.engine.Utils;
import org.lwjglb.engine.graph.Mesh;
import org.lwjglb.engine.graph.Shader;

public class Sierpinski3dDet extends Entity {

	ArrayList<Vector3f> vertices;
	ArrayList<Integer> indices;

	public Sierpinski3dDet() throws Exception {
		Shader shader = new Shader("/shaders/angel/vertex.vs", "/shaders/angel/fragment.fs");
		setShader(shader);

		mesh = new Mesh(GL_TRIANGLES);

		// vertices
		vertices = new ArrayList<Vector3f>();
		indices = new ArrayList<Integer>();

		Vector3f a = new Vector3f(-1, -1, -1);
		Vector3f b = new Vector3f(0, -1, 1);
		Vector3f c = new Vector3f(1, -1, -1);
		Vector3f d = new Vector3f(0, 1, 0);
		divide(a, b, c, d, 4);
		float[] verts = Utils.Vector3fListToFloatArray(vertices);
		mesh.genArrayBufferf(verts, 0, 3);

		int[] idx = Utils.IntListToIntArray(indices);
		mesh.genElementBuffer(idx);
	}

	private void divide(Vector3f a, Vector3f b, Vector3f c, Vector3f d, int numDivisions) {
		if (numDivisions < 1) {
			vertices.add(a);
			vertices.add(b);
			vertices.add(c);
			vertices.add(d);
			int startIdx = vertices.size() - 4;
			indices.add(startIdx);
			indices.add(startIdx + 1);
			indices.add(startIdx + 2);

			indices.add(startIdx);
			indices.add(startIdx + 1);
			indices.add(startIdx + 3);

			indices.add(startIdx);
			indices.add(startIdx + 2);
			indices.add(startIdx + 3);

			indices.add(startIdx + 1);
			indices.add(startIdx + 2);
			indices.add(startIdx + 3);

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
		// TODO Auto-generated method stub

	}

}
