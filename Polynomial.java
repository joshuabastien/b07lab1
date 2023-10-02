import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

public class Polynomial {
    private double[] coefficients;
    private int[] exponents;

    public Polynomial() {
        this.coefficients = new double[0];
        this.exponents = new int[0];
    }

    public Polynomial(double[] coefficients, int[] exponents) {
        this.coefficients = coefficients;
        this.exponents = exponents;
    }

    public Polynomial(File file) throws FileNotFoundException {
        Scanner scanner = new Scanner(file);
        String line = scanner.nextLine();
        scanner.close();
        String[] terms = line.split("(?=[+-])");
        double[] coefficients = new double[terms.length];
        int[] exponents = new int[terms.length];
        int index = 0;
        for (String term : terms) {
            if (term.isEmpty()) {
                continue;
            }
            int xIndex = term.indexOf('x');
            double coefficient = 0;
            int exponent = 0;
            if (xIndex == -1) {
                coefficient = Double.parseDouble(term);
            } else {
                if (xIndex == 0 || (xIndex == 1 && (term.charAt(0) == '+' || term.charAt(0) == '-'))) {
                    coefficient = term.charAt(0) == '-' ? -1 : 1;
                } else {
                    coefficient = Double.parseDouble(term.substring(0, xIndex));
                }
                if (xIndex == term.length() - 1) {
                    exponent = 1;
                } else {
                    exponent = Integer.parseInt(term.substring(xIndex + 1));
                }
            } 
            coefficients[index] = coefficient;
            exponents[index] = exponent;
            index++;
        }
        this.coefficients = Arrays.copyOf(coefficients, index);
        this.exponents = Arrays.copyOf(exponents, index);
    }


    public void saveToFile(String fileName) throws FileNotFoundException {
        PrintWriter writer = new PrintWriter(fileName);
        for (int i = 0; i < coefficients.length; i++) {
            double coefficient = coefficients[i];
            int exponent = exponents[i];
            if (coefficient != 0) {
                if (coefficient > 0 && i > 0) {
                    writer.print("+");
                }
                if (coefficient != 1 || exponent == 0) {
                    writer.print(coefficient);
                }
                if (exponent > 0) {
                    writer.print("x");
                    if (exponent != 1) {
                        writer.print(exponent);
                    }
                }
            }
        }
        writer.close();
    }

    public Polynomial add(Polynomial other) {
        int maxLength = Math.max(this.exponents[this.exponents.length - 1] + 1, other.exponents[other.exponents.length - 1] + 1);
        double[] resultCoefficients = new double[maxLength];
        int[] resultExponents = new int[maxLength];
        for (int i = 0; i < this.coefficients.length; i++) {
            resultCoefficients[this.exponents[i]] += this.coefficients[i];
            resultExponents[this.exponents[i]] = this.exponents[i];
        }
        for (int i = 0; i < other.coefficients.length; i++) {
            resultCoefficients[other.exponents[i]] += other.coefficients[i];
            resultExponents[other.exponents[i]] = other.exponents[i];
        }
        return new Polynomial(resultCoefficients, resultExponents);
    }

    public double evaluate(double x) {
        double result = 0;
        for (int i = 0; i < coefficients.length; i++) {
            result += coefficients[i] * Math.pow(x, exponents[i]);
        }
        return result;
    }

    public boolean hasRoot(double x) {
        return evaluate(x) == 0;
    }

    public Polynomial multiply(Polynomial other) {
        int maxLength = this.exponents[this.exponents.length - 1] + other.exponents[other.exponents.length - 1] + 1;
        double[] resultCoefficients = new double[maxLength];
        int[] resultExponents = new int[maxLength];
        for (int i = 0; i < this.coefficients.length; i++) {
            for (int j = 0; j < other.coefficients.length; j++) {
                resultCoefficients[this.exponents[i] + other.exponents[j]] += this.coefficients[i] * other.coefficients[j];
                resultExponents[this.exponents[i] + other.exponents[j]] = this.exponents[i] + other.exponents[j];
            }
        }
        return new Polynomial(resultCoefficients, resultExponents);
    }


}