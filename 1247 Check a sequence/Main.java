import java.io.*;

public class Main {
	public static void main(String[] args) throws IOException {
		try {
			if (new File("input.txt").exists())
				System.setIn(new FileInputStream("input.txt"));
		} catch (SecurityException e) {}
		
		new Main().run();
	}

	BufferedReader in;
	
	void run() throws IOException {
		in = new BufferedReader(new InputStreamReader(System.in));
		int S = nextInt();
		int N = nextInt();
		int[] sum = new int [S + 1];
		for (int i = 1; i <= S; i++)
			sum[i] = sum[i - 1] + nextInt();
		for (int i = 1; i <= S; i++)
			sum[i] -= i;
		int max = Integer.MIN_VALUE;
		boolean ok = true;
		for (int i = S; i > 0; i--) {
			max = Math.max(max, sum[i]);
			if (max > sum[i - 1] + N) {
				ok = false;
				break;
			}
		}
		System.out.println(ok ? "YES" : "NO");
	}
	
	int nextInt() throws IOException {
		int n, c;
		for (c = in.read(); c < '0' || c > '9'; c = in.read());
		for (n = 0; '0' <= c && c <= '9'; c = in.read())
			n = n * 10 + c - '0';
		return n;
 	}
}
