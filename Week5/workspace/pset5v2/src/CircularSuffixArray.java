
/**
 * @author David Price
 *
 */
public class CircularSuffixArray {
	
	private String string;
	private int n;
	
	
	// circular suffix array of s
	public CircularSuffixArray(String s) {
		
		if (s == null) {
			throw new IllegalArgumentException();
		}
		
		this.n = string.length();
	}
	
	// length of s
	public int length() {
		return n;
	}
	
	// returns index of ith sorted suffix
	public int index(int i) {
		
		if (i < 0 || i > n - 1) {
			throw new IllegalArgumentException();
		}
		
		return 0;
	}
	
	
	/**
	 * unit testing (required)
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
