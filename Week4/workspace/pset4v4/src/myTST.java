import edu.princeton.cs.algs4.TST;

public class myTST<Value> extends TST<Value> {
	private int n;				// size
	private Node<Value> root;	// root of TST
	
	private static class Node<Value> {
        private char c;                        // character
        private Node<Value> left, mid, right;  // left, middle, and right subtries
        private Value val;                     // value associated with string
    }
	
	/**
     * Initializes an empty string symbol table.
     */
    public myTST() {
    	super();
    }
	
    // method to find if given prefix exists in myTST
    public boolean isValidPrefix(String prefix) {
    	
    	
    	return false;
    }
    
}
