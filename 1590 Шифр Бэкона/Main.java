import java.io.*;
import java.util.*;

public class Main {
	public static void main(String[] args) throws IOException {
		try {
			if (new File("input.txt").exists())
				System.setIn(new FileInputStream("input.txt"));
		} catch (SecurityException e) {}
		
		new Thread(null, new Runnable() {
			public void run() {
				try {
					new Main().run();
				} catch (IOException e) {
					System.out.println(e);
				}
			}
		}, "1", 1L << 20).start();
	}

	void run() throws IOException {
		System.out.println(new SuffixAutomaton((new BufferedReader(new InputStreamReader(System.in))).readLine().toCharArray()).differentSubstrings());
	}
	
	class State {
		int link;
		int length;
		int[] next = new int [26];
		
		State() {
			Arrays.fill(next, link = -1);
		}
	}
	
	class SuffixAutomaton {
		State[] state;
		int size;
		int last;
		
		SuffixAutomaton(char[] s) {
			int len = s.length;
			state = new State [Math.max(2, 2 * len - 1)];
			state[size++] = new State();
			for (char c : s)
				append(c - 'a');
		}

		void append(int c) {
			int cur = size++, p = last;
			state[cur] = new State();
			state[cur].length = state[last].length + 1;
			for (; p != -1 && state[p].next[c] == -1; p = state[p].link)
				state[p].next[c] = cur;
			if (p == -1)
				state[cur].link = 0;
			else {
				int q = state[p].next[c];
				if (state[p].length + 1 == state[q].length)
					state[cur].link = q;
				else {
					int tmp = size++;
					state[tmp] = new State();
					state[tmp].length = state[p].length + 1;
					state[tmp].link = state[q].link;
					for (int i = 0; i < 26; i++)
						state[tmp].next[i] = state[q].next[i];
					for (; p != -1 && state[p].next[c] == q; p = state[p].link)
						state[p].next[c] = tmp;
					state[q].link = state[cur].link = tmp;
				}
			}
			last = cur;
		}
		
		int[] dp;
		
		int differentSubstrings() {
			dp = new int [size];
			return ds_dfs(0);
		}

		int ds_dfs(int v) {
			if (dp[v] != 0)
				return dp[v];
			int ret = 0;
			for (int i = 0; i < 26; i++)
				if (state[v].next[i] != -1)
					ret += 1 + ds_dfs(state[v].next[i]);
			return dp[v] = ret;
		}
	}
}
