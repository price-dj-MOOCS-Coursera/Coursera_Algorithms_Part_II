import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;



/**
 * @author David Price
 *
 */
public class MoveToFront {
	
	private static MyIndexMinPQ<Character> encodeQ = new MyIndexMinPQ<Character>(256);
	private static MyIndexMinPQ<Character> decodeQ = new MyIndexMinPQ<Character>(256);
	
	// apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
    	
    	// initialise encode list
    	for (int i = 0; i < 256; i++) {
    		//encodeList.add((char) i);
    		encodeQ.insert(i, (char) i);
    	}
    	
    	// reads StdIn Binary
    	while (!BinaryStdIn.isEmpty()) {
    		char c = BinaryStdIn.readChar();
    		
    		int index = encodeQ.findIndexOfKey(c);
    		
    		// output 8-bit index
    		
    		BinaryStdOut.write(index & 0xff, 8);
    		
    		// move forward
    		encodeQ.decreaseKey(0, c);
    	}
    	
    	BinaryStdOut.close();
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
    	
    	// initialise decode list
    	for (int i = 0; i < 256; i++) {
    		//decodeList.add((char) i);
    		decodeQ.insert(i, (char) i);
    	}
    	
    	// reads StdIn Binary
    	while (!BinaryStdIn.isEmpty()) {
    		int index = (int) BinaryStdIn.readChar();
    		
    		char c = (char) decodeQ.keyOf(index);
    		
    		// output char
    		
    		BinaryStdOut.write(c);
    		
    		// move forward
    		decodeQ.decreaseKey(0, c);
    	}
    	
    	BinaryStdOut.close();
    }
    
   
    

    // if args[0] is '-', apply move-to-front encoding
    // if args[0] is '+', apply move-to-front decoding
	public static void main(String[] args) {
		
		 String input = args[0].trim();
		 if (input.equals("-")) {
			 encode();
		 }
		 if (input.equals("+")) {
			 decode();
		 }
		 
		 

	}

}
