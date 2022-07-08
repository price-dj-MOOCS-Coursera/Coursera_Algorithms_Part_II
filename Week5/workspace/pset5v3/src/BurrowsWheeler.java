import java.util.Arrays;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;


/**
 * @author David Price
 *
 */
public class BurrowsWheeler {
	
	private static final int EXTENDED_ASCII = 256;
	
	// apply Burrows-Wheeler transform, reading from standard input and writing to standard output
    public static void transform() {
    	
    	String text = BinaryStdIn.readString();
    	
    	CircularSuffixArray sfa = new CircularSuffixArray(text);
    	
    	int len = sfa.length();
    	
    	int first = 0;
    	
    	char[] bwt = new char[len];
    	
    	for (int i = 0; i < len; i++) {
    		if (sfa.index(i) == 0) {
    			first = i;
    			bwt[i] = text.charAt(len - 1);
    			continue;
    		}
    		bwt[i] = text.charAt(sfa.index(i) - 1);
    	}
    	BinaryStdOut.write(first);
    	
    	for (int j = 0; j < len; j++) {
    		BinaryStdOut.write(bwt[j]);
    	}
    	BinaryStdOut.close();
    	
    }

    // apply Burrows-Wheeler inverse transform, reading from standard input and writing to standard output
    public static void inverseTransform() {
    	
    	// read first
    	int first = BinaryStdIn.readInt();
    	
    	String bwtInput = BinaryStdIn.readString();
    	
    	// t[] array
    	char[] t = bwtInput.toCharArray();
    	
    	int len = t.length;
    	
    	// array for first sorted suffix
    	char[] firstSS = bwtInput.toCharArray();
    	
    	// auxillary array to sort firstSS array by key index counting
    	char[] aux = new char[len];
    	
    	//Arrays.sort(firstSS);
    	
    	int[] count1 = new int[EXTENDED_ASCII + 1];
    	
    	int[] count2 = new int[EXTENDED_ASCII + 1];
    	
    	int[] next = new int[len];
    	
    	// count frequencies of letters in t
    	for (int i = 0; i < len; i++) {
    		count1[t[i] + 1]++;
    		count2[firstSS[i] + 1]++;
    	}
    	
    	// compute cumulates
    	for (int j = 0; j < EXTENDED_ASCII; j++) {
    		count1[j + 1] += count1[j];
    		count2[j + 1] += count2[j];
    	}
    	
    	// calc next arr & move items of first Sort Suffixes
    	for (int k = 0; k < len; k++) {
    		aux[count2[firstSS[k]]++] = firstSS[k];
    		next[count1[t[k]]++] = k;
    	}
    	
    	// sort first suffixes
    	for (int l = 0; l < len; l++) {
    		firstSS[l] = aux[l];
    	}
    	
    	
    	// output
    	int curr = first;
    	for (int m = 0; m < len; m++) {
    		BinaryStdOut.write(firstSS[curr]);
    		curr = next[curr];
    	}
    	
    	BinaryStdOut.close();
    }

    // if args[0] is '-', apply Burrows-Wheeler transform
    // if args[0] is '+', apply Burrows-Wheeler inverse transform
	public static void main(String[] args) {
		if (args[0].equals("-")) { transform(); }
		else if (args[0].equals("+")) { inverseTransform(); }

	}

}
