
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;


public class BoggleSolver {
   
	private boolean[][] visited;
    private Set<String> words;
    private int m;
    private int n;
    private Set<String> dict;
    private BoggleBoard board;
   
    public BoggleSolver(String[] dictionary) {
        if (dictionary == null) throw new IllegalArgumentException();
        
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
            	
            	String prefix = "";
            	
            	/*StringBuffer prefix = new StringBuffer("");*/
            	
            	char c = board.getLetter(i, j);
            	
            	/*prefix.append((c == 'Q') ? "QU" : c);*/
            	
            	/*if (c == 'Q') { prefix.append("QU"); }
            	else { prefix.append(c); }*/
            	
            	dfs(i, j, prefix + ((c == 'Q') ? "QU" : c));
            	
            }
        
        return new HashSet<String>(words);
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

  

    private void dfs(int i, int j, String prefix) {
       
        visited[i][j] = true;
        
        int len = prefix.length();
        
       // System.out.println(prefix.toString());
        
        if (len > 2 && this.dict.contains(prefix)) { this.words.add(prefix); }
        
        for (int row = i - 1; row <= i + 1; row++)
            for (int col = j - 1; col <= j + 1; col++)
                if (isValidIndex(row, col) && !visited[row][col]) {
                	
                	char c = board.getLetter(row, col);
                	
                	dfs(row, col, prefix + ((c == 'Q') ? "QU" : c));
                }
        
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