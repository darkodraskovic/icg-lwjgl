#version 330

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 modelViewMatrix;

layout (location = 0) in vec3 vPosition;
layout (location = 1) in vec3 vColor;
layout (location = 2) in vec2 vTexCoord;

out vec4 position;
out vec3 color;
out vec2 texCoord;

void main()
{
    gl_Position = projectionMatrix * modelViewMatrix * vec4(vPosition, 1.0);
    position = gl_Position;
    color = vColor;
    texCoord = vTexCoord;
}