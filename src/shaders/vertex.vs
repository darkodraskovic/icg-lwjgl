#version 330

uniform mat4 viewMatrix;
uniform mat4 projectionMatrix;

in vec3 vPosition;
in vec3 vColor;

out vec3 color;

void main()
{
    gl_Position = projectionMatrix * viewMatrix * vec4(vPosition, 1.0);
    color = vColor;
}