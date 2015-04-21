
package rmi.model;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.*;
import java.util.*;

import rmi.view.View;

public class ChatServer extends UnicastRemoteObject implements ChatInterface {
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 6159674065408397806L;
	public String								name;
	public static List<ChatClient>	clients						= new ArrayList<ChatClient>();
	public View view;
	
	public ChatServer() throws RemoteException {
		super(0);
	}
	
	public String getName() throws RemoteException {
		return this.name;
	}
	
	public void send(String s) throws RemoteException {
		System.out.println(s);
	}
	
	@Override
	public void receive() throws RemoteException {
	}
	
	@Override
	public void setClient(ChatClient client) throws RemoteException {
		clients.add(client);
	}
	
	@Override
	public ChatClient getClient(int id) throws RemoteException {
		ChatClient ci;
		ci = clients.get(id);
		return ci;
	}

	public List<ChatClient> getClients() {
		return clients;
	}
	
	public static void main(String[] args) throws Exception {
		try {
			System.out.println("RMI server started");
			try { // special exception handler for registry creation
				LocateRegistry.createRegistry(1099);
				System.out.println("java RMI registry created.");
			}
			catch (RemoteException e) {
				// do nothing, error means registry already exists
				System.out.println("java RMI registry already exists.");
			}
			ChatServer server = new ChatServer();
			Naming.rebind("//localhost/RmiChat", server);
			System.out.println("[System] Chat Remote Object is ready:");
			
			Scanner s = new Scanner(System.in);
		}
		catch (Exception e) {
			System.out.println("[System] Server failed: " + e);
		}
	}
}
