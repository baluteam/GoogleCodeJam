/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.codejam.year2020.round1A.patternMatching01;

import java.util.*;
import java.io.*;

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
    private static final String OUTPUT_FORMAT = "Case #%d: %s"; //Use with String.format - 1.: number of the test case, 2.: solution
    private static final String ASTERISK = "*";
    private static final char ASTERISK_CH = '*';
    private static final String ASTERISK_PLACEHOLDER = "";
    
    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        final int T = in.nextInt(); //number of test cases
        in.nextLine();
test:   for (int currentTestCase = 1; currentTestCase <= T; currentTestCase++) {
            final int N = in.nextInt(); //number of patterns
            in.nextLine();
            final String[] PATTERNS = new String[N];
            final List<String[]> PATTERN_PARTS = new ArrayList();
            for(int pi=0; pi<N; pi++) {
                PATTERNS[pi] = in.nextLine().trim();
                List<String> patternPartsExtended = new ArrayList();
                char[] patternChars = PATTERNS[pi].toCharArray();
                String lastValue = null;
                for(char pChar : patternChars) {
                    //testPrint("char: " + pChar);
                    if(pChar == ASTERISK_CH) {
                        patternPartsExtended.add(ASTERISK_PLACEHOLDER);
                        lastValue = null;
                        //testPrint("ASTERISK " + Arrays.toString(patternPartsExtended.toArray()));
                        continue;
                    }
                    if(lastValue == null) {
                        //if last element in the list was a placeholder for an asterisk
                        lastValue = String.valueOf(pChar);
                        patternPartsExtended.add(lastValue);
                    }
                    else {
                        //otherwise we add this next char to the previous value
                        lastValue += pChar;
                        patternPartsExtended.set((patternPartsExtended.size()-1), lastValue);
                    }
                    //testPrint(Arrays.toString(patternPartsExtended.toArray()));
                }
                PATTERN_PARTS.add(patternPartsExtended.toArray(new String[patternPartsExtended.size()]));
            }
            //start solution
            StringBuilder sb = new StringBuilder();
            //patterns order does not matter, so lets sort it according to the pattern parts and the length of those
            Comparator<String[]> comparator = (String[] p1, String[] p2) -> {
                if(p1.length != p2.length) {
                    //if p2 has more pattern parts, we want it before p1, thus p1 has to get a positive compare value compared to p2
                    return (p1.length < p2.length) ? +1 : -1;
                }
                for(int i=0; i<p1.length; i++) {
                    if(p1[i].length() != p2[i].length()) {
                        //if p2 has more characters, then we want it before p1, that is p1 has to get a positive value
                        return (p1[i].length() < p2[i].length()) ? +1 : -1;
                    }
                }
                //the pattern parts and the pattern part words are of same length
                return 0;
            };
//*
            PATTERN_PARTS.forEach((pi) -> {
                testPrint(Arrays.toString(pi));
            });
//*/
            Collections.sort(PATTERN_PARTS, comparator);
//*
            testPrint("Sorted");
            PATTERN_PARTS.forEach((pi) -> {
                testPrint(Arrays.toString(pi));
            });
//*/
            //add the first parts into our matcher string array
            String[] bestMatches = PATTERN_PARTS.get(0);
            int bestStartMatchI, bestEndMatchI;
            int startMatchI, endMatchI;
            for(int i=1; i<PATTERN_PARTS.size(); i++) {
                String[] pi = PATTERN_PARTS.get(i);
                bestStartMatchI = 0;
                bestEndMatchI = bestMatches.length - 1;
                startMatchI = 0;
                endMatchI = pi.length - 1;
                while(bestStartMatchI <= bestEndMatchI && startMatchI <= endMatchI) {
                    testPrint("(" + bestStartMatchI + "," + startMatchI + ") - " + "'" + bestMatches[bestStartMatchI] + "' startsWith '" + pi[startMatchI] + "'?");
                    if(bestMatches[bestStartMatchI].startsWith(pi[startMatchI])) {
                        //this is fine, our best match from the beginning contains and ends with the current pattern from the beginning
                        testPrint("yes");
                    }
                    else if(pi[startMatchI].startsWith(bestMatches[bestStartMatchI])) {
                        //our current pattern from the beginning contains and ends with our best match from the beginning, we need to swap
                        bestMatches[bestStartMatchI] = pi[startMatchI];
                        testPrint("swap and yes");
                    }
                    else {
                        //beginning or end does not match, there is no solution
                        System.out.println(String.format(OUTPUT_FORMAT, currentTestCase, ASTERISK));
                        continue test;
                    }
                    testPrint("(" + bestEndMatchI + "," + endMatchI + ") - " + "'" + bestMatches[bestEndMatchI] + "' endsWith '" + pi[endMatchI] + "'?");
                    if(bestMatches[bestEndMatchI].endsWith(pi[endMatchI])) {
                        //this is fine, our best match from the end contains and ends with the current pattern from the end
                        testPrint("yes");
                    }
                    else if(pi[endMatchI].endsWith(bestMatches[bestEndMatchI])) {
                        //our current pattern from the end contains and ends with our best match from the end, we need to swap
                        bestMatches[bestEndMatchI] = pi[endMatchI];
                        testPrint("swap and yes");
                    }
                    else {
                        //beginning or end does not match, there is no solution
                        System.out.println(String.format(OUTPUT_FORMAT, currentTestCase, ASTERISK));
                        continue test;
                    }
                    bestStartMatchI++;
                    bestEndMatchI--;
                    startMatchI++;
                    endMatchI--;
                }
            } //end of iterating over all patterns
            for(String p : bestMatches) {
                sb.append(p);
            }
            //end solution
            System.out.println(String.format(OUTPUT_FORMAT, currentTestCase, sb.toString()));
        } //end of test cases
    } //end of main
    
    private static void testPrint(String s) {
        //System.out.println(s);
    }
}
