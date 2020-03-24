package angel.chapter02;

import static org.lwjgl.opengl.GL11.GL_POINTS;

import java.util.ArrayList;

import org.joml.Vector3f;

import lib.Entity;
import lib.graph.Mesh;

public class Sierpinski2dRnd extends Entity {

	ArrayList<Vector3f> vertices = new ArrayList<Vector3f>();
	int numPoints = 50000;

	public Sierpinski2dRnd() throws Exception {
		setShader("/shaders/angel/vertex.vs", "/shaders/angel/fragment-color.fs");

		mesh = new Mesh(GL_POINTS);

		// vertices
		vertices.add(new Vector3f(-1, -1, 0));
		vertices.add(new Vector3f(1, -1, 0));
		vertices.add(new Vector3f(0, 1, 0));

		Vector3f point = new Vector3f();
		for (int i = 0; i < numPoints; i++) {
			int index = (int) (Math.random() * 3);
			point = vertices.get(index).add(point, new Vector3f()).mul(0.5f);
			vertices.add(point);
		}

		mesh.genArrayBufferv3f(vertices);
	}

	@Override
	public void update(float interval) {
		// TODO Auto-generated method stub
	}

}
