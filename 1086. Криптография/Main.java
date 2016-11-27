import java.io.*;

public class Main {
	public static void main(String[] args) throws IOException {
		try {
			if (new File("input.txt").exists())
				System.setIn(new FileInputStream("input.txt"));
		} catch (SecurityException e) {}
		
		// Решето Эратосфена //
		int MAXN = 163841, SQRT = (int) Math.sqrt(MAXN) + 1;
		int MAXP = 15000;
		int pNum = 0;
		int[] primes = new int [MAXP]; // список простых чисел
		boolean[] erat = new boolean [MAXN + 1]; // решето
		for (int i = 2; i <= MAXN; i++) { // перебираем числа
			if (!erat[i]) { // если текущее число не выкинуто
				primes[pNum++] = i; // то оно простое
				if (i <= SQRT) // а если оно не сильно большое, то выкинем числа которые на него делятся
					for (int j = i * i; j <= MAXN; j += i) // оптимизация 1: числа до квадрат уже были выкинуты
						erat[j] = true;                    // оптимизация 2: бежим с шагом i
			}
		}
		
		// Решение задачи //
		for (int T = nextInt(); T --> 0; System.out.println(primes[nextInt() - 1]));
	}

	static int nextInt() throws IOException {
		int n, c;
		for (c = System.in.read(); c < '0' || c > '9'; c = System.in.read());
		for (n = 0; '0' <= c && c <= '9'; c = System.in.read())
			n = n * 10 + c - '0';
		return n;
 	}
}
