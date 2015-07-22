package server;

import java.util.*;
import java.io.*;
import java.net.*;

public class ServerMain {

	public static String Version = "v.0.0.1";
	public static String line = 	"---------------------------------";
	public static String nameMsg = 	"     Java ChatServer " + Version + "      ";
	public static String welcomeMsg = "Welcome to Java ChatServer!";
	public static int p = 8000;
	
	public static void main(String[] args)throws IOException {
		
		ArrayList <User> userList = null;
		//User[] userList;
		Scanner sc = null;
		ServerSocket Listener = null;
		int port = 0 ;
		int n = 0;
		
		/*
		 * welcome message
		 */
		
		System.out.println(line);
		System.out.println(nameMsg);
		System.out.println(line);
		System.out.println(welcomeMsg);
		System.out.println("Server started");
		
		sc = new Scanner(System.in);
		
		try{
			while(true){
				System.out.println("Input number of Server's port");
				//port = sc.nextInt();
				port = p;
				Listener = new ServerSocket(port);
				System.out.print(Listener.getInetAddress());
				break;
			}
		}catch(IOException e){
			System.out.println("Couldn't listen to port " + port + "! Try again.");
		}
		
		sc.close();
		
		System.out.println("");
		
		userList = new ArrayList<User>();
		while(true){
			User nU = new User(Listener.accept(), System.out, ++n, userList);
			
			userList.add(nU);
			System.out.println("New user connected!");
		}
	}

}
