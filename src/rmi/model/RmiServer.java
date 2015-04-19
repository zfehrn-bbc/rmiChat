
package rmi.model;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

public class RmiServer extends UnicastRemoteObject implements RmiServerIntf {
	/**
	 * 
	 */
	public RmiServerIntf client = null;
	private static final long serialVersionUID = -6576223930486368371L;
	public static final String STARTMESSAGE = "\nWillkommen im Niggo RMI Chat!\n";
	public static final String ENDMESSAGE = "\nChat erfolgreich verlassen!\n";

	public RmiServer() throws RemoteException {
		super(); // required to avoid the 'rmic' step, see below
	}
	
	public RmiServerIntf getClient() {
		client = (RmiServerIntf) new RmiClient();
		return client;
	}

	public String getStartMessage() {
		return STARTMESSAGE;
	}

	public String getEndMessage() {
		return ENDMESSAGE;
	}

	public static void main(String args[]) throws Exception {
		System.out.println("RMI server started");
		try { // special exception handler for registry creation
			LocateRegistry.createRegistry(1099);
			System.out.println("java RMI registry created.");
		}
		catch (RemoteException e) {
			// do nothing, error means registry already exists
			System.out.println("java RMI registry already exists.");
		}
		// Instantiate RmiServer
		RmiServer obj = new RmiServer();
		// Bind this object instance to the name "RmiServer"
		Naming.bind("//localhost/RmiServer", obj);
		System.out.println("PeerServer bound in registry");
		Scanner s = new Scanner(System.in);
		while (true) {
			String msg = s.nextLine().trim();
			if(obj.getClient() != null) {
				RmiServerIntf client = obj.getClient();
				msg = "[" + System.getProperty("user.name") + "] " + msg;
				client.send(msg);
			}
		}
	}

	@Override
  public void send(String msg) throws RemoteException {
	  // TODO Auto-generated method stub
	  System.out.println(msg);
  }
}
