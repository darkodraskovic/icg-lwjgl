package angel.chapter02.ex00;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;

import java.util.ArrayList;

import org.joml.Vector3f;

import lib.Entity;
import lib.graph.Mesh;

public class Sierpinski2dDet extends Entity {

	ArrayList<Vector3f> vertices = new ArrayList<Vector3f>();
	
	public Sierpinski2dDet() throws Exception{
		setShader("/shaders/angel/vertex.vs", "/shaders/angel/fragment-color.fs");

		mesh = new Mesh(GL_TRIANGLES);
		
		Vector3f a = new Vector3f(-1,-1,0);
		Vector3f b = new Vector3f(1,-1,0);
		Vector3f c = new Vector3f(0,1,0);
		divide(a, b, c, 6);
		
		mesh.genArrayBufferv3f(vertices);
	}
	
	private void divide(Vector3f a, Vector3f b, Vector3f c, int numDivisions) {
		if (numDivisions < 1) {
			vertices.add(a);
			vertices.add(b);
			vertices.add(c);
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
	
	@Override
	public void update(float interval) {
		// TODO Auto-generated method stub

	}

}
