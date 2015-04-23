package rmi.model;

import java.rmi.*;
import java.util.List;
 
public interface ChatInterface extends Remote {
	public String getName() throws RemoteException;
	public void send(Message msg) throws RemoteException;
	public List<Message> returnMessages() throws RemoteException;
	public void rmvPrintedMsgs() throws RemoteException;
	public List<ChatClient> returnClients() throws RemoteException;
	public void setClient(ChatClient client) throws RemoteException;
}