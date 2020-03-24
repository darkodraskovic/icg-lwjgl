package angel.chapter02;

import static org.lwjgl.opengl.GL11.GL_LINES;
import static org.lwjgl.opengl.GL11.GL_POINTS;

import java.util.ArrayList;

import org.joml.Vector3f;

import algo.Koch;
import lib.Entity;
import lib.graph.Mesh;

public class KochEntity extends Entity {

	ArrayList<Vector3f> points = new ArrayList<Vector3f>();

	public KochEntity() throws Exception {
		setShader("/shaders/angel/vertex.vs", "/shaders/angel/fragment-color.fs");
//		mesh = new Mesh(GL_POINTS);
		mesh = new Mesh(GL_LINES);

		int numDivisions = 4;
		Vector3f a = new Vector3f(-1, 1, 0);
		Vector3f b = new Vector3f(1, 1, 0);
		Vector3f c = new Vector3f(0, -1, 0);

		points.addAll(Koch.koch2D(a, b, numDivisions));
		points.addAll(Koch.koch2D(b, c, numDivisions));
		points.addAll(Koch.koch2D(c, a, numDivisions));

		ArrayList<Vector3f> line = new ArrayList<Vector3f>();
		for (int i = 0; i < points.size() - 1; i++) {
			line.add(points.get(i));
			line.add(points.get(i + 1));
		}
		mesh.genArrayBufferv3f(line);
	}

	@Override
	public void update(float interval) {
		// TODO Auto-generated method stub

	}
}
