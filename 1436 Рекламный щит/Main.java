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
	
	double EPS = 1e-11;
	double LEFT = -1e5;
	double RIGHT = 1e5;
	
	Point p0;
	Point p1;
	Point p2;
	
	void run() throws IOException {
		in = new BufferedReader(new InputStreamReader(System.in));
		out = new PrintWriter(System.out);
		
		p0 = new Point(0.0, 0.0);
		p1 = new Point(nextDouble(), nextDouble());
		p2 = new Point(nextDouble(), nextDouble());

		double miny = min(p1.y, p2.y) - EPS;
		double maxy = max(p1.y, p2.y) + EPS;
		double ans;
		
		if (miny < 0.0 && 0.0 < maxy) {
			ans = PI;
		} else {
			ans = solve(LEFT, RIGHT);
			if (abs(p2.y - p1.y) > EPS) {
				double A = p2.y - p1.y;
				double B = p1.x - p2.x;
				double C = -(A * p1.x + B * p1.y);
				double x = -C / A;
				ans = max(solve(LEFT, x + EPS), solve(x - EPS, RIGHT));
			}
		}
		out.printf(Locale.US, "%.6f%n", ans);
		out.close();
	}
	
	double solve(double left, double right) {
		for (int it = 0; it < 128; it++) {
			double dx = (right - left) / 3;
			double x1 = left + dx;
			double x2 = right - dx;
			if (f(x1) > f(x2)) {
				right = x2;
			} else {
				left = x1;
			}
		}
		return f(0.5 * (left + right));
	}
	
	
	double f(double x) {
		p0.x = x;
		double a = dist(p0, p1);
		double b = dist(p0, p2);
		double c = dist(p1, p2);
		if (abs(a - c) < EPS || abs(b - c) < EPS)
			return PI;
		return acos(min(1.0, max(-1.0, 0.5 * (a * a + b * b - c * c) / (a * b))));
	}

	double dist(Point p1, Point p2) {
		return sqrt(sqr(p2.x - p1.x) + sqr(p2.y - p1.y));
	}

	double sqr(double x) {
		return x * x;
	}

	class Point {
		double x;
		double y;
		
		Point(double x, double y) {
			this.x = x;
			this.y = y;
		}
	}
	
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
}
