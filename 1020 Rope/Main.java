import java.io.*;
import java.util.*;

import static java.lang.Math.*;

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
		
		int n = nextInt();
		double r = nextDouble();
		double[] x = new double [n];
		double[] y = new double [n];
		for (int i = 0; i < n; i++) {
			x[i] = nextDouble();
			y[i] = nextDouble();
		}
		double ans = 0.0;
		for (int i = 0; i < n; i++)
			ans += dist(x[i], y[i], x[(i + 1) % n], y[(i + 1) % n]);
		out.printf(Locale.US, "%.2f%n", ans + 2.0 * PI * r);
		
		out.close();
	}
	
	double dist(double x1, double y1, double x2, double y2) {
		return sqrt(sqr(x2 - x1) + sqr(y2 - y1));
	}

	double sqr(double x) {
		return x * x;
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

	double nextDouble() throws IOException {
		return Double.parseDouble(nextToken());
	}
}
