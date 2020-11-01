public class MyBigInteger {
    String value;

    MyBigInteger(){
        value = "0";
    }

    MyBigInteger(String val){
        value = val;
    }

    public String abbreviatedValue(){
        return (this.value.length() < 12) ? this.value :
                this.value.substring(0,5) + "..." + this.value.substring(this.value.length() - 5);
    }

    public MyBigInteger plus(MyBigInteger b){
        int sizeDiff;
        char valueChanged;
        if (this.value.length() < b.value.length()){
            sizeDiff = b.value.length() - this.value.length();
            valueChanged = 'a';
            String zero = "0";
            zero = zero.repeat(sizeDiff);
            this.value = zero + this.value;
        } else {
            sizeDiff = this.value.length() - b.value.length();
            valueChanged = 'b';
            String zero = "0";
            zero = zero.repeat(sizeDiff);
            b.value = zero + b.value;
        }

        MyBigInteger c = new MyBigInteger();
        char[] charArr = new char[this.value.length()];
        int carry = 0;
        for (int i = this.value.length()-1; i >= 0; i--) {
            int dA = convertToInt(this.value.charAt(i));
            int dB = convertToInt(b.value.charAt(i));
            int sum = dA + dB + carry;
            int dC = 0;
            if (sum >= 10 && i > 0){
                carry = 1;
                dC = sum - 10;
            } else if( sum >= 10 && i == 0) {
                //2nd most significant digit = sum % 10
                charArr[i] = convertToChar(sum % 10);
                //most significant digit = 1
                c.value = "1" + String.valueOf(charArr);
                if(sizeDiff > 0)
                   trimLeadingZeros(this, b, valueChanged, sizeDiff);
                return c;
            } else{
                dC = sum;
                carry = 0;
            }
            charArr[i] = convertToChar(dC);
        }
        c.value = String.valueOf(charArr);
        if(sizeDiff > 0)
            trimLeadingZeros(this, b, valueChanged, sizeDiff);
        return c;
    }
    public MyBigInteger minus(MyBigInteger b){
        int sizeDiff;
        char valueChanged;
        if (this.value.length() < b.value.length()){
            sizeDiff = b.value.length() - this.value.length();
            valueChanged = 'a';
            String zero = "0";
            zero = zero.repeat(sizeDiff);
            this.value = zero + this.value;
        } else {
            sizeDiff = this.value.length() - b.value.length();
            valueChanged = 'b';
            String zero = "0";
            zero = zero.repeat(sizeDiff);
            b.value = zero + b.value;
        }

        MyBigInteger c = new MyBigInteger();
        char[] charArr = new char[this.value.length()];
        int carry = 0;
        for (int i = this.value.length()-1; i >= 0; i--) {
            int dA = convertToInt(this.value.charAt(i));
            int dB = convertToInt(b.value.charAt(i));
            int diff;
            if (dA >= dB) {
                diff = dA - dB - carry;
                carry = 0;
            }
            else{
                diff = 10 + dA - dB - carry;
                carry = 1;
            }

            int dC = diff;
            charArr[i] = convertToChar(dC);
        }

        c.value = String.valueOf(charArr);
        if (sizeDiff > 0)
            trimLeadingZeros(this, b, valueChanged, sizeDiff);
        return c;
    }

    public void trimLeadingZeros(MyBigInteger a, MyBigInteger b, char valueChanged, int diff){
        if (valueChanged == 'a') {
            a.value = this.value.substring(diff);
        } else {
            b.value = b.value.substring(diff);
        }
    }
    public MyBigInteger times(MyBigInteger b){

        int currPowerOfTen = -1; //used for added 0's in multiplication
        int maxLen = (this.value.length() > b.value.length()) ? this.value.length() : b.value.length();
        String zeroStr = "0".repeat(maxLen * 2);
        MyBigInteger temp1 = new MyBigInteger(zeroStr);
        MyBigInteger temp2 = new MyBigInteger(zeroStr);
        for (int i = b.value.length()-1; i >= 0 ; i--) {
            String result = "";
            currPowerOfTen++;
            int carry = 0;
            for (int j = this.value.length() - 1; j >= 0; j--) {
                int dA = convertToInt(this.value.charAt(j));
                int dB = convertToInt(b.value.charAt(i));
                int product = dA * dB + carry;
                int dC = 0;
                if (product >= 10 && j > 0) {
                    carry = product / 10;
                    dC = product % 10;
                    result = dC + result;
                } else if (product >= 10 && j == 0) {
                    carry = product / 10;
                    dC = product % 10;
                    result = carry + String.valueOf(dC) + result;
                } else{
                    dC = product;
                    carry = 0;
                    result = convertToChar(dC) + result;
                }
            }
            String appendZero = "0";
            appendZero = appendZero.repeat(currPowerOfTen);
            result += appendZero;
            if (currPowerOfTen % 2 == 0) {
                temp1.value = result;
                if (currPowerOfTen > 0) {
                    temp1 = temp2.plus(temp1);
                }
            }
            else {
                temp2.value = result;
                temp2 = temp1.plus(temp2);
            }

        }
        if (currPowerOfTen % 2 == 0)
            return temp1;
        else
            return temp2;

    }

    public MyBigInteger karatsuba(MyBigInteger b){
        //modified from pseudocode from https://en.wikipedia.org/wiki/Karatsuba_algorithm
        if (this.value.length() == 1 || b.value.length() == 1)
            return this.times(b);

        int lenA = this.value.length();
        int lenB = b.value.length();
        int m = (lenA < lenB) ? lenA : lenB;
        int m2 = m/2;

        MyBigInteger high1 = new MyBigInteger(this.value.substring(0, m2));
        MyBigInteger low1 = new MyBigInteger(this.value.substring(m2));
        MyBigInteger high2 = new MyBigInteger(b.value.substring(0, m2));
        MyBigInteger low2 = new MyBigInteger(b.value.substring(m2));

        MyBigInteger z0 = low1.karatsuba(low2);
        MyBigInteger z1part1 = low1.plus(high1);
        MyBigInteger z1part2 = low2.plus(high2);
        MyBigInteger z1 = z1part1.karatsuba(z1part2);
        MyBigInteger z2 = high1.karatsuba(high2);

        String zero = "0";
        String tenToTwoM2 = "1" + zero.repeat(m2 * 2);
        MyBigInteger tenToTwoM2Big = new MyBigInteger(tenToTwoM2);
        String tenToM2 = "1" + zero.repeat(m2);
        MyBigInteger tenToM2Big = new MyBigInteger(tenToM2);

        MyBigInteger z3 = z1.minus(z2).minus(z0);

        MyBigInteger retPart1 = z2.times(tenToTwoM2Big);
        MyBigInteger retPart2 = z3.times(tenToM2Big);

        return (retPart1.plus(retPart2).plus(z0));
    }

    private int convertToInt(char c){
        return c - 48;
    }
    private char convertToChar(int x){
        return (char)(x + 48);
    }


}
