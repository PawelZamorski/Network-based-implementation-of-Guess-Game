package ie.gmit.dip;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author  Pawel Zamorski
 * @version 1.0
 * @since 1.8
 * 
 * The <code>GuessServer</code> class provides a server-side of the 'GuessGame'.
 * <p>
 * The constructor enables to set up a port.
 * <p>
 * To start the GuessServer use <code>run</code> method.
 */
public class GuessServerMultithread {
	private int port; // Port on which the server listens to.
//	private BufferedReader input; // Input from the socket
//	private PrintWriter output; // Output to the socket
	private ExecutorService executorService;
	
	/**
	 * Constructor for GuessServer class.
	 * 
	 * @param port int port on which the servers listens to.
	 */
	public GuessServerMultithread(int port) {
		this.port = port;
	}
	
	public void run() {
		try( // try-with-resource
				ServerSocket serverSocket = new ServerSocket(port); // Initialize ServerSocet
				) 
		{
			
			System.out.println("Server is running."); // Control message displayed to the console
			// Create ExecutorService for managing multithreading
			ExecutorService executorService = Executors.newFixedThreadPool(2);
			while(true) {
				 // Accept connection (returns Socket) and start the thread
				Runnable thread = new ServerThread(serverSocket.accept());
				executorService.execute(thread);
			}
		}catch(IOException e) {
			System.err.println("Could not listen on port" + port + ". Port is not available.");
		}finally {
			// Shut down executorService and cancel any lingering tasks
			if(executorService != null) {
				executorService.shutdown(); // Disable new tasks from being submitted
				try {
				    if (!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
				        executorService.shutdownNow(); // Cancel currently executing tasks
				    }
				} catch (InterruptedException iEx) {
					// (Re-)Cancel if current thread also interrupted
				     executorService.shutdownNow();
				     // Preserve interrupt status
				     Thread.currentThread().interrupt();
				}
			}
		}
	}
	
	/*
	 * 
	 */
	private class ServerThread implements Runnable {
		private Socket socket;
		
		public ServerThread(Socket socket) {
			this.socket = socket;
		}
		
		public void run() {
			try( // try-with-resource
					// input stream from the socket
					BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					// output stream to the socket
					PrintWriter output = new PrintWriter(socket.getOutputStream(), true); // Flush data from the buffer: true in PrintWriter constructor
					)
			{
				// Control message displayed to the console
				System.out.println("Connection established between server is running on" + socket.getLocalAddress() + " on port " + 
			socket.getLocalPort() + " and is connected to client" + socket.getInetAddress() + " on port " + socket.getPort());

				// CHANGE OR REMOVE
				String line;
				// Echo server CHANGE TO PING SERVER IF(LINE.EQUALS"PING")
				while(true) {
					line = input.readLine();
					// Check connection between client and server. When server receives a 'ping', it closes resources and returns
					// 'ping' is used in isServerRunning(String host) method of GuessClient class
					if(line != null && line.equals("ping")) {
						output.println(line);
						// Close resources
						output.close();
						input.close();
						socket.close();
						return;
					}
					
					// Start Guess Game after receiving 'Start Guess Game' message from the client
					if(line != null && line.equals("Start Guess Game")) {						
						break;
					}
					output.println(line);
				}

				// Start GuessGame (Could be moved to the while loop -> 'Start Guess Game' condition to allow further communication after the game)
				// Initialize Scanner to use in GuessGame and IntValidatorImpl constructor
				Scanner scanner = new Scanner(input);
				GuessGame gg = new GuessGame(new InputValidatorImpl(scanner, output), output); // No need to close Scanner as it is not using System.in
				gg.run(); // run GuessGame
			}catch (IOException e) {
				System.err.println(e);				
			}finally {
			    if (socket != null) {
			        System.out.println("Closing Socket");
			        try {
						socket.close();
					} catch (IOException e) {
						System.err.println("Exception when closing a socket" + e);
					}
			    }
			}
		}
	}
}
