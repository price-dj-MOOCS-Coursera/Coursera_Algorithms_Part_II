import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.util.Scanner;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * 
 */

/**
 * @author David Price
 *
 */
public class Outcast {
    
    private WordNet wordnet;
    
    
    // constructor takes a WordNet object
    public Outcast(WordNet wordnet) {
        this.wordnet = wordnet;
    }

    // given an array of WordNet nouns, return an outcast
    public String outcast(String[] nouns) {
        
        int maxDist = 0;
        String outcast = "";
        
        for (String noun : nouns) {
            int distToOther = 0;
            for (String otherNoun : nouns) {
                if (!noun.equals(otherNoun)) {
                    distToOther += wordnet.distance(noun, otherNoun);
                }
            }
            if (distToOther > maxDist) {
                maxDist = distToOther;
                outcast = noun;
            }
        }
        return outcast;
        
    }

    // see test client below
    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}