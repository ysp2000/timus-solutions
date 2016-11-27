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
		// ���� //
		char[] s = (new BufferedReader(new InputStreamReader(System.in))).readLine().toCharArray();
		// ����������� //
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
		// ������� //
		int leftBorder = 0;         // ����� ������� ���������
		int rightBorder = s.length; // ������ ������� ���������
		int ansStart = -1;          // ��������� ������� ������ � �������� ������
		int ansLength = 0;          // ����� ������
		while (leftBorder <= rightBorder) { // ������� ��������
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
		// ����� ������ //
		for (int i = 0; i < ansLength; i++)
			System.out.print(s[ansStart + i]);
		System.out.println();
	}

	/* ������� ������ ��������� ���������� ����� len */
	int findPalindrome(int len) {
		if (len > 0)
			for (int start = 0; start + len <= stringLength; start++)
				if (isPalindrome(start, start + len - 1))
					return start;
		return -1;
	}

	/* ���������, ��� ��������� [l, r] - ��������� */
	boolean isPalindrome(int l, int r) {
		int dirHash = directHashes[r + 1] - directHashes[l];   // �������� ������ ��� ���������
		int revHash = reverseHashes[l] - reverseHashes[r + 1]; // �������� �������� ��� ���������
		int dirOffset = l;                                     // ������ ������� ����
		int revOffset = stringLength - 1 - r;                  // ������ ��������� ����
		if (dirOffset > revOffset)                             // ���� ������ ��� ������� �������
			revHash *= basePows[dirOffset - revOffset];        //   �������� �������� ��� 
		else                                                   // �����
			dirHash *= basePows[revOffset - dirOffset];        //   �������� ������ ���
		return dirHash == revHash;                             // ���������, ��� ���� �������
	}
}
