package arbitraryarithmetic;

public class AInteger {

    public String literal;
    public String value;
    public boolean isNegative = false;

    public static String removeLeadingZeros(String number) {

        int index = 0;

        while (index < number.length() && number.charAt(index) == '0') {
            index++;
        }
        if (index == number.length()) {
            return "0";
        }
        return number.substring(index);
    }
    

    public AInteger() {
        this.value = "0";
        this.literal = "0";
    }

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
        }
        else{
            this.value = value;
            }        
    }

    public AInteger (AInteger aInteger) {
        this.literal = aInteger.literal;
        this.value = aInteger.value;
        this.isNegative = aInteger.isNegative;
    }

    public static AInteger parse(String s){
        return new AInteger(s);
    }

    public static AInteger add(AInteger a, AInteger b) {
        String result = "";
        String stra = a.value;
        String strb = b.value;

        if (a.isNegative && b.isNegative) {
            return new AInteger("-" + (AInteger.add(new AInteger(stra), new AInteger(strb))).value);
        } else if (a.isNegative) {
            return AInteger.subtract(new AInteger (b.value), new AInteger (a.value));
        } else if (b.isNegative) {
            return AInteger.subtract(new AInteger(a.value), new AInteger(b.value));
        }

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