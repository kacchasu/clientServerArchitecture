import java.io.Serializable;

public class Solution implements Serializable {
    private double root1;
    private double root2;
    private boolean hasSolution;

    public Solution(double root1, double root2, boolean hasSolution) {
        this.root1 = root1;
        this.root2 = root2;
        this.hasSolution = hasSolution;
    }

    public double getRoot1() {
        return root1;
    }

    public double getRoot2() {
        return root2;
    }

    public boolean hasSolution() {
        return hasSolution;
    }
}
