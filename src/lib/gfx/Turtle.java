package lib.gfx;

import static org.lwjgl.opengl.GL11.GL_LINES;

import java.util.ArrayList;

import org.joml.Vector3f;

import lib.graph.Mesh;

public class Turtle extends Mesh {

	// vertices
	private boolean penDown = false;
	private float penAngle = 0;
	private Vector3f penPosition = new Vector3f();
	private ArrayList<Vector3f> vertices = new ArrayList<Vector3f>();
	// colors
	private Vector3f color = new Vector3f(1, 1, 1);
	private ArrayList<Vector3f> colors = new ArrayList<Vector3f>();

	public Turtle() {
		mode = GL_LINES;
	}

	public void forward(float distance) {
		if (penDown) {
			vertices.add(new Vector3f(penPosition));
			colors.add(new Vector3f(color));
		}
		penPosition.x += (float) Math.cos(penAngle) * distance;
		penPosition.y += (float) Math.sin(penAngle) * distance;
		if (penDown) {
			vertices.add(new Vector3f(penPosition));
			colors.add(new Vector3f(color));
		}
	}

	public void to(Vector3f destination) {
		Vector3f dirVec = destination.sub(penPosition, new Vector3f());
		float dirAngle = (float) Math.atan2(dirVec.y, dirVec.x);
		float angleDiff = dirAngle - penAngle;
		if (angleDiff < 0) {
			right(Math.abs(angleDiff));
		} else {
			left(Math.abs(angleDiff));
		}
		forward(dirVec.length());
	}

	public void up() {
		this.penDown = false;
	}

	public void down() {
		this.penDown = true;
	}

	public void right(float angle) {
		this.penAngle -= angle;
	}

	public void left(float angle) {
		this.penAngle += angle;
	}

	public void update() {
		genArrayBufferv3f(vertices, 0);
		genArrayBufferv3f(colors, 1);
	}
}
