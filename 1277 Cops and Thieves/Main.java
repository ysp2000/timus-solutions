import java.io.*;
import java.util.*;

public class Main {
	int INF = (1 << 25);

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
	StringTokenizer st = new StringTokenizer("");
	
	int vNum;
	int[][] cf;
	int[][] f;
	int S;
	int F;
	
	void run() throws IOException {
		in = new BufferedReader(new InputStreamReader(System.in));
		
		int k = nextInt();
		int n = nextInt();
		vNum = n << 1;
		cf = new int [vNum][vNum];
		f = new int [vNum][vNum];

		int m = nextInt();
		S = n + nextInt() - 1;
		F = nextInt() - 1;
		
		for (int i = 0; i < n; i++) {
			int w = nextInt();
			cf[i][i + n] = w;
			cf[i + n][i] = w;
		}
		
		for (int i = 0; i < m; i++) {
			int u = nextInt() - 1;
			int v = nextInt() - 1;
			cf[u + n][v] = INF;
			cf[v + n][u] = INF;
		}
		
		u = new int [vNum];
		System.out.println(k >= maxFlow() && S != F + n ? "YES" : "NO");
	}
	
	int[] u;
	int tick = 0;
	
	int maxFlow() {
		int mf = 0;
		
		for (int tf = INF; tf > 0; tf >>= 1) {
			tick++;
			
			while (dfs(S, tf)) {
				mf += tf;
				tick++;
			}
		}
		
		return mf;
	}

	boolean dfs(int v, int tf) {
		if (v == F) {
			return true;
		}
		
		
		u[v] = tick;
		
		for (int i = 0; i < vNum; i++) {
			if (u[i] != tick && cf[v][i] - f[v][i] >= tf && dfs(i, tf)) {
				f[v][i] += tf;
				f[i][v] -= tf;
				return true;
			}
		}
		
		return false;
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
