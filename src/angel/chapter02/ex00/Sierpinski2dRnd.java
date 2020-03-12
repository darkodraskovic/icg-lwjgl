package angel.chapter02.ex00;

import static org.lwjgl.opengl.GL11.GL_POINTS;

import java.util.ArrayList;

import org.joml.Vector3f;
import org.lwjglb.engine.Entity;
import org.lwjglb.engine.Utils;
import org.lwjglb.engine.graph.Mesh;
import org.lwjglb.engine.graph.Shader;

public class Sierpinski2dRnd extends Entity {

	ArrayList<Vector3f> vertices;
	int numPoints = 50000;
	
	public Sierpinski2dRnd() throws Exception{
		Shader shader = new Shader("/shaders/angel/vertex.vs", "/shaders/angel/fragment.fs");
		setShader(shader);

		mesh = new Mesh(GL_POINTS);
		
		// vertices
		vertices = new ArrayList<Vector3f>();
		vertices.add(new Vector3f(-1,-1,0));
		vertices.add(new Vector3f(1,-1,0));
		vertices.add(new Vector3f(0,1,0));

		Vector3f point = new Vector3f();
		for (int i = 0; i < numPoints; i++) {
			int index = (int)(Math.random()*3);
			point = vertices.get(index).add(point, new Vector3f()).mul(0.5f);
			vertices.add(point);
		}
		
		float[] verts = Utils.Vector3fListToFloatArray(vertices);
		mesh.genArrayBufferf(verts, 0, 3);
	}
	
	@Override
	public void update(float interval) {
		// TODO Auto-generated method stub
	}

}
