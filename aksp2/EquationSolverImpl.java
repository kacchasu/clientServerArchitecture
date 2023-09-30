import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

public class EquationSolverImpl extends UnicastRemoteObject implements EquationSolver {
    public EquationSolverImpl() throws RemoteException {
        super();
    }

    public Solution solveEquation(double a, double b, double c) throws RemoteException {
        double discriminant = b * b - 4 * a * c;
        if (discriminant < 0) {
            return new Solution(0, 0, false); // No solution
        } else if (discriminant == 0) {
            double root = -b / (2 * a);
            return new Solution(root, root, true); // One real solution
        } else {
            double root1 = (-b + Math.sqrt(discriminant)) / (2 * a);
            double root2 = (-b - Math.sqrt(discriminant)) / (2 * a);
            return new Solution(root1, root2, true); // Two real solutions
        }
    }

}
