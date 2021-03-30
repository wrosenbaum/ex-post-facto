/***********************************************************
 * This program reads an input stream of characters are writes a
 * corresponding stream of ASCII values to standard output.
 *
 * Example usage:
 *
 * java CharToInt < input.txt > output.txt
 * 
 * will read each character in input.txt and write the corresponding
 * (integer) ASCII values of the contents of intput.txt to output.txt.
 * 
 * Running the program without an output redirect, i.e., running
 *
 * java CharToInt < input.txt
 * 
 * will print the ASCII values for the contents of input.txt to the
 * console.
 **********************************************************/


import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.util.ArrayList;


public class CharToInt {
    
    private static ArrayList<Integer> chars = new ArrayList<Integer>();

    public static void main (String[] args) {
	read();
	write();
    }

    private static void read () {
	// Prepare to read from stdin.
	InputStream in = System.in;
	ArrayList<Character> l = Cipher.createList();

	// Read one character at a time, appending each to the list.  If the
	// end-of-file code is returned, end the loop.
	while (true) {
	    int readValue = 0;
	    try {
		readValue = in.read();
	    } catch (IOException e) {
		System.err.println("ERROR: Failed reading");
		System.exit(1);
	    }
	    if (readValue == -1) {
		break;
	    } else {
		chars.add(readValue);
	    }
	}	

    } // read ()

    private static void write () {

	// Prepare to write to stdout.
	OutputStream out = System.out;

	// Write one character at a time.
	for (int i = 0; i < chars.size(); i += 1) {
	    int c = chars.get(i);
	    try {
		out.write(("" + c).getBytes());
		out.write(' ');
	    } catch (IOException e) {
		System.err.println("ERROR: Failed writing");
		System.exit(1);
	    }
	}

	try {
	    out.write('\n');
	} catch (IOException e) {
	    System.err.println("ERROR: Failed writing");
	    System.exit(1);
	}


	// Closing the output flushes any buffered data.  If this step fails,
	// some portion of output's end may be lost---that is, it may not come
	// through stdout.
	try {
	    out.close();
	} catch (IOException e) {
	    System.err.println("WARNING: Failed to close output. " +
			       "Data may have been lost.");
	}

    } // write ()
    
}
