package angel.chapter02.ex00;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;

import java.util.ArrayList;

import org.joml.Vector3f;
import org.lwjglb.engine.Entity;
import org.lwjglb.engine.Utils;
import org.lwjglb.engine.graph.Mesh;
import org.lwjglb.engine.graph.Shader;

public class Sierpinski00 extends Entity {

	ArrayList<Vector3f> vertices;
	
	public Sierpinski00() throws Exception{
		Shader shader = new Shader("/shaders/angel/vertex.vs", "/shaders/angel/fragment.fs");
		setShader(shader);

		Mesh mesh = new Mesh();
		mesh.setMode(GL_TRIANGLES);
		setMesh(mesh);
		
		// vertices
		vertices = new ArrayList<Vector3f>();
		
		Vector3f a = new Vector3f(-1,-1,0);
		Vector3f b = new Vector3f(1,-1,0);
		Vector3f c = new Vector3f(0,1,0);
		divide(a, b, c, 6);
		
		float[] verts = Utils.Vector3fListToFloatArray(vertices);
		mesh.genArrayBufferf(verts, 0, 3);
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
