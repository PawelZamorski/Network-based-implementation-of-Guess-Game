package ie.gmit.dip;

import java.io.*;
import java.net.*;
// Use DI>> import java.util.Scanner;


/**
 * @author  Pawel Zamorski
 * @version 1.0
 * @since 1.8
 * 
 * The <code>GuessClient</code> class provides a client-side of the 'GuessGame'.
 * <p>
 * The constructor enables to set up host name, port and StringValidator for validation User input from the console.
 * <p>
 * To start the GuessClient use <code>run</code> method.
 * <p>
 * The GuessClient class provides addition method that enables setting up (changing) the host name, 
 * checking a host name correctness, checking if server is running on a given host.
 */
public class GuessClient {
	private String host;
	private int port;
	private InputValidator userInput; // Use to validate input from the user

// (ver1)	private BufferedReader userInput;	
	
	/**
	 * Constructor for GuessClient class.
	 * 
	 * @param host String the host name on which the GuessGame server is run (e.g. www.java.com)
	 * @param port int the port on which the server listens to
	 * @param userInput InputValidator for validating an input from the User
	 */
	public GuessClient(String host, int port, InputValidator userInput) { // Use Dependency Injection
		this.host = host;
		this.port = port;
		this.userInput = userInput;
	}
	
	public void run() {
		try( // try-with-resource; no need to use close() method.
			//The Java runtime closes these resources in reverse order that they were created.
				Socket socket = new Socket(host, port);
				// input - data from the socket
				BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				// output - data to the socket
				PrintWriter output = new PrintWriter(socket.getOutputStream(), true); // Flush stream: set up true in PrintWriter constructor 
				) {

// (ver1)			userInput = new BufferedReader(new InputStreamReader(System.in));
// use Dependency Injection >> 	InputValidator userInput = new StringValidatorImpl(new Scanner(System.in));
			
			
			// String to store user input stream (message from the user)
			String userMessage = null;
			// String to store input stream from the socket (message send by server to the client)
			String serverMessage = null; 

			// Step 1: Check if connection is established. Uses 'echo' service
			String echo = "echo"; // Value to check a connection
			output.println(echo);
			serverMessage = input.readLine();
			if(serverMessage.equals(echo)) {
				System.out.println("Connection established."); // Control info
				System.out.println("Launching Guess Game ..."); // Control info
				output.println("Start Guess Game"); // Start game
				System.out.println(input.readLine()); // Read 'welcome' message from the server
			}

			// Step 2: Run loop. The protocol to quit the loop (and program) is 'quit' -> the response is 'Bye bye!'
			while(true) {
				// Read and validate user input from the console. Do not allow empty or null String
				userMessage = userInput.validateStringNotEmpty();
				// output data to the socket
				output.println(userMessage);
				// Read data from socket input and display it in the console
				serverMessage = input.readLine();
				System.out.println(serverMessage);
				// Break the loop.
				if(serverMessage.equals("Bye bye!")) break;
			}

			userInput.close(); // Close StringValidatorImpl (it uses Scanner object -> close Scanner)
			System.out.println("Thank you for playing GuessGame :)");
		}catch(UnknownHostException e) {
			System.out.println("Unknown host.");
			System.err.println(e);
		}catch(IOException e) {
			System.err.println(e);
		}
	}
		
	/**
	 * Sets new host name on which server is running
	 * 
	 * @param host hostname
	 */
	public void setHost(String host) {
		this.host = host;
	}
	
	/**
	 * Returns true if a hostname passed as a parameter is correct
	 * 
	 * @param host hostname 
	 * @return true if a host is correct
	 */
	public boolean isHostnameCorrect(String host) {
		try {
			InetAddress.getByName(host);
			return true;
		} catch (UnknownHostException e) {
			System.out.println("Unknown host: " + host);
			return false;
		}
	}
	
	/*
	 * Returns true is the server 'GuessGame' is running on a host past as a parameter
	 * <p>
	 * Server listens on DEFAULT_PORT
	 * 
	 * @param host on which 'GesssGame' is running and listens on DEFAULT_PORT
	 * @return true if the server 'GuessGame' is running on a host and listens on DEFAULT_PORT
	 */
	public boolean isServerRunning(String host) {
		try (
				Socket socket = new Socket(host, port);
				BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				PrintWriter output = new PrintWriter(socket.getOutputStream(), true); // Flush stream: set up true in PrintWriter constructor 
				){
			// Check if the connection is established and there is a communication:send a packet to the server and get the input from the socket 
			String ping = "ping"; // Use the same value on both sides (on a client and server)
			output.println(ping);
			String serverMessage = input.readLine();			
			if(serverMessage != null) {
				System.out.println("Test if the server is running on " + host + " on port " + port + 
						". Message received form the server is '" + serverMessage + "' - server is running.");
				return true;
			}else {
				System.out.println("No message received. Server is not running on " + host + " on port " + port);				
				return false;
			}
		} catch (UnknownHostException e) {
			System.out.println("Unknown host: " + host);
			return false;
		} catch (IOException e) {
			System.out.println("IO EX from isServerRunning: Server is not running on " + host + " on port " + port);
			return false;
		}
	}
}
