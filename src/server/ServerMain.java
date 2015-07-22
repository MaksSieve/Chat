package server;

import java.util.*;
import java.io.*;
import java.net.*;

public class ServerMain {

	public static String Version = "v.0.0.1";
	public static String line = 	"---------------------------------";
	public static String nameMsg = 	"     Java ChatServer " + Version + "      ";
	public static String welcomeMsg = "Welcome to Java ChatServer!";
	public static int defaultPort = 8000;
	
	@SuppressWarnings({ "resource", "unused" })
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
	
		/*
		 * Starting server
		 */
		sc = new Scanner(System.in);
		
		while(true){
			try{
				System.out.println("Input number of Server's port");
				port = sc.nextInt();
				//port = defaultPort;
				Listener = new ServerSocket(port);
				break;
			}catch(IOException e){
				System.out.println("I/O ERROR! Couldn't listen to port " + port + "! Try again.");
			}
		}
		
		
		sc.close();
		
		System.out.println(line);
		System.out.println("Server started at: " + Listener.getInetAddress());
		System.out.println("Local port: " + Listener.getLocalPort());
		System.out.println(line);
		
		
		/*
		 * Main block
		 */
		System.out.println("Waiting for users...");
		userList = new ArrayList<User>();
		while(true){
			try{
				User nU = new User(Listener.accept(), System.out, userList);
			}catch(IOException e){
				System.out.println("I/O ERROR! User didn't connect!");
			}
			Iterator <User> itr = userList.iterator();
			while(itr.hasNext()){
				User u = itr.next();
				if(!(u.trd.isAlive())){
					userList.remove(u);
				}
			}
		}
		//Listener.close();
	}

}
