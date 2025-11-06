package edu.seg2105.client.ui;
// This file contains material supporting section 3.7 of the textbook:
// "Object Oriented Software Engineering" and is issued under the open-source
// license found at www.lloseng.com 

import java.io.*;
import java.util.Scanner;

import edu.seg2105.client.backend.ChatClient;
import edu.seg2105.client.common.*;

/**
 * This class constructs the UI for a chat client.  It implements the
 * chat interface in order to activate the display() method.
 * Warning: Some of the code here is cloned in ServerConsole 
 *
 * @author Fran&ccedil;ois B&eacute;langer
 * @author Dr Timothy C. Lethbridge  
 * @author Dr Robert Lagani&egrave;re
 */
public class ClientConsole implements ChatIF 
{
  //Class variables *************************************************
  
  /**
   * The default port to connect on.
   */
  final public static int DEFAULT_PORT = 5555;
  
  //Instance variables **********************************************
  
  /**
   * The instance of the client that created this ConsoleChat.
   */
  ChatClient client;
  
  
  
  /**
   * Scanner to read from the console
   */
  Scanner fromConsole; 

  
  //Constructors ****************************************************

  /**
   * Constructs an instance of the ClientConsole UI.
   *
   * @param host The host to connect to.
   * @param port The port to connect on.
   */
  public ClientConsole(String loginId,String host, int port) 
  {
    try 
    {
      client= new ChatClient(loginId,host, port, this);
      
      
    } 
    catch(IOException exception) 
    {
      System.out.println("Error: Can't setup connection!" + " Terminating client.");
      System.exit(1);
    }
    
    // Create scanner object to read from console
    fromConsole = new Scanner(System.in); 
  }

  
  //Instance methods ************************************************
  
  /**
   * This method waits for input from the console.  Once it is 
   * received, it sends it to the client's message handler.
   */
  public void accept() 
  {

	  
	  
    try
    {

      String message;

      while (true) 
      {
    	 
        message = fromConsole.nextLine();
        String [] parts= message.split(" ");
        String command= parts[0];
        String argument=null;
        if(parts.length>1)
        	argument=parts[1];
        
        switch(command) {
        case "#quit":
        	client.closeConnection();
        	System.exit(0);
        	break;
        case "#logoff":
        	client.closeConnection();
        	break;
        case "#sethost":
        	if(client.isConnected()) {
        		System.out.println("Error! log off to set host. ");}
        	else if(argument!=null) {
        		client.setHost(argument);}
        	else 
        		System.out.println("No host provided. ");
        	break;
        case "#setport":
        	if(client.isConnected()) {
        		System.out.print("Error! log off to set port. ");
        	}
        	else if(argument!=null) {
        		client.setPort(Integer.parseInt(argument));
        	}
        	else
        		System.out.println("No port provided. ");
        	break;
        case "#login":
        	if(client.isConnected()) {
        		System.out.println("You are already logged in. ");
        	}
        	else 
        		client.openConnection();
        	break;
        case "gethost":
        	client.getHost();
        	break;
        case "getport":
        	client.getPort();
        	break;
        default:
            if (!message.startsWith("#")) {
                client.handleMessageFromClientUI(message);
            } else {
                System.out.println("Unrecognizable command. ");
            }
            break;


        }
      }
    } 
    catch (Exception ex) 
    {
      System.out.println
        ("Unexpected error while reading from console!");
    }
  }

  /**
   * This method overrides the method in the ChatIF interface.  It
   * displays a message onto the screen.
   *
   * @param message The string to be displayed.
   */
  public void display(String message) 
  {
    System.out.println("> " + message);
  }

  
  //Class methods ***************************************************
  
  /**
   * This method is responsible for the creation of the Client UI.
   *
   * @param args[0] The host to connect to.
   */
  public static void main(String[] args) 
  {
    String host = "";
    int port;
    String loginId;
    if(args.length<1) {
    	System.out.println("Error! LoginID is required! ");
    	return;}
    else
    	loginId=args[0];
    if(args.length>1)
    	host=args[1];
    else
    	host="localhost";
    if(args.length>2)
    	port=Integer.parseInt(args[2]);
    else
    	port=DEFAULT_PORT;
    ClientConsole chat= new ClientConsole(loginId, host, port);
    chat.accept();
    
    	


  }
}
//End of ConsoleChat class
