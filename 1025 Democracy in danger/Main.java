import java.io.*;
import java.util.*;

public class Main {
	public static void main(String[] args) throws IOException {
		try {
			if (new File("input.txt").exists()) {
				System.setIn(new FileInputStream("input.txt"));
			}
		} catch (SecurityException e) {
		}
		
		new Main().run();
	}

	BufferedReader in;
	PrintWriter out;
	StringTokenizer st = new StringTokenizer("");
	
	void run() throws IOException {
		in = new BufferedReader(new InputStreamReader(System.in));
		out = new PrintWriter(System.out);
		
		int n = nextInt();
		int[] a = new int [n];
		
		for (int i = 0; i < n; i++) {
			a[i] = nextInt() >> 1;
		}
		
		Arrays.sort(a);
		n >>= 1;
		int ans = n + 1;
		
		for (int i = 0; i <= n; i++) {
			ans += a[i]; 
		}
		
		out.println(ans);
		out.close();
	}
	
	String nextToken() throws IOException {
		while (!st.hasMoreTokens()) {
			st = new StringTokenizer(in.readLine());
		}
		
		return st.nextToken();
	}
	
	int nextInt() throws IOException {
		return Integer.parseInt(nextToken());
	}
}
