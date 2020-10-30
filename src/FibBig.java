import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.math.BigInteger;
import java.util.Hashtable;
import java.util.Random;

public class FibBig {
    public static void main(String[] args) {
//
//        MyBigInteger x = new MyBigInteger("1");
//        MyBigInteger y = new MyBigInteger("0");
//
//        MyBigInteger z = x.times(y);
//        System.out.println(z.value);
//
////        karatsubaTest();
//        additionTest();
//        multiplicationTest();

//        for (int i = 4; i < 100; i++) {
//            MyBigInteger bigResult = fibMatrixBig(i);
//            System.out.println(i + ": " + bigResult.value);
//        }

//        MyBigInteger bigResult = fibLoopBig(1000);
//        System.out.println(bigResult.value);

//        additionResultsTable();
//        multiplicationResultsTable();
        compareToBigIntMultiplication();
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

    public static void fibFunctionTest(){

    }
    public static void multiplicationTest(){
        boolean testFailed = false;
        for (long i = 1; i < 1000000000; i*=2) {
            for (long j = 1; j < 1000000000; j*=3) {
                long result = i * j;
                MyBigInteger iBig = new MyBigInteger(Long.toString(i));
                MyBigInteger jBig = new MyBigInteger(Long.toString(j));
                MyBigInteger kBig = iBig.times(jBig);
//                if (j % 531441 == 0){
                if (i > 100000000){
                    System.out.format("%10s * %-10s  =  %-20s\n", iBig.value, jBig.value, kBig.value);
                }
                if (result != Long.parseLong(kBig.value)){
                    testFailed = true;
                    System.out.println("Test failed: " + iBig.value + " * " + jBig.value + "does not equal: " + kBig.value);
                }
            }
        }
        if (!testFailed)
            System.out.println("All calculated products matched the results of the long multiplications.");
    }

    public static void compareToBigIntMultiplication(){
        for (int N = 1; N < 10000; N+=5) {
            String rand1 = generateRandomNDigitNum(N);
            String rand2 = generateRandomNDigitNum(N);
            MyBigInteger a = new MyBigInteger(rand1);
            MyBigInteger b = new MyBigInteger(rand2);
            MyBigInteger c = a.times(b);
            BigInteger x = new BigInteger(rand1);
            BigInteger y = new BigInteger(rand2);

            BigInteger expResult = x.multiply(y);
            BigInteger calcResult = new BigInteger(c.value);

            if (expResult.compareTo(calcResult) == 0){
                System.out.format("%3s%-8d%10s * %-10s  =  %-20s\n", "N: ", N, a.abbreviatedValue(), b.abbreviatedValue(), c.abbreviatedValue());
            }else {
                    System.out.format("%****ERROR: 10s * %-10s  =  %-20s is not correct****\n", a.abbreviatedValue(), b.abbreviatedValue(), c.abbreviatedValue());
            }

        }
    }

    public static void karatsubaTest(){
        boolean testFailed = false;
        for (long i = 1; i < 100; i++) {
            for (long j = 1; j < 100; j++) {
                long result = i * j;
                MyBigInteger iBig = new MyBigInteger(Long.toString(i));
                MyBigInteger jBig = new MyBigInteger(Long.toString(j));
                MyBigInteger kBig = iBig.karatsuba(jBig);
//                if (j % 531441 == 0){
                if (j % 3 == 0){
                    System.out.format("%10s * %-10s  =  %-20s\n", iBig.value, jBig.value, kBig.value);
                }
                if (result != Long.parseLong(kBig.value)){
                    testFailed = true;
                    System.out.println("Test failed: " + iBig.value + " * " + jBig.value + " does not equal: " + kBig.value);
                }
            }
        }
        if (!testFailed)
            System.out.println("All calculated products matched the results of the long multiplications.");
    }

    public static void additionTest(){
        boolean testFailed = false;
        for (long i = 1; i < 1000000000; i*=2) {
            for (long j = 1; j < 1000000000; j*=3) {
                long result = i + j;
                MyBigInteger iBig = new MyBigInteger(Long.toString(i));
                MyBigInteger jBig = new MyBigInteger(Long.toString(j));
                MyBigInteger kBig = iBig.plus(jBig);
//                if (j % 531441 == 0){
                if (i > 1000000){
//                    System.out.println(iBig.value + " * " + jBig.value + " = " + kBig.value);
                    System.out.format("%10s + %-10s  =  %-20s\n", iBig.value, jBig.value, kBig.value);
                }
                if (result != Long.parseLong(kBig.value)){
                    testFailed = true;
                    System.out.println("Test failed: " + iBig.value + " * " + jBig.value + "does not equal: " + kBig.value);
                }
            }
        }
        if (!testFailed)
            System.out.println("All calculated sums matched the results of the long additions.");
    }

    public static void additionResultsTable(){
        System.out.format("%-10s|%14s + %-14s = %-20s%-14s%-14s%20s\n", "N", "x value 1:", "x value 2:", "sum", "time", "doubling ratio", "exp doubling ratio");
        long currTime;
        long prevTime = 0;
        for (int N = 1; N < 2000000000; N*=2) {
            MyBigInteger a = new MyBigInteger(generateRandomNDigitNum(N));
            MyBigInteger b = new MyBigInteger(generateRandomNDigitNum(N));

            long before = getCpuTime();
            MyBigInteger c = a.plus(b);
            long after = getCpuTime();
            currTime = after - before;
            double actualDoubling = (N > 1) ? (double)currTime/(double)prevTime : 0.0;
            System.out.format("%-10s|%14s + %-14s = %-20s%-14s%1.5f%20s\n", N, a.abbreviatedValue(),
                    b.abbreviatedValue(), c.abbreviatedValue(), currTime, actualDoubling, "2");
            prevTime = currTime;
        }
    }

    public static void multiplicationResultsTable(){
        System.out.format("%-10s|%14s * %-14s = %-20s%-14s%-14s%20s\n", "N", "x", "y", "product", "time", "doubling ratio", "exp doubling ratio");
        long currTime;
        long prevTime = 0;
        for (int N = 1; N < 2000000; N*=2) {
            MyBigInteger a = new MyBigInteger(generateRandomNDigitNum(N));
            MyBigInteger b = new MyBigInteger(generateRandomNDigitNum(N));

            long before = getCpuTime();
            MyBigInteger c = a.times(b);
            long after = getCpuTime();
            currTime = after - before;
            double actualDoubling = (N > 1) ? (double)currTime/(double)prevTime : 0.0;
            System.out.format("%-10s|%14s * %-14s = %-20s%-14s%1.5f%20s\n", N, a.abbreviatedValue(),
                    b.abbreviatedValue(), c.abbreviatedValue(), currTime, actualDoubling, "4");
            prevTime = currTime;
        }
    }

    public static String generateRandomNDigitNum(int N){
        //code adapted from https://www.baeldung.com/java-random-string
        int leftLimit = 48; // 0
        int rightLimit = 57; // 9
        int targetStringLength = N;
        Random random = new Random();
        String generatedString = random.ints(leftLimit, rightLimit + 1)
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        return generatedString;
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

    /** Get CPU time in nanoseconds since the program(thread) started. */
    /** from: http://nadeausoftware.com/articles/2008/03/java_tip_how_get_cpu_and_user_time_benchmarking#TimingasinglethreadedtaskusingCPUsystemandusertime **/
    public static long getCpuTime( ) {
        ThreadMXBean bean = ManagementFactory.getThreadMXBean( );
        return bean.isCurrentThreadCpuTimeSupported( ) ?
                bean.getCurrentThreadCpuTime( ) : 0L;
    }
}
