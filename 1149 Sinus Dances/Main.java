import java.io.*;

public class Main {
	public static void main(String[] args) throws IOException {
		try {
			if (new File("input.txt").exists())
				System.setIn(new FileInputStream("input.txt"));
		} catch (SecurityException e) {}
		
		new Main().run();
	}

	PrintWriter out = new PrintWriter(System.out);
	int N;
	
	void run() throws IOException {
		S(N = nextInt());
		out.println();
		out.close();
	}

	void S(int n) {
		if (n > 1) {
			out.print('(');
			S(n - 1);
			out.print(')');
		}
		A(n);
		out.print('+');
		out.print(N - n + 1);
	}

	void A(int n) {
		for (int i = 1; i <= n; i++) {
			if (i > 1) out.print((i & 1) == 0 ? '-' : '+');
			out.print("sin(");
			out.print(i);
		}
		for (int i = 1; i <= n; i++) out.print(')');
	}

	int nextInt() throws IOException {
		int n, c, s = 1;
		for (c = System.in.read(); c < '0' || c > '9'; c = System.in.read())
			if (c == '-')
				s = -1;
		for (n = 0; '0' <= c && c <= '9'; c = System.in.read())
			n = n * 10 + c - '0';
		return s == 1 ? n : -n;
 	}
}
