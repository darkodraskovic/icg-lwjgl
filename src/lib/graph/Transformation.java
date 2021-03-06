package lib.graph;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import lib.Entity;

public class Transformation {

	private final Matrix4f projectionMatrix;

	private final Matrix4f modelViewMatrix;

	private final Matrix4f viewMatrix;

	public Transformation() {
		projectionMatrix = new Matrix4f();
		modelViewMatrix = new Matrix4f();
		viewMatrix = new Matrix4f();
	}

	public final Matrix4f getProjectionMatrix(float fov, float width, float height, float zNear, float zFar) {
		return projectionMatrix.setPerspective(fov, width / height, zNear, zFar);
	}

	public Matrix4f getViewMatrix(Camera camera) {
		Vector3f cameraPos = camera.getPosition();
		Vector3f rotation = camera.getRotation();

		viewMatrix.identity();
		// First do the rotation so camera rotates over its position
		viewMatrix.rotate(rotation.x, new Vector3f(1, 0, 0)).rotate(rotation.y, new Vector3f(0, 1, 0));
		// Then do the translation
		viewMatrix.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);
		return viewMatrix;
	}

	public Matrix4f getModelViewMatrix(Entity gameItem, Matrix4f viewMatrix) {
		Vector3f rotation = gameItem.getRotation();
		modelViewMatrix.identity().translate(gameItem.getPosition()).rotateX(-rotation.x).rotateY(-rotation.y)
				.rotateZ(-rotation.z).scale(gameItem.getScale());
		Matrix4f viewCurr = new Matrix4f(viewMatrix);
		return viewCurr.mul(modelViewMatrix);
	}
}
