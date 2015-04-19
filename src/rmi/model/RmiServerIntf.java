package rmi.model;


import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RmiServerIntf extends Remote {
	public void send(String msg) throws RemoteException;
	public String getStartMessage() throws RemoteException;
	public String getEndMessage() throws RemoteException;
}
