package game;

import lib.App;
import lib.ILogic;
import lib.Window;

public class MyGame extends lib.app.Game {
	public static void main(String[] args) {
		try {
			boolean vSync = true;
			ILogic gameLogic = new MyGame();
			App game = new App("MY_GAME", 600, 480, vSync, gameLogic);
			game.run();
		} catch (Exception excp) {
			excp.printStackTrace();
			System.exit(-1);
		}
	}

	@Override
	public void init(Window window) throws Exception {
		entities.add(new Triangle());
	}
}
