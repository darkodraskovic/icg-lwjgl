package lib;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import lib.graph.Mesh;
import lib.graph.Shader;

public abstract class Entity {

	public Mesh mesh;

	private Shader shader;
	private String[] transformUniforms = new String[] { "projectionMatrix", "viewMatrix", "modelViewMatrix", };
	
	private final Vector3f position = new Vector3f();

	private float scale = 1;

	private final Vector3f rotation = new Vector3f();

	public Entity() {
		super();
	}

	public Entity(Shader shader, Mesh mesh) throws Exception {
		setShader(shader);
		this.mesh = mesh;
	}

	// SHADER
	public Shader getShader() {
		return shader;
	}

	public void setShader(Shader shader) throws Exception {
		this.shader = shader;
		this.setTransformUniforms();
	}

	public void setShader(String vertex, String fragment) throws Exception {
		shader = new Shader(vertex, fragment);
		this.setTransformUniforms();
	}
	
	private void setTransformUniforms() throws Exception {
		for (String uniform : transformUniforms) {
			shader.createUniform(uniform);
		}
	}
	
	// TRANSFORM
	public Vector3f getPosition() {
		return position;
	}

	public void setPosition(float x, float y, float z) {
		position.x = x;
		position.y = y;
		position.z = z;
	}

    public void movePosition(float offsetX, float offsetY, float offsetZ) {
    	position.x += offsetX;
    	position.y += offsetY;
    	position.z += offsetZ;
    }	
	public float getScale() {
		return scale;
	}

	public void setScale(float scale) {
		this.scale = scale;
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

    // UPDATE
    abstract public void update(float interval);
    
    // DRAW
	public void draw(Matrix4f projectionMatrix, Matrix4f viewMatrix, Matrix4f modelViewMatrix) {
		shader.bind();
		shader.setUniform("projectionMatrix", projectionMatrix);
		shader.setUniform("viewMatrix", viewMatrix);
		shader.setUniform("modelViewMatrix", modelViewMatrix);
		mesh.draw();
		shader.unbind();
	};

	// CLEANUP
	public void cleanup() {
		if (shader != null) {
			shader.cleanup();
		}

		if (mesh != null) {
			mesh.cleanup();
		}
	}
}