package org.lwjglb.game;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_E;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_Q;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;

import java.util.ArrayList;
import java.util.Iterator;

import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjglb.engine.GameItem;
import org.lwjglb.engine.IGameLogic;
import org.lwjglb.engine.MouseInput;
import org.lwjglb.engine.Window;
import org.lwjglb.engine.graph.Camera;
import org.lwjglb.engine.graph.ShaderProgram;

public class Game implements IGameLogic {
	private static final float MOUSE_SENSITIVITY = 0.2f;

	private static final float CAMERA_POS_STEP = 0.05f;
	private final Vector3f cameraInc;
	private final Camera camera;

	private final Renderer renderer;

	private ArrayList<GameItem> gameItems;

	public Game() {
		renderer = new Renderer();
		camera = new Camera();
		cameraInc = new Vector3f();

		gameItems = new ArrayList<GameItem>();
	}

	@Override
	public void init(Window window) throws Exception {
		ShaderProgram shaderProgram = new ShaderProgram("/shaders/vertex.vs", "/shaders/fragment.fs",
				new String[] { "projectionMatrix", "viewMatrix", "modelViewMatrix" });
		Triangle triangle = new Triangle();
		GameItem gameItem = new GameItem(triangle, shaderProgram);
		gameItems.add(gameItem);
		
		camera.setPosition(0, 0, 1);
	}

	@Override
	public void input(Window window, MouseInput mouseInput) {
		cameraInc.set(0, 0, 0);
		if (window.isKeyPressed(GLFW_KEY_W)) {
			cameraInc.z = -1;
		} else if (window.isKeyPressed(GLFW_KEY_S)) {
			cameraInc.z = 1;
		}
		if (window.isKeyPressed(GLFW_KEY_A)) {
			cameraInc.x = -1;
		} else if (window.isKeyPressed(GLFW_KEY_D)) {
			cameraInc.x = 1;
		}
		if (window.isKeyPressed(GLFW_KEY_Q)) {
			cameraInc.y = -1;
		} else if (window.isKeyPressed(GLFW_KEY_E)) {
			cameraInc.y = 1;
		}
	}

	@Override
	public void update(float interval, MouseInput mouseInput) {
		// Update camera position
		camera.movePosition(cameraInc.x * CAMERA_POS_STEP, cameraInc.y * CAMERA_POS_STEP,
				cameraInc.z * CAMERA_POS_STEP);

		// Update camera based on mouse
		if (mouseInput.isMiddleButtonPressed()) {
			Vector2f rotVec = mouseInput.getDisplVec();
			camera.moveRotation(rotVec.x * MOUSE_SENSITIVITY, rotVec.y * MOUSE_SENSITIVITY, 0);
		}
	}

	@Override
	public void render(Window window) {
		renderer.render(window, camera, gameItems);
	}

	@Override
	public void cleanup() {
		for (Iterator<GameItem> iterator = gameItems.iterator(); iterator.hasNext();) {
			GameItem iGameItem = (GameItem) iterator.next();
			iGameItem.cleanup();
		}
	}

}
