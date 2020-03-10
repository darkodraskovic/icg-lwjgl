package org.lwjglb.game;

import static org.lwjgl.glfw.GLFW.GLFW_KEY_A;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_D;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_E;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_Q;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_S;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_W;
import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glViewport;

import java.util.ArrayList;
import java.util.Iterator;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.lwjglb.engine.App;
import org.lwjglb.engine.Entity;
import org.lwjglb.engine.ILogic;
import org.lwjglb.engine.Mouse;
import org.lwjglb.engine.Window;
import org.lwjglb.engine.graph.Camera;
import org.lwjglb.engine.graph.Transformation;
public class Game implements ILogic {
	private static final float MOUSE_SENSITIVITY = 0.2f;

	private static final float FOV = (float) Math.toRadians(60.0f);
	private static final float Z_NEAR = 0.01f;
	private static final float Z_FAR = 1000.f;
	private static final float CAMERA_POS_STEP = 0.05f;
	private final Vector3f cameraInc;
	private final Camera camera;

	private final Transformation transformation  = new Transformation();

	private ArrayList<Entity> entities;

    public static void main(String[] args) {
        try {
            boolean vSync = true;
            ILogic gameLogic = new Game();
            App gameEng = new App("GAME", 600, 480, vSync, gameLogic);
            gameEng.run();
        } catch (Exception excp) {
            excp.printStackTrace();
            System.exit(-1);
        }
    }	
	
	public Game() {
		camera = new Camera();
		cameraInc = new Vector3f();

		entities = new ArrayList<Entity>();
	}

	@Override
	public void init(Window window) throws Exception {
		Entity entity = new Triangle();
		entities.add(entity);
		
		camera.setPosition(0, 0, 1);
	}

	@Override
	public void input(Window window, Mouse mouseInput) {
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
	public void update(float interval, Mouse mouseInput) {
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
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		if (window.isResized()) {
			glViewport(0, 0, window.getWidth(), window.getHeight());
			window.setResized(false);
		}

		Matrix4f projectionMatrix = transformation.getProjectionMatrix(FOV, window.getWidth(), window.getHeight(),
				Z_NEAR, Z_FAR);
		Matrix4f viewMatrix = transformation.getViewMatrix(camera);

		for (Iterator<Entity> iterator = entities.iterator(); iterator.hasNext();) {
			Entity iEntity = (Entity) iterator.next();
			Matrix4f modelViewMatrix = transformation.getModelViewMatrix(iEntity, viewMatrix);
			iEntity.draw(projectionMatrix, viewMatrix, modelViewMatrix);
		}
	}

	@Override
	public void cleanup() {
		for (Iterator<Entity> iterator = entities.iterator(); iterator.hasNext();) {
			Entity iEntity = (Entity) iterator.next();
			iEntity.cleanup();
		}
	}

}
