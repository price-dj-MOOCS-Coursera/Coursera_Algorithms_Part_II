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
    	
    	char[] asciiArr = new char[EXTENDED_ASCII];
    	
    	for (char i = 0; i < EXTENDED_ASCII; i++) {
    		asciiArr[i] = i;
    	}
    	
    	// Read in string
    	String line = BinaryStdIn.readString();
    	int len = line.length();
    	
    	char[] inputChar = line.toCharArray();
    	
    	char inputTemp;
    	char temp;
    	
    	for (int j = 0; j < len; j++) {
    		inputTemp = inputChar[j];
    		char k = 0;
    		while (inputChar[j] != asciiArr[k]) {
    			temp = asciiArr[k];
    			asciiArr[k] = inputTemp;
    			inputTemp = temp;
    			k++;
    		}
    		asciiArr[k] = inputTemp;
    		
    		// output char
    		BinaryStdOut.write(k);
    	}
    	BinaryStdOut.close();
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
    	
    	char[] asciiArr = new char[EXTENDED_ASCII];
    	
    	for (char i = 0; i < EXTENDED_ASCII; i++) {
    		asciiArr[i] = i;
    	}
    	
    	char index;
    	
    	// reads StdIn Binary
    	while (!BinaryStdIn.isEmpty()) {
    		
    		index = BinaryStdIn.readChar();
    		
    		// output char
    		
    		char out = asciiArr[index];
    		
    		BinaryStdOut.write(out);
    		
    		for (int i = index; i > 0; i--) {
    			asciiArr[i] = asciiArr[i - 1];
    		}
    		asciiArr[0] = out;
    	}
    	BinaryStdOut.close();
    }
    
   
    

    // if args[0] is '-', apply move-to-front encoding
    // if args[0] is '+', apply move-to-front decoding
	public static void main(String[] args) {
		
		 if (args[0].equals("-")) { encode(); }
		 else if (args[0].equals("+")) { decode(); }
	}

}
