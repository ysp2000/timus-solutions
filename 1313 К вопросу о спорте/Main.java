import java.io.*;

public class Main {
	public static void main(String[] args) throws IOException {
		try {
			if (new File("input.txt").exists())
				System.setIn(new FileInputStream("input.txt"));
		} catch (SecurityException e) {}

		int n = nextInt();
		int[][] col = new int [n][n];
		for (int i = 0; i < n; i++)
			for (int j = 0; j < n; j++)
				col[i][j] = nextInt();
		int sx = 0, sy = 0, cx = 1, cy = -1;
		PrintWriter out = new PrintWriter(System.out);
		boolean blank = false;
		for (int op = n * n; op > 0; op--) {
			cx--; cy++;
			if (cx < 0 || cy >= n) {
				if (sx < n - 1) sx++;
				else sy++;
				cx = sx; cy = sy;
			}
			if (blank) out.print(' '); else blank = true;
			out.print(col[cx][cy]);
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
