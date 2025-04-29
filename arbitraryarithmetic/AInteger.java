package arbitraryarithmetic;

public class AInteger {

    // This class represents an arbitrary-precision integer
    // It can handle positive and negative integers
    // It can handle addition, subtraction, multiplication, and division
    // It can handle leading zeros
    // It can handle negative numbers
    // It can handle large integers
    public String literal; // The original string representation of the number
    public String value; // The string representation of the number without leading zeros
    public boolean isNegative = false; // Flag to indicate if the number is negative

    public static String removeLeadingZeros(String number) {
        // Remove leading zeros from the number
        int index = 0;

        while (index < number.length() && number.charAt(index) == '0') {
            index++;
        }
        if (index == number.length()) {
            return "0";
        }
        return number.substring(index);
    }
    
    // Constructor to create an AInteger object with a default value of 0
    public AInteger() {
        this.value = "0";
        this.literal = "0";
    }

    // Constructor to create an AInteger object with a given string value
    public AInteger(String value) {

        value = removeLeadingZeros(value);
        if (value == null) {
            this.value = "0";
            this.literal = "0";
            return;
        }
        if (value.isEmpty()) {
            this.value = "0";
            this.literal = "0";
            return;
        }
        this.literal = value;
        if (value.charAt(0) == '-') {
            this.isNegative = true;
            this.value = value.substring(1);
            return;
        } else if (value.charAt(0) == '+') {
            this.isNegative = false;
            this.value = value.substring(1);
        }else{
            this.value = value;
        }        
    }
    // Copy constructor to create an AInteger object from another AInteger object

    public AInteger (AInteger aInteger) {
        this.literal = aInteger.literal;
        this.value = aInteger.value;
        this.isNegative = aInteger.isNegative;
    }

    // Constructor to create an AInteger object from another AInteger object
    public static AInteger parse(String s){
        return new AInteger(s);
    }

    // Add two AInteger objects
    // The method handles addition of two AInteger objects
    // It checks the sign of the numbers and adds them accordingly
    public static AInteger add(AInteger a, AInteger b) {
        String result = "";
        String stra = a.value;
        String strb = b.value;

        // Check Sign of the numbers, the code only handles positive numbers so if the input is negative
        // it will convert it to positive and then add them
        if (a.isNegative && b.isNegative) {
            return new AInteger("-" + (AInteger.add(new AInteger(stra), new AInteger(strb))).value);
        } else if (a.isNegative) {
            return AInteger.subtract(new AInteger (b.value), new AInteger (a.value));
        } else if (b.isNegative) {
            return AInteger.subtract(new AInteger(a.value), new AInteger(b.value));
        }

        //Normal addition

        int carry = 0;

        int i = stra.length() - 1;
        int j = strb.length() - 1;
        if (i>j) {
            for (int k = 0; k < i - j; k++) {
                strb = "0" + strb;
            }
        } else if (i < j) {
            for (int k = 0; k < j - i; k++) {
                stra = "0" + stra;
            }
        }
        for (int k = stra.length() - 1; k >= 0; k--) {
            int sum = (stra.charAt(k) - '0') + (strb.charAt(k) - '0') + carry;
            carry = sum / 10;
            result = (sum % 10) + result;
        }

        if (carry != 0) {
            result = carry + result;
        }
        
        return new AInteger(result);
    }


    // Subtract two AInteger objects
    // again this only handles positive numbers so if the input is negative it 
    // will change the sign and then subtract them or add them accordingly
    public static AInteger subtract(AInteger a, AInteger b) {
        String result = "";
        String stra = a.value;
        String strb = b.value;

        if (a.isNegative && !(b.isNegative)){
            return new AInteger("-" + (AInteger.add(new AInteger(stra), new AInteger(strb))).value);
        } else if (!(a.isNegative) && b.isNegative) {
            return AInteger.add(new AInteger(a.value), new AInteger(b.value));
        } else if (a.isNegative && b.isNegative) {
            return AInteger.subtract(new AInteger(b.value), new AInteger(a.value));
        }

        // Normal subtraction
        int borrow = 0;

        int i = stra.length() - 1;
        int j = strb.length() - 1;
        if (i>j) {
            for (int k = 0; k < i - j; k++) {
                strb = "0" + strb;
            }
        } else if (i < j) {
            for (int k = 0; k < j - i; k++) {
                stra = "0" + stra;
            }
        }
        for (int k = stra.length() - 1; k >= 0; k--) {
            int diff = (stra.charAt(k) - '0') - (strb.charAt(k) - '0') - borrow;
            if (diff < 0) {
                diff += 10;
                borrow = 1;
            } else {
                borrow = 0;
            }
            result = diff + result;
        }
        if ( borrow != 0) {
            AInteger resultobj = AInteger.subtract(b, a);
            result = resultobj.value;
            result = "-" + result;
        }

        return new AInteger(result);
    }

    // Multiply two AInteger objects
    // The method handles multiplication of two AInteger objects
    // It checks the sign of the numbers and multiplies them accordingly
    // The method uses the standard multiplication algorithm

    public static AInteger mul(AInteger a, AInteger b) {
        String result = "";
        String stra = a.value;
        String strb = b.value;

        if (a.isNegative && b.isNegative) {
            return new AInteger(AInteger.mul(new AInteger(stra), new AInteger(strb)).value);
        } else if (a.isNegative) {
            return new AInteger("-" + (AInteger.mul(new AInteger(stra), new AInteger(strb))).value);
        } else if (b.isNegative) {
            return new AInteger("-" + (AInteger.mul(new AInteger(stra), new AInteger(strb))).value);
        }

        for (int i = stra.length() - 1; i >= 0; i--) {
            int digit1 = stra.charAt(i) - '0';
            String tempResult = "";
            int carry = 0;
            for (int j = strb.length() - 1; j >= 0; j--) {
                int digit2 = strb.charAt(j) - '0';
                int product = digit1 * digit2 + carry;
                carry = product / 10;
                tempResult = (product % 10) + tempResult;
            }
            if (carry != 0) {
                tempResult = carry + tempResult;
            }
            for (int k = 0; k < stra.length() - 1 - i; k++) {
                tempResult += "0";
            }
            result = AInteger.add(new AInteger(result), new AInteger(tempResult)).value;
        }

        return new AInteger(result);


    }

    // Divide two AInteger objects
    // The method handles division of two AInteger objects
    // It checks the sign of the numbers and divides them accordingly
    // The method uses the standard long division algorithm

    public static AInteger div(AInteger a, AInteger b) {
        String result = "";
        String stra = a.value;
        String strb = b.value;
        int lena = stra.length();
        int lenb = strb.length();

        if (b.value.equals("0")) {
            throw new ArithmeticException("Division by zero");
        }

        if (a.isNegative && b.isNegative) {
            return new AInteger(AInteger.div(new AInteger(stra), new AInteger(strb)).value);
        } else if (a.isNegative) {
            return new AInteger("-" + (AInteger.div(new AInteger(stra), new AInteger(strb))).value);
        } else if (b.isNegative) {
            return new AInteger("-" + (AInteger.div(new AInteger(stra), new AInteger(strb))).value);
        }

        int digit = lenb;

        if (lena < lenb) {
            return new AInteger("0");
        }

        String intermediary = stra.substring(0, digit);

        while (digit <= lena) {
            int count = 0;
            while (AInteger.subtract(new AInteger(intermediary), new AInteger(strb)).isNegative == false) {
                intermediary = AInteger.subtract(new AInteger(intermediary), new AInteger(strb)).value;
                count++;
            }
            result += count;

            if (digit < lena) {
                intermediary += stra.charAt(digit);
            }           
            digit++;
            
        }            

        return new AInteger(result);
    }
    public static void main(String[] args) {
        AInteger num1 = new AInteger("-1223423423423423442342343");
        AInteger num2 = new AInteger("45656855565230906000321267892363258741258963");

        AInteger sum = AInteger.add(num1, num2);
        System.out.println("Sum: " + sum.literal);

        AInteger diff = AInteger.subtract(num1, num2);
        System.out.println("Difference: " + diff.literal);

        AInteger product = AInteger.mul(num1, num2);
        System.out.println("Product: " + product.literal);

        AInteger quotient = AInteger.div(num2, num1);
        System.out.println("Quotient: " + quotient.literal);
    }
}