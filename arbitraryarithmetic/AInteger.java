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
    public AInteger add(AInteger b) {
        String result = "";
        String stra = this.value;
        String strb = b.value;;

        // Check Sign of the numbers, the code only handles positive numbers so if the input is negative
        // it will convert it to positive and then add them
        if (this.isNegative && b.isNegative) {
            AInteger a = new AInteger(stra);
            return new AInteger("-" + (a.add( new AInteger(strb))).value);
        } else if (this.isNegative) {
            AInteger a = new AInteger(this.value);
            return b.subtract(a);
        } else if (b.isNegative) {
            AInteger b1 = new AInteger(b.value);
            return this.subtract(b1);
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
    public AInteger subtract( AInteger b) {
        String result = "";
        String stra = this.value;
        String strb = b.value;

        if (this.isNegative && !(b.isNegative)){
            AInteger a = new AInteger(stra);
            AInteger b1 = new AInteger(b.value);
            return new AInteger("-" + (a.add(b1)).value);
        } else if (!(this.isNegative) && b.isNegative) {
            AInteger a1 = new AInteger(this.value);
            AInteger b1 = new AInteger(b.value);
            return a1.add(b1);
        } else if (this.isNegative && b.isNegative) {
            AInteger a = new AInteger(stra);
            AInteger b1 = new AInteger(b.value);
            return b1.subtract(a);
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
            AInteger a1 = new AInteger(this);
            AInteger b1 = new AInteger(b);

            AInteger resultobj = b1.subtract(a1);
            result = resultobj.value;
            result = "-" + result;
        }

        AInteger res = new AInteger(result);
        return res;
    }

    // Multiply two AInteger objects
    // The method handles multiplication of two AInteger objects
    // It checks the sign of the numbers and multiplies them accordingly
    // The method uses the standard multiplication algorithm

    public AInteger mul( AInteger b) {
        String result = "";
        String stra = this.value;
        String strb = b.value;

        if (this.isNegative && b.isNegative) {
            AInteger a1 = new AInteger(stra);
            AInteger b1 = new AInteger(strb);
            return new AInteger(a1.mul(b1).value);
        } else if (this.isNegative || b.isNegative) {
            AInteger a1 = new AInteger(stra);
            AInteger b1 = new AInteger(strb);
            return new AInteger("-" + (a1.mul(b1)).value);
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
            AInteger interres = new AInteger (result);
            AInteger temp = new AInteger(tempResult);
            result = interres.add(temp).value;
        }

        return new AInteger(result);


    }

    // Divide two AInteger objects
    // The method handles division of two AInteger objects
    // It checks the sign of the numbers and divides them accordingly
    // The method uses the standard long division algorithm

    public AInteger div(AInteger b) {
        String result = "";
        String stra = this.value;
        String strb = b.value;
        AInteger b1 = new AInteger(b.value);
        int lena = stra.length();
        int lenb = strb.length();

        if (b.value == "0") {
            System.out.println("Division by zero is not allowed");
            return new AInteger("0");
        }

        if (this.isNegative && b.isNegative) {
            AInteger a1 = new AInteger(stra);
            return new AInteger(a1.div(b1).value);
        } else if (this.isNegative || b.isNegative) {
            AInteger a1 = new AInteger(stra);
            return new AInteger("-" + (a1.div(b1)).value);
        }

        int digit = lenb;

        if (lena < lenb) {
            return new AInteger("0");
        }

        String intermediary = stra.substring(0, digit);

        while (digit <= lena) {
            int count = 0;
            AInteger intermediaryobj = new AInteger(intermediary);
            intermediaryobj = intermediaryobj.subtract(b1);
            while (intermediaryobj.isNegative == false && count < 15) {
                intermediaryobj = intermediaryobj.subtract(b1);
                count++;
            }
            result += count;

            if (digit < lena) {
                intermediary = (intermediaryobj.add(b1)).value + stra.charAt(digit);
            }           
            digit++;
            
        }            

        return new AInteger(result);
    }
    public static void main(String[] args) {
        AInteger num1 = new AInteger("10");
        AInteger num2 = new AInteger("1100");

        AInteger sum = num1.add(num2);
        System.out.println("Sum: " + sum.literal);

        AInteger diff = num1.subtract(num2);
        System.out.println("Difference: " + diff.literal);

        AInteger prod = num1.mul(num2);
        System.out.println("Product: " + prod.literal);

        AInteger quot = num2.div(num1);
        System.out.println("Quotient: " + quot.literal);
    }

    
}