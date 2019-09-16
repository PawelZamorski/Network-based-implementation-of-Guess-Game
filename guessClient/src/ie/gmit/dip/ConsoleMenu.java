package ie.gmit.dip;

import java.io.PrintWriter;
import java.util.Scanner;

/**
 * @author  Pawel Zamorski
 * @version 1.0
 * @since 1.8
 * 
 * The <code>ConsoleMenu</code> class provides a console menu for the User.
 * <p>
 * When the program is run as a console program, the ConsoleMenu object should be instantiated in the <code>main</code> method.
 * Then the start() method should be invoked.
 */
public class ConsoleMenu {
	private String host;
	private int port;
	private Scanner scanner = new Scanner(System.in);
	private PrintWriter output = new PrintWriter(System.out, true); // Print to the console, autoflush the output buffer
	
	/**
	 * A constructor for ConsoleMenu class.
	 * 
	 * @param host String the hostname on which the server is running 
	 * @param port int the port on which the server is listening on
	 */
	public ConsoleMenu(String host, int port) {
		super();
		this.host = host;
		this.port = port;
	}
	
    /**
     * Displays the main menu with options. The user have option to change the host name (could be done the same for port) 
     */
    public void start(){
    	GuessClient gc = new GuessClient(host, port, new InputValidatorImpl(scanner, output));

    	output.println("The program connects by default to the server on " + host + " on port " + port);
    	output.println("Insert 'y' to connect to the default server | other key to connect to the different server");
		// Change the host name if user wish so
		if(!scanner.nextLine().equalsIgnoreCase("y")) {
			message();
			host = scanner.nextLine();
		}
		
		// Check if a host name is correct and if the server is running
		// Keep running until both conditions are true
		boolean isServerRunning = false;
		while(!isServerRunning) {
			// Check if host exist
			if(!gc.isHostnameCorrect(host)) {
				// Allow User to quit program
				if(quit()) break;
				message();
				host = scanner.nextLine();
				continue;
			}
			// Check if server is running on host on the default port
			if(!gc.isServerRunning(host)) {
				// Allow User to quit program
				if(quit()) break;
				message();
				host = scanner.nextLine();
				continue;
			}
			isServerRunning = true;
		}
		// Connect to the server
		System.out.println("Connection to the server is pending ...");
		gc.run();		
		// Close Scanner
		scanner.close();

    }

	/*
	 * Returns true if the User wants to exit program. False otherwise
	 * 
	 * @return true if the User wants to exit program, false otherwise
	 */
    private boolean quit() {
		boolean exit = false;
		output.println("Insert 'q' to quit program or other key to continue.");
		if(scanner.nextLine().equalsIgnoreCase("q")) {
			exit = true;
		}
		return exit;
	}
	
    /*
     * Displays message to the console
     */
	private void message() {
		output.println("Insert the hostname of the server on which the GuessGame is running on port 5888, e.g. www.java.com");		
	}
}
