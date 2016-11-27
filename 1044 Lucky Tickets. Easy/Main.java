import java.io.*;

public class Main {
	public static void main(String[] args) throws IOException {
		try {
			if (new File("input.txt").exists())
				System.setIn(new FileInputStream("input.txt"));
		} catch (SecurityException e) {}
		
		int n = nextInt() / 2 - 1;
		int[] count = new int [37];
		int[] max = { 10, 100, 1000, 10000 };
		for (int i = 0; i < max[n]; i++) {
			int digSum = 0;
			for (int j = 0, t = i; j < 4; j++, t /= 10)
				digSum += t % 10;
			count[digSum]++;
		}
		int ans = 0;
		for (int i = 0; i < count.length; i++) ans += count[i] * count[i];
		System.out.println(ans);
	}

	static int nextInt() throws IOException {
		int n, c, s = 1;
		for (c = System.in.read(); c < '0' || c > '9'; c = System.in.read())
			if (c == '-')
				s = -1;
		for (n = 0; '0' <= c && c <= '9'; c = System.in.read())
			n = n * 10 + c - '0';
		return s == 1 ? n : -n;
 	}
}
