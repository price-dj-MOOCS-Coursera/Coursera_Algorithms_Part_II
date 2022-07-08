
import java.util.HashSet;
import java.util.Set;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * Project: Boggle
 * Author: CtheSky
 * Create Date: 2017/2/11
 * Description:
 * All rights reserved.
 */
public class BoggleSolver {
    private static final String ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int R = 26;
    private static class Node {
        private boolean hasWord;
        private Node[] next = new Node[R];
    }
    private Node root;
    private boolean[][] used;

    public BoggleSolver(String[] dictionary) {
        if (dictionary == null) throw new IllegalArgumentException();

        for (String word : dictionary)
            putWord(word);
    }

    public Iterable<String> getAllValidWords(BoggleBoard board) {
        if (board == null) throw new IllegalArgumentException();

        int m = board.rows();
        int n = board.cols();
        used = new boolean[m][n];
        
        
        
        Set<String> words = new HashSet<>();
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++) {
            	
            	 char c = board.getLetter(i, j);
            	 searchForWords(board, i, j, c == 'Q' ? "QU" : "" + c, words);
            }
        return words;
    }

    public int scoreOf(String word) {
        if (!hasWord(word)) return 0;

        int length = word.length();
        if (length > 7) return 11;
        if (length == 7) return 5;
        if (length == 6) return 3;
        if (length == 5) return 2;
        if (length > 2) return 1;
        return 0;
    }

    private char charAt(String s, int d) {
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
    }

    private void searchForWords(BoggleBoard board, int i, int j, String prefix, Set<String> words) {
        if (!isValidPrefix(prefix)) return;

        used[i][j] = true;
        if (hasWord(prefix))
            words.add(prefix);

        for (int row = i - 1; row <= i + 1; row++)
            for (int col = j - 1; col <= j + 1; col++)
                if (isValidIndex(board, row, col) && !used[row][col]) {
                	char c = board.getLetter(row, col);
                	searchForWords(board, row, col, prefix + (c == 'Q' ? "QU" : c), words);
                }
        
        //prefix = new StringBuilder();
        used[i][j] = false;
    }

    private boolean isValidIndex(BoggleBoard board, int i, int j) {
        return i >= 0 && i < board.rows() && j >= 0 && j < board.cols();
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