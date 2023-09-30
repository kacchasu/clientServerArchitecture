import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RmiServer extends Remote {
    Solution solveEquation(double a, double b, double c) throws RemoteException;
}

