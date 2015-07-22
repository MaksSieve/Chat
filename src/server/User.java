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
	private Socket userSocket = null;
	public Thread trd = null;
	public String name = null;
		
	User(Socket uSocket, PrintStream sOut,ArrayList <User> uList) throws IOException
	{
		this.userList = uList;
		this.serverOut = sOut;
		this.userSocket = uSocket;
		this.userIn = new BufferedReader(
			new InputStreamReader(userSocket.getInputStream())
		);
		this.userOut = userSocket.getOutputStream();
		trd = new Thread(this, "user thread");
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
	
	private void listen() throws IOException
	{
		String msg = "";
		while ((msg = userIn.readLine()) != null) {
			if (msg.equalsIgnoreCase("exit")) break;
			sayToServer(name + " ::: " + msg);
			Iterator <User> itr = userList.iterator();
			while (itr.hasNext())
			{
				User u = itr.next();
				if(this != u){
					u.sayToUser(name + " :::" + msg);
				}
			}
		}
	}
	 
	@Override
	public void run()
	{
		try {
			sayToUser("Enter your nick:");
			name = userIn.readLine();
			userList.add(this);
			sayToUser(name + ", welcome in JavaChat");
			sayToServer("User <<" + name + ">> conected!");
			listen();
		} catch (IOException e) {
			sayToServer("I/O ERROR! User's connection error!");
		}
	}
}
