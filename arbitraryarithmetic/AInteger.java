package arbitraryarithmetic;

public class AInteger {

    public String literal;
    public String value;
    public boolean isNegative = false;

    public AInteger() {
        this.value = "0";
        this.literal = "0";
    }

    public AInteger(String value) {
        this.literal = value;
        if (value.charAt(0) == '-') {
            this.isNegative = true;
            value = value.substring(1);
        } else if (value.charAt(0) == '+') {
            this.isNegative = false;
            value = value.substring(1);
        }
        this.value = value;
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
            System.out.println("Error: Negative result");
            AInteger resultobj = AInteger.subtract(b, a);
            result = resultobj.value;
            result = "-" + result;
        }

        return new AInteger(result);
    }
    public static void main(String[] args) {
        AInteger num1 = new AInteger("-123");
        AInteger num2 = new AInteger("456");

        AInteger sum = AInteger.add(num1, num2);
        System.out.println("Sum: " + sum.literal);

        AInteger diff = AInteger.subtract(num1, num2);
        System.out.println("Difference: " + diff.literal);
    }
}