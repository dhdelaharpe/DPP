import java.rmi.*;
import java.util.*;
public interface MessageService extends Remote
{
	String send(String username, String Message) throws RemoteException;
	List<String> fetch(String user) throws RemoteException;
}
