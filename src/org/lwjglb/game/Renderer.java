package org.lwjglb.game;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;
import static org.lwjgl.opengl.GL11.glViewport;

import java.util.ArrayList;
import java.util.Iterator;

import org.joml.Matrix4f;
import org.lwjglb.engine.GameItem;
import org.lwjglb.engine.Window;
import org.lwjglb.engine.graph.Camera;
import org.lwjglb.engine.graph.Transformation;

public class Renderer {
	private static final float FOV = (float) Math.toRadians(60.0f);
	private static final float Z_NEAR = 0.01f;
	private static final float Z_FAR = 1000.f;
	
	private final Transformation transformation  = new Transformation();

	public void render(Window window, Camera camera, ArrayList<GameItem> gameItems) {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);

		if (window.isResized()) {
			glViewport(0, 0, window.getWidth(), window.getHeight());
			window.setResized(false);
		}

		Matrix4f projectionMatrix = transformation.getProjectionMatrix(FOV, window.getWidth(), window.getHeight(),
				Z_NEAR, Z_FAR);
		Matrix4f viewMatrix = transformation.getViewMatrix(camera);

		for (Iterator<GameItem> iterator = gameItems.iterator(); iterator.hasNext();) {
			GameItem iGameItem = (GameItem) iterator.next();
			Matrix4f modelViewMatrix = transformation.getModelViewMatrix(iGameItem, viewMatrix);
			iGameItem.draw(projectionMatrix, viewMatrix, modelViewMatrix);
		}
	}
}
