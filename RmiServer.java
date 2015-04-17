package rmi;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.RemoteObject;
import java.rmi.server.UnicastRemoteObject;
import java.rmi.registry.*;

public class RmiServer extends UnicastRemoteObject implements RmiServerIntf {
	/**
	 * 
	 */
	private static final long	serialVersionUID	= -6576223930486368371L;
	public static final String	MESSAGE	= "Willkommen im Niggo RMI Chat\n"
			+ "---------------------------------------------";
	
	public RmiServer() throws RemoteException {
		super(); // required to avoid the 'rmic' step, see below
	}
	
	public String getMessage() {
		return MESSAGE;
	}
	
	public void setMessage(String string) {
		System.out.println(string);
	}
	
	public static void main(String args[]) throws Exception {
		System.out.println("RMI server started");
		
		try { // special exception handler for registry creation
			LocateRegistry.createRegistry(1099);
			System.out.println("java RMI registry created.");
		} catch (RemoteException e) {
			// do nothing, error means registry already exists
			System.out.println("java RMI registry already exists.");
		}
		
		// Instantiate RmiServer
		RmiServer obj = new RmiServer();
		
		// Bind this object instance to the name "RmiServer"
		Naming.bind("//localhost/RmiServer", obj);
		System.out.println("PeerServer bound in registry");
	}
}