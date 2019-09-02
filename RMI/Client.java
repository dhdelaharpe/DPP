import java.rmi.*;
import java.util.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client
{
    public static void main(String[] args)
    {
	Scanner in = new Scanner(System.in); //used to take input
	System.out.println("Enter username to cont");
	String username = in.nextLine();//fetch name from user
	try{
	    Registry reg = LocateRegistry.getRegistry();
	    MessageService stub = (MessageService)reg.lookup("MessageService");
	    System.out.println("S - send\n F - fetch\n E-kill");
	    String inp = in.nextLine().toUpperCase();
	    while(true){
		 switch(inp)
		 {
		     case "S":
			System.out.println("Enter message to send");
			String msg = in.nextLine();
			stub.send(username,msg);
			 break;
	             case "F":
			System.out.println("Listing all");
			List<String> msgs = stub.fetch(username);//get the list
			for(int i=0;i<msgs.size();i++)
			{
			    System.out.println("#"+(i+1)+"\n"+msgs.get(i));
			}
			 break;
	             case "E":
			 System.exit(0);//die now
		     default:
			 System.out.println("Invalid");
		}//end switch
		System.out.println("Next?");
		inp=in.nextLine().toUpperCase();
	    }//end while
	}catch(Exception e){System.out.println("Client err:"+e.toString());}

    }//end main


}//end client
