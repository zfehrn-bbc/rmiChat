package rmi.model;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import rmi.view.View;

public class ChatClient implements Serializable {
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 6253087201489232951L;
	private static ChatClient sgltclient;
	private ChatInterface			server;
	public static View				view							= new View();
	private String						name;
	private final String			msgname						= "["
																									+ System
																											.getProperty("user.name")
																									+ "] ";
	
	public ChatClient(String name) {
		this.name = name;
	}
	
	private ChatClient() {
	}
	
	public static synchronized ChatClient getInstance() {
		client = new ChatClient();
		return client;
	}
	
	public void send(String msg) throws RemoteException {
		View.append(msgname + msg);
		try {
			ChatClient.getServer().send(msg);
		} catch (MalformedURLException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static ChatInterface getServer() throws MalformedURLException,
			RemoteException, NotBoundException {
		ChatInterface server = (ChatInterface) Naming.lookup("//localhost/RmiChat");
		return server;
	}
	
	public static void main(String[] args) throws Exception {
		try {
			ChatClient client = new ChatClient();
			ChatInterface server = ChatClient.getServer();
			server.setClient(client);
			
			JFrame frame = new JFrame("Niggos RMI Chat");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.getContentPane().add(view);
			frame.pack();
			frame.setVisible(true);
			frame.setResizable(false);
			
			String msg = "[" + System.getProperty("user.name") + "] got connected";
			server.send(msg);
			View.append("Erfolgreich Verbunden!\n");
			View.append("Willkommen im Niggo RMI Chat\n -----------------------------------------\n");
			View.append(msg);
			frame.repaint();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(view,
					"[System] Server konnte nicht erreicht werden!", "Fehler",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}
}
