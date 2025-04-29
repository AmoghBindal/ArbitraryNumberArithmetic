import arbitraryarithmetic.AInteger;
import arbitraryarithmetic.AFloat;

public class MyInfArith {
    public static void main(String[] args) {
        if (args.length != 4) {
            System.out.println("Usage: <int/float> <add/sub/mul/div> <operand1> <operand2>");
            return;
        }

        String type = args[0];
        String op = args[1];
        String operand1 = args[2];
        String operand2 = args[3];

        switch (type) {
            case "int":
                AInteger int1 = new AInteger(operand1);
                AInteger int2 = new AInteger(operand2);
                switch (op) {
                    case "add":
                        System.out.println(AInteger.add(int1, int2).literal);
                        break;
                    case "sub":
                        System.out.println(AInteger.subtract(int1, int2).literal);
                        break;
                    case "mul":
                        System.out.println(AInteger.mul(int1, int2).literal);
                        break;
                    case "div":
                        System.out.println(AInteger.div(int1, int2).literal);
                        break;
                    default:
                        System.out.println("Unsupported operation for int: " + op);
                }
                break;

            case "float":
                AFloat float1 = new AFloat(operand1);
                AFloat float2 = new AFloat(operand2);
                switch (op) {
                    case "add":
                        System.out.println(AFloat.addF(float1, float2).literal);
                        break;
                    case "sub":
                        System.out.println(AFloat.subF(float1, float2).literal);
                        break;
                    case "mul":
                        System.out.println(AFloat.mulF(float1, float2).literal);
                        break;
                    case "div":
                        System.out.println(AFloat.divF(float1, float2).literal);
                        break;
                    default:
                        System.out.println("Unsupported operation for float: " + op);
                }
                break;

            default:
                System.out.println("Invalid type. Use 'int' or 'float'.");
        }
    }
}
