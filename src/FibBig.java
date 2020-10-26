import java.math.BigInteger;

public class FibBig {
    public static void main(String[] args) {
//        int x = 100;
        MyBigInteger bigResult = new MyBigInteger();
//        bigResult = fibLoopBig(100);
//        for (int i = 0; i < 150; i++) {
//            bigResult = fibLoopBig(i);
//            System.out.println(i + ": " + bigResult.value);
//        }
        bigResult = fibLoopBig(300);
        System.out.println(bigResult.value);
        BigInteger myBigIntToBigInt = new BigInteger(bigResult.value);
        BigInteger fib300 = new BigInteger("222232244629420445529739893461909967206666939096499764990979600");

        System.out.println(fib300.toString());
        if(fib300.compareTo(myBigIntToBigInt) == 0)
            System.out.println("They match!");
        MyBigInteger myBigFib400 = fibLoopBig(400);
        BigInteger fib400 = new BigInteger("176023680645013966468226945392411250770384383304492191886725992896575345044216019675");
        BigInteger convertFib400 = new BigInteger(myBigFib400.value);
        if (fib400.compareTo(convertFib400) == 0 )
            System.out.println(fib400.toString());

        MyBigInteger myBigFib500 = fibLoopBig(500);
        BigInteger fib500 = new BigInteger("139423224561697880139724382870407283950070256587697307264108962948325571622863290691557658876222521294125");
        BigInteger convertFib500 = new BigInteger(myBigFib500.value);
        if (fib500.compareTo(convertFib500) == 0 )
            System.out.println(fib500.toString());



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

    public static long fibMatrixBig(long n){
        long F[][] = new long[][]{{1,1},{1,0}};
        if (n == 0)
            return 0;
        power(F, n-1);

        return F[0][0];
    }

    static void multiply(long F[][], long M[][])
    {
        long x =  F[0][0]*M[0][0] + F[0][1]*M[1][0];
        long y =  F[0][0]*M[0][1] + F[0][1]*M[1][1];
        long z =  F[1][0]*M[0][0] + F[1][1]*M[1][0];
        long w =  F[1][0]*M[0][1] + F[1][1]*M[1][1];

        F[0][0] = x;
        F[0][1] = y;
        F[1][0] = z;
        F[1][1] = w;
    }

    static void power(long F[][], long n)
    {
        if(n==0 || n==1) return;
        power(F,n/2);
        multiply(F,F);

        long m[][]={{1,1},{1,0} };
        if(n%2!=0)
            multiply(F,m);
    }
}
