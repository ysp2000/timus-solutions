import java.io.*;
import java.util.*;
import static java.lang.Math.*;
import static java.util.Arrays.fill;
import static java.util.Arrays.binarySearch;
import static java.util.Arrays.sort;

public class Main {
	double EPS = 1e-7;

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
		
		double a = nextDouble();
		double b = nextDouble();
		Point p1 = new Point(nextDouble(), nextDouble(), nextDouble());
		Point p2 = new Point(nextDouble(), nextDouble(), nextDouble());
		Line rail = new Line(p1, p2);
		Point M = rail.getNearest();
		double l = M.len();
		
		Point E = null;
		double angle = Double.NaN;
		
		if (a * a + b * b < l * l + EPS && a + b > l - EPS) {
			Point N = vec(p1, p2);
			double x = (a * a - b * b + l * l) / (2 * l);
			double h = sqrt(max(0.0, a * a - x * x));
				
			Point H = M.norm(x);
			E = H.add(N.norm(h));
			angle = getAngle(new Point(0.0, 0.0, 0.0), E, M);
			
			if (angle < PI / 2 - EPS || angle > PI) {
				throw new RuntimeException("Error!");
			}
		}
		
		out.println(E == null ? "No solution." : (E + " " + angle));
		
		out.close();
	}
	
	double dist(Point p1, Point p2) {
		return sqrt(sqr(p2.x - p1.x) + sqr(p2.y - p1.y) + sqr(p2.z - p1.z));
	}
	
	double sqr(double x) {
		return x * x;
	}

	double mul(Point p1, Point p2) {
		return p1.x * p2.x + p1.y * p2.y + p1.z * p2.z;
	}
	
	Point vec(Point p1, Point p2) {
		return new Point(p1.y * p2.z - p1.z * p2.y, p1.z * p2.x - p1.x * p2.z, p1.x * p2.y - p1.y * p2.x);
	}
	
	double getAngle(Point p1, Point p0, Point p2) {
		Point a = p1.sub(p0);
		Point b = p2.sub(p0);
		return acos(max(-1.0, min(1.0, mul(a, b) / a.len() / b.len())));
	}
	
	class Point {
		double x;
		double y;
		double z;
		
		Point(double x, double y, double z) {
			this.x = x;
			this.y = y;
			this.z = z;
		}
		
		Point add(Point v) {
			return new Point(x + v.x, y + v.y, z + v.z);
		}
		
		Point sub(Point v) {
			return new Point(x - v.x, y - v.y, z - v.z);
		}
		
		Point mul(double k) {
			return new Point(k * x, k * y, k * z);
		}
		
		double len() {
			return sqrt(x * x + y * y + z * z);
		}
		
		Point norm(double newLen) {
			return this.mul(newLen / this.len());
		}
		
		@Override
		public String toString() {
			return x + " " + y + " " + z;
		}
	}
	
	class Line {
		Point p1;
		Point p2;
		double u;
		double v;
		double w;
		
		Line(Point p1, Point p2) {
			this.p1 = p1;
			this.p2 = p2;
			u = p2.x - p1.x;
			v = p2.y - p1.y;
			w = p2.z - p1.z;
		}
		
		Point getNearest() {
			double t = -(u * p1.x + v * p1.y + w * p1.z) / (u * u + v * v + w * w);
			return new Point(u * t + p1.x, v * t + p1.y, w * t + p1.z);
		}
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
			
			if (s == null) {
				return true;
			}
			
			st = new StringTokenizer(s);
		}
		
		return false;
	}
}
