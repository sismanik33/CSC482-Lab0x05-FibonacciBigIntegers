import java.awt.*;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.util.Hashtable;
import java.util.Random;

public class FibBig {
    public static void main(String[] args) {


//        additionResultsTable();
        multiplicationResultsTable();
//        additionTest();
//        multiplicationTest();
//        compareToBigIntCalculations("+");
//        compareToBigIntCalculations("*");
//        genFibBigFunctionResultsTable(2);
//        genFibBigFunctionResultsTable(2);
//        fibFunctionTest1000();


    }

    public static void fibFunctionTest1000(){
        for (int i = 990; i < 1001 ; i++) {
            MyBigInteger loop = fibLoopBig(i);
            MyBigInteger matrix = fibMatrixBig(i);
            String dash = "-";
            dash = dash.repeat(50);
            System.out.println("X= " + i + " fibLoopBig returns:\t\t" + loop.abbreviatedValue());
            System.out.println("X= " + i + " fibMatrixBig returns:\t" + loop.abbreviatedValue());
            System.out.println(dash);
        }

    }
    public static void multiplicationTest(){
        boolean testFailed = false;
        System.out.println("Multiplying numbers below 1 billion with MyBigInt.times and comparing results to Java long multiplication:");
        for (long i = 1; i < 1000000000; i*=2) {
            for (long j = 1; j < 1000000000; j*=3) {
                long result = i * j;
                MyBigInteger iBig = new MyBigInteger(Long.toString(i));
                MyBigInteger jBig = new MyBigInteger(Long.toString(j));
                MyBigInteger kBig = iBig.times(jBig);
                if (j % 531441 == 0){
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

    public static void compareToBigIntCalculations(String operator){
        String opStr = (operator == "+") ? "addition":"multiplication";
        System.out.println("Comparing MyBigInt " + opStr + " to Java BigInteger calculations using N-length random numbers:" );
        for (int N = 1; N < 1200000; N*=2) {
            String rand1 = generateRandomNDigitNum(N);
            String rand2 = generateRandomNDigitNum(N);
            MyBigInteger a = new MyBigInteger(rand1);
            MyBigInteger b = new MyBigInteger(rand2);
            MyBigInteger c = new MyBigInteger();
            BigInteger x = new BigInteger(rand1);
            BigInteger y = new BigInteger(rand2);
            BigInteger expResult;
            if (operator == "+") {
                c = a.plus(b);
                expResult = x.add(y);
            }
            else {
                c = a.times(b);
                expResult = x.multiply(y);
            }
            BigInteger calcResult = new BigInteger(c.value);
            if (expResult.compareTo(calcResult) == 0){
                System.out.format("%3s%-8d%10s %s %-10s  =  %-20s\n", "N: ", N, a.abbreviatedValue(), operator, b.abbreviatedValue(), c.abbreviatedValue());
            }else {
                    System.out.format("%****ERROR: 10s %s %-10s  =  %-20s is not correct****\n", a.abbreviatedValue(), operator, b.abbreviatedValue(), c.abbreviatedValue());
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
        System.out.println("Adding numbers below 1 billion with MyBigInt.plus and comparing results to Java long addition:");
        for (long i = 1; i < 1000000000; i*=2) {
            for (long j = 1; j < 1000000000; j*=3) {
                long result = i + j;
                MyBigInteger iBig = new MyBigInteger(Long.toString(i));
                MyBigInteger jBig = new MyBigInteger(Long.toString(j));
                MyBigInteger kBig = iBig.plus(jBig);
                if (j % 531441 == 0){
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


    public static void genFibBigFunctionResultsTable(int testToRun){
        long currTime[] = new long[9];
        long prevTime[] = new long[9];
        long avgNTimes[] = new long[10];
        double expTenXRatio = 0;
        double expTenNRatio = 0;
        long cumulativeNTime = 0;
        String dash = "-";
        dash = dash.repeat(105);
        for (int N = 1; N < 100; N++) {
            System.out.format("%-10s%-15s%-20s%-20s%10s%10s%10s\n", "N (size)", "X (input val)", "fib(X)", "Run Time", "10x Ratio", "T_x exp", "T_n exp");
            cumulativeNTime = 0;
            for (int i = 0; i < 9; i++) {
                int x = (int)Math.pow(10, N - 1);
                x = (i+1) * x;
                long before;
                long after;
                MyBigInteger result = new MyBigInteger();
                if (testToRun == 1) {
                    before = getCpuTime();
                    result = fibLoopBig(x);
                    after = getCpuTime();
                } else {
                    before = getCpuTime();
//                    BigInteger soln = fibMatrixJavaBig(x);
//                    result.value = soln.toString();
                    result = fibMatrixBig(x);
                    after = getCpuTime();
                }
                currTime[i] = after - before;
                cumulativeNTime += currTime[i];
                double tenRatio = (N > 1) ? (double)currTime[i]/(double)prevTime[i] : 0.0;
                expTenXRatio = (testToRun == 1) ? 4 : Math.log(x) * 4;
                if (N > 1)
                    expTenNRatio = (testToRun == 1) ? Math.pow(Math.log(N),2) : Math.pow(N, 2)/Math.pow((N-1),2);
                else
                    expTenNRatio = 0.0;
                System.out.format("%-10s%-15s%-20s%-20s%10.5f%10.5f%10.5f\n", N, x, result.abbreviatedValue(), currTime[i], tenRatio, expTenXRatio, expTenNRatio);
                prevTime[i] = currTime[i];
            }
//            avgNTimes[N] = cumulativeNTime/9;
//            System.out.println(dash);
//            if(N > 1)
//                System.out.format("%-10s%-15s%-20s%-20s%10.5f%10.5f%10s\n", "N = ", N, "average:", avgNTimes[N], (double)avgNTimes[N]/(double)avgNTimes[N-1],
//                        expTenXRatio, expTenNRatio);
//            else
//                System.out.format("%-10s%-15s%-20s%-20s%10s%10s%10s\n", "N = ", N, "average:", avgNTimes[N], "-",
//                        expTenXRatio, expTenNRatio);
//            System.out.println(dash);
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

    public static MyBigInteger fibMatrixBig(int x){
        MyBigInteger zeroZero = new MyBigInteger("1");
        MyBigInteger zeroOne = new MyBigInteger("1");
        MyBigInteger oneZero = new MyBigInteger("1");
        MyBigInteger oneOne = new MyBigInteger("0");
        MyBigInteger F[][] = new MyBigInteger[][]{ {zeroZero,zeroOne},{oneZero,oneOne} };
        if (x == 0){
            MyBigInteger zero = new MyBigInteger("0");
            return zero;
        }
        power(F, x-1);

        return F[0][0];
    }

//    public static BigInteger fibFormula(int x, int precision){
//        BigDecimal five = new BigDecimal("5");
//        BigDecimal two = new BigDecimal("2");
//        BigDecimal one = new BigDecimal("1");
//        MathContext mc = new MathContext(precision);
//        BigDecimal sqrt5 = five.sqrt(mc);
//        BigDecimal phiNumerator = sqrt5.add(one, mc);
//
//        BigDecimal phi = phiNumerator.divide(two, mc);
//        BigDecimal sqrt5toN = sqrt5.pow(x, mc);
//    }

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

    static void power(MyBigInteger F[][], int x)
    {
        if(x==0 || x==1) return;
        power(F,x/2);
        multiply(F,F);

        MyBigInteger zeroZero = new MyBigInteger("1");
        MyBigInteger zeroOne = new MyBigInteger("1");
        MyBigInteger oneZero = new MyBigInteger("1");
        MyBigInteger oneOne = new MyBigInteger("0");
        MyBigInteger m[][]={{zeroZero,zeroOne},{oneZero,oneOne} };
        if(x%2!=0)
            multiply(F,m);
    }

    /** Get CPU time in nanoseconds since the program(thread) started. */
    /** from: http://nadeausoftware.com/articles/2008/03/java_tip_how_get_cpu_and_user_time_benchmarking#TimingasinglethreadedtaskusingCPUsystemandusertime **/
    public static long getCpuTime( ) {
        ThreadMXBean bean = ManagementFactory.getThreadMXBean( );
        return bean.isCurrentThreadCpuTimeSupported( ) ?
                bean.getCurrentThreadCpuTime( ) : 0L;
    }

    public static BigInteger fibMatrixJavaBig(int x){
        BigInteger zeroZero = new BigInteger("1");
        BigInteger zeroOne = new BigInteger("1");
        BigInteger oneZero = new BigInteger("1");
        BigInteger oneOne = new BigInteger("0");
        BigInteger F[][] = new BigInteger[][]{ {zeroZero,zeroOne},{oneZero,oneOne} };
        if (x == 0){
            BigInteger zero = new BigInteger("0");
            return zero;
        }
        powerJavaBig(F, x-1);

        return F[0][0];
    }

    static void multiplyJavaBig(BigInteger F[][], BigInteger M[][])
    {
        BigInteger x =  F[0][0].multiply(M[0][0]).add( F[0][1].multiply(M[1][0]) );
        BigInteger y =  F[0][0].multiply(M[0][1]).add( F[0][1].multiply(M[1][1]) );
        BigInteger z =  F[1][0].multiply(M[0][0]).add( F[1][1].multiply(M[1][0]) );
        BigInteger w =  F[1][0].multiply(M[0][1]).add( F[1][1].multiply(M[1][1]) );

        F[0][0] = x;
        F[0][1] = y;
        F[1][0] = z;
        F[1][1] = w;
    }

    static void powerJavaBig(BigInteger F[][], int x)
    {
        if(x==0 || x==1) return;
        powerJavaBig(F,x/2);
        multiplyJavaBig(F,F);

        BigInteger zeroZero = new BigInteger("1");
        BigInteger zeroOne = new BigInteger("1");
        BigInteger oneZero = new BigInteger("1");
        BigInteger oneOne = new BigInteger("0");
        BigInteger m[][]={{zeroZero,zeroOne},{oneZero,oneOne} };
        if(x%2!=0)
            multiplyJavaBig(F,m);
    }
}
