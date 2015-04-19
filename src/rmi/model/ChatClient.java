
package rmi.model;

import java.rmi.*;
import java.rmi.server.*;
import java.util.*;

import javax.swing.JFrame;

public class ChatClient {
	public static void main(String[] argv) {
		try {			
			ChatInterface client = new Chat(System.getProperty("user.name"));
			ChatInterface server = (ChatInterface) Naming
			    .lookup("rmi://localhost/ABC");
			String msg = "[" + client.getName() + "] got connected";
			server.send(msg);
			System.out.println("[System] Chat Remote Object is ready:");
			server.setClient(client);
			Scanner s = new  Scanner(System.in);
			while (true) {
				msg = s.nextLine().trim();
				msg = "[" + client.getName() + "] " + msg;
				server.send(msg);
			}
		}
		catch (Exception e) {
			System.out.println("[System] Server failed: " + e);
		}
	}
}
