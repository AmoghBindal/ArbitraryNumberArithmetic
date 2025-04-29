package arbitraryarithmetic;

public class AFloat extends AInteger {

    public int digitsAfterDecimal;
    public String valWODecimal;

    public AFloat(){
        this.value = "0.0";
        this.valWODecimal = "0";
        this.literal = "0.0";
        this.digitsAfterDecimal = 0;
    }

    public AFloat(String value) {
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
            throw new IllegalArgumentException("Invalid float format: " + value);
        }
    }

    public AFloat(AFloat aFloat) {
        this.literal = aFloat.literal;
        this.value = aFloat.value;
        this.isNegative = aFloat.isNegative;
        this.digitsAfterDecimal = aFloat.digitsAfterDecimal;
        this.valWODecimal = aFloat.valWODecimal;
    }

    public static AFloat parse(String s) {
        return new AFloat(s);
    }

    public static AFloat addF(AFloat a, AFloat b) {
        

        if (a.isNegative && b.isNegative){
            return new AFloat('-' + AFloat.addF(new AFloat(a.value), new AFloat(b.value)).value);
        }else if (a.isNegative && !b.isNegative){
            return AFloat.subF(new AFloat(b.value), new AFloat(a.value));
        }else if (!a.isNegative && b.isNegative){
            return AFloat.subF(new AFloat(a.value), new AFloat(b.value));
        }

        if (a.digitsAfterDecimal > b.digitsAfterDecimal) {
            String intera = a.valWODecimal.substring(0, a.valWODecimal.length() - (a.digitsAfterDecimal - b.digitsAfterDecimal));
            String inter = AInteger.add(AInteger.parse(intera), AInteger.parse(b.valWODecimal)).value;
            inter += a.valWODecimal.substring(a.valWODecimal.length() - (a.digitsAfterDecimal - b.digitsAfterDecimal));
            inter = inter.substring(0, inter.length() - a.digitsAfterDecimal) + "." + inter.substring(inter.length() - a.digitsAfterDecimal);
            return new AFloat(inter);
        } else if (a.digitsAfterDecimal <= b.digitsAfterDecimal) {
            String aVal = a.valWODecimal + "0".repeat(b.digitsAfterDecimal - a.digitsAfterDecimal);
            String inter  = AInteger.add(AInteger.parse(aVal), AInteger.parse(b.valWODecimal)).value;
            inter = inter.substring(0, inter.length() - b.digitsAfterDecimal) + "." + inter.substring(inter.length() - b.digitsAfterDecimal);
            return new AFloat(inter);
        }
        return null;
    }

    public static AFloat subF(AFloat a, AFloat b) {
        if (a.isNegative && b.isNegative){
            return AFloat.subF(new AFloat(b.value), new AFloat(a.value));
        }else if (a.isNegative && !b.isNegative){
            return new AFloat('-' + AFloat.addF(new AFloat(a.value), new AFloat(b.value)).value);
        }else if (!a.isNegative && b.isNegative){
            return AFloat.addF(new AFloat(a.value), new AFloat(b.value));
        }
        String intparta = a.valWODecimal.substring(0, a.valWODecimal.length() - a.digitsAfterDecimal);
        String intpartb = b.valWODecimal.substring(0, b.valWODecimal.length() - b.digitsAfterDecimal);
        String decparta = a.valWODecimal.substring(a.valWODecimal.length() - a.digitsAfterDecimal);
        String decpartb = b.valWODecimal.substring(b.valWODecimal.length() - b.digitsAfterDecimal);

        if (a.digitsAfterDecimal > b.digitsAfterDecimal) {
            decpartb += "0".repeat(a.digitsAfterDecimal - b.digitsAfterDecimal);
        } else if (a.digitsAfterDecimal < b.digitsAfterDecimal) {
            decparta += "0".repeat(b.digitsAfterDecimal - a.digitsAfterDecimal);
        }
        AInteger decpart = AInteger.subtract(AInteger.parse(decparta), AInteger.parse(decpartb));
        
        AInteger intpart = AInteger.subtract(AInteger.parse(intparta), AInteger.parse(intpartb));

        if (decpart.isNegative){
            String temp = 1 + "0".repeat(Math.max(a.digitsAfterDecimal, b.digitsAfterDecimal));
            decpart = AInteger.add(AInteger.parse(temp), decpart);
            intpart = AInteger.subtract(intpart, AInteger.parse("1"));
            return new AFloat(intpart.literal + '.' + decpart.value);
        }
        return new AFloat(intpart.literal + '.' + decpart.value);
    }

    public static AFloat mulF(AFloat a, AFloat b){
        if (a.isNegative && b.isNegative){
            return AFloat.mulF(new AFloat(a.value), new AFloat(b.value));
        }else if (a.isNegative && !b.isNegative || !a.isNegative && b.isNegative){
            return new AFloat('-' + AFloat.mulF(new AFloat(a.value), new AFloat(b.value)).value);           
        }
        String inter = AInteger.mul(AInteger.parse(a.valWODecimal), AInteger.parse(b.valWODecimal)).value;

        inter = inter.substring(0, inter.length() - (a.digitsAfterDecimal + b.digitsAfterDecimal)) + "." + inter.substring(inter.length() - (a.digitsAfterDecimal + b.digitsAfterDecimal));
        return new AFloat(inter);
    }
    // public static AFloat divF(AFloat a, AFloat b){
    //     if (a.isNegative && b.isNegative){
    //         return AFloat.divF(new AFloat(a.value), new AFloat(b.value));
    //     }else if (a.isNegative && !b.isNegative || !a.isNegative && b.isNegative){
    //         return new AFloat('-' + AFloat.divF(new AFloat(a.value), new AFloat(b.value)).value);           
    //     }
    //     if (a.valWODecimal.length() - (b.valWODecimal.length() + 30) < 0){
            

    //     }

    public static void main(String[] args) {
        AFloat a = new AFloat("123.456");
        AFloat b = new AFloat("789.012");
        System.out.println(AFloat.addF(a, b).literal); // 912.468
        System.out.println(AFloat.subF(a, b).literal); // -665.556
        System.out.println(AFloat.mulF(a, b).literal); // 97377.635872
    }



}
