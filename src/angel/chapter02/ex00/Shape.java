package angel.chapter02.ex00;

import static org.lwjgl.opengl.GL11.glPointSize;

import org.lwjglb.engine.Entity;
import org.lwjglb.engine.pixel.Buffer;

public class Shape extends Entity {
	public Buffer frameBuffer;

	public Shape(int width, int height) throws Exception {
		setShader("/shaders/angel/vertex.vs", "/shaders/angel/fragment.fs");
		setFrameBuffer(width, height);

		frameBuffer.update();

		setPosition(-width / 2, -height / 2, 0);

		glPointSize(3);
	}

	public void setFrameBuffer(int width, int height) {
		this.frameBuffer = new Buffer(width, height);
		mesh = frameBuffer;
	}

	@Override
	public void update(float interval) {
		// TODO Auto-generated method stub
	}
}
