import java.io.*;
import java.util.*;
import static java.lang.Math.*;

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
	
	int N;
	int M;
	
	void run() throws IOException {
		StringTokenizer st = new StringTokenizer(new BufferedReader(new InputStreamReader(System.in)).readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		long cnt = -1;
		int x = 0;
		int y = 0;
		int sz = (N + M) >> 1;
		boolean[] main = new boolean [sz];
		boolean[] inv = new boolean [sz];
		int dx = 1;
		int dy = 1;
		boolean mn = false;
		
		while (true) {
			cnt++;
			int dh = (dx == 1 ? N - 1 - x : x);
			int dv = (dy == 1 ? M - 1 - y : y);
			
			if (dh < dv) {
				x += dh * dx;
				y += dh * dy;
				dx *= -1;
			} else {
				x += dv * dx;
				y += dv * dy;
				dy *= -1;
			}
			
			if (mn) {
				int ind = (x + y) >> 1;
				main[ind] = !main[ind];
			} else {
				int ind = (x + M - 1 - y) >> 1;
				inv[ind] = !inv[ind];
			}
			
			mn = !mn;
			
			if (isAngle(x, y)) {
				break;
			}
		}
		
		int[] psM = new int [sz + 1];
		
		for (int i = 1; i <= sz; i++) {
			psM[i] = psM[i - 1] + (main[i - 1] ? 1 : 0);
		}
		
//		System.out.println(Arrays.toString(psM));
		
		long cross = 0;
		
		for (int i = 0; i < sz; i++) {
			if (inv[i]) {
//				System.out.println("i = " + i);
				int C = M - 1 - ((M - 1) & 1);
				int min = abs(C - 2 * i) / 2;
				int max = 0;
				
				int y1 = N - 1 + C - 2 * i;
				int x1 = M - 1 - C + 2 * i;
				
				if (x1 < N) {
					y1 = x1 + C - 2 * i;
				} else {
					x1 = y1 - C + 2 * i;
				}

//				System.out.println(x1);
//				System.out.println(y1);
				max = (x1 + y1) / 2;
				
//				System.out.println("min = " + min);
//				System.out.println("max = " + max);
				
				cross += psM[max + 1] - psM[min];
			}
		}
		
//		System.out.println(cross);
		
		System.out.println(lcm(N - 1, M - 1) + 1 - 2 * (cross - cnt));
	}

	boolean isAngle(int x, int y) {
		return x == 0 && y == M - 1 || x == N - 1 && y == 0 || x == N - 1 && y == M - 1;
	}
	
	long lcm(long a, long b) {
		return a * b / gcd(a, b);
	}
	
	long gcd(long a, long b) {
		while (a > 0 && b > 0) {
			if (a > b) {
				a %= b;
			} else {
				b %= a;
			}
		}
		
		return a + b;
	}
}
