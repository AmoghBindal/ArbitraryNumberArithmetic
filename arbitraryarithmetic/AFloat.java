package arbitraryarithmetic;

public class AFloat extends AInteger {

    // This class represents an arbitrary-precision float
    // It can handle positive and negative floats
    // It can handle addition, subtraction, multiplication, and division
    // It can handle leading zeros
    // It can handle negative numbers
    // It can handle large floats

    // OTher than the previous feilds i have storeed the digits after decimal as an int
    // and the value without decimal as a string
    // value in this case is the absolute value of the float
    // literal is the original string representation of the number
    public int digitsAfterDecimal;
    public String valWODecimal;

    // This is a default constructor
    public AFloat(){
        this.value = "0.0";
        this.valWODecimal = "0";
        this.literal = "0.0";
        this.digitsAfterDecimal = 0;
    }

    // This constructor takes a string value and creates an AFloat object
    public AFloat(String value) {
        //Handle the case where the value is null or empty
        if (value == null) {
            this.value = "0";
            this.valWODecimal = "0";
            this.literal = "0.0";
            this.digitsAfterDecimal = 0;
            return;
        }
        if (value.isEmpty()) {
            this.value = "0";
            this.valWODecimal = "0";
            this.literal = "0.0";
            this.digitsAfterDecimal = 0;
            return;
        }

        if (value.charAt(0) == '-') {
            this.isNegative = true;
            this.literal = value;
            value = value.substring(1);
        } else if (value.charAt(0) == '+') {
            this.isNegative = false;
            this.literal = value.substring(1);
            value = value.substring(1);
        }else {
            this.literal = value;
        }
        String[] parts = value.split("\\.");
        if (parts.length == 2) {
            this.digitsAfterDecimal = parts[1].length();
            this.valWODecimal = removeLeadingZeros(parts[0]) + parts[1];
            this.value = removeLeadingZeros(parts[0]) + "." + parts[1];

        } else if (parts.length == 1) {
            this.value = removeLeadingZeros(value);
            this.digitsAfterDecimal = 0;
            this.valWODecimal = removeLeadingZeros(value);
        } else {
            System.out.println("Invalid float format: " + value);
            throw new IllegalArgumentException("Invalid float format: " + value);
        }
    }

    // This constructor takes an AFloat object and creates a new AFloat object
    public AFloat(AFloat aFloat) {
        this.literal = aFloat.literal;
        this.value = aFloat.value;
        this.isNegative = aFloat.isNegative;
        this.digitsAfterDecimal = aFloat.digitsAfterDecimal;
        this.valWODecimal = aFloat.valWODecimal;
    }

    // This constructor takes an AInteger object and creates a new AFloat object
    public static AFloat parse(String s) {
        return new AFloat(s);
    }

    // This method Adds floats, 
    // It Scales the First number to the size of the second without decimals and then
    // adds the two numbers using AInterger.add and places decimal accordingly
    // Again only positive numbers are added if a negative number occurs it is directed to the subtract function
    public AFloat add(AFloat b) {
        

        if (this.isNegative && b.isNegative){
            AFloat a = new AFloat(this.value);
            AFloat b1 = new AFloat(b.value);
            return new AFloat('-' + a.add(b1).value);
        }else if (this.isNegative && !b.isNegative){
            AFloat a = new AFloat(this.value);
            AFloat b1 = new AFloat(b.value);
            return b1.sub(a);
        }else if (!this.isNegative && b.isNegative){
            AFloat a = new AFloat(this.value);
            AFloat b1 = new AFloat(b.value);
            return a.sub(b1);
        }

        if (this.digitsAfterDecimal > b.digitsAfterDecimal) {
            String intera = this.valWODecimal.substring(0, this.valWODecimal.length() - (this.digitsAfterDecimal - b.digitsAfterDecimal));
            String inter = (AInteger.parse(intera)).add(AInteger.parse(b.valWODecimal)).value;
            inter += this.valWODecimal.substring(this.valWODecimal.length() - (this.digitsAfterDecimal - b.digitsAfterDecimal));
            inter = inter.substring(0, inter.length() - this.digitsAfterDecimal) + "." + inter.substring(inter.length() - this.digitsAfterDecimal);
            return new AFloat(inter);
        } else if (this.digitsAfterDecimal <= b.digitsAfterDecimal) {
            String aVal = this.valWODecimal + "0".repeat(b.digitsAfterDecimal - this.digitsAfterDecimal);
            String inter  = (AInteger.parse(aVal)).add(AInteger.parse(b.valWODecimal)).value;
            inter = inter.substring(0, inter.length() - b.digitsAfterDecimal) + "." + inter.substring(inter.length() - b.digitsAfterDecimal);
            return new AFloat(inter);
        }
        return null;
    }

    // This method Subtracts floats,
    // Integer part and Decimal parts are subtracted separately
    // If decimal part is negative then the integer part is decremented by 1 and is added to the decimal part
    // Again only positive numbers are subtracted if a negative number occurs it is directed to the add function
    // The result is then returned as a new AFloat object
    public AFloat sub( AFloat b) {
        if (this.isNegative && b.isNegative){
            return (AFloat.parse(b.value)).sub(AFloat.parse(this.value));
        }else if (this.isNegative && !b.isNegative){
            return new AFloat('-' + ((AFloat.parse(this.value)).add(AFloat.parse(b.value))).value);
        }else if (!this.isNegative && b.isNegative){
            return AFloat.parse(this.value).add(AFloat.parse(b.value));
        }
        String intparta = this.valWODecimal.substring(0, this.valWODecimal.length() - this.digitsAfterDecimal);
        String intpartb = b.valWODecimal.substring(0, b.valWODecimal.length() - b.digitsAfterDecimal);
        String decparta = this.valWODecimal.substring(this.valWODecimal.length() - this.digitsAfterDecimal);
        String decpartb = b.valWODecimal.substring(b.valWODecimal.length() - b.digitsAfterDecimal);

        if (this.digitsAfterDecimal > b.digitsAfterDecimal) {
            decpartb += "0".repeat(this.digitsAfterDecimal - b.digitsAfterDecimal);
        } else if (this.digitsAfterDecimal < b.digitsAfterDecimal) {
            decparta += "0".repeat(b.digitsAfterDecimal - this.digitsAfterDecimal);
        }
        AInteger decpart = (AInteger.parse(decparta)).subtract(AInteger.parse(decpartb));
        
        AInteger intpart = (AInteger.parse(intparta)).subtract(AInteger.parse(intpartb));

        if (decpart.isNegative){
            String temp = 1 + "0".repeat(Math.max(this.digitsAfterDecimal, b.digitsAfterDecimal));
            decpart = (AInteger.parse(temp)).add(decpart);
            intpart = intpart.subtract(AInteger.parse("1"));
            return new AFloat(intpart.literal + '.' + decpart.value);
        }
        return new AFloat(intpart.literal + '.' + decpart.value);
    }

    // This method Multiplies floats,
    // It Multiplies the two numbers using AInteger.mul and then places the decimal point accordingly
    // The result is then returned as a new AFloat object
    // Again only positive numbers are multiplied if a negative number occurs it is multiplied and a - is added to the result
    public AFloat mul(AFloat b){
        if (this.isNegative && b.isNegative){
            return AFloat.parse(this.value).mul(AFloat.parse(b.value));
        }else if (this.isNegative && !b.isNegative || !this.isNegative && b.isNegative){
            return new AFloat('-' + (AFloat.parse(this.value).mul(AFloat.parse(b.value))).value);           
        }
        String inter = ((AInteger.parse(this.valWODecimal)).mul(AInteger.parse(b.valWODecimal))).value;

        inter = inter.substring(0, inter.length() - (this.digitsAfterDecimal + b.digitsAfterDecimal)) + "." + inter.substring(inter.length() - (this.digitsAfterDecimal + b.digitsAfterDecimal));
        return new AFloat(inter);
    }

    // This method Divides floats,
    // It Divides the two numbers using AInteger.div and then places the decimal point accordingly
    // the First number is taken upto the precision of the second numbers decimal part + 30 and then divided ensuring 30 decimal places at the end 
    // The result is then returned as a new AFloat object
    // Again only positive numbers are divided if a negative number occurs it is divided and a - is added to the result
    public AFloat div(AFloat b){
        if (this.isNegative && b.isNegative){
            return AFloat.parse(this.value).div(AFloat.parse(b.value));
        }else if (this.isNegative && !b.isNegative || !this.isNegative && b.isNegative){
            return new AFloat('-' + (AFloat.parse(this.value).div(AFloat.parse(b.value))).value);           
        }
        int k = this.digitsAfterDecimal - b.digitsAfterDecimal - 30;
        if ( k < 0){
            String intera = this.valWODecimal + "0".repeat(Math.abs(k));
            
            AInteger inter = (AInteger.parse(intera).div(AInteger.parse(b.valWODecimal)));
            intera = inter.value;

            if (intera.length() <= 30){
                intera = "0".repeat(30 - intera.length()) + intera;
                return new AFloat("0." + intera);
            }

            intera = intera.substring(0, intera.length() - 30) + "." + intera.substring(intera.length() - 30);
            return new AFloat(intera);
        }else{
            String intera = this.valWODecimal;
            intera = intera.substring(0, intera.length() - k);
            AInteger inter = AInteger.parse(intera).div(AInteger.parse(b.valWODecimal));
            intera = inter.value;
            if (intera.length() <= 30){
                intera = "0".repeat(30 - intera.length()) + intera;
                return new AFloat("0." + intera);
            }
            intera = intera.substring(0, intera.length() - 30) + "." + intera.substring(intera.length() - 30);
            return new AFloat(intera);
        }
    }

    public static void main(String[] args) {
        AFloat a = new AFloat("3.6");
        AFloat b = new AFloat("7.2");
        System.out.println(a.add(b).literal); // 912.468
        System.out.println(a.sub(b).literal); // -665.556
        System.out.println(a.mul(b).literal); // 97377.635872
        System.out.println(a.div(b).literal); // 0.156793
    }



}
