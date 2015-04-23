package rmi.model;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import rmi.view.View;
import rmi.view.View2;

public class ChatClient implements Serializable {
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 6253087201489232951L;
	private ChatInterface			server;
	private static View2			view							= new View2();
	private static String			msgname;
	private Message msg;
	
	private static ChatClient instance = null;
	
	private ChatClient(String name) {
		this.setMsgname(name);
	}
	
	private ChatClient() {
		
	}
	
	public static synchronized ChatClient getInstance() {
		if(instance == null) {
			instance = new ChatClient();
		}
		return instance;
	}
	
	public void send(Message msg) throws RemoteException {
		try {
			getServer().send(msg);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static ChatInterface getServer() throws MalformedURLException,
			RemoteException {
		ChatInterface server = null;
		try {
			server = (ChatInterface) Naming.lookup("//192.168.3.151/RmiChat");
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return server;
	}
	
	public static void main(String[] args) throws Exception {
		try {
			ChatClient client = new ChatClient(
					JOptionPane.showInputDialog(getView(), "Gib deinen Chatnamen ein!", "Willkommen!", JOptionPane.INFORMATION_MESSAGE));
			ChatInterface server = ChatClient.getServer();
			User user = new User(getMsgname());
			server.setClient(client);
			
			JFrame frame = new JFrame("Niggos RMI Chat");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.getContentPane().add(getView());
			frame.pack();
			frame.setVisible(true);
			frame.setResizable(false);
			
			getView().append("Erfolgreich Verbunden!\n");
			getView().append("Willkommen im Niggo RMI Chat\n -----------------------------------------\n");
			getView().repaint();
			
			getView().jcomp1.requestFocusInWindow();
			
			Message msg = new Message("[" + getMsgname() + "] ", "got connected\n");
			server.send(msg);
			getView().repaint();
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(getView(),
					"[System] Server konnte nicht erreicht werden!", "Fehler",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
	}

	public static View2 getView() {
		return view;
	}

	public static void setView(View2 view) {
		ChatClient.view = view;
	}

	public static String getMsgname() {
		return msgname;
	}

	public void setMsgname(String name) {
		this.msgname = name;
	}
}
