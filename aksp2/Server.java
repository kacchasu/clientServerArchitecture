import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static void main(String[] args) {
        try {
            EquationSolver solver = new EquationSolverImpl();
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.bind("EquationSolver", solver);
            System.out.println("Server is ready");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
