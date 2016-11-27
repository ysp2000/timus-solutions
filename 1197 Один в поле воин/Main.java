import java.io.*;

public class Main {
	public static void main(String[] args) throws IOException {
		try {
			if (new File("input.txt").exists())
				System.setIn(new FileInputStream("input.txt"));
		} catch (SecurityException e) {}
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		int[] ans = {
			2, 3, 4, 4, 4, 4, 3, 2,
			3, 4, 6, 6, 6, 6, 4, 3,
			4, 6, 8, 8, 8, 8, 6, 4,
			4, 6, 8, 8, 8, 8, 6, 4,
			4, 6, 8, 8, 8, 8, 6, 4,
			4, 6, 8, 8, 8, 8, 6, 4,
			3, 4, 6, 6, 6, 6, 4, 3,
			2, 3, 4, 4, 4, 4, 3, 2,
		};
		for (int T = Integer.parseInt(in.readLine()); T --> 0; System.out.println(ans[pos(in.readLine())]));
	}

	static int pos(String s) {
		return ((s.charAt(1) - '1') << 3) | (s.charAt(0) - 'a');
	}
}
