
package rmi.common;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIInterface extends Remote{ 

String loginSystem(String name)throws RemoteException; 
String operationSystem(String operation)throws RemoteException;    
void uploadProcess(String userFileName) throws RemoteException;
void downloadProcess(String userFileName) throws RemoteException;
void updateProcess(String userFileName) throws RemoteException;
String deleteProcess(String userFileName)throws RemoteException;

} 