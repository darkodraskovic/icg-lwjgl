package angel.chapter02.ex00;

import static org.lwjgl.opengl.GL11.glPointSize;

import java.util.ArrayList;

import org.joml.Vector2i;
import org.joml.Vector3f;
import org.lwjglb.engine.Entity;
import org.lwjglb.engine.gfx.PixelBuffer;
import org.lwjglb.engine.gfx.PixelShaper;

public class Shape extends Entity {
	public PixelBuffer frameBuffer;

	public Shape(int width, int height) throws Exception {
		setShader("/shaders/angel/vertex.vs", "/shaders/angel/fragment.fs");
		setFrameBuffer(width, height);

		ArrayList<Vector2i> line1 = PixelShaper.bresenhamLine(5, 5, 2, 40);
		frameBuffer.writePixels(line1, new Vector3f(1, 0, 0));

		ArrayList<Vector2i> line2 = PixelShaper.bresenhamLine(5, 3, 20, 1);
		frameBuffer.writePixels(line2, new Vector3f(0, 1, 0));
		
		frameBuffer.update();

		setPosition(-width / 2, -height / 2, 0);

		glPointSize(3);
	}

	public void setFrameBuffer(int width, int height) {
		this.frameBuffer = new PixelBuffer(width, height);
		mesh = frameBuffer;
	}

	@Override
	public void update(float interval) {
		// TODO Auto-generated method stub
	}
}
