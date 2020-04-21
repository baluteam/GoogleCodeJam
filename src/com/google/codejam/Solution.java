/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.codejam;

import java.util.*;
import java.io.*;

/*
My Google Code Jam template file solution.

@see https://codingcompetitions.withgoogle.com/codejam
*/
public class Solution {
    private static final boolean LOCAL_TESTING = true;
    private static final boolean LOCAL_TESTING_SOLUTION = true;
    private static final String OUTPUT_FORMAT = "Case #%d: %s"; //Use with String.format - 1.: number of the test case, 2.: some string
    private static final String SEPARATOR = " ";
    
    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        final int T = in.nextInt(); //number of test cases
        in.nextLine();
test:   for (int currentTestCase = 1; currentTestCase <= T; currentTestCase++) {
            final int N = in.nextInt(); //some number
            in.nextLine();
            StringBuilder sb = new StringBuilder();
            //System.out.println(String.format(OUTPUT_FORMAT, currentTestCase, "TEST"));
            printSolution((N+""), currentTestCase, sb.toString());
        } //end of test cases
    } //end of main
    
    /**
     * Helper method to print the solution and locally to test the expected values.
     * 
     * @param input the whole input under the testcase concatenated or passed over in any format
     * @param currentTestCase
     * @param solution 
     */
    protected static void printSolution(String input, int currentTestCase, String solution) {
        if(!LOCAL_TESTING_SOLUTION) { //solution for the contest
            System.out.println(String.format(OUTPUT_FORMAT, currentTestCase, solution));
        }
        else {
            System.out.print(String.format(OUTPUT_FORMAT, currentTestCase, solution));
            //TODO feel free to extend the solution with checking expected values
            String expected;
            if("2 3".equals(input)) {
                expected = "SEN";
                testPrint(" <-- " + (expected.equals(solution) ? "OK" : "NOT OK") + " | '" + expected + "' was expected for input: " + input);
            }
            else if("3 2".equals(input)) {
                expected = "WNE";
                testPrint(" <-- " + (expected.equals(solution) ? "OK" : "NOT OK") + " | '" + expected + "' was expected for input: " + input);
            }
            else if("-1 1".equals(input)) {
                expected = "IMPOSSIBLE";
                testPrint(" <-- " + (expected.equals(solution) ? "OK" : "NOT OK") + " | '" + expected + "' was expected for input: " + input);
            }
            testPrintln("");
        }
    }
    
    /**
     * Helper method to print text while locally testing but easily switching it off in the Code Jam submission
     * @param s 
     */
    protected static void testPrintln(String s) {
        if(LOCAL_TESTING) {
            System.out.println(s);
        }
    }
    /**
     * Helper method to print text while locally testing but easily switching it off in the Code Jam submission
     * @param s 
     */
    protected static void testPrint(String s) {
        if(LOCAL_TESTING) {
            System.out.print(s);
        }
    }
}
