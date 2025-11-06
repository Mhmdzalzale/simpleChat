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
	public void accept() {
	    String message;
	    try {
	        while (true) {
	            message = scan.nextLine();
	            String[] parts = message.split(" ");
	            String command = parts[0];
	            String argument = (parts.length > 1) ? parts[1] : null;

	            if (command.startsWith("#")) {
	                switch (command) {
	                    case "#quit":
	                        System.exit(0);
	                        break;

	                    case "#stop":
	                        server.stopListening();
	                        System.out.println("Server has stopped listening for connections.");
	                        break;

	                    case "#close":
	                        try {
	                            server.close();
	                            System.out.println("Server closed and all clients disconnected.");
	                        } catch (Exception e) {
	                            System.out.println("Error closing the server.");
	                        }
	                        break;

	                    case "#setport":
	                        if (!server.isListening()) {
	                            if (argument != null) {
	                                try {
	                                    int newPort = Integer.parseInt(argument);
	                                    server.setPort(newPort);
	                                    System.out.println("Port set to " + newPort);
	                                } catch (NumberFormatException e) {
	                                    System.out.println("Invalid port number.");
	                                }
	                            } else {
	                                System.out.println("No port number provided.");
	                            }
	                        } else {
	                            System.out.println("Error: Close the server before changing the port.");
	                        }
	                        break;

	                    case "#start":
	                        if (server.isListening()) {
	                            System.out.println("Server is already running.");
	                        } else {
	                            try {
	                                server.listen();
	                                System.out.println("Server started listening for connections.");
	                            } catch (Exception e) {
	                                System.out.println("Error starting the server.");
	                            }
	                        }
	                        break;

	                    case "#getport":
	                        System.out.println("Port: " + server.getPort());
	                        break;

	                    default:
	                        System.out.println("Invalid command.");
	                        break;
	                }
	            } else {
	                System.out.println("SERVER MSG> " + message);
	                server.sendToAllClients("SERVER MSG> " + message);
	            }
	        }
	    } catch (Exception ex) {
	        System.out.println("Unexpected error while reading from console!");
	    }
	}

	public static void main(String[] args) {
	    int port = EchoServer.DEFAULT_PORT;
	    if (args.length>0) {
	        try {
	            port = Integer.parseInt(args[0]);
	        } catch (NumberFormatException e) {
	            System.out.println("Invalid port number. Using default (5555).");
	        }
	    }

	    EchoServer server = new EchoServer(port);
	    ServerConsole console = new ServerConsole(server);

	    try {
	        server.listen();
	    } catch (Exception e) {
	        System.out.println("ERROR! Could not listen for clients!");
	    }

	    console.accept();
	}

	

}
