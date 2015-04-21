package rmi.model;

import java.rmi.*;
 
public interface ChatInterface extends Remote {
	public String getName() throws RemoteException;
	public void send(String msg) throws RemoteException;
	public void setClient(ChatClient client) throws RemoteException;
	public ChatClient getClient(int id) throws RemoteException;
	public void receive() throws RemoteException;
}