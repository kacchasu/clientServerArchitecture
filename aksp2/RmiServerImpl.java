import java.rmi.RemoteException;

public class RmiServerImpl extends java.rmi.server.UnicastRemoteObject implements RmiServer {
    protected RmiServerImpl() throws RemoteException {
        super();
    }

    @Override
    public Solution solveEquation(double a, double b, double c) throws RemoteException {
        double discriminant = b * b - 4 * a * c;
        double root1 = (-b + Math.sqrt(discriminant)) / (2 * a);
        double root2 = (-b - Math.sqrt(discriminant)) / (2 * a);
        return new Solution(root1, root2);
    }
}
