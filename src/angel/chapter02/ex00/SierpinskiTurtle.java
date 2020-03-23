package angel.chapter02.ex00;

import java.util.ArrayList;

import org.joml.Vector3f;

import algo.Sierpinski;
import lib.Entity;
import lib.gfx.Turtle;

public class SierpinskiTurtle extends Entity {
	public SierpinskiTurtle() throws Exception {
		setShader("/shaders/angel/vertex.vs", "/shaders/angel/fragment-color.fs");
		Turtle turtle = new Turtle();
		mesh = turtle;

		ArrayList<Vector3f> gasket2D = Sierpinski.sierpinski2D(5);
		for (int i = 0; i < gasket2D.size() - 2; i += 3) {
			turtle.to(gasket2D.get(i));
			turtle.down();
			turtle.to(gasket2D.get(i + 1));
			turtle.to(gasket2D.get(i + 2));
			turtle.to(gasket2D.get(i));
			turtle.up();
		}

		turtle.update();
	}

	@Override
	public void update(float interval) {

	}

}
