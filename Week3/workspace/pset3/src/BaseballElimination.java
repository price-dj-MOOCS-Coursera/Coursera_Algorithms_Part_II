

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class BaseballElimination {
	
	private final int n;		// total num teams
	
	private final int[] wins;			// array of wins for i team
	private final int[] loss;			// array of losses for i team
	private final int[] rem;			// array of total games remaining for i team
	private final int[][] games;		// 2-D array of game schedule of num games of team i vs team j
	private final HashMap<String, Integer> teamToID;
	private final HashMap<Integer, String> idToTeam;
	private FordFulkerson ff;
	private Set<String> certElim;
	private boolean trivial;
	
	// create a baseball division from given filename
	public BaseballElimination(String filename) {
		
		if (filename == null) {
			throw new java.lang.IllegalArgumentException();
		}
		
		In input = new In(filename);
		
		// read in number of teams
		this.n = Integer.parseInt(input.readLine());
		
		this.wins = new int[n];
		this.loss = new int[n];
		this.rem = new int[n];
		this.games = new int[n][n];
		this.trivial = false;
		
		this.idToTeam = new HashMap<Integer, String>();
		this.teamToID = new HashMap<String, Integer>();
		
		this.certElim = new HashSet<String>();
		
		String[] lines = input.readAllLines();
		for (int i = 0; i < lines.length; i++) {
			String[] lineArr = lines[i].trim().split("\\s+", 5);
			
			this.idToTeam.put(i, lineArr[0]);
			this.teamToID.put(lineArr[0], i);
			
			
			this.wins[i] = Integer.parseInt(lineArr[1]);
			this.loss[i] = Integer.parseInt(lineArr[2]);
			this.rem[i] = Integer.parseInt(lineArr[3]);
			
			
			String[] gamesAgainst = lineArr[4].split("\\s+");
			for (int j = 0; j < gamesAgainst.length; j++) {
				this.games[i][j] = Integer.parseInt(gamesAgainst[j]);
			}
		}
		
	}
	
	// number of teams
	public int numberOfTeams() {
		return this.n;
	}
	
	// all teams
	public Iterable<String> teams() {
		return this.teamToID.keySet();
	}
	
	// number of wins for given team
	public int wins(String team) {
		if (!teamToID.containsKey(team)) {
			throw new IllegalArgumentException();
		}
		
		// return that num wins for given team
		return this.wins[teamToID.get(team)];
		
	}
	
	// number of losses for given team
	public int losses(String team) {
		if (!teamToID.containsKey(team)) {
			throw new IllegalArgumentException();
		}
		
		// return that num losses for given team
		return this.loss[teamToID.get(team)];
	}
	
	// number of remaining games for given team
	public int remaining(String team) {
		if (!teamToID.containsKey(team)) {
			throw new IllegalArgumentException();
		}
		
		// return number of remaining games for that teamNum
		return this.rem[teamToID.get(team)];
	}
	
	// number of remaining games between team1 and team2
	public int against(String team1, String team2) {
		if (!teamToID.containsKey(team1) ||
				!teamToID.containsKey(team2)) {
			throw new IllegalArgumentException();
		}
		
		// return games remaining
		return this.games[teamToID.get(team1)][teamToID.get(team2)];
		
	}
	
	// is given team eliminated?
	public boolean isEliminated(String team) {
		if (!teamToID.containsKey(team)) {
			throw new IllegalArgumentException();
		}
		
		// get teamNum from team Name
		int x = teamToID.get(team);
		
		// check for trivial elimination
		// if any team has wins gt maxWins for this team then return true
		// find the team that is eliminating add to certificate of elimination
		int xWins = this.wins[x] + this.rem[x];
		/*double maxWins = Double.NEGATIVE_INFINITY;
		int winID = 0;
		for (int j = 0; j < this.n; j++) {
			if (j != x && xWins < this.wins[j] ) {
				if (this.wins[j] > maxWins) {
					maxWins = this.wins[j];
					winID = j;
					this.trivial = true;
				}
			}
		}
		
		if (this.trivial) {
			this.certElim.add(this.idToTeam.get(winID));
			return this.trivial;
		}*/
		
		int maxWins = this.wins[x] + this.rem[x];
		for (int j = 0; j < this.n; j++) {
			if (j != x && maxWins < this.wins[j] ) {
				this.certElim.add(this.idToTeam.get(j));
				this.trivial = true;
				return this.trivial;
			}
		}
		
		/*for (int j = 0; j < this.n; j++) {
			if (this.wins[j] > maxWins) {
				maxWins = this.wins[j];
				winID = j;
				if (j !=x && maxWins > xWins) {
					this.trivial = true;
				}
						
			}
		}
		
		if (this.trivial) {
			this.certElim.add(this.idToTeam.get(winID));
			return this.trivial;
		}*/
		
		// Construct FlowNetwork
		FlowNetwork fn = this.constructFlowNetwork(x);
		
		// Construct FordFulkerson datatype
		int s = fn.V() - 2;
		int t = fn.V() - 1;
		
		this.ff = new FordFulkerson(fn, s, t);
		
		for (FlowEdge fe : fn.adj(s)) {
			if (fe.flow() < fe.capacity()) {
				return true;
			}
		}
		return false;
	}
	
	// subset R of teams that eliminates given team; null if not eliminated
	public Iterable<String> certificateOfElimination(String team) {
		if (!teamToID.containsKey(team)) {
			throw new IllegalArgumentException();
		}
		int x = this.teamToID.get(team);
		
		if (!this.isEliminated(team)) {
			return null;
		}
		
		if (this.trivial) {
			return this.certElim;
		}
		
		else {
			for (int v = 0; v < this.n; v++) {
				if (v != x && this.ff != null && this.ff.inCut(v)) {
				//if (v != x && this.ff.inCut(v)) {
					this.certElim.add(this.idToTeam.get(v));
				}
			}
			return this.certElim;
		}
		
	}
	
	// private method to construct the FlowNetwork
	private FlowNetwork constructFlowNetwork(int x) {
		// number of FlowEdge vertices V
		int numV = 2 + (this.n - 1) * (this.n - 2) / 2 + this.n;
		
		FlowNetwork fn = new FlowNetwork(numV);
		
		// 0 to n - 1 are team vertices but x will not be used
		// s = V - 2 and t = V - 1, remaining are the game vertices
		
		int s = numV - 2;
		int t = numV - 1;
		
		for (int v = 0; v < this.n; v++) {
			if (v == x) { continue; }
			
			int capacity = this.wins[x] + this.rem[x] - this.wins[v];
			
			FlowEdge fe = new FlowEdge(v, t, Math.max(capacity, 0));
			fn.addEdge(fe);
		}
		
		// offset k
		int k = 0;
		
		for (int i = 0; i < this.n; i++) {
			for (int j = i + 1; j < this.n; j++) {
				if (i == x || j == x) { continue; }
				
				int v = this.n + k++;
				
				fn.addEdge(new FlowEdge(s, v, this.games[i][j]));
				fn.addEdge(new FlowEdge(v, i, Double.POSITIVE_INFINITY));
				fn.addEdge(new FlowEdge(v, j, Double.POSITIVE_INFINITY));
			}
		}
		return fn;
	}
	
	private double validateCertificateOfElimination() {
		
		int sumWins = 0;
		int sumGames = 0;
		
		for (String team : this.certElim) {
			sumWins += this.wins[this.teamToID.get(team)];
		}
		
		String[] teams = this.certElim.toArray(new String[this.certElim.size()]);
		
		if (teams.length == 1) { sumGames += 0; }
		
		else {
			for (int i = 0; i < teams.length; i++) {
				for (int j = i + 1; j < teams.length; j++) {
					String team1 = teams[i];
					String team2 = teams[j];
					sumGames += this.games[this.teamToID.get(team1)][this.teamToID.get(team2)];
				}
			}
		}
		
		// calc validated a(R) = (sumWins(R) + sumGames(R))/|R|
		double a_R = (sumWins + sumGames) / (double) teams.length;
		
		return a_R;
	}
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BaseballElimination division = new BaseballElimination(args[0]);
	    for (String team : division.teams()) {
	        if (division.isEliminated(team)) {
	            StdOut.print(team + " is eliminated by the subset R = { ");
	            for (String t : division.certificateOfElimination(team)) {
	                StdOut.print(t + " ");
	            }
	            StdOut.println("}"/* + " a_R = " + division.validateCertificateOfElimination() + " wins = " + division.wins(team)*/);
	        }
	        else {
	            StdOut.println(team + " is not eliminated");
	        }
	    }
	}

}
