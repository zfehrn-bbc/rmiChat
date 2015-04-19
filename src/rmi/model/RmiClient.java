
package rmi.model;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class RmiClient implements RmiServerIntf {
	public static RmiServerIntf obj = null;
	public static RmiClient CLIENT = new RmiClient();

	public RmiClient getInstance() {
		if (CLIENT == null) {
			CLIENT = new RmiClient();
		}
		return CLIENT;
	}
	
	public void run() throws MalformedURLException, RemoteException, NotBoundException {
		obj = (RmiServerIntf) Naming.lookup("//localhost/RmiServer");
		String msg = "[" + System.getProperty("user.name") + "] " + "got connected";
		obj.send(msg);
		
		Scanner s = new Scanner(System.in);
		while (true) {
			msg = s.nextLine().trim();
			msg = "[" + System.getProperty("user.name") + "] " + msg;
			obj.send(msg);
		}
	}

	@Override
  public void send(String msg) throws RemoteException {
	  // TODO Auto-generated method stub
	  System.out.println(msg);
  }

	@Override
  public String getStartMessage() throws RemoteException {
	  // TODO Auto-generated method stub
	  return null;
  }

	@Override
  public String getEndMessage() throws RemoteException {
	  // TODO Auto-generated method stub
	  return null;
  }
}
