package ie.gmit.dip;

import java.io.PrintWriter;
import java.util.Scanner;

/**
 * @author  Pawel Zamorski
 * @version 1.0
 * @since 1.8
 * 
 * The InputValidatorImpl class is an implementation of InputValidator interface.
 * It is used for reading and processing input inserted to the console.
 * It uses Scanner class to pre-process data.
 * It uses PrintWriter to output the stream.
 */
public class InputValidatorImpl implements InputValidator {
	private Scanner scanner;
	private PrintWriter output;

	/**
	 * Creates a new InputValidatorImpl object that is ready for taking and processing an input.
	 * 
	 * @param scanner
	 */
	public InputValidatorImpl(Scanner scanner, PrintWriter output) {
		super();
		this.scanner  = scanner;
		this.output = output;
	}
	
	/**
	 * Validates if the input is not an empty string or null.
	 * <p>
	 * Method uses Scanner class. It runs in a loop until the input is correct. It accepts String that is not empty.
	 * Otherwise the message is displayed to the console.
	 * 
	 * @return a String that is not empty or null
	 */
	public String validateStringNotEmpty() {
		String s;
    	// Run while loop until the input is correct: input consist a token
        do {
        	s = scanner.nextLine();        	
        	// Check if User inserted valid input.
            if(s == null || s.equals("")) {
            	output.println("Invalid input - it is empty. Please, insert a token.");           	
            }
        } while(s == null || s.equals(""));
        return s;
	}
	
	/**
	 * Returns an integer as an chosen option by the User.
	 * <p>
	 * It displays a range of options and asks User to select one from the range.
	 * It accepts only integers.
	 * The range of options is specified by the min and max parameters.
	 * <p>
	 * This method processes the first token. The rest of the line is consumed.
	 * 
	 * @param min a minimum value for option
	 * @param max a maximum value for option
	 * @return a selected option
	 */
    public int validateIntOfRange(int min, int max){
        // Initialize the input with the value less than min which is incorrect input. This variable is used as a test condition for a loop.
        int input = min - 1;
        // Run while loop until the input is correct: type of integer and from the given range
        do {
        	// Check if User inserted integer. If not - continue.
            if(!scanner.hasNextInt()) {
                output.print("Invalid input ('" + scanner.nextLine()  + "' is not a number - integer). ");
                output.println("Please select the valid option [" + min + " - " + max + "] >");
                continue;            	
            }        	
            input = scanner.nextInt();
        	// Consume the rest of the current line 
        	scanner.nextLine();
        	// Check if User inserted integer from the given range
        	if(!(input >= min && input <= max)) {
        		output.print("Invalid input ('" + input  + "' is out of range). ");
                output.println("Please select the valid option [" + min + " - " + max + "] >");
        	}
        } while(!(input >= min && input <= max));
        return input;
    }

    /**
     * Returns the whole input as a string
     * 
     * @return a whole input as a string
     */
    public String validateString(){
    	return scanner.nextLine();
    }
        
	/**
	 * Closes the underlying Scanner
	 */
	public void close() {
		this.scanner.close();
	}

}
