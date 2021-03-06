package angel.chapter02;

import static org.lwjgl.opengl.GL11.GL_LINE_STRIP;

import java.util.ArrayList;

import lib.Entity;
import lib.graph.Mesh;

public class Flower extends Entity {
	public Flower() throws Exception {
		super();

		setShader("/shaders/angel/vertex.vs", "/shaders/angel/fragment.fs");

		mesh = new Mesh(GL_LINE_STRIP);
		
		// vertices
		ArrayList<Float> vs = new ArrayList<Float>();
		int numSteps = 12;
		double angleStep = (Math.PI * 2) / numSteps;
		vs.add(0.0f);
		vs.add(0.0f);
		vs.add(0.0f);
		for (int i = 0; i < numSteps; i++) {
			vs.add((float) Math.cos(i * angleStep));
			vs.add((float) Math.sin(i * angleStep));
			vs.add(0.0f);
		}

		final float[] vertices = new float[vs.size()];
		final float[] colors = new float[vs.size()];
		for (int i = 0; i < vs.size(); i++) {
			vertices[i] = vs.get(i);
			colors[i] = Math.abs(vs.get(i));
		}
		colors[2] = 1;

		mesh.genArrayBufferf(vertices, 3);
		mesh.genArrayBufferf(colors, 3);
		
		// indices
		int[] indices = new int[numSteps / 2 * 3 + 1];
		int index = 0;
		for (int i = 0; i < numSteps / 2; i++) {
			indices[index++] = 0;
			indices[index++] = 2 * i + 1;
			indices[index++] = 2 * i + 2;
		}
		indices[indices.length - 1] = 0;

		mesh.genElementBuffer(indices);
	}

	@Override
	public void update(float interval) {
		moveRotation(0, 0, interval * (float)Math.PI/4);
	}
}
