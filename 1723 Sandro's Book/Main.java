import java.io.*;
import java.util.*;
import java.util.Map.*;

public class Main {
	public static void main(String[] args) throws IOException {
		try {
			if (new File("input.txt").exists())
				System.setIn(new FileInputStream("input.txt"));
		} catch (SecurityException e) {}
		
		String s = (new BufferedReader(new InputStreamReader(System.in))).readLine();
		Map<String, Integer> map = new HashMap<String, Integer>(s.length() * s.length());
		for (int i = 0; i < s.length(); i++)
			for (int j = i; j < s.length(); j++)
				add(map, s.substring(i, j + 1));
		String ans = null;
		for (Entry<String, Integer> e : map.entrySet())
			if (ans == null || map.get(ans) < e.getValue())
				ans = e.getKey();
		System.out.println(ans);
	}

	static void add(Map<String, Integer> map, String s) {
		if (!map.containsKey(s))
			map.put(s, 0);
		map.put(s, map.get(s) + 1);
	}
}
