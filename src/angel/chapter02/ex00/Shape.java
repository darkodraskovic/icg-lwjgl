package angel.chapter02.ex00;

import org.joml.Vector3f;
import org.lwjglb.engine.Entity;
import org.lwjglb.engine.graph.PixelBuffer;

public class Shape extends Entity {
	public PixelBuffer frameBuffer;
	
	public Shape(int width, int height) throws Exception {
		setShader("/shaders/angel/vertex.vs", "/shaders/angel/fragment.fs");
		setFrameBuffer(width, height);
		
		frameBuffer.writePixel(5, 1, new Vector3f(1,0,0));
		frameBuffer.update();
		
		setPosition(-width/2, -height/2, 0);
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
