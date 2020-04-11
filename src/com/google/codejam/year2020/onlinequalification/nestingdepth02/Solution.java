/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.codejam.year2020.onlinequalification.nestingdepth02;

import java.util.*;
import java.io.*;

/*
Problem
tl;dr: Given a string of digits S, insert a minimum number of opening and closing parentheses into it such that the resulting string is balanced and each digit d is inside exactly d pairs of matching parentheses.

Let the nesting of two parentheses within a string be the substring that occurs strictly between them. An opening parenthesis and a closing parenthesis that is further to its right are said to match if their nesting is empty, or if every parenthesis in their nesting matches with another parenthesis in their nesting. The nesting depth of a position p is the number of pairs of matching parentheses m such that p is included in the nesting of m.

For example, in the following strings, all digits match their nesting depth: 0((2)1), (((3))1(2)), ((((4)))), ((2))((2))(1). The first three strings have minimum length among those that have the same digits in the same order, but the last one does not since ((22)1) also has the digits 221 and is shorter.

Given a string of digits S, find another string S', comprised of parentheses and digits, such that:
all parentheses in S' match some other parenthesis,
removing any and all parentheses from S' results in S,
each digit in S' is equal to its nesting depth, and
S' is of minimum length.

Input
The first line of the input gives the number of test cases, T. T lines follow. Each line represents a test case and contains only the string S.

Output
For each test case, output one line containing Case #x: y, where x is the test case number (starting from 1) and y is the string S' defined above.

Limits
Time limit: 20 seconds per test set.
Memory limit: 1GB.
1 ? T ? 100.
1 ? length of S ? 100.

Test set 1 (Visible Verdict)
Each character in S is either 0 or 1.

Test set 2 (Visible Verdict)
Each character in S is a decimal digit between 0 and 9, inclusive.

Sample

Input
 	
Output
 
4
0000
101
111000
1
  
Case #1: 0000
Case #2: (1)0(1)
Case #3: (111)000
Case #4: (1)
  
The strings ()0000(), (1)0(((()))1) and (1)(11)000 are not valid solutions to Sample Cases #1, #2 and #3, respectively, only because they are not of minimum length. In addition, 1)( and )(1 are not valid solutions to Sample Case #4 because they contain unmatched parentheses and the nesting depth is 0 at the position where there is a 1.

You can create sample inputs that are valid only for Test Set 2 by removing the parentheses from the example strings mentioned in the problem statement.

My own calculations and test cases:
    0220 	--> 	0((22))0
                            0
                            0((2))
                            0((22))
                            0((22))0
    1220	--> 	(1(22))0
                            (1)
                            (1(2))
                            (1(22))
                            (1(22))0
                        (1)((22))0 --> wrong
    2330	-->	((2(33)))0
                            ((2))
                            ((2(3)))
                            ((2(33)))
                            ((2(33)))0

    2100	-->	((2)1)00
                            ((2))
                            ((2)1)
                            ((2)1)0
                            ((2)1)00

    021         --> 	0((2)1)
    312         -->	(((3))1(2))
    4           -->	((((4))))
    221         -->	((22)1)
                        ((2))((2))(1) --> wrong
input:
8
0000
101
111000
1
021
312
4
221

solution:
Case #1: 0000
Case #2: (1)0(1)
Case #3: (111)000
Case #4: (1)
Case #5: 0((2)1)
Case #6: (((3))1(2))
Case #7: ((((4))))
Case #8: ((22)1)
*/
public class Solution {
    private static final String OUTPUT_FORMAT = "Case #%d: %s"; //Use with String.format - 1.: number of the test case, 2.:the string concatenation of the solution
    private static final String L_P = "("; //left parenthesis
    private static final String R_P = ")"; //right parenthesis
    
    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        final int T = in.nextInt(); //number of test cases
        in.nextLine();
        for (int currentTestCase = 1; currentTestCase <= T; currentTestCase++) {
            String inputDigitsStr = in.nextLine().trim();
            final int[] inputDigits = new int[inputDigitsStr.length()];
            for(int i=0; i<inputDigitsStr.length(); i++) {
                inputDigits[i] = Integer.parseInt(inputDigitsStr.substring(i, i+1));
            }
            //start of solution
            StringBuilder sb = new StringBuilder();
            for(int i=0; i<inputDigits.length; i++) {
                int v = inputDigits[i]; //the current value
                if(v == 0) {
                    sb.append(v);
                }
                else {
                    //it needs to be surrounded exactly with v piece of matching parenthesis
                    if(i == 0) {
                        //if it is the first digit, we just do it
                        String str = surroundDigitWithParenthesis(v,v);
                        sb.append(str);
                    }
                    else {
                        int previousValue = inputDigits[i-1];
                        if(previousValue == v) {
                            //it can be moved between the parenthesis of the previous value, because they are same
                            //System.out.println(sb + " | length:" + sb.length() + ", prevVal:" + previousValue);
                            int prevValuePos = sb.lastIndexOf(""+previousValue);
                            sb.insert(prevValuePos+1, v);
                        }
                        else if(previousValue < v) {
                            int diff = v - previousValue;
                            int prevValuePos = sb.lastIndexOf(""+previousValue);
                            String str = surroundDigitWithParenthesis(diff,v);
                            sb.insert(prevValuePos+1, str);
                        }
                        else {
                            //v < previousValue 
                            sb.insert(sb.length()-v, v);
                        }
                    }
                }
            }
            //end of solution
            System.out.println(String.format(OUTPUT_FORMAT, currentTestCase, sb.toString()));
        } //end of test cases
    }
    
    /**
     * Helper function to create as many multiplication of the given string like left or right parenthesis as needed.
     * 
     * @param count
     * @param str
     * @return 
     */
    private static String multiplyStr(int count, String str) {
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<count; i++) {
            sb.append(str);
        }
        return sb.toString();
    }
    
    /**
     * Helper method to surround the digit with the needed matching parenthesis from both side
     * 
     * @param count
     * @param digit
     * @return 
     */
    private static String surroundDigitWithParenthesis(int count, int digit) {
        StringBuilder sb = new StringBuilder();
        String LEFT_PARENTHESIS = multiplyStr(count, L_P);
        String RIGHT_PARENTHESIS = multiplyStr(count, R_P);
        sb.append(LEFT_PARENTHESIS);
        sb.append(digit);
        sb.append(RIGHT_PARENTHESIS);
        return sb.toString();
    }
}
