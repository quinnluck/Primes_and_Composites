package test;


import java.math.BigInteger;
import java.util.Random;

/**
 * In this quiz, you will consider this question:
 Suppose that you choose at random an n-bit integer N (i.e., N's binary representation consists of 1 followed by n-1 other bits).  You will be determining the answers to the following three questions for various values of n:
 A) What is the probability that N is prime?
 B) What is the probability that N is a composite that fools the primality test from Figure 1.7 of the text for a=2?
 C) What is the probability that N is a composite that doesn't fool the Fermat primality test from Figure 1.7 of the text for a=2?
 The three probabilities should add up to 1.0, of course.
 You are to determine these probabilities experimentally by randomly generating and testing a variety of N-bit integers.  Unless you have a better idea, you should use the java.math.BigInteger class, which provides all the constructors and methods that you'll need.  Please read the API documentation thoroughly before you start asking questions about this class.
 */
public class QuizFour {

    private Random random;
    private final BigInteger TWO = new BigInteger("2");

    public QuizFour(){
        random = new Random();
    }

    public static void main(String[] args){
        QuizFour solution = new QuizFour();
        double trials = 100000;
        double totalProbability = 0.0;

        // Question two asks what the probability N is prime for some 32 bit integer
        for(int i = 0; i < trials; i++){
            totalProbability += solution.runTestA(128, 100, 10);
        }

        double probabilityA = totalProbability / trials;
        System.out.println("Probability A:\t" + probabilityA);

        // Question four asks what the probability that N is composite and fools the primality test provided by the book
        totalProbability = 0.0;
        for(int i = 0; i < trials; i++){
            totalProbability += solution.runTestB(128, 100, 10);
        }

        double probabilityB = totalProbability / trials;
        System.out.println("Probability B:\t" + probabilityB);

        // Question six asks what the probability that N is composite and does not fool the primality test provided by the book
        totalProbability = 0.0;
        for(int i = 0; i < trials; i++){
            totalProbability += solution.runTestC(128, 100, 10);
        }

        double probabilityC = totalProbability / trials;
        System.out.println("Probability C:\t" + probabilityC);

        totalProbability = probabilityA + probabilityB + probabilityC;

        System.out.println("Probability Sum:\t" + (totalProbability));
        System.out.println("Margin of error:\t" + (1.0 - totalProbability));
    }


    /**
     * Returns the probability of that a number of length numberOfBits is prime. Certainty is used to check if the
     * number is probably prime, where the probability that the number generated is prime is equal to 1-(1/2)^certainty.
     *
     * @param numberOfBits
     * @param trials
     * @param certainty
     * @return
     */
    public double runTestA(int numberOfBits, int trials, int certainty){
        double primeNumbersGenerated = 0;
        double totalNumbersGenerated = (double) trials;

        for(int i = 0; i < trials; i++){
            BigInteger number = new BigInteger(numberOfBits, random);

            if(number.isProbablePrime(certainty))
                primeNumbersGenerated += 1.0;
        }

        return primeNumbersGenerated / totalNumbersGenerated;
    }

    /**
     * Returns the probability that some composite number of length numberOfBits tricks the primality test given in the book.
     *
     * @param numberOfBits
     * @param trials
     * @param certainty
     * @return
     */
    public double runTestB(int numberOfBits, int trials, int certainty){
        double compositeNumbersGenerated = 0;
        double totalNumbersGenerated = (double)trials;

        for(int i = 0; i < trials; i++){
            BigInteger number = new BigInteger(numberOfBits, random);

            if(!number.isProbablePrime(certainty) && primalityTest(number))
                compositeNumbersGenerated += 1.0;
        }

        return compositeNumbersGenerated / totalNumbersGenerated;
    }

    /**
     * Returns the probability that some composite number of length numberOfBits doesn't trick the primality test given in the book.
     *
     * @param numberOfBits
     * @param trials
     * @param certainty
     * @return
     */
    public double runTestC(int numberOfBits, int trials, int certainty){
        double compositeNumbersGenerated = 0;
        double totalNumbersGenerated = (double)trials;

        for(int i = 0; i < trials; i++){
            BigInteger number = new BigInteger(numberOfBits, random);

            if(!number.isProbablePrime(certainty) && !primalityTest(number))
                compositeNumbersGenerated += 1.0;
        }

        return compositeNumbersGenerated / totalNumbersGenerated;
    }

    /**
     * Helper method. Basically the primality test from the algorithms book, figure 1.7.
     *
     * @param number
     * @return
     */
    private boolean primalityTest(BigInteger number){
        // Return true if 2^(N-1) mod N = 1
        if(TWO.modPow(number.subtract(BigInteger.ONE), number).equals(BigInteger.ONE))
            return true;

        return false;
    }
}