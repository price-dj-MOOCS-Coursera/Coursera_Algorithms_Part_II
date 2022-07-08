

public class myTrieSET {
	private static final int R = 26;	
	
	private Node root;
	private int n;
	
	 // R-way trie node
    private static class Node {
        private Node[] next = new Node[R];
        private boolean isString;
    }

    /**
     * Initializes an empty set of strings.
     */
    public myTrieSET() {
    	
    }
    /**
     * Does the set contain the given key?
     * @param key the key
     * @return {@code true} if the set contains {@code key} and
     *     {@code false} otherwise
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    //@Override
    public boolean contains(String key) {
        if (key == null) throw new IllegalArgumentException("argument to contains() is null");
        Node x = get(root, key, 0);
        if (x == null) return false;
        return x.isString;
    }

    private Node get(Node x, String key, int d) {
        if (x == null) return null;
        if (d == key.length()) return x;
        char c = key.charAt(d);
        return get(x.next[c - 'A'], key, d+1);
    }

    /**
     * Adds the key to the set if it is not already present.
     * @param key the key to add
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    //@Override
    public void add(String key) {
        if (key == null) throw new IllegalArgumentException("argument to add() is null");
        root = add(root, key, 0);
    }

    private Node add(Node x, String key, int d) {
        if (x == null) x = new Node();
        if (d == key.length()) {
            if (!x.isString) n++;
            x.isString = true;
        }
        else {
            char c = key.charAt(d);
            x.next[c - 'A'] = add(x.next[c - 'A'], key, d+1);
        }
        return x;
    }
    
    /**
     * Returns the number of strings in the set.
     * @return the number of strings in the set
     */
    //@Override
    public int size() {
        return n;
    }

    /**
     * Is the set empty?
     * @return {@code true} if the set is empty, and {@code false} otherwise
     */
    //@Override
    public boolean isEmpty() {
        return size() == 0;
    }

    // method to search for prefix
    public boolean isValidPrefix(String prefix) {
    	if (prefix == null) {
    		throw new IllegalArgumentException("calls isValidPrefix() with null argument");
    	}
    	
    	//Node x = get(root, prefix, 0);
    	
    	Node x = root;
    	int len = prefix.length();
    	for (int i = 0; i < len && x != null; i++) {
    		x = x.next[prefix.charAt(i) - 'A'];
    	}
    	
    	return x != null;
    }
    
}
