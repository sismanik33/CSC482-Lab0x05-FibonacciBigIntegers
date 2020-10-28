import java.math.BigInteger;

public class FibBig {
    public static void main(String[] args) {

        MyBigInteger x = new MyBigInteger("5579889987");
        MyBigInteger y = new MyBigInteger("5879899");
//        MyBigInteger bigResult = x.times(y);

//        multiplicationTest();


//        for (int i = 4; i < 100; i++) {
//            MyBigInteger bigResult = fibMatrixBig(i);
//            System.out.println(i + ": " + bigResult.value);
//        }

        MyBigInteger bigResult = fibLoopBig(500);
        System.out.println(bigResult.value);
//        BigInteger myBigIntToBigInt = new BigInteger(bigResult.value);
//        BigInteger fib300 = new BigInteger("222232244629420445529739893461909967206666939096499764990979600");
//
//        System.out.println(fib300.toString());
//        if(fib300.compareTo(myBigIntToBigInt) == 0)
//            System.out.println("They match!");
//        MyBigInteger myBigFib400 = fibLoopBig(400);
//        BigInteger fib400 = new BigInteger("176023680645013966468226945392411250770384383304492191886725992896575345044216019675");
//        BigInteger convertFib400 = new BigInteger(myBigFib400.value);
//        if (fib400.compareTo(convertFib400) == 0 )
//            System.out.println(fib400.toString());
//
//        MyBigInteger myBigFib500 = fibLoopBig(500);
//        BigInteger fib500 = new BigInteger("139423224561697880139724382870407283950070256587697307264108962948325571622863290691557658876222521294125");
//        BigInteger convertFib500 = new BigInteger(myBigFib500.value);
//        if (fib500.compareTo(convertFib500) == 0 )
//            System.out.println(fib500.toString());



    }

    public static void multiplicationTest(){
        for (long i = 1; i < 10000000; i*=2) {
            for (long j = 1; j < 10000000; j*=3) {
                long result = i * j;
                MyBigInteger iBig = new MyBigInteger(Long.toString(i));
                MyBigInteger jBig = new MyBigInteger(Long.toString(j));
                MyBigInteger kBig = iBig.times(jBig);
                if (j % 729 == 0){
                    System.out.println(iBig.value + " * " + jBig.value + " = " + kBig.abbreviatedValue());
                }
                if (result != Long.parseLong(kBig.value)){
                    System.out.println("Test failed: " + iBig.value + " * " + jBig.value + "does not equal: " + kBig.value);
                }
            }
        }
    }
    public static MyBigInteger fibLoopBig(int x){
        MyBigInteger A = new MyBigInteger("0");
        MyBigInteger B = new MyBigInteger("1");
        if ( x < 2) {
            String xStr = Integer.toString(x);
            MyBigInteger xBig = new MyBigInteger(xStr);
            return xBig;
        }
        for (int i = 2; i <= x; i++) {
            MyBigInteger next = new MyBigInteger();
            next = A.plus(B);
            A = B;
            B = next;
        }
        return B;
    }

    public static MyBigInteger fibMatrixBig(int n){
        MyBigInteger zeroZero = new MyBigInteger("1");
        MyBigInteger zeroOne = new MyBigInteger("1");
        MyBigInteger oneZero = new MyBigInteger("1");
        MyBigInteger oneOne = new MyBigInteger("0");
        MyBigInteger F[][] = new MyBigInteger[][]{ {zeroZero,zeroOne},{oneZero,oneOne} };
        if (n == 0){
            MyBigInteger zero = new MyBigInteger("0");
            return zero;
        }
        power(F, n-1);

        return F[0][0];
    }

    static void multiply(MyBigInteger F[][], MyBigInteger M[][])
    {
        MyBigInteger x =  F[0][0].times(M[0][0]).plus( F[0][1].times(M[1][0]) );
        MyBigInteger y =  F[0][0].times(M[0][1]).plus( F[0][1].times(M[1][1]) );
        MyBigInteger z =  F[1][0].times(M[0][0]).plus( F[1][1].times(M[1][0]) );
        MyBigInteger w =  F[1][0].times(M[0][1]).plus( F[1][1].times(M[1][1]) );

        F[0][0] = x;
        F[0][1] = y;
        F[1][0] = z;
        F[1][1] = w;
    }

    static void power(MyBigInteger F[][], int n)
    {
        if(n==0 || n==1) return;
        power(F,n/2);
        multiply(F,F);

        MyBigInteger zeroZero = new MyBigInteger("1");
        MyBigInteger zeroOne = new MyBigInteger("1");
        MyBigInteger oneZero = new MyBigInteger("1");
        MyBigInteger oneOne = new MyBigInteger("0");
        MyBigInteger m[][]={{zeroZero,zeroOne},{oneZero,oneOne} };
        if(n%2!=0)
            multiply(F,m);
    }
}
