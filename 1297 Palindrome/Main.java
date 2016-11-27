import java.io.*;
import java.util.*;

public class Main {
	public static void main(String[] args) throws IOException {
		try {
			if (new File("input.txt").exists())
				System.setIn(new FileInputStream("input.txt"));
		} catch (SecurityException e) {}
		
		new Main().run();
	}
	
	Random rnd = new Random(777L);
	int HASH_BASE = java.math.BigInteger.probablePrime(17, rnd).intValue();
	int[] charCodes = new int [256];
	int[] basePows;
	int[] directHashes;
	int[] reverseHashes;
	int stringLength;
	
	void run() throws IOException {
		// Ввод //
		char[] s = (new BufferedReader(new InputStreamReader(System.in))).readLine().toCharArray();
		// Предподсчет //
		stringLength = s.length;
		for (int i = 0; i < 256; i++)
			charCodes[i] = rnd.nextInt();
		basePows = new int [stringLength + 1];
		basePows[0] = 1;
		for (int i = 1; i < basePows.length; i++)
			basePows[i] = basePows[i - 1] * HASH_BASE;
		directHashes = new int [stringLength + 1];
		for (int i = 1, pow = 1; i <= stringLength; i++, pow *= HASH_BASE)
			directHashes[i] = directHashes[i - 1] + pow * charCodes[s[i - 1]];
		reverseHashes = new int [stringLength + 1];
		for (int i = stringLength - 1, pow = 1; i >= 0; i--, pow *= HASH_BASE)
			reverseHashes[i] = reverseHashes[i + 1] + pow * charCodes[s[i]];
		// Решение //
		int leftBorder = 0;         // левая граница бинпоиска
		int rightBorder = s.length; // правая граница бинпоиска
		int ansStart = -1;          // начальная позиция ответа в исходной строке
		int ansLength = 0;          // длина ответа
		while (leftBorder <= rightBorder) { // обычный бинпоиск
			int middle = (leftBorder + rightBorder) >> 1;
			int curStart = findPalindrome(middle * 2 + 1);
			int curLen = middle * 2 + 1;
			if (curStart == -1) {
				curStart = findPalindrome(middle * 2);
				curLen--;
			}
			if (curStart != -1) {
				if (ansLength < curLen) {
					ansLength = curLen;
					ansStart = curStart;
				}
				leftBorder = middle + 1;
			} else {
				rightBorder = middle - 1;
			}
		}
		// Вывод ответа //
		for (int i = 0; i < ansLength; i++)
			System.out.print(s[ansStart + i]);
		System.out.println();
	}

	/* Находит первое вхождение палиндрома длины len */
	int findPalindrome(int len) {
		if (len > 0)
			for (int start = 0; start + len <= stringLength; start++)
				if (isPalindrome(start, start + len - 1))
					return start;
		return -1;
	}

	/* Проверяет, что подстрока [l, r] - палиндром */
	boolean isPalindrome(int l, int r) {
		int dirHash = directHashes[r + 1] - directHashes[l];   // получаем прямой хэш подстроки
		int revHash = reverseHashes[l] - reverseHashes[r + 1]; // получаем обратный хэш подстроки
		int dirOffset = l;                                     // отступ прямого хэша
		int revOffset = stringLength - 1 - r;                  // отступ обратного хэша
		if (dirOffset > revOffset)                             // если прямой хэш сдвинут сильнее
			revHash *= basePows[dirOffset - revOffset];        //   сдвигаем обратный хэш 
		else                                                   // иначе
			dirHash *= basePows[revOffset - dirOffset];        //   сдвигаем прямой хэш
		return dirHash == revHash;                             // проверяем, что хэши совпали
	}
}
