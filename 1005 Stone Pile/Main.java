import java.io.*;
import java.util.*;
import static java.lang.Math.*;
import static java.util.Arrays.fill;
import static java.util.Arrays.binarySearch;
import static java.util.Arrays.sort;

public class Main {
	public static void main(String[] args) throws IOException {
		try {
			if (new File("input.txt").exists())
				System.setIn(new FileInputStream("input.txt"));
		} catch (SecurityException e) {}
		
		new Main().run();
	}

	BufferedReader in;
	PrintWriter out;
	StringTokenizer st = new StringTokenizer("");
	
	int N;
	int[] a;
	
	void run() throws IOException {
		in = new BufferedReader(new InputStreamReader(System.in));
		out = new PrintWriter(System.out);
		
		N = nextInt();
		a = new int [N];
		for (int i = 0; i < N; i++)
			a[i] = nextInt();
		int ans = Integer.MAX_VALUE;
		for (int m = 0; m < 1 << N; m++)
			ans = min(ans, calc(m));
		out.println(ans);
		
		out.close();
	}
	
	int calc(int m) {
		int s1 = 0;
		int s2 = 0;
		for (int i = 0; i < N; i++) {
			if ((m & (1 << i)) == 0) {
				s1 += a[i];
			} else {
				s2 += a[i];
			}
		}
		return abs(s2 - s1);
	}

	/**********************************************
	 * Input
	 **********************************************/
	String nextToken() throws IOException {
		while (!st.hasMoreTokens())
			st = new StringTokenizer(in.readLine());
		return st.nextToken();
	}
	
	int nextInt() throws IOException {
		return Integer.parseInt(nextToken());
	}
	
	long nextLong() throws IOException {
		return Long.parseLong(nextToken());
	}
	
	double nextDouble() throws IOException {
		return Double.parseDouble(nextToken());
	}
	
	String nextLine() throws IOException {
		st = new StringTokenizer("");
		return in.readLine();
	}
	
	boolean EOF() throws IOException {
		while (!st.hasMoreTokens()) {
			String s = in.readLine();
			if (s == null)
				return true;
			st = new StringTokenizer(s);
		}
		return false;
	}
}
