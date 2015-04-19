
package rmi.model;

import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.*;
import java.util.*;

public class ChatServer {
	public static void main(String[] argv) {
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
			Scanner s = new Scanner(System.in);
			System.out.println("Enter Your name and press Enter:");
			String name = s.nextLine().trim();
			Chat server = new Chat(name);
			Naming.rebind("rmi://localhost/ABC", server);
			System.out.println("[System] Chat Remote Object is ready:");
			while (true) {
				String msg = s.nextLine().trim();
				if (server.getClient() != null) {
					ChatInterface client = server.getClient();
					msg = "[" + server.getName() + "] " + msg;
					client.send(msg);
				}
			}
		}
		catch (Exception e) {
			System.out.println("[System] Server failed: " + e);
		}
	}
}
