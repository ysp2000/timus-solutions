import java.io.*;

public class Main {
	public static void main(String[] args) throws IOException {
		try {
			if (new File("input.txt").exists())
				System.setIn(new FileInputStream("input.txt"));
		} catch (SecurityException e) {}
		
		int n = nextInt();
		System.out.println(n > 0 ? sum(1, n, n) : sum(-2, n, -n - 1));
	}

	static int sum(int a1, int an, int n) {
		return n * (a1 + an) / 2;
	}

	static int nextInt() throws IOException {
		int n, c, s = 1;
		for (c = System.in.read(); c < '0' || c > '9'; c = System.in.read())
			if (c == '-')
				s = -1;
		for (n = 0; '0' <= c && c <= '9'; c = System.in.read())
			n = n * 10 + c - '0';
		return s == 1 ? n : -n;
 	}
}
