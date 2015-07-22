package server;

import java.io.*;
import java.net.Socket;
import java.util.*;

public class User implements Runnable {
	
	private BufferedReader userIn = null, serverIn = null;
	private OutputStream userOut = null;
	private PrintStream serverOut = null;
	private ArrayList <User> userList = null;
	private String msg = "";
	private int name = 0;
	private Socket userSocket = null;
	private Thread trd = null;
		
	User(Socket uSocket, PrintStream sOut, int n, ArrayList <User> uList) throws IOException
	{
		this.name = n;
		this.userList = uList;
		this.serverOut = sOut;
		this.userSocket = uSocket;
		this.userIn = new BufferedReader(
			new InputStreamReader(userSocket.getInputStream())
		);
		this.userOut = userSocket.getOutputStream();
		
		sayToUser("Welcome!\n");
		
		//TODO Move to ServerMain
		trd = new Thread(this, "user "+name+" thread");
		trd.start();
	}
	
	synchronized private void sayToServer(String m)
	{
		serverOut.println(m);
		sayToUser("Meesage resieved!");
	}
	
	synchronized public void sayToUser(String m)
	{
		try {
			userOut.write((m + "\n").getBytes());
		} catch (IOException e) {
			
		}
	}
	
	private void listen() throws IOException
	{
		while (true) {
			msg = userIn.readLine();
			if (msg == "exit") break;
			sayToServer(name + " ::: " + msg);
			Iterator itr = userList.iterator();
			while (itr.hasNext())
			{
				User u = (User) itr.next();
				if(this != u){
					u.sayToUser( name + " :::" + msg);
				}
			}
		}
	}
	
	@Override
	public void run()
	{
		try {
			listen();
		} catch (IOException e) {
			
		}
	}
}
