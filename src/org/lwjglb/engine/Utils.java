package org.lwjglb.engine;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import org.joml.Vector2f;
import org.joml.Vector3f;

public class Utils {

    public static String loadResource(String fileName) throws Exception {
        String result;
        try (InputStream in = Utils.class.getResourceAsStream(fileName);
             Scanner scanner = new Scanner(in, java.nio.charset.StandardCharsets.UTF_8.name())) {
            result = scanner.useDelimiter("\\A").next();
        }
        return result;
    }
    
    public static float[] Vector2fListToFloatArray(ArrayList<Vector2f> list) {
		int index = 0;
		float[] array = new float[list.size()*2];
		for (Iterator<Vector2f> iterator = list.iterator(); iterator.hasNext();) {
			Vector2f vector2f = (Vector2f) iterator.next();
			array[index++] = vector2f.x;
			array[index++] = vector2f.y;
		}
		return array;
    }
    
    public static float[] Vector3fListToFloatArray(ArrayList<Vector3f> list) {
		int index = 0;
		float[] array = new float[list.size()*3];
		for (Iterator<Vector3f> iterator = list.iterator(); iterator.hasNext();) {
			Vector3f vector3f = (Vector3f) iterator.next();
			array[index++] = vector3f.x;
			array[index++] = vector3f.y;
			array[index++] = vector3f.z;
		}
		return array;
    }

}