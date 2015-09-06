package client;

import java.io.*;
import java.util.*;


public class UserToServerThread implements Runnable {
	
	public Thread trd = null;
	Scanner user = null;
	OutputStream server = null;
	String message = null;
	int code = 0;
	
	UserToServerThread(OutputStream server, InputStream user){
		this.user = new Scanner(user);
		this.server = server;
		trd = new Thread(this, "UserToServer");
		trd.start();
	}
	
	synchronized private int listen() throws IOException{
		message = user.nextLine();
		while (message != "exit"){
			server.write((message+"\n").getBytes());
			message = user.nextLine();
		}
		return 1;
	}
	
	@Override
	public void run() {
		try {
			code = listen();
		} catch (IOException e) {
			System.out.println("I/O ERROR");
		}
	}

}
