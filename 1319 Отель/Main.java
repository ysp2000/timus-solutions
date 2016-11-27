import java.io.*;

public class Main {
	public static void main(String[] args) throws IOException {
		try {
			if (new File("input.txt").exists())
				System.setIn(new FileInputStream("input.txt"));
		} catch (SecurityException e) {}

		int n = nextInt();
		int[][] ans = new int [n][n];
		int sx = 0, sy = 0, cx = -1, cy = 1;
		for (int op = n * n, cur = 1; op > 0; op--) {
			cy--; cx++;
			if (cy < 0 || cx >= n) {
				if (sy < n - 1) sy++;
				else sx++;
				cy = sy; cx = sx;
			}
			ans[cx][n - cy - 1] = cur++;
		}
		PrintWriter out = new PrintWriter(System.out);
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (j > 0) out.print(' ');
				out.print(ans[i][j]);
			}
			out.println();
		}
		out.close();
	}
	
	static int nextInt() throws IOException {
		int n, c;
		for (c = System.in.read(); c < '0' || c > '9'; c = System.in.read());
		for (n = 0; '0' <= c && c <= '9'; c = System.in.read())
			n = n * 10 + c - '0';
		return n;
 	}
}
