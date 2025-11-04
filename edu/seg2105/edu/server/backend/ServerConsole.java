package edu.seg2105.edu.server.backend;

import edu.seg2105.client.common.ChatIF;
import java.util.*;

public class ServerConsole implements ChatIF {
	private EchoServer server;
	Scanner scan;
	public ServerConsole(EchoServer server) {
		this.server=server;
		scan=new Scanner(System.in);
	}
	@Override
	public void display(String message) {
		// TODO Auto-generated method stub
		System.out.println(message);

	}
	private void accept() {
		Scanner scan=new Scanner(System.in);
		String message;
		try {
			while(true) {
				message= scan.nextLine();
				display("SERVER MSG> " +message);
				server.sendToAllClients("SERVER MSG> "+message);
				
			}
			
		}
		catch(Exception ex) {
			System.out.println("Unexpected error while sending to console ");

		}
	}
	

}
