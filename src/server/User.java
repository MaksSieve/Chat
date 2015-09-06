package server;

import java.io.*;
import java.net.Socket;
import java.util.*;

public class User implements Runnable {
	
	@SuppressWarnings("unused")
	private BufferedReader userIn = null, serverIn = null;
	private OutputStream userOut = null;
	private PrintStream serverOut = null;
	private ArrayList <User> userList = null;
<<<<<<< HEAD
	private String msg = "";
	private int num = 0;
	public String nick = "";
=======
>>>>>>> origin/master
	private Socket userSocket = null;
	public Thread trd = null;
	public String name = null;
		
	User(Socket uSocket, PrintStream sOut,ArrayList <User> uList) throws IOException
	{
<<<<<<< HEAD
		
=======
>>>>>>> origin/master
		this.userList = uList;
		this.num = userList.indexOf(this);
		this.serverOut = sOut;
		this.userSocket = uSocket;
		this.userIn = new BufferedReader(
			new InputStreamReader(userSocket.getInputStream())
		);
		this.userOut = userSocket.getOutputStream();
<<<<<<< HEAD
		
		sayToUser("Welcome!");
		sayToUser("Enter your nickname:");
		this.nick = userIn.readLine();
		//TODO Move to ServerMain
		trd = new Thread(this, "user "+num+" thread");
=======
		trd = new Thread(this, "user thread");
>>>>>>> origin/master
		trd.start();
	}
	
	synchronized private void sayToServer(String m)
	{
		serverOut.println(m);
		//sayToUser("Meesage resieved!");
	}
	
	synchronized public void sayToUser(String m)
	{
		try {
			userOut.write((m + "\n").getBytes());
		} catch (IOException e) {
			sayToServer("I/O ERROR! Couldn't send message to user!");
		}
	}
	
	private int listen() throws IOException
	{
<<<<<<< HEAD
		while (true) {
			msg = userIn.readLine();
			if (msg == "exit") return 0;
			sayToServer(nick + " ::: " + msg);
			Iterator<User> itr = userList.iterator();
=======
		String msg = "";
		while ((msg = userIn.readLine()) != null) {
			if (msg.equalsIgnoreCase("exit")) break;
			sayToServer(name + " ::: " + msg);
			Iterator <User> itr = userList.iterator();
>>>>>>> origin/master
			while (itr.hasNext())
			{
				User u = itr.next();
				if(this != u){
<<<<<<< HEAD
					u.sayToUser(nick + " ::: " + msg);
=======
					u.sayToUser(name + " :::" + msg);
>>>>>>> origin/master
				}
			}
		}
	}
	 
	@Override
	public void run()
	{
		try {
<<<<<<< HEAD
			if (listen()==0){
				sayToServer("User #" + num + " disconnected.");
				userList.remove(this);
			};
			
		} catch (IOException e) {
			sayToServer("ERROR! I/O Exception. ");
=======
			sayToUser("Enter your nick:");
			name = userIn.readLine();
			userList.add(this);
			sayToUser(name + ", welcome in JavaChat");
			sayToServer("User <<" + name + ">> conected!");
			listen();
		} catch (IOException e) {
			sayToServer("I/O ERROR! User's connection error!");
>>>>>>> origin/master
		}
	}
}
