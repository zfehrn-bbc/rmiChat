package rmi.model;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

 
public interface ChatInterface extends Remote {
	
	public static List<User> clients = new ArrayList<User>();
	
	public String getName() throws RemoteException;
	public void send(Message msg) throws RemoteException;
	public List<Message> returnMessages() throws RemoteException;
	public void rmvPrintedMsgs() throws RemoteException;
	public List<User> returnClients() throws RemoteException;
	public void setClient(User client) throws RemoteException;
}