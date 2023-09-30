import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
    public static void main(String[] args) {
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            EquationSolver solver = (EquationSolver) registry.lookup("EquationSolver");
            Solution solution = solver.solveEquation(1, -3, 2);
            if (solution.hasSolution()) {
                System.out.println("Root1: " + solution.getRoot1());
                System.out.println("Root2: " + solution.getRoot2());
            } else {
                System.out.println("The equation has no real solutions.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
