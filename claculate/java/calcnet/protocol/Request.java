package calcnet.protocol;

public class Request {
    public String version = "1.0";
    public String operation;
    public int operandCount;
    public double op1;
    public double op2;
    public String requestId;

    public boolean isValidBasic(){
        return operation != null && operandCount == 2 && !Double.isNaN(op1) && !Double.isNaN(op2);
    }
}
