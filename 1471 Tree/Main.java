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
	
	int vNum;
	MultiList tree;
	int[] tin;
	int[] tout;
	int[] dist;
	int[][] up;
	int timer;
	boolean[] used;
	int log;
	
	void run() throws IOException {
		in = new BufferedReader(new InputStreamReader(System.in));
		out = new PrintWriter(System.out);
	
		vNum = nextInt();
		tree = new MultiList(vNum, (vNum - 1) << 1);
		
		for (int i = 1; i < vNum; i++) {
			int u = nextInt();
			int v = nextInt();
			int d = nextInt();
			tree.add(u, v, d);
			tree.add(v, u, d);
		}
		
		for (log = 1; (1 << log) <= vNum; log++);
		up = new int [vNum][log + 1];
		timer = 1;
		tin = new int [vNum];
		tout = new int [vNum];
		dist = new int [vNum];
		used = new boolean [vNum];
		
		dfs(0, 0);
		
		int qNum = nextInt();
		
		for (int i = 0; i < qNum; i++) {
			int u = nextInt();
			int v = nextInt();
			out.println((dist[u] + dist[v] - (dist[lca(u, v)] << 1)));
		}
		
		out.close();
	}
	
	void dfs(int v, int p) {
		tin[v] = timer++;
		used[v] = true;
		up[v][0] = p;
		
		for (int i = 1; i <= log; i++) {
			up[v][i] = up[up[v][i - 1]][i - 1];
		}
		
		for (int i = tree.head[v]; i > 0; i = tree.next[i]) {
			int adj = tree.vert[i];

			if (!used[adj]) {
				dist[adj] = dist[v] + tree.dist[i];
				dfs(adj, v);
			}
		}
		
		tout[v] = timer++;
	}
	
	boolean isAncestor(int u, int v) {
		return tin[u] <= tin[v] && tout[u] >= tout[v];
	}
	
	int lca(int u, int v) {
		if (isAncestor(u, v)) {
			return u;
		} else if (isAncestor(v, u)) {
			return v;
		}
		
		for (int i = log; i >= 0; i--) {
			if (!isAncestor(up[u][i], v)) {
				u = up[u][i];
			}
		}
		
		return up[u][0];
	}

	class MultiList {
		int[] head;
		int[] next;
		int[] vert;
		int[] dist;
		int pnt = 1;
		
		MultiList(int hNum, int dSize) {
			head = new int [hNum];
			next = new int [dSize + 1];
			vert = new int [dSize + 1];
			dist = new int [dSize + 1];
		}
		
		void add(int u, int v, int d) {
			next[pnt] = head[u];
			vert[pnt] = v;
			dist[pnt] = d;
			head[u] = pnt++;
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
}
