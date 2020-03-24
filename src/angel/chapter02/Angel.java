package angel.chapter02;

import lib.App;
import lib.ILogic;
import lib.Window;
import lib.app.Game;

public class Angel extends Game {

	public static void main(String[] args) {
		try {
			boolean vSync = true;
			ILogic gameLogic = new Angel();
			App game = new App("ANGEL", 800, 600, vSync, gameLogic);
			game.run();
		} catch (Exception excp) {
			excp.printStackTrace();
			System.exit(-1);
		}
	}

	@Override
	public void init(Window window) throws Exception {
		// TEST
//		entities.add(new Flower());
		
		// SIERPINSKI
//		entities.add(new Sierpinski2dDet());
//		entities.add(new Sierpinski2dRnd());
//		entities.add(new Sierpinski3dDet());
//		entities.add(new Sierpinski3dRnd());
//		entities.add(new Sierpinski2dDetMnt());
//		entities.add(new Sierpinski3dDetMnt());
		
		// PIXEL
//		entities.add(new Shape(64, 64));
//		cameraPosStep = 1.5f;
//		camera.setPosition(0, 0, 64);
		
		// TURTLE, KOCH
//		entities.add(new SierpinskiTurtle());
		entities.add(new KochEntity());
	}
}
