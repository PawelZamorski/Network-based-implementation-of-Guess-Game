
########## GUESS GAME - GUESSCLIENT AND GUESSSERVER  ############
version 1.1

### OVERWIEV ###

The client server program to play Guess Game.

### AUTHOR ###
Pawel Zamorski, G00364553@gmit.ie

### INSTALL ###

To run the program go to the directory containing guessServer.jar and guessClient.jar files and use the following command:

	java -cp .\guessServer.jar ie.gmit.dip.Runner
	java -cp .\guessClient.jar ie.gmit.dip.Runner

Run programs in a separate command prompt.

GuessServer by default listens on port 5888. To change it insert the port number before starting program, e.g.:

	java -cp .\guessServer.jar ie.gmit.dip.Runner 5000

GuessClient by default connects to the server on localhost on port 5888. To change it insert both parameters before starting program, e.g.:

	java -cp .\guessServer.jar ie.gmit.dip.Runner www.gmit.ie 5000

It is possible to change the server hostname after starting the guessClient. There is a menu that enables it. 

### GETTING STARTED ###

Once the guessClient is lunched the menu is displayed to the console. 

There is an option to change the server hostname.

The program makes few steps before connection to the server. At first it checks if the hostname is known. Next it checks if the server is listening on the given port. If both are correct, client connects to the server. The game starts.

The input validation is done on both sides. On the clients side it prevents the user from sending an empty string. On the server side it checks if the input is valid.

The GuessServerMultithread uses ExecutorService. However, the ExecutorService pool is set up to 2 in order to test the behaviour of it (no solution is presented for client waiting for a busy server).

The server asks user to enter the name. It could be used to login to the server, to store user data (name, score) and be used in case of reconnection (not implemented in the project).

The detailed description is done in comments.
