
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;


public class BoggleSolver {
    /*private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int R = 26;
    private static class Node {
        private boolean hasWord;
        private Node[] next = new Node[R];
    }
    private Node root;*/
	private boolean[][] visited;
    private Set<String> words;
    private int m;
    private int n;
    private Set<String> dict;
    private BoggleBoard board;

    public BoggleSolver(String[] dictionary) {
        if (dictionary == null) throw new IllegalArgumentException();

        /*for (String word : dictionary)
            putWord(word);*/
        
        this.dict = new TreeSet<String>();
        
        for (String d : dictionary) {
        	this.dict.add(d);
        }
        this.words = new HashSet<>();
        
    }

    public Iterable<String> getAllValidWords(BoggleBoard board) {
        if (board == null) {throw new IllegalArgumentException(); }
        
        this.board = board;
        this.m = board.rows();
        this.n = board.cols();
        this.visited = new boolean[m][n];
        
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++) {
            	
            	//StringBuilder prefix = new StringBuilder("");
            	
            	char c = board.getLetter(i, j);
            	
            	/*if (c == 'Q') { prefix.append("QU"); }
            	else { prefix.append(c); }
            	
            	if (c == 'Q') { 
            		String b = c + "U"; 
            		dfs(i, j, new StringBuilder(b));
            	}
            	else {
            		dfs(i, j, new StringBuilder(c));
            	}*/
            	
            	//System.out.println(c + "");
            	 /*char c = board.getLetter(i, j);
            	 dfs(board, i, j, c == 'Q' ? "QU" : "" + c, words);*/
            	
            	dfs(i, j, c + "" /*== 'Q' ? "QU" : "" + c*/);
            	
            }
        
        return words;
    }

    public int scoreOf(String word) {
        if (!this.dict.contains(word)) { return 0; }

        int length = word.length();
        if (length > 7) { return 11; }
        if (length == 7) { return 5; }
        if (length == 6) { return 3; }
        if (length == 5) { return 2; }
        if (length > 2) { return 1; }
        return 0;
    }

   /* private char charAt(String s, int d) {
        return (char) (s.charAt(d) - 'A');
    }
    private boolean hasWord(String word) {
        Node x = get(root, word, 0);
        return x != null && x.hasWord && word.length() > 2;
    }

    private Node get(Node x, String word, int d) {
        if (x == null) return null;
        if (d == word.length()) return x;
        char c = charAt(word, d);
        return get(x.next[c], word, d + 1);
    }

    private void putWord(String word) {
        root = put(root, word, 0);
    }

    private Node put(Node x, String word, int d) {
        if (x == null) x = new Node();
        if (d == word.length()) {
            x.hasWord = true;
            return x;
        }
        char c = charAt(word, d);
        x.next[c] = put(x.next[c], word, d + 1);
        return x;
    }

    private boolean isValidPrefix(String prefix) {
        Node x = root;
        for (int i = 0; i < prefix.length() && x != null; i++)
            x = x.next[charAt(prefix, i)];
        return x != null;
    }*/

    private void dfs(int i, int j, String prefix) {
        /*if (!isValidPrefix(prefix)) return;*/
    	
        visited[i][j] = true;
        
        int len = prefix.length();
        
        //System.out.println(prefix);
        
        if (len > 2 && this.dict.contains(prefix))
            this.words.add(prefix);

        for (int row = i - 1; row <= i + 1; row++)
            for (int col = j - 1; col <= j + 1; col++)
                if (isValidIndex(row, col) && !visited[row][col]) {
                	
                	/*char c = board.getLetter(i, j);
                	
                	if (c == 'Q') { prefix.append("QU"); }
                	else { prefix.append(c); }
                	
                	if (c == 'Q') { 
                		String b = c + "U"; 
                		dfs(i, j, prefix.append(b));
                	}
                	else {
                		dfs(i, j, prefix.append(c));
                	}*/
                	
                	char c = board.getLetter(row, col);
                	
                	dfs(row, col, prefix + /*(c == 'Q' ? "QU" : c)*/ c);
                }
        
        //prefix = new StringBuilder();
        visited[i][j] = false;
    }

    private boolean isValidIndex(int i, int j) {
        return !(i < 0 || i >= this.m || j < 0 || j >= this.n);
    }
    
    
    public static void main(String[] args) {
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(args[1]);
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
    }
}