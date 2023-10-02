import java.io.File;
import java.io.FileNotFoundException;

public class Driver {
    public static void main(String[] args) throws FileNotFoundException {
        // Create a new empty polynomial
        Polynomial p = new Polynomial();
        // Evaluate the polynomial at x = 3 and print the result
        double pResult = p.evaluate(3);
        System.out.println("p(3) = " + pResult);
        System.out.println("Expected output: 0.0 (since the polynomial is empty)");

        // Create two new polynomials with specified coefficients and exponents
        double[] c1 = {6, 0, 0, 5};
        int[] e1 = {0, 1, 2, 3};
        Polynomial p1 = new Polynomial(c1, e1);

        double[] c2 = {0, -2, 0, 0, -9};
        int[] e2 = {0, 1, 3, 4, 5};
        Polynomial p2 = new Polynomial(c2, e2);

        // Add the two polynomials together to create a new polynomial
        Polynomial s = p1.add(p2);
        // Evaluate the new polynomial at x = 0.1 and print the result
        double sResult = s.evaluate(0.1);
        System.out.println("s(0.1) = " + sResult);
        System.out.println("Expected output: 5.80491");

        // Check if the new polynomial has a root at x = 1 and print the result
        boolean hasRoot = s.hasRoot(1);
        if (hasRoot) {
            System.out.println("1 is a root of s");
        } else {
            System.out.println("1 is not a root of s");
        }
        System.out.println("Expected output: 1 is a root of s");

        // Save the new polynomial to a file named "polynomial.txt"
        s.saveToFile("polynomial.txt");
        System.out.println("Saved polynomial to file polynomial.txt");

        // Load the polynomial from the file and evaluate it at x = 0.1
        Polynomial sFromFile = new Polynomial(new File("polynomial.txt"));
        // Print the result of evaluating the polynomial from the file at x = 0.1
        double sFromFileResult = sFromFile.evaluate(0.1);
        System.out.println("sFromFile(0.1) = " + sFromFileResult);
        System.out.println("Expected output: 5.80491");
    }
}