import java.io.*;
import java.util.*;
import java.util.Map.Entry;

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
	
	int bNum = 0;
	int cNum = 0;
	long[] fortune;
	Map<String, Integer> mapBil = new HashMap<String, Integer>();
	Map<String, Integer> mapCity = new HashMap<String, Integer>();
	Map<String, Integer> mapAns = new TreeMap<String, Integer>();
	
	int[] where;
	
	int days;
	Event[] events;
	Item[] items;
	long[] init;
	
	RMQ rmq;
	
	void run() throws IOException {
		in = new BufferedReader(new InputStreamReader(System.in));
		out = new PrintWriter(System.out);
		
		int BN = nextInt();
		fortune = new long [BN];
		where = new int [BN];
		
		for (int i = 0; i < BN; i++) {
			int bil = indexBil(nextToken());
			int dst = indexCity(nextToken());
			long frt = nextLong();
			fortune[bil] = frt;
			where[bil] = dst;
		}
		
		days = nextInt();
		
		int MN = nextInt();
		events = new Event [MN];
		for (int i = 0; i < MN; i++)
			events[i] = new Event(nextInt(), indexBil(nextToken()), indexCity(nextToken()));
		
		items = new Item [cNum];
		for (Entry<String, Integer> e : mapCity.entrySet()) {
			items[e.getValue()] = new Item(e.getKey());
		}
		
		init = new long [cNum];
		for (int i = 0; i < BN; i++)
			init[where[i]] += fortune[i];
		rmq = new RMQ(init);
		
		int maxCity;
		int prevDay = 1;
		int curDay = 1;
		
		for (int i = 0; i < MN;) {
			curDay = events[i].day;
			maxCity = rmq.maxInd();
			if (rmq.unique(maxCity))
				items[maxCity].inc(curDay - prevDay + 1);
			prevDay = curDay + 1;
			while (i < MN && curDay == events[i].day) {
				int bil = events[i].bil;
				int dst = events[i].dst;
				rmq.inc(where[bil], -fortune[bil]);
				rmq.inc(dst, fortune[bil]);
				where[bil] = dst;
				i++;
			}
		}
		
		curDay = days;
		maxCity = rmq.maxInd();
		if (rmq.unique(maxCity))
			items[maxCity].inc(curDay - prevDay + 1);
		sort(items);
		
		for (Item item : items)
			if (item.count > 0)
				out.println(item);
		
		out.close();
	}
	
	int indexBil(String name) {
		if (!mapBil.containsKey(name))
			mapBil.put(name, bNum++);
		return mapBil.get(name);
	}
	
	int indexCity(String name) {
		if (!mapCity.containsKey(name))
			mapCity.put(name, cNum++);
		return mapCity.get(name);
	}
	
	void inc(String city, int add) {
		if (mapAns.containsKey(city))
			mapAns.put(city, mapAns.get(city) + add);
		else
			mapAns.put(city, add);
	}
	
	class Event {
		int day;
		int bil;
		int dst;
		
		Event(int day, int bil, int dst) {
			this.day = day;
			this.bil = bil;
			this.dst = dst;
		}
	}
	
	class Item implements Comparable<Item> {
		String city;
		int count = 0;
		
		Item(String city) {
			this.city = city;
		}

		void inc(int add) {
			count += add;
		}

		@Override
		public int compareTo(Item item) {
			return city.compareTo(item.city);
		}
		
		@Override
		public String toString() {
			return city + " " + count;
		}
	}
	
	class RMQ {
		int n;
		long[] val;
		int[] ind;
		
		RMQ(long[] a) {
			n = a.length;
			val = new long [2 * n];
			ind = new int [2 * n];
			for (int i = 0; i < n; i++) {
				val[n + i] = a[i];
				ind[n + i] = i;
			}
			build();
		}

		void build() {
			for (int i = n - 1; i > 0; i--) {
				int lt = 2 * i;
				int rt = lt + 1;
				if (val[lt] > val[rt]) {
					val[i] = val[lt];
					ind[i] = ind[lt];
				} else {
					val[i] = val[rt];
					ind[i] = ind[rt];
				}
			}
		}
		
		long get(int ind) {
			return val[n + ind];
		}
		
		int maxInd() {
			return ind[1];
		}
		
		boolean unique(int ind) {
			for (int v = (n + ind) >> 1; v > 0; v >>= 1) {
				int lt = 2 * v;
				int rt = lt + 1;
				if (val[lt] == val[rt]) {
					return false;
				}
			}
			return true;
		}
		
		void inc(int ind, long add) {
			set(ind, get(ind) + add);
		}
		
		void set(int i, long nval) {
			int v = n + i;
			val[v] = nval;
			for (v >>= 1; v > 0; v >>= 1) {
				int lt = 2 * v;
				int rt = lt + 1;
				if (val[lt] > val[rt]) {
					val[v] = val[lt];
					ind[v] = ind[lt];
				} else {
					val[v] = val[rt];
					ind[v] = ind[rt];
				}
			}
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
	
	long nextLong() throws IOException {
		return Long.parseLong(nextToken());
	}
}
