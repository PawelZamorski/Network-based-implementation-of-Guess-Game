package ie.gmit.dip;

import java.io.PrintWriter;

/**
 * @author  Pawel Zamorski
 * @version 1.0
 * @since 1.8
 * 
 * The <code>GuessGame</code> class provides an implementation of Guess Game.
 * <p>
 * User has ten (iterative) attempts at guessing the number that is randomly chosen by computer.
 * Program responds with 
 * “Correct – you win!”, or 
 * “Too high – guess again”, or
 * “Too low – guess again”, or 
 * “You’re out of guesses – you lose!” as appropriate.
 * User wins if they guess correctly.
 * Program wins if the User runs out of guesses.
 * Program keeps a tally of scores, and asks the user at the end of each game if they would like to play again or not.
 * <p>
 * The constructor uses Input Validator and PrintWriter.
 * InputValidator is used for for getting and validating user input.
 * PrintWriter is used for printing formatted representations of objects to a text-output stream.
 * <p>
 * To start the GuessClient use <code>run</code> method.
 */
public class GuessGame {
	private final int MAX_GUESSES = 10;
	private InputValidator input;
	private PrintWriter output;
	
	 // userScore, compScore, userName SHOULD BE MOVED TO THE SEPARATE CLASS (class Player).
	//  Data should be stored to allow further use after reconnection
	private int userScore = 0;
	private int compScore = 0;
	private String userName = "player";
	
	/**
	 * Constructor
	 * 
	 * @param input
	 * @param output
	 */
	public GuessGame(InputValidator input, PrintWriter output) {
		this.output = output;
		this.input = input;
	}
	
	/**
	 * Runs a Guess Game.
	 */
	public void run() {
		boolean keepRunning = true; // Variable used in while loop as a control condition
		String playAgain; // Variable to check if user wants to play again
		// Set up the User name
		output.println("Welcome to the Guess Game! Insert Your name >");
		userName = input.validateString();
		
		output.print( userName + ", Your have " + MAX_GUESSES + 
				" attempts at guessing the number between 1 and 1000. Lets start! "); // Use print method to print the rest of the line
		// Use loop to enable the user to play again
		while(keepRunning) {
			output.print("Round number " + (userScore + compScore + 1) + ". ");
			int counter = 1; // Count the number of attempts
			int randomNumber = getRandomNumber();
			output.println("Insert number >"); // ONLY ONE PRINTLN. Otherwise client reads first line and the other lines are kept in a buffer
			while(counter <= MAX_GUESSES) {
				int number = input.validateIntOfRange(1, 1000); // Accept only input if it is from the range 1 - 1000
				if(counter >= 10 && number != randomNumber) {
					compScore++;
					output.print("You're out of guesses  you lose! ");
				}else if (number < randomNumber) {
					output.println("Too low - guess again");
				}else if(number > randomNumber){
					output.println("Too high - guess again");				
				}else {
					output.print("Correct - you win!");
					userScore++;
					// Set the loop condition to false to finish the inner loop
					counter = MAX_GUESSES + 1;
				}
				counter++; // Increment counter
			}
			
			// Display score and ask User if they want to play again
			output.print("Score: " + userName + " " + userScore + " | computer " + compScore + ". ");
			output.println("Would you like to play again? Insert 'yes' to continue | 'no' to end the game.");

			// Read line from socket
			playAgain = input.validateString();
			
			System.out.println("Round finished. Play again: " + playAgain); // Control information displayed is the server console
			
			if(playAgain.equalsIgnoreCase("no")) {
				keepRunning = false;
			}
		}
		output.println("Bye bye!");
	}
	
	/*
	 * Returns a random number between 1 and 1000 inclusive.
	 */
	private int getRandomNumber() {
		return (int) Math.floor(Math.random() * 1000 + 1);
	}

}
