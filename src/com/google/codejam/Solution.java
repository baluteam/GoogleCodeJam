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
    private static final String OUTPUT_FORMAT = "Case #%d: %d %s"; //Use with String.format - 1.: number of the test case, 2.: some number, 3.: some string
    private static final String SEPARATOR = " ";
    
    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        final int T = in.nextInt(); //number of test cases
        in.nextLine();
test:   for (int currentTestCase = 1; currentTestCase <= T; currentTestCase++) {
            final int N = in.nextInt(); //some number
            in.nextLine();
            System.out.println(String.format(OUTPUT_FORMAT, currentTestCase, 1, "TEST"));
        } //end of test cases
    } //end of main
    
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
