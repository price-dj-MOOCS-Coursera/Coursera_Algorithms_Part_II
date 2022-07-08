
import java.util.HashSet;
import java.util.Set;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;


public class BoggleSolver {
   
	private boolean[][] visited;
	//private boolean[] visited;
    private Set<String> words;
    private int m;
    private int n;
    private myTrieSET dict;
    private BoggleBoard board;
   
    public BoggleSolver(String[] dictionary) {
        if (dictionary == null) throw new IllegalArgumentException();
        
        this.dict = new myTrieSET();
        
        for (String d : dictionary) {
        	this.dict.add(d);
        }
        
        
    }

    public Iterable<String> getAllValidWords(BoggleBoard board) {
        if (board == null) {throw new IllegalArgumentException(); }
        
        this.board = board;
        this.m = board.rows();
        this.n = board.cols();
        /*if (n == 0) { this.visited = new boolean[m]; }
        else if (m == 0) { this.visited = new boolean[n]; }
        else { this.visited = new boolean[m*n]; }*/
        
        this.visited = new boolean[m][n];
        
        this.words = new HashSet<>();
        
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++) {
            	
            	String prefix = "";
            	
            	char c = board.getLetter(i, j);
            	
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
    	
    	if (!this.dict.isValidPrefix(prefix)) { return; }
       
        //visited[(i * m) + j] = true;
        
    	visited[i][j] = true;
    	
        int len = prefix.length();
        
        //System.out.println(prefix);
        
        if (len > 2 && this.dict.contains(prefix)) { this.words.add(prefix); }
        
        for (int row = i - 1; row <= i + 1; row++)
            for (int col = j - 1; col <= j + 1; col++)
                if (isValidIndex(row, col) && !visited[row][col]/*!visited[(row *m) + col]*/) {
                	
                	char c = board.getLetter(row, col);
                	
                	/*if (!this.dict.altIsValidPrefix(prefix, c)) { return; }*/
                	
                	dfs(row, col, prefix + ((c == 'Q') ? "QU" : c));
                }
        
       // visited[(i * m) + j] = false;
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
        //BoggleBoard board = new BoggleBoard();
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
        /*BoggleBoard board;
        
        for (int i = 0; i < 1000; i++) {
        	board = new BoggleBoard(2, 1);
        	int score = 0;
            for (String word : solver.getAllValidWords(board)) {
                //StdOut.println(word);
                score += solver.scoreOf(word);
            }
            StdOut.println("Score = " + score);
        }*/
        
        
    }
}