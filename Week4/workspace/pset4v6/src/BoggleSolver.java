
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;


public class BoggleSolver {
   
	//private boolean[][] visited;
    private Set<String> words;
    private int m;
    private int n;
    private myTrieSET dict;
    private BoggleBoard board;
    private Node[][] boardNodes;
    private Bag<Node> nodeBag;
   
    private static class Node {
		private int x;
    	private int y;
    	private char c;
    	private Set<Node> next;
    	private boolean visited;
    	
    	Node(char c, int i, int j) {
    		this.c = c;
    		this.x = i;
    		this.y = j;
    		this.next = new HashSet<Node>();
    		this.visited = false;
    	}
    	
    	public char getChar() {
    		return this.c;
    	}
    	
    	/*public Node getNode(int i, int j) {
    		
    		if (this.x == i && this.y == j) {
    			return this;
    		}
    		return null;
    	}*/
    	
    	public List<Node> getNext() {
    		ArrayList<Node> list = new ArrayList<Node>(this.next);
    		return list;
    	}
    	
    }
    
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
        this.words = new HashSet<String>();
        
        this.precomputeBoard();
        
        
        for (Node n : this.nodeBag) {
        	
        	String prefix = "";
        	
        	char c = n.getChar();
        	
        	dfs(n, prefix + (c == 'Q' ? "QU" : c));
        	
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

    
    private void dfs(Node node, String prefix) {
    	
    	if (!this.dict.isValidPrefix(prefix)) { return; }
    	
    	node.visited = true;
    	
    	int len = prefix.length();
        
        if (len > 2 && this.dict.contains(prefix)) { this.words.add(prefix); }
    	
    	List<Node> next = node.getNext();
    	
    	for (int k = 0; k < next.size(); k++) {
    		Node n = next.get(k);
    		
    		if (n != null && !n.visited) {
    			
    			char c = n.getChar();
    			
    			dfs(n, prefix + ((c == 'Q') ? "QU" : c));
    			
    		}
    	}
    	
    	node.visited = false;
    	
    }
    
    
    private void precomputeBoard() {
    	
    	boardNodes = new Node[this.m][this.n];
    	
    	this.nodeBag = new Bag<Node>();
    	
    	for (int i = 0; i < this.m; i++) {
    		for (int j = 0; j < this.n; j++) {
    			
    			Node x = new Node(board.getLetter(i, j), i, j);
    			this.nodeBag.add(x);
    			
    			this.boardNodes[i][j] = x;
    		}
    	}
    	
    	for (int i = 0; i < this.m; i++) {
    		for (int j = 0; j < this.n; j++) {
    			Node x = boardNodes[i][j];
    			
    			
    			if (i > 0 && j > 0 && i < this.m -1 && j < this.n - 1) {
    				x.next.add(boardNodes[i + 1][j - 1]);
    				x.next.add(boardNodes[i + 1][j + 1]);
    				x.next.add(boardNodes[i - 1][j - 1]);
    				x.next.add(boardNodes[i - 1][j + 1]);
    				x.next.add(boardNodes[i][j + 1]);
    				x.next.add(boardNodes[i][j - 1]);
    				x.next.add(boardNodes[i - 1][j]);
    				x.next.add(boardNodes[i + 1][j]);
    				
    			}
    			
    			if (i == 0 ) {
    				if (i < this.m - 1) {
    					x.next.add(boardNodes[i + 1][j]);
    				}
    				if (i < this.m - 1 && j < this.n - 1) {
    					x.next.add(boardNodes[i + 1][j + 1]);
    				}
    				if (j < this.n - 1) {
    					x.next.add(boardNodes[i][j + 1]);
    				}
    				if (j > 0) {
    					x.next.add(boardNodes[i][j - 1]);
    				}
    				if (j > 0 && i < this.m - 1) {
    					x.next.add(boardNodes[i + 1][j - 1]);
    				}
    			}
    			
    			if (j == 0) {
    				if (j < this.n - 1) {
    					x.next.add(boardNodes[i][j + 1]);
    				}
    				if (j < this.n - 1 && i < this.m - 1) {
    					x.next.add(boardNodes[i + 1][j + 1]);
    				}
    				if (i < this.m - 1) {
    					x.next.add(boardNodes[i + 1][j]);
    				}
    				if (i > 0) {
    					x.next.add(boardNodes[i - 1][j]);
    				}
    				if (i > 0 && j < this.n - 1) {
    					x.next.add(boardNodes[i - 1][j + 1]);
    				}
    			}
    			
    			if (i == this.m - 1) {
    				if (i > 0) {
    					x.next.add(boardNodes[i - 1][j]);
    				}
    				if (i > 0 && j < this.n - 1) {
    					x.next.add(boardNodes[i - 1][j + 1]);
    				}
    				if (j < this.n - 1) {
    					x.next.add(boardNodes[i][j + 1]);
    				}
    				if (j > 0) {
    					x.next.add(boardNodes[i][j - 1]);
    				}
    				if (j > 0 && i > 0) {
    					x.next.add(boardNodes[i - 1][j - 1]);
    				}
    			}
    			
    			if (j == this.n - 1) {
    				if (j > 0) {
    					x.next.add(boardNodes[i][j - 1]);
    				}
    				if (j > 0 && i < this.m - 1) {
    					x.next.add(boardNodes[i + 1][j - 1]);
    				}
    				if (i > 0) {
    					x.next.add(boardNodes[i - 1][j]);
    				}
    				if (i > 0 && j > 0) {
    					x.next.add(boardNodes[i - 1][j - 1]);
    				}
    			}
    			
    			/*if (i == 0) {
    				if (i < this.m - 1) {
    					x.next.add(boardNodes[i + 1][j]);
    					if (j < this.n - 1) {
    						x.next.add(boardNodes[i + 1][j + 1]);
    						x.next.add(boardNodes[i][j + 1]);
    						
    					}
    					if (j > 0) {
							x.next.add(boardNodes[i + 1][j - 1]);
							x.next.add(boardNodes[i][j - 1]);
						}
    				}
    			}
    			
    			if (i <= this.m - 1) {
    				if (i > 0) {
    					x.next.add(boardNodes[i - 1][j]);
    					if (j < this.n - 1) {
    						x.next.add(boardNodes[i - 1][j + 1]);
    						x.next.add(boardNodes[i - 1][j]);
    						
    					}
    					if (j > 0) {
							x.next.add(boardNodes[i - 1][j - 1]);
    						x.next.add(boardNodes[i - 1][j]);
						}
    				}
    			}
    			
    			if (j == 0) {
    				if (j < this.n - 1) {
    					x.next.add(boardNodes[i][j + 1]);
    					if (i < this.m - 1) {
    						x.next.add(boardNodes[i + 1][j + 1]);
    						x.next.add(boardNodes[i][j + 1]);
    						
    					}
    					if (i > 0) {
							x.next.add(boardNodes[i - 1][j + 1]);
							x.next.add(boardNodes[i - 1][j]);
						}
    				}
    			}
    			
    			if (j == this.n - 1) {
    				if (j > 0) {
    					x.next.add(boardNodes[i][j - 1]);
    					if (i < this.m - 1) {
    						x.next.add(boardNodes[i + 1][j - 1]);
    						x.next.add(boardNodes[i + 1][j]);
    						
    					}
    					if (i > 0) {
    						x.next.add(boardNodes[i - 1][j - 1]);
    						x.next.add(boardNodes[i - 1][j]);
    					}
    				}
    			}
    			
    			if (i > 0 && j > 0 && i < this.m -1 && j < this.n - 1) {
    				x.next.add(boardNodes[i + 1][j - 1]);
    				x.next.add(boardNodes[i + 1][j + 1]);
    				x.next.add(boardNodes[i - 1][j - 1]);
    				x.next.add(boardNodes[i - 1][j + 1]);
    				x.next.add(boardNodes[i][j + 1]);
    				x.next.add(boardNodes[i][j - 1]);
    				x.next.add(boardNodes[i - 1][j]);
    				x.next.add(boardNodes[i + 1][j]);
    				
    			}*/
    			
    			/*
    			// left vertical edge not including corners
    			if (i == 0) {
    				if (i < this.m - 1) {
    					x.next.add(boardNodes[i + 1][j]);
    					if (j < this.n - 1) {
    						x.next.add(boardNodes[i + 1][j + 1]);
    						x.next.add(boardNodes[i][j + 1]);
    						if (j > 0) {
    							x.next.add(boardNodes[i + 1][j - 1]);
    							x.next.add(boardNodes[i][j - 1]);
    						}
    					}
    				}
    				
					x.next.add(boardNodes[i + 1][j]);
					
    				x.next.add(boardNodes[i + 1][j + 1]);
    				
    			}
    			
    			// right vertical edge not including corners
    			if (i == this.m - 1) {
    				if (i > 0) {
    					x.next.add(boardNodes[i - 1][j]);
    					if (j < this.n - 1) {
    						
    					}
    				}
    				
					x.next.add(boardNodes[i - 1][j]);
					x.next.add(boardNodes[i - 1][j - 1]);
    				x.next.add(boardNodes[i - 1][j + 1]);
    				x.next.add(boardNodes[i][j + 1]);
    				x.next.add(boardNodes[i][j - 1]);
    				
    			}
    			
    			// top horizontal edge not including corners
    			else if (j == 0 && i > 0 && i < this.m -1) {
					x.next.add(boardNodes[i][j + 1]);
					x.next.add(boardNodes[i + 1][j + 1]);
    				x.next.add(boardNodes[i - 1][j + 1]);
    				x.next.add(boardNodes[i - 1][j]);
    				x.next.add(boardNodes[i + 1][j]);
    			}
    			
    			// bottom horizontal edge not including corners
    			else if (j == this.n - 1 && i > 0 && i < this.m -1) {
					x.next.add(boardNodes[i][j - 1]);
					x.next.add(boardNodes[i + 1][j - 1]);
    				x.next.add(boardNodes[i - 1][j - 1]);
    				x.next.add(boardNodes[i - 1][j]);
    				x.next.add(boardNodes[i + 1][j]);
    			}
    			
    			// corners
    			else if (i == 0 && j == 0) {
    				x.next.add(boardNodes[i + 1][j]);
    				x.next.add(boardNodes[i + 1][j + 1]);
    				x.next.add(boardNodes[i][j + 1]);
    			}
    			
    			else if (i == this.m - 1 && j == 0) {
    				x.next.add(boardNodes[i - 1][j]);
    				x.next.add(boardNodes[i - 1][j + 1]);
    				x.next.add(boardNodes[i][j + 1]);
    			}
    			
    			else if (i == 0 && j == this.n - 1) {
    				x.next.add(boardNodes[i][j - 1]);
    				x.next.add(boardNodes[i + 1][j - 1]);
    				x.next.add(boardNodes[i + 1][j]);
    			}
    			
    			else if (i == this.m - 1 && j == this.n - 1) {
    				x.next.add(boardNodes[i][j - 1]);
    				x.next.add(boardNodes[i - 1][j - 1]);
    				x.next.add(boardNodes[i - 1][j]);
    			}
    			
    			// elsewhere
    			else if (i > 0 && j > 0 && i < this.m -1 && j < this.n - 1) {
    				x.next.add(boardNodes[i + 1][j - 1]);
    				x.next.add(boardNodes[i + 1][j + 1]);
    				x.next.add(boardNodes[i - 1][j - 1]);
    				x.next.add(boardNodes[i - 1][j + 1]);
    				x.next.add(boardNodes[i][j + 1]);
    				x.next.add(boardNodes[i][j - 1]);
    				x.next.add(boardNodes[i - 1][j]);
    				x.next.add(boardNodes[i + 1][j]);
    				
    			}*/
    			
    			
    		}
    	}
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
        
        // tester for listNode
        for (Node n : solver.nodeBag) {
        	System.out.println(n.getChar());
        }
        
        System.out.println();
        
        /*for (int l = 0; l < solver.nodeBag.size(); l++) {
        	Node n = solver.nodeBag.get(l);
        	List<Node> next = n.getNext();
        	for (int o = 0; o < next.size(); o++) {
        		System.out.print(next.get(o).getChar() + " ");
        	}
        	System.out.println();
        }
        */
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