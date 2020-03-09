#version 330

in  vec3 color;

out vec4 fragColor;

void main()
{
    fragColor = vec4(color, 1.0);
    // fragColor = vec4(1.0, 0.0, 0.0, 1.0);
}