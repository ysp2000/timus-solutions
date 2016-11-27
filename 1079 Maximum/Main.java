import java.io.*;

public class Main {
	public static void main(String[] args) throws IOException {
		try {
			if (new File("input.txt").exists())
				System.setIn(new FileInputStream("input.txt"));
		} catch (SecurityException e) {}
		
		int[] a = new int [100000]; a[0] = 0; a[1] = 1;
		int[] m = new int [100000]; m[0] = 0; m[1] = 1;
		for (int i = 2; i < a.length; i++) {
			a[i] = (i & 1) == 0 ? a[i >> 1] : (a[i >> 1] + a[(i >> 1) + 1]);
			m[i] = Math.max(a[i], m[i - 1]);
		}
		for (;;) {
			int n = nextInt();
			if (n == 0) break;
			System.out.println(m[n]);
		}
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
