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
	public String										name;
	public static List<Message>						msgs		= new ArrayList<Message>();
	
	public ChatServer() throws RemoteException {
		super(0);
	}
	
	public String getName() throws RemoteException {
		return this.name;
	}
	
	@Override
	public List<Message> returnMessages() throws RemoteException {
		return this.msgs;
	}
	
	@Override
	public void rmvPrintedMsgs() throws RemoteException {
		List<Message> msgarray = this.returnMessages();
		try {
			
			// Mit sleep(int) kann ein Cooldown eingestellt werden. Für besonders Nervige Chatter :)
			Thread.sleep(1000);
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		msgarray.clear();
		this.msgs = msgarray;
	}
	
	@Override
	public void send(Message msg) throws RemoteException {
		msgs.add(msg);
		// Wird vom Server ausgegeben
		System.out.println(msg.getName() + msg.getMsg());
	}
	
	public static void sendServerMessage(Message msg) throws RemoteException {
		msgs.add(msg);
		System.out.println(msg.getName() + msg.getMsg());
	}
	
	public static void main(String[] args) throws Exception {
		try {
			System.out.println("RMI server started");
			try { // special exception handler for registry creation
				LocateRegistry.createRegistry(1099);
				System.out.println("java RMI registry created.");
			} catch (RemoteException e) {
				// do nothing, error means registry already exists
				System.out.println("java RMI registry already exists.");				
			}
			ChatServer server = new ChatServer();
			Naming.rebind("rmi://localhost/RmiChat", server);
			System.out.println("[System] Chat Remote Object is ready:");
			
			Scanner s = new Scanner(System.in);
			while (true) {
				Message msg = new Message("[System] " , s.nextLine().trim() + "\n");
				sendServerMessage(msg);
			}
		} catch (Exception e) {
			System.out.println("[System] Server failed: " + e);
		}
	}
}
