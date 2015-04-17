package rmi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.rmi.Naming;

public class RmiClient {
	
	public static RmiServerIntf obj = null;
	public static RmiClient CLIENT = new RmiClient();
	
	private RmiClient() {
	}
	
	public RmiClient getInstance() {
		if(CLIENT == null) {
			CLIENT = new RmiClient();
		}
		return CLIENT;
	}
	
	public static void main(String args[]) throws Exception {
		obj = (RmiServerIntf) Naming.lookup("//localhost/RmiServer");
		System.out.println(obj.getMessage());
		CLIENT.getInstance().sendMessage("Hi");
	}
	
	public void sendMessage(String msg) throws IOException {
//		BufferedReader eingabe = new BufferedReader(
//				new InputStreamReader(System.in));
		obj.setMessage(msg);
	}
}