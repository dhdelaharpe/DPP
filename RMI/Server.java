import java.rmi.*;
import java.util.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
public class Server implements MessageService
{
	Hashtable<String,List<String>>msgs = new Hashtable<String,List<String>>();

	public Server()
	{}//empty DC - doesn't need to do anything

	public String send(String username, String msg){
	    List<String> msgsU = new ArrayList<String>();
	    if(msgs.containsKey(username))
	    {
	        msgsU=msgs.get(username);//fetchall current
		msgsU.add(msg); //add new ------should update hashtable -- test? 
	    }else
	    {//not current in table
		msgsU.add(msg);//add to list
		msgs.put(username,msgsU); //add to table
	    }//end else
	    System.out.println("Sent");
	    return "sent";
	}//end send

	public List<String> fetch(String username)
	{
	   return msgs.get(username); //simple just send all back
	}//end fetch

	public static void main(String[] args)
	{
	  try{
	    Server ms = new Server(); //create object
	    MessageService stub = (MessageService) UnicastRemoteObject.exportObject(ms,0); //0? 
	    Registry reg = LocateRegistry.getRegistry();
	    reg.bind("MessageService",stub);
	  }catch(Exception e){System.out.println(e.toString());}
	}//end main

}//end server
