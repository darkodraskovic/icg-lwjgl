package lib.graph;

import org.joml.Vector3f;

public class Camera {

	private final Vector3f position;

	private final Vector3f rotation;

	public Camera() {
		position = new Vector3f();
		rotation = new Vector3f();
	}

	public Camera(Vector3f position, Vector3f rotation) {
		this.position = position;
		this.rotation = rotation;
	}

	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(float x, float y, float z) {
		position.x = x;
		position.y = y;
		position.z = z;
	}

	public void movePosition(float offsetX, float offsetY, float offsetZ) {
		if (offsetZ != 0) {
			position.x += Math.sin(rotation.y) * -1.0f * offsetZ;
			position.z += Math.cos(rotation.y) * offsetZ;
		}
		if (offsetX != 0) {
			position.x += Math.sin(rotation.y - Math.PI / 2) * -1.0f * offsetX;
			position.z += Math.cos(rotation.y - Math.PI / 2) * offsetX;
		}
		position.y += offsetY;
	}

	public Vector3f getRotation() {
		return rotation;
	}

	public void setRotation(float x, float y, float z) {
		rotation.x = x;
		rotation.y = y;
		rotation.z = z;
	}

	public void moveRotation(float offsetX, float offsetY, float offsetZ) {
		rotation.x += offsetX;
		rotation.y += offsetY;
		rotation.z += offsetZ;
	}
}