import java.io.*;
import java.util.*;

import static java.lang.Math.*;
import static java.util.Arrays.fill;
import static java.util.Arrays.binarySearch;
import static java.util.Arrays.sort;

public class Main {
	public static void main(String[] args) throws IOException {
		try {
			if (new File("input.txt").exists())
				System.setIn(new FileInputStream("input.txt"));
		} catch (SecurityException e) {}
		
		new Main().run();
	}

	BufferedReader in;
	PrintWriter out;
	StringTokenizer st = new StringTokenizer("");
	
	long MAX = (1L << 32) - 1;
	
	int N1;
	int N2;
	Event[] events1;
	Event[] events2;

	
	void run() throws IOException {
		in = new BufferedReader(new InputStreamReader(System.in));
		out = new PrintWriter(System.out);
		
		N1 = nextInt();
		events1 = new Event [N1 * 2];
		for (int i = 0; i < N1; i++) {
			long d = nextIP();
			long m = nextIP();
			long g = nextIP();
			events1[2 * i] = new Event(d & m, g, Event.BEGIN, Long.bitCount(m));
			events1[2 * i + 1] = new Event((d & m) | (MAX - m), g, Event.END, Long.bitCount(m));
		}
		sort(events1);
		
		N2 = nextInt();
		events2 = new Event [N2 * 2];
		for (int i = 0; i < N2; i++) {
			long d = nextIP();
			long m = nextIP();
			long g = nextIP();
			events2[2 * i] = new Event(d & m, g, Event.BEGIN, Long.bitCount(m));
			events2[2 * i + 1] = new Event((d & m) | (MAX - m), g, Event.END, Long.bitCount(m));
		}
		sort(events2);
		
		out.println(good(events1, events2) && good(events2, events1) ? "YES" : "NO");
		
		out.close();
	}
	
	Set<Long> set = new TreeSet<Long>();
	long[] stack1 = new long [65536];
	long[] stack2 = new long [65536];
	
	boolean good(Event[] e1, Event[] e2) {
		int pnt1 = 0;
		int pnt2 = 0;
		
		set.clear();
		for (Event e : e1) {
			set.add(e.pos - 1L);
			set.add(e.pos);
			set.add(e.pos + 1L);
		}
		for (Event e : e2) {
			set.add(e.pos - 1L);
			set.add(e.pos);
			set.add(e.pos + 1L);
		}
		
		int sz1 = 0;
		int sz2 = 0;
		
		stack1[sz1++] = -1L;
		stack2[sz2++] = -1L;
		
		for (long ip : set) {
			while (pnt1 < e1.length) {
				Event e = e1[pnt1];
				if (e.pos < ip || e.pos == ip && e.type == Event.BEGIN) {
					if (e.type == Event.BEGIN) {
						stack1[sz1++] = e.gate;
					} else {
						sz1--;
					}
					pnt1++;
				} else {
					break;
				}
			}
			
			while (pnt2 < e2.length) {
				Event e = e2[pnt2];
				if (e.pos < ip || e.pos == ip && e.type == Event.BEGIN) {
					if (e.type == Event.BEGIN) {
						stack2[sz2++] = e.gate;
					} else {
						sz2--;
					}
					pnt2++;
				} else {
					break;
				}
			}
			
			if (stack1[sz1 - 1] != stack2[sz2 - 1])
				return false;
		}
		
		return true;
	}

	long nextIP() throws IOException {
		long ret = 0L;
		StringTokenizer tok = new StringTokenizer(nextToken(), ".");
		for (int i = 0; i < 4; i++)
			ret = (ret << 8) | Integer.parseInt(tok.nextToken());
		return ret;
	}

	class Event implements Comparable<Event> {
		final static int BEGIN = 0;
		final static int END = 1;
		
		long pos;
		long gate;
		int type;
		int bits;
		
		Event(long start, long gate, int type, int length) {
			this.pos = start;
			this.gate = gate;
			this.type = type;
			this.bits = length;
		}
		
		@Override
		public int compareTo(Event e) {
			if (pos != e.pos) return pos < e.pos ? -1 : 1;
			if (type != e.type) return type < e.type ? -1 : 1;
			if (bits != e.bits) {
				if (type == BEGIN) return bits < e.bits ? -1 : 1;
				if (type == END) return bits > e.bits ? -1 : 1;
			}
			return 0;
		}
		
		@Override
		public String toString() {
			return pos + " " + gate + " " + (type == Event.BEGIN ? "Begin" : "End") + " " + bits;
		}
	}
	
	String nextToken() throws IOException {
		while (!st.hasMoreTokens())
			st = new StringTokenizer(in.readLine());
		return st.nextToken();
	}
	
	int nextInt() throws IOException {
		return Integer.parseInt(nextToken());
	}
}
