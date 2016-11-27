import java.io.*;
import java.util.*;
import static java.lang.Math.*;

public class Main {
	public static void main(String[] args) throws IOException {
		new Main().run();
	}
	
	void run() throws IOException {
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(System.out);
		int M = Integer.parseInt(in.readLine());
		RMQ rmq = new RMQ(25000);
		int N = 0;

		for (int v = Integer.parseInt(in.readLine()); v >= 0; v = Integer.parseInt(in.readLine())) {
			rmq.set(N++, v);
		}
		
		for (int i = 0; i + M - 1 < N; i++) {
			out.println(rmq.get(i, i + M - 1));
		}
		
		out.close();
	}
	
	class RMQ {
		int n;
		int[] val;
		
		RMQ(int sz) {
			n = sz;
			val = new int [n << 1];
		}
		
		void set(int i, int v) {
			val[i += n] = v;
			i >>= 1;
			
			while (i > 0) {
				val[i] = max(val[i << 1], val[(i << 1) + 1]);
				i >>= 1;
			}
		}
		
		int get(int l, int r) {
			int res = -1000000;
			l += n;
			r += n;
			
			while (l <= r) {
				res = max(res, max(val[l], val[r]));
				l = (l + 1) >> 1;
				r = (r - 1) >> 1;
			}
			
			return res;
		}
	}
}