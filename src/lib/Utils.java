package lib;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import org.joml.Vector2f;
import org.joml.Vector2i;
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
	
	public static int[] IntListToIntArray(ArrayList<Integer> list) {
		int index = 0;
		int[] array = new int[list.size()];
		for (Iterator<Integer> iterator = list.iterator(); iterator.hasNext();) {
			array[index++] = iterator.next();
		}
		return array;
	}

	// vector int
	public static int[] Vector2iListToIntArray(ArrayList<Vector2i> list) {
		int index = 0;
		int[] array = new int[list.size() * 2];
		for (Iterator<Vector2i> iterator = list.iterator(); iterator.hasNext();) {
			Vector2i vector2i = iterator.next();
			array[index++] = vector2i.x;
			array[index++] = vector2i.y;
		}
		return array;
	}

	// vetcor float
	public static float[] Vector2fListToFloatArray(ArrayList<Vector2f> list) {
		int index = 0;
		float[] array = new float[list.size() * 2];
		for (Iterator<Vector2f> iterator = list.iterator(); iterator.hasNext();) {
			Vector2f vector2f = iterator.next();
			array[index++] = vector2f.x;
			array[index++] = vector2f.y;
		}
		return array;
	}

	public static float[] Vector3fListToFloatArray(ArrayList<Vector3f> list) {
		int index = 0;
		float[] array = new float[list.size() * 3];
		for (Iterator<Vector3f> iterator = list.iterator(); iterator.hasNext();) {
			Vector3f vector3f = iterator.next();
			array[index++] = vector3f.x;
			array[index++] = vector3f.y;
			array[index++] = vector3f.z;
		}
		return array;
	}
}