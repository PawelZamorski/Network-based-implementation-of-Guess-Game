package ie.gmit.dip;

public class Runner {
	public static void main(String[] args) {
		int port = 5888;
		
		if(args.length > 0) {
			port = Integer.parseInt(args[0]);
		}
		
		GuessServerMultithread gs = new GuessServerMultithread(port);
		gs.run();
	}
}
