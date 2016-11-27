import java.io.*;

public class Main {
	public static void main(String[] args) throws IOException {
		try {
			if (new File("input.txt").exists())
				System.setIn(new FileInputStream("input.txt"));
		} catch (SecurityException e) {}
		
		boolean blank = false;
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter out = new PrintWriter(System.out);
		for (int T = Integer.parseInt(in.readLine()); T --> 0; blank = true) {
			if (blank) out.print(' ');
			out.print(isSquare(8L * Integer.parseInt(in.readLine()) - 7L) ? '1' : '0'); 
		}
		out.close();
	}

	static boolean isSquare(long x) {
		long s = (long) Math.sqrt(x);
		return s * s == x;
	}
}
