package com.google.codejam.year2019.onlinequalification.foregonesolution01;

import java.util.*;
import java.io.*;

/**
 * <b><u>01_Foregone Solution</u><b/> <br/><br/>
 * 
 * Solution: we go through the digits in the string one by one, and where it is the BAD_NUMBER(4), we use a addition like (3 and 1). <br/> 
 * At any other digit we use the original digit in A and 0 in B.
 */
public class Solution {
    private static final String OUTPUT_FORMAT_STRINGS = "Case #%d: %s %s"; //Use with String.format - 1.: number of the test case, 2.:first number, 3.: second number
    private static final int RADIX = 10;
    private static final int BAD_NUMBER_INT = 4;
    private static final int BAD_NUMBER_INT_B = 1;
    private static final char BAD_NUMBER_CHAR_B = (char)(BAD_NUMBER_INT_B + '0'); //using typecasting and adding the missing char value to get back character '1'
    private static final char BAD_NUMBER_CHAR_A = Character.forDigit((BAD_NUMBER_INT - BAD_NUMBER_INT_B), RADIX);
    private static final char BAD_NUMBER_CHAR = Character.forDigit(BAD_NUMBER_INT, RADIX);
    
    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        final int T = in.nextInt(); //number of test cases
        for (int currentTestCase = 1; currentTestCase <= T; currentTestCase++) {
            //giveBruteForceSolution(currentTestCase, in);
            //this solution works on the numbers as string so they can be of length of anything
            final String N_str = in.next().trim();
            final char[] a = new char[N_str.length()]; //max char array size will be 100 * 2 bytes, so there should be no need for optimized storage e.g. with LinkedArray<Character> 
            final char[] b = new char[N_str.length()];
            char currentDigit;
            final char NO_DIGIT_FOR_B = '0';
            int firstValidNumberForB = -1; //there must be at least one BAD_NUMBER in the input
            for(int i=0; i<N_str.length(); i++) { //we iterate over the digits of the number
                currentDigit = N_str.charAt(i);
                if(currentDigit == BAD_NUMBER_CHAR) { //if current digit is 4
                    a[i] = BAD_NUMBER_CHAR_A; //3
                    b[i] = BAD_NUMBER_CHAR_B; //1
                    if(firstValidNumberForB < 0) {
                        firstValidNumberForB = i;
                    }
                }
                else {
                    a[i] = currentDigit;
                    b[i] = NO_DIGIT_FOR_B;
                }
            }
            String a_str = new String(a);
            String b_str = new String(Arrays.copyOfRange(b, firstValidNumberForB, b.length));
            System.out.println(String.format(OUTPUT_FORMAT_STRINGS, currentTestCase, a_str, b_str));
        }
    }
    
    /**
     * The brute force solution. This prints the solution to the output as expected as well. <br/>
     * <b>NOTE</b>: <br/>
     * Here we exoect the visible test cases, where 1 &lt; N &lt; 10^9, which can still fit into an int. <br/>
     * 10^9 &lt; Integer.MAX_VALUE(=2,147,483,647): an int can safely store a number less than 10^9, but can't store 10^10 <br/>
     * 10^18 &lt; Long.MAX_VALUE(=9,223,372,036,854,775,807): a long can safely store a number less than 10^18, but can't store 10^19 <br/>
     * The hidden test cases are of 1 &lt; N &lt; 10^100. That needs to treat the number as String or as BigInteger <br/>
     * 
     * @param currentTestCase the number of the current test case
     * @param in the input stream where we need to read the next N jamcoins number for the current test case
     * @deprecated it uses bruteforce to find the first proper numbers and works currently only for the range of int types
     */
    @Deprecated
    private static void giveBruteForceSolution(final int currentTestCase, Scanner in) {
        final String OUTPUT_FORMAT_DIGITS = "Case #%d: %d %d"; //Use with String.format - 1.: number of the test case, 2.:first number, 3.: second number
        final String N_str = in.next(); //number of jamcoins as string to divide
        try {
            int N_int = Integer.parseInt(N_str); //in.nextInt(); 
            int a, b;
            a = solve_bruteForce(N_int);
            b = N_int - a;
            System.out.println(String.format(OUTPUT_FORMAT_DIGITS, currentTestCase, a, b));
        }
        catch(NumberFormatException e) {
            System.out.println("Bruteforce solution for int types can't be used here.\n" + e);
            System.exit(-1);
        }
    }
    
    /**
     * Brute force solution that starts from one end and tries the possible solutions until it finds the first, where both numbers do not contain the BAD_NUMBER.
     * 
     * @param N the jamcoins number, that contains the BAD_NUMBER, thus needs to be divided for 2 good numbers.
     * @return 'a': one number. The other number can be calculated with: N - a
     * @deprecated it uses bruteforce to find the first proper numbers and works currently only for the range of int types
     */
    @Deprecated
    private static int solve_bruteForce(final int N) {
        final String BAD_NUMBER = "4";
        int a=0, b=0;
        for(int i=1; i<N; i++) {
            a = i;
            b = N - i;
            if(Integer.toString(a).contains(BAD_NUMBER)) {
                continue;
            }
            if(Integer.toString(b).contains(BAD_NUMBER)) {
                continue;
            }
            break;
        }
        return a;
    }
}