package client;

import java.io.*;

public class ServerToUserThread implements Runnable {
	
	public Thread trd = null;
	BufferedReader server = null;
	PrintStream user = null;
	String message = null;
	int code = 0;
	
	ServerToUserThread(InputStream server, PrintStream user){
		this.server = new BufferedReader(new InputStreamReader(server));
		this.user = user;
		trd = new Thread(this, "ServerToUser");
		trd.start();
	}
	
	private int listen() throws IOException{
		while (true){
			message = server.readLine();
			if (message == "exit") break;
			user.println(message);;
		}
		return 1;
	}
	
	@Override
	public void run() {
		try {
			code = listen();
		} catch (IOException e) {
			code = 0;
		}
	}

}
