package calcnet.server;

public class Calculator {
    public static double compute(String op, double a, double b) {
        switch (op) {
            case "ADD": return a + b;
            case "SUB": return a - b;
            case "MUL": return a * b;
            case "DIV":
                if (b == 0.0) throw new ArithmeticException("DIVIDE_BY_ZERO");
                return a / b;
            default: throw new IllegalArgumentException("INVALID_OPERATION");
        }
    }
}
