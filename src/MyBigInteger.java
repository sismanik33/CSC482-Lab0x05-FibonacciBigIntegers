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
        if (this.value.length() < b.value.length()){
            int diff = b.value.length() - this.value.length();
            String zero = "0";
            zero = zero.repeat(diff);
            this.value = zero + this.value;
        } else {
            int diff = this.value.length() - b.value.length();
            String zero = "0";
            zero = zero.repeat(diff);
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
                return c;
            } else{
                dC = sum;
                carry = 0;
            }
            charArr[i] = convertToChar(dC);
        }
        c.value = String.valueOf(charArr);
        return c;
    }

    public MyBigInteger times(MyBigInteger b){

        int currPowerOfTen = -1; //used for added 0's in multiplication
        MyBigInteger temp1 = new MyBigInteger();
        MyBigInteger temp2 = new MyBigInteger();
        MyBigInteger c = new MyBigInteger();
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

    private int convertToInt(char c){
        return c - 48;
    }
    private char convertToChar(int x){
        return (char)(x + 48);
    }


}
