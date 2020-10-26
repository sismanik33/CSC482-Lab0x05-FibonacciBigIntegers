public class MyBigInteger {
    String value;
    //    int numDigits = value.length();
    MyBigInteger(){
        this.value = "0";
    }

    MyBigInteger(String integer){
        this.value = integer;
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

//    public static MyBigInteger times(MyBigInteger b){
//        for (int i = b.value.length()-1; i <= 0 ; i++) {
//
//        }
//    }

    private int convertToInt(char c){
        return c - 48;
    }
    private char convertToChar(int x){
        return (char)(x + 48);
    }


}
