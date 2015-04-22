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
	private ChatInterface			server;
	private static View				view							= new View();
	private static String			msgname = null;
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
		} catch (MalformedURLException | NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static ChatInterface getServer() throws MalformedURLException,
			RemoteException, NotBoundException {
		ChatInterface server = (ChatInterface) Naming.lookup("//192.168.3.151/RmiChat");
		return server;
	}
	
	public static void main(String[] args) throws Exception {
		try {
			
			
			ChatClient client = new ChatClient(
					JOptionPane.showInputDialog(getView(), "Gib deinen Chatnamen ein!", "Willkommen!", JOptionPane.INFORMATION_MESSAGE));
			ChatInterface server = ChatClient.getServer();
			
			JFrame frame = new JFrame("Niggos RMI Chat");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.getContentPane().add(getView());
			frame.pack();
			frame.setVisible(true);
			frame.setResizable(false);
			
			getView().append("Erfolgreich Verbunden!\n");
			getView().append("\u001B" + "Willkommen im Niggo RMI Chat\n -----------------------------------------\n");
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

	public static View getView() {
		return view;
	}

	public static void setView(View view) {
		ChatClient.view = view;
	}

	public static String getMsgname() {
		return msgname;
	}

	public void setMsgname(String msgname) {
		this.msgname = msgname;
	}
}
