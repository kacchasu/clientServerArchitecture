import java.rmi.Naming;
import java.rmi.RemoteException;

public class RmiClient {
    private RmiClient() {}

    public static void main(String[] args) {
        try {
            RmiServer stub = (RmiServer) Naming.lookup("rmi://localhost/RmiServer");
            Solution solution = stub.solveEquation(1, -3, 2);
            System.out.println("Root1: " + solution.root1);
            System.out.println("Root2: " + solution.root2);
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
