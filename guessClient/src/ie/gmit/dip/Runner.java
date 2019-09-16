package ie.gmit.dip;

/**
 * @author  Pawel Zamorski
 * @version 1.0
 * @since 1.8
 * 
 * The <code>Runner</code> class contains main method.
 * 
 */
public class Runner {
	
	public static void main(String[] args) {
		String host = "localhost";
		int port = 5888;
				
		if(args.length > 0) {
			host = args[0];
			port = Integer.parseInt(args[1]);
		}
	
		ConsoleMenu cm = new ConsoleMenu(host, port);
		cm.start();
	}
}
