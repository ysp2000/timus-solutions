import java.io.*;
import static java.lang.Math.*;
import static java.util.Arrays.fill;

public class Main {
	public static void main(String[] args) throws IOException {
		try {
			if (new File("input.txt").exists())
				System.setIn(new FileInputStream("input.txt"));
		} catch (SecurityException e) {}
		
//		long t = System.currentTimeMillis();
		new Main().run();
//		System.out.println((System.currentTimeMillis() - t) + " ms");
	}

	double[] memP;
	double[] memPs;
	
	void run() throws IOException {
		int L = nextInt() - 2;
		memP = new double [400 * (L + 1)];
		memPs = new double [400 * (L + 1)];
		fill(memP, -1.0);
		fill(memPs, -1.0);
		memP[((1 << 8) + (1 << 7) + (1 << 4) + 1)] = memPs[((1 << 8) + (1 << 7) + (1 << 4) + 1)] = 1.0;
		double M = 0.0;
		for (int K = 1; K <= L; K++)
			M += 10 * K * P(L, K);
		System.out.println(M);
	}
	
	double P(int L, int K) {
		if (K <= 0 || L < K) return 0.0;
		int ind = (L << 8) + (L << 7) + (L << 4) + K;
		double x = memP[ind];
		if (x != -1.0) return x;
		x = 0.0;
		for (int i = max(1, K - 1); i <= L - K; i++)
			x += P(i, K - 1) * PS(L - i - 1, K - 2);
		for (int i = L - K + 1; i <= L - 2; i++)
			x += P(i, K - 1);
		for (int i = 1; i <= K - 2; i++)
			x += P(L - i - 1, K - 1);
		for (int i = K - 1; i <= L - 2; i++)
			x += PS(i, K - 2) * P(L - i - 1, K - 1);
		for (int i = max(1, K - 1); i <= L - K + 1; i++)
			x += P(i, K - 1) * P(L - i - 1, K - 1);
		x += 2.0 * P(L - 1, K - 1);
		return memP[ind] = x / L;
	}

	double PS(int L, int K) {
		if (K <= 0) return 0.0;
		if (L < K) return PS(L, L);
		int ind = (L << 8) + (L << 7) + (L << 4) + K;
		double x = memPs[ind];
		if (x != -1.0) return x;
		return memPs[ind] = PS(L, K - 1) + P(L, K);
	}
	
	static int nextInt() throws IOException {
		int n, c;
		for (c = System.in.read(); c < '0' || c > '9'; c = System.in.read());
		for (n = 0; '0' <= c && c <= '9'; c = System.in.read())
			n = n * 10 + c - '0';
		return n;
 	}
}
