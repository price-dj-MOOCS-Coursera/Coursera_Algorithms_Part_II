import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;



/**
 * @author David Price
 *
 */
public class MoveToFront {
	
	private static final int EXTENDED_ASCII = 256;
	
	// apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
    	
    	List<Character> encodeList = new ArrayList<Character>();
    	
    	// initialise encode list
    	for (int i = 0; i < EXTENDED_ASCII; i++) {
    		encodeList.add((char) i);
    	}
    	
    	// reads StdIn Binary
    	while (!BinaryStdIn.isEmpty()) {
    		char c = BinaryStdIn.readChar();
    		
    		int index = encodeList.indexOf(c);
    		
    		// output 8-bit index
    		
    		BinaryStdOut.write(index & 0xff, 8);
    		
    		// move forward
    		encodeList.remove(index);
    		encodeList.add(0, c);
    	}
    	
    	BinaryStdOut.close();
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
    	
    	List<Character> decodeList = new ArrayList<Character>();
    	
    	// initialise decode list
    	for (int i = 0; i < EXTENDED_ASCII; i++) {
    		decodeList.add((char) i);
    	}
    	
    	// reads StdIn Binary
    	while (!BinaryStdIn.isEmpty()) {
    		int index = (int) BinaryStdIn.readChar();
    		
    		char c = decodeList.get(index);
    		
    		// output char
    		
    		BinaryStdOut.write(c);
    		
    		// move forward
    		decodeList.remove(index);
    		decodeList.add(0, c);
    	}
    	
    	BinaryStdOut.close();
    }
    
   
    

    // if args[0] is '-', apply move-to-front encoding
    // if args[0] is '+', apply move-to-front decoding
	public static void main(String[] args) {
		
		 /*String input = args[0].trim();
		 if (input.equals("-")) {
			 encode();
		 }
		 if (input.equals("+")) {
			 decode();
		 }*/
		 
		 if (args[0].equals("-")) {
			 encode();
		 }
		 if (args[0].equals("+")) {
			 decode();
		 }
	}

}
