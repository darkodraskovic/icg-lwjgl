#version 330

in vec3 color;
in vec2 texCoord;

out vec4 fragColor;

void main()
{
    // fragColor = vec4(color, 1.0);
    fragColor = vec4(0.0, 1.0, 1.0, 1.0);
}
