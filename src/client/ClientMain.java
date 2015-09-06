package client;

import java.net.*;
import java.util.Scanner;
import java.io.*;

public class ClientMain {
	
	public static String Version = "v.0.0.1";
	public static String line = 	"---------------------------------";
	public static String nameMsg = 	"     Java ChatClient " + Version + "      ";
	public static int defaultPort = 8000;
	public static String defaultServer = "localhost";
	
<<<<<<< HEAD
	@SuppressWarnings({ "unused", "resource" })
=======
	@SuppressWarnings("unused")
>>>>>>> origin/master
	public static void main(String[] args) {
		
		Socket serverSocket = null;
		int serverPort = 0;
		InetAddress serverAddr = null;
		Scanner sc = null;
		BufferedReader serverIn = null, userIn = null;
		PrintStream userOut = null;
		OutputStream serverOut = null;
		
		ServerToUserThread STU = null;
		UserToServerThread UTS = null;
		
		/*
		 * welcome message
		 */
		System.out.println(line);
		System.out.println(nameMsg);
		System.out.println(line);
		
		/*
		 * connecting
		 */
		
		sc = new Scanner(System.in);
		while (true){
			try{
				System.out.println("Input server's ip");
				serverAddr = InetAddress.getByName(sc.nextLine());
				//serverAddr = InetAddress.getByName(defaultServer);
				break;
			}catch(UnknownHostException e){
				System.out.println("ERROR! Unknown server's ip.");
			}
		}
		
		while (true){
			try{
				System.out.println("Input server's port");
				serverPort = sc.nextInt();
				//serverPort = defaultPort;
				serverSocket = new Socket(serverAddr, serverPort);
				break;
			}catch(IOException e){
				System.out.println("ERROR! Couldn't connect to this port");
			}
		}
		
			
		try {
			serverIn = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
			userIn = new BufferedReader(new InputStreamReader(System.in));
			serverOut = serverSocket.getOutputStream();
			userOut = System.out;
		} catch (IOException e) {
			System.out.println("I/O ERROR");
		}
		
		/*
		 * writing to server		
		 */
		
		String userMsg = null, serverMsg = null;
		System.out.println("Connected.");
		System.out.println(line);
		System.out.print("");
		//sc = new Scanner(System.in);
		/*
		 * Non-threads communication
		try{
			while(true) {
				userMsg = sc.nextLine() + "\n";
				serverOut.write(userMsg.getBytes());
				serverMsg = serverIn.readLine();
				userOut.println(serverMsg);
			}
				
		} catch (IOException e) {
			System.out.println("I/O ERROR!");
		}
			
			//userOut.println(serverMsg);
			
		*/
		 try{
			STU = new ServerToUserThread(serverSocket.getInputStream(), System.out);
			UTS = new UserToServerThread(serverSocket.getOutputStream(), System.in);
		}catch(IOException e){
			
		}
		try{
			STU.trd.join();
			UTS.trd.join();
		}catch(InterruptedException e){
			
		}
		sc.close();
		userOut.println("Connection closed");
	}

}
