/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.codejam.year2020.round1A.patternMatching01;

import java.util.*;
import java.io.*;
import java.util.regex.Pattern;

/*
Problem
Many terminals use asterisks (*) to signify "any string", including the empty string. For example, when listing files matching BASH*, a terminal may list BASH, BASHER and BASHFUL. For *FUL, it may list BEAUTIFUL, AWFUL and BASHFUL. When listing B*L, BASHFUL, BEAUTIFUL and BULL may be listed.

In this problem, formally, a pattern is a string consisting of only uppercase English letters and asterisks (*), and a name is a string consisting of only uppercase English letters. A pattern p matches a name m if there is a way of replacing every asterisk in p with a (possibly empty) string to obtain m. Notice that each asterisk may be replaced by a different string.

Given N patterns, can you find a single name of at most 104 letters that matches all those patterns at once, or report that it cannot be done?

Input
The first line of the input gives the number of test cases, T. T test cases follow. Each test case starts with a line with a single integer N: the number of patterns to simultaneously match. Then, N lines follow, each one containing a single string Pi representing the i-th pattern.

Output
For each test case, output one line containing Case #x: y, where x is the test case number (starting from 1) and y is any name containing at most 104 letters such that each Pi matches y according to the definition above, or * (i.e., just an asterisk) if there is no such name.

Limits
Time limit: 20 seconds per test set.
Memory limit: 1GB.
1 ? T ? 100.
2 ? N ? 50.
2 ? length of Pi ? 100, for all i.
Each character of Pi is either an uppercase English letter or an asterisk (*), for all i.
At least one character of Pi is an uppercase English letter, for all i.

Test set 1 (Visible Verdict)
Exactly one character of Pi is an asterisk (*), for all i.
The leftmost character of Pi is the only asterisk (*), for all i.

Test set 2 (Visible Verdict)
Exactly one character of Pi is an asterisk (*), for all i.

Test set 3 (Visible Verdict)
At least one character of Pi is an asterisk (*), for all i.

Sample

Input
 	
Output
 
2
5
*CONUTS
*COCONUTS
*OCONUTS
*CONUTS
*S
2
*XZ
*XYZ

  
Case #1: COCONUTS
Case #2: *

  
In Sample Case #1, there are other possible answers, including COCOCONUTS and ILIKECOCONUTS. Neither COCONUTSAREGREAT nor COCOANUTS would be acceptable. Notice that the same pattern may appear more than once within a test case.

In Sample Case #2, there is no acceptable name, so the answer is *.

The following cases could not appear in Test Set 1, but could appear in Test Set 2 or Test Set 3:

4
H*O
HELLO*
*HELLO
HE*
HELLO and HELLOGOODBYEHELLO are examples of acceptable answers. OTHELLO and HELLOO would not be acceptable.

2
CO*DE
J*AM
There is no name that matches both patterns, so the answer would be *.

2
CODE*
*JAM
CODEJAM is one example of an acceptable answer.

The following cases could not appear in Test Set 1 or Test Set 2, but could appear in Test Set 3:

2
A*C*E
*B*D*
ABCDE and ABUNDANCE are among the possible acceptable answers, but BOLDFACE is not.

2
A*C*E
*B*D
There is no name that matches both patterns, so the answer would be *.

2
**Q**
*A*
QUAIL and AQ are among the possible acceptable answers here.

MY TEST INPUTS:
10
5
*CONUTS
*COCONUTS
*OCONUTS
*CONUTS
*S
2
*XZ
*XYZ
4
H*O
HELLO*
*HELLO
HE*
2
CO*DE
J*AM
2
CODE*
*JAM
2
*AB
*A
5
*CONUTS
*COCONUTS
*OCONUTS
*CONUTS
*C*S
2
A*C*E
*B*D*
2
A*C*E
*B*D
2
**Q**
*A*

@see https://codingcompetitions.withgoogle.com/codejam/round/000000000019fd74/00000000002b3034
*/
public class Solution {
    private static final boolean LOCAL_TESTING = false;
    private static final String OUTPUT_FORMAT = "Case #%d: %s"; //Use with String.format - 1.: number of the test case, 2.: solution
    private static final String ASTERISK = "*";
    
    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        final int T = in.nextInt(); //number of test cases
        in.nextLine();
test:   for (int currentTestCase = 1; currentTestCase <= T; currentTestCase++) {
            final int N = in.nextInt(); //number of patterns
            in.nextLine();
            testPrintln("Test " + currentTestCase + ".: with " + N + " patterns.");
            String firstPart = null;
            List<String> middleParts = new ArrayList();
            String lastPart = null;
            boolean noSolution = false;
pattern:    for(int pi=0; pi<N; pi++) {
                String pattern = in.nextLine().trim();
                if(noSolution) {
                    //if we set it already, that there is no solution for this case, we still have to read in the rest of the patterns and output our answer only after that
                    continue;
                }
                String restOfPattern = pattern;
                //start solution
                testPrintln("Pattern " + (pi) + ".: '" + pattern + "'");
// BLABLA*
                if(!pattern.startsWith(ASTERISK)) {
                    int indexOfFirstAsterisk = pattern.indexOf(ASTERISK);
                    String firstPart_pi = pattern.substring(0, indexOfFirstAsterisk);
                    testPrintln("Solution has to start with: " + firstPart_pi);
                    if(pattern.length() <= indexOfFirstAsterisk + 1) {
                        //first astersik was the last character, there is nothing left after it to handle
                        restOfPattern = "";
                        testPrintln("There is nothing left from the pattern");
                    }
                    else {
                        restOfPattern = pattern.substring(indexOfFirstAsterisk+1, pattern.length());
                        testPrintln("Rest of the pattern to be handled: '" + restOfPattern + "'");
                    }
                    testPrintln("Solution before: " + getCurrentSolutionAsList(firstPart, middleParts, lastPart).toString());
                    if(firstPart == null) {
                        //no start pattern is stored yet
                        firstPart = firstPart_pi;
                    }
                    else {
                        if(firstPart.startsWith(firstPart_pi)) {
                            //"BLABLA".startsWith("BLA"), then we are fine
                        }
                        else if(firstPart_pi.startsWith(firstPart)) {
                            //we need to swap the first part, the previous starts with and is included in this current part
                            firstPart = firstPart_pi;
                        }
                        else {
                            //the current first part has to start like firtPart for this pattern, but it just does not fit with the previous starting pattern part
                            //System.out.println(String.format(OUTPUT_FORMAT, currentTestCase, ASTERISK));
                            //continue test;
                            noSolution = true;
                            continue pattern;
                        }
                    }
                    testPrintln("Solution after: " + getCurrentSolutionAsList(firstPart, middleParts, lastPart).toString());
                } // end of if starts like  BLABLA*
// *BLABLA
                if(!restOfPattern.endsWith(ASTERISK) && !"".equals(restOfPattern)) { //if the rest does not end with asterisk
                    if(!restOfPattern.contains(ASTERISK)) { //but there is no asterisk in it left
                        testPrintln("There was only 1 asterisk in this (rest of the) pattern.");
                        if(lastPart == null) {
                            //no end pattern is stored yet
                            lastPart = restOfPattern;
                        }
                        else {
                            if(lastPart.endsWith(restOfPattern)) {
                                //"BLABLA".endsWith("BLA"), then we are fine
                            }
                            else if(restOfPattern.endsWith(lastPart)) {
                                //we need to swap the last part, the previous ends with and is included in this current part
                                lastPart = restOfPattern;
                            }
                            else {
                                //the current rest part has to end like lastPart for this pattern, but it just does not fit with the previous ending pattern part
                                //System.out.println(String.format(OUTPUT_FORMAT, currentTestCase, ASTERISK));
                                //continue test;
                                noSolution = true;
                                continue pattern;
                            }
                        }
                    }
                    int indexOfLastAsterisk = restOfPattern.lastIndexOf(ASTERISK);
                    //there has to be at least one char after the last asterisk because of this if condition
                    String lastPart_pi = restOfPattern.substring(indexOfLastAsterisk+1, restOfPattern.length());
                    testPrintln("Solution has to end with: " + lastPart_pi);
                    if(indexOfLastAsterisk - 1 < 0) {
                        //last astersik was the first character, there is nothing left before it to handle
                        restOfPattern = "";
                        testPrintln("There is nothing before the pattern");
                    }
                    else {
                        restOfPattern = restOfPattern.substring(0, indexOfLastAsterisk);
                        testPrintln("Rest of the pattern to be handled: '" + restOfPattern + "'");
                    }
                    testPrintln("Solution before: " + getCurrentSolutionAsList(firstPart, middleParts, lastPart).toString());
                    if(lastPart == null) {
                        //no end pattern is stored yet
                        lastPart = lastPart_pi;
                    }
                    else {
                        if(lastPart.endsWith(lastPart_pi)) {
                            //"BLABLA".endsWith("BLA"), then we are fine
                        }
                        else if(lastPart_pi.endsWith(lastPart)) {
                            //we need to swap the last part, the previous ends with and is included in this current part
                            lastPart = lastPart_pi;
                        }
                        else {
                            //the current last part has to start like lastPart for this pattern, but it just does not fit with the previous ending pattern part
                            //System.out.println(String.format(OUTPUT_FORMAT, currentTestCase, ASTERISK));
                            //continue test;
                            noSolution = true;
                            continue pattern;
                        }
                    }
                    testPrintln("Solution after: " + getCurrentSolutionAsList(firstPart, middleParts, lastPart).toString());
                } //end of if ends like *BLABLA
                //the rest is/was between asterisks like *BLABLABLA*, so we just need to split and append them
                testPrintln("Rest of the pattern between the first and last asterisk: '" + restOfPattern + "'");
                if(!"".equals(restOfPattern.trim())) {
                    testPrintln("Solution before: " + getCurrentSolutionAsList(firstPart, middleParts, lastPart).toString());
                    String[] restOfPatternsFromTheMiddle = restOfPattern.split(Pattern.quote(ASTERISK));
                    for(String rest : restOfPatternsFromTheMiddle) {
                        if(!"".equals(rest.trim())) {
                            middleParts.add(rest);
                        }
                    }
                    testPrintln("Solution after: " + getCurrentSolutionAsList(firstPart, middleParts, lastPart).toString());
                } //end adding the middle parts
            } //end of reading in the patterns
            //end solution
            if(noSolution) {
                System.out.println(String.format(OUTPUT_FORMAT, currentTestCase, ASTERISK));
            }
            else {
                printSolution(currentTestCase, firstPart, middleParts, lastPart);
            }
        } //end of test cases
    } //end of main
    
    /**
     * Helper method to get back the current solution as a list.
     * 
     * @param firstPart
     * @param middleParts
     * @param lastPart
     * @return 
     */
    private static List<String> getCurrentSolutionAsList(String firstPart, List<String> middleParts, String lastPart) {
        List<String> currentSolution = new ArrayList();
        currentSolution.add((firstPart == null) ? "" : firstPart);
        if(middleParts != null) {
            middleParts.forEach(middlePart -> {
                currentSolution.add((middlePart == null) ? "" : middlePart);
            });
        }
        currentSolution.add((lastPart == null) ? "" : lastPart);
        return currentSolution;
    }
    
    /**
     * Helper method to get back the current pattern parts.
     * 
     * @param firstPart
     * @param middleParts
     * @param lastPart
     * @return 
     */
    private static String getCurrentSolution(String firstPart, List<String> middleParts, String lastPart) {
        StringBuilder sb = new StringBuilder();
        sb.append((firstPart == null) ? "" : firstPart);
        if(middleParts != null) {
            middleParts.forEach(pattern -> {
                sb.append(pattern);
            });
        }
        sb.append((lastPart == null) ? "" : lastPart);
        return sb.toString();
    }
    
    /**
     * Helper method to print out the solution. 
     * 
     * @param currentTestCase
     * @param firstPart
     * @param middleParts
     * @param lastPart 
     */
    private static void printSolution(int currentTestCase, String firstPart, List<String> middleParts, String lastPart) {
        System.out.println(String.format(OUTPUT_FORMAT, currentTestCase, getCurrentSolution(firstPart,middleParts,lastPart)));
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
