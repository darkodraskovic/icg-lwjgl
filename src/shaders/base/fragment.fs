#version 330

in vec4 position;
in vec3 color;
in vec2 texCoord;

out vec4 fragColor;

void main()
{
    fragColor = vec4(color, 1.0);
}
