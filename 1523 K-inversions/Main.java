import java.io.*;

import static java.util.Arrays.fill;

public class Main {
	public static void main(String[] args) throws IOException {
		try {
			if (new File("input.txt").exists())
				System.setIn(new FileInputStream("input.txt"));
		} catch (SecurityException e) {}
		
		new Main().run();
	}

	BufferedReader in;
	
	int MOD = (int) 1e9;
	int N;
	int K;
	int[] perm;
	boolean[] used;
	long[] sum;
	long[] dp;
	
	void run() throws IOException {
		in = new BufferedReader(new InputStreamReader(System.in));
		
		N = nextInt();
		K = nextInt();
		perm = new int [N];
		for (int i = 0; i < N; i++)
			perm[i] = nextInt() - 1;
		dp = new long [N];
		fill(dp, 1L);
		used = new boolean [N];
		sum = new long [2 * N];
		
		for (int it = 1; it < K; it++) {
			build();
			fill(dp, 0L);
			for (int i = 0; i < N; i++) {
				int x = perm[i];
				dp[x] = sum(x, N - 1);
				add(x);
			}
		}
		
		long ans = 0L;
		for (long x : dp)
			ans = (ans + x) % MOD;
		
		System.out.println(ans);
	}
	
	long sum(int l, int r) {
		l += N;
		r += N;
		long ret = 0L;
		while (l <= r) {
			if (l % 2 == 1)
				ret = (ret + get(l)) % MOD;
			if (r % 2 == 0)
				ret = (ret + get(r)) % MOD;
			l = (l + 1) / 2;
			r = (r - 1) / 2;
		}
		return ret;
	}

	void build() {
		fill(sum, 0L);
		fill(used, false);
		for (int i = 0; i < N; i++)
			sum[N + i] = dp[i];
	}
	
	long get(int ind) {
		return (ind < N || used[ind - N]) ? sum[ind] : 0L;
	}
	
	void add(int ind) {
		used[ind] = true;
		for (int v = (ind + N) >> 1; v > 0; v >>= 1) {
			int lt = v << 1;
			int rt = lt + 1;
			sum[v] = (get(lt) + get(rt)) % MOD;
		}
	}
	
	int nextInt() throws IOException {
		int n, c;
		for (c = in.read(); c < '0' || c > '9'; c = in.read());
		for (n = 0; '0' <= c && c <= '9'; c = in.read())
			n = n * 10 + c - '0';
		return n;
 	}
}
