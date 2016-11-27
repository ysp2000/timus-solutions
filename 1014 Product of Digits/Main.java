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
	
	void run() throws IOException {
		in = new BufferedReader(new InputStreamReader(System.in));
		out = new PrintWriter(System.out);
		
		int N = nextInt();
		if (N == 0) {
			out.println(10);
			out.close();
			return;
		}
		if (N < 10) {
			out.println(N);
			out.close();
			return;
		}
		
		int sz = 0;
		int[] digits = new int [10000];
		for (int d = 9; d > 1; d--) {
			while (N % d == 0) {
				N /= d;
				digits[sz++] = d;
			}
		}
		if (N > 1) {
			out.println("-1");
		} else {
			for (int i = sz - 1; i >= 0; i--)
				out.print(digits[i]);
			out.println();
		}
		
		out.close();
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
