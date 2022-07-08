import java.util.Arrays;
import java.util.Comparator;

import edu.princeton.cs.algs4.StdOut;

/**
 * @author David Price
 *
 */
public class CircularSuffixArray {
	
	private String s0;
	private Integer[] index;
	private int n;
	
	private class CircularSuffixOrder implements Comparator<Integer> {

		@Override
		public int compare(Integer i1, Integer i2) {
			for (int i = 0; i < n; i++) {
				char c1 = s0.charAt((i1 + i) % n);
				char c2 = s0.charAt((i2 + i) % n);
				
				if (c1 < c2) { return -1; }
				else if (c1 > c2) { return 1;}
			}
			return (int) Math.signum(i2 - i1);
		}
		
	}
	
	private Comparator<Integer> circularSuffixOrder() {
		return new CircularSuffixOrder();
	}
	
	
	// circular suffix array of s
	public CircularSuffixArray(String s) {
		if (s == null) {
			throw new IllegalArgumentException();
		}
		
		this.s0 = s;
		this.n= s0.length();
		
		// integer suffixes
		this.index = new Integer[n];
		for (int i = 0; i < n; i++) {
			index[i] = i;
		}
		
		Arrays.sort(index, circularSuffixOrder());
		
	}
	
	// length of s
	public int length() {
		return n;
	}
	
	// returns index of ith sorted suffix
	public int index(int i) {
		if (i < 0 || i > length() - 1) {
	  		throw new IllegalArgumentException();
	  	}
		return index[i];
	}
	
	
	/**
	 * unit testing (required)
	 * @param args
	 */
	public static void main(String[] args) {
		String s = "ABRACADABRA!";
    	CircularSuffixArray testArr = new CircularSuffixArray(s);
    	int len = testArr.length();
    	for (int i = 0; i < len; i++) {
    		StdOut.println(testArr.index(i));
    	}

	}

}
