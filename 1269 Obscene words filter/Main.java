import java.io.*;
import java.util.*;
import static java.lang.Math.*;
import static java.util.Arrays.fill;

public class Main {
	int INF = Integer.MAX_VALUE >> 1;
	int MAX_VERTS = 200 * 1024;
	
	public static void main(String[] args) throws IOException {
		try {
			if (new File("input.txt").exists()) {
				System.setIn(new FileInputStream("input.txt"));
			}
		} catch (SecurityException e) {
		}
		
		new Main().run();
	}

	int vNum = 1;
	int[] head = new int [MAX_VERTS];
	int[] next = new int [MAX_VERTS + 1];
	int[] prvc = new int [MAX_VERTS + 1];
	int[] term = new int [MAX_VERTS];
	int[] link = new int [MAX_VERTS + 1];
	int[] prvt = new int [MAX_VERTS + 1];
	int[] queue = new int [MAX_VERTS];
	int qT, qH;
	
	BufferedReader in;
	
	void run() throws IOException {
		in = new BufferedReader(new InputStreamReader(System.in));
		fill(term, INF);
		for (int N = Integer.parseInt(in.readLine()), i = 0; i < N; i++)
			addWord();
		qT = qH = 0;
		for (int i = head[0]; i != 0; i = next[i])
			queue[qT++] = i;
		while (qH != qT) {
			int v = queue[qH];
			if (++qH == MAX_VERTS)
				qH = 0;
			for (int i = head[v]; i != 0; i = next[i]) {
				int ch = prvc[i];
				int l = link[v];
				while (l != 0 && go(v, ch) == 0)
					l = link[l];
				l = go(v, ch);
				prvt[i] = term[l] == INF ? prvt[l] : l;
				link[i] = l;
				queue[qT] = i;
				if (++qT == MAX_VERTS)
					qT = 0;
			}
		}
		for (int M = Integer.parseInt(in.readLine()), l = 1; l <= M; l++) {
		}
		System.out.println("Passed");
	}

	void addWord() throws IOException {
		int v = 0;
		int l = 0;
		for (int ch = in.read(); ch != 13; l++, ch = in.read())
			v = next(v, ch);
		in.read();
		term[v] = l;
	}

	int go(int v, int ch) {
		for (int i = head[v]; i != 0; i = next[i])
			if (prvc[i] == ch)
				return i;
		return 0;
	}
	
	int next(int v, int ch) {
		int next = go(v, ch);
		return next == 0 ? addVert(v, ch) : next;
	}

	int addVert(int v, int ch) {
		next[vNum] = head[v];
		prvc[vNum] = ch;
		return head[v] = vNum++;
	}
}
