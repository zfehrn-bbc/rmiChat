package rmi.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

import rmi.model.ChatClient;
import rmi.model.Message;
import rmi.model.User;

public class View2 extends JPanel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1897354102628757158L;
	public static JTextPane scrollPane;
	public static JScrollPane sp;
	private JMenuBar menu;
	private JLabel jcomp3;
	public JTextField jcomp1;
	private JButton send;
	private JLabel srvmsg;
	private JComboBox smileys;
	private static ChatClient CLIENT = null;
	private View2 view = this;
	public JScrollPane chatters;
	public JPanel users = new JPanel();

	public View2() {
		// construct preComponents
		JMenu fileMenu = new JMenu("File");
		JMenuItem exitItem = new JMenuItem("Exit");
		fileMenu.add(exitItem);
		JMenu chatMenu = new JMenu("Chat");
		JMenuItem connectItem = new JMenuItem("Connect");
		chatMenu.add(connectItem);
		JMenuItem disconnectItem = new JMenuItem("Disconnect");
		chatMenu.add(disconnectItem);
		JMenu helpMenu = new JMenu("Help");
		JMenuItem helpItem = new JMenuItem("Help");
		helpMenu.add(helpItem);
		JMenuItem versionItem = new JMenuItem("Version");
		helpMenu.add(versionItem);
		JMenuItem aboutItem = new JMenuItem("About");
		helpMenu.add(aboutItem);
		String[] smileysItems = { ": )", ": P", ": (", "; )", ": *", "xD", "8====D" };
		menu = new JMenuBar();
		menu.add(fileMenu);
		menu.add(chatMenu);
		menu.add(helpMenu);
		chatters = new JScrollPane(users);
		chatters.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		users.setLayout(new FlowLayout(FlowLayout.LEADING));
		users.add(new JLabel("Chatters Online: "));
		// Jscrollpane für Messages
		scrollPane = new JTextPane();
		scrollPane.setEditable(false);
		// make scrollPane scrollable
		sp = new JScrollPane(scrollPane);
		sp.getVerticalScrollBar().addAdjustmentListener(new AdjustmentListener() {
			@Override
			public void adjustmentValueChanged(AdjustmentEvent e) {
				e.getAdjustable().setValue(e.getAdjustable().getMaximum());
			}
		});
		jcomp3 = new JLabel("newLabel");
		jcomp1 = new JTextField(5);
		send = new JButton("SEND");
		srvmsg = new JLabel("Servermsgs");
		smileys = new JComboBox(smileysItems);
		// set components properties
		jcomp1.setToolTipText("Press Enter to send");
		send.setToolTipText("Send your message");
		// adjust size and set layout
		setPreferredSize(new Dimension(627, 485));
		setLayout(null);
		// Allow to send with enter
		jcomp1.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					e.consume();
					try {
						CLIENT.getInstance().send(new Message("[" + CLIENT.getInstance().getMsgname() + "] ", jcomp1.getText() + "\n"));
						jcomp1.setText("");
					} catch (RemoteException e1) {
						// TODO Auto-generated catch block
						JOptionPane.showMessageDialog(view, "[System] Nachricht konnte nicht gesendet werden!", "Fehler", JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					}
				}
			}

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
			}
		});
		// Action Listener send button
		send.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				try {
					CLIENT.getInstance().send(new Message("[" + CLIENT.getInstance().getMsgname() + "] ", jcomp1.getText() + "\n"));
				} catch (RemoteException e1) {
					// TODO Auto-generated catch block
					JOptionPane.showMessageDialog(view, "[System] Nachricht konnte nicht gesendet werden!", "Fehler", JOptionPane.ERROR_MESSAGE);
					e1.printStackTrace();
				}
			}
		});
		connectItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
			}
		});
		disconnectItem.addActionListener(new java.awt.event.ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {

			}
		});
		// add components
		add(chatters);
		add(menu);
		add(sp);
		add(jcomp3);
		add(jcomp1);
		add(send);
		add(srvmsg);
		add(smileys);
		// set component bounds (only needed by Absolute Positioning)
		chatters.setBounds(10, 40, 210, 425);
		sp.setBounds(230, 40, 391, 385);
		menu.setBounds(0, 0, 680, 30);
		jcomp3.setBounds(-270, 450, 655, 25);
		jcomp1.setBounds(230, 435, 255, 30);
		send.setBounds(550, 435, 70, 30);
		srvmsg.setBounds(10, 465, 610, 25);
		smileys.setBounds(487, 435, 60, 30);

		// Thread checkChatters = new Thread(new Chatters());
		// checkChatters.start();
		//
		// Thread thread = new Thread(new Receiver());
		// thread.start();

		Prozess prozess = new Prozess();
		Timer timer = new Timer(true);
		timer.scheduleAtFixedRate(prozess, 0, 10);
	}

	public static void append(String s) {
		try {
			Document doc = scrollPane.getDocument();
			doc.insertString(doc.getLength(), s, null);
		} catch (BadLocationException exc) {
			exc.printStackTrace();
		}
	}

	class Prozess extends TimerTask {

		public void run() {

			receiveChatters();
			receiveMsgs();

		}
	}

	public void receiveChatters() {
		while (true) {
			users.removeAll();
			users.add(new JLabel("Online Chatters:"));
			List<User> onlineUser = new ArrayList<User>();
			try {
				onlineUser = ChatClient.getServer().returnClients();
			} catch (RemoteException | MalformedURLException | NotBoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			for (User user : onlineUser) {
				JLabel lbltest = new JLabel(user.getName());
				users.add(lbltest);
				try {
					ChatClient.getServer().rmvPrintedMsgs();
				} catch (RemoteException | MalformedURLException | NotBoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public void receiveMsgs() {
		while (true) {
			scrollPane.removeAll();
			List<Message> messages = null;
			try {
				messages = ChatClient.getServer().returnMessages();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NotBoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for (Message i : messages) {
				append(i.getName() + i.getMsg());
				try {
					ChatClient.getServer().rmvPrintedMsgs();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NotBoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			scrollPane.repaint();
			try {
				Thread.sleep(20);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
