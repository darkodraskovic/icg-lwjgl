package angel.chapter02.ex00;

import org.lwjglb.engine.App;
import org.lwjglb.engine.ILogic;
import org.lwjglb.engine.Window;
import org.lwjglb.engine.app.Game;

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
//		entities.add(new Flower());
//		entities.add(new Sierpinski2dDet());
//		entities.add(new Sierpinski2dRnd());
//		entities.add(new Sierpinski3dDet());
//		entities.add(new Sierpinski3dRnd());
		entities.add(new Sierpinski2dDetMnt());
	}

}
