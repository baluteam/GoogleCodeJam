/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.codejam.year2020.round1C.overexcitedFan01;

import java.util.*;
import java.io.*;

/*
Problem
Today will be the day?today will be the day that you finally get a picture with Peppurr the cat!

It has just been announced that Peppurr will be touring your city. The city has infinitely many infinitely-long streets running north-south and infinitely many infinitely-long streets running east-west. An intersection is any point at which a north-south street and an east-west street meet. From any given intersection, the closest intersection in each of the four directions (north, east, south and west) is exactly one block away.

You know the exact path that Peppurr's tour will take along those streets. Your goal is to be at one of the intersections on Peppurr's tour at the same time that Peppurr is there, and you want to do so as fast as possible. This is how you will get your picture with Peppurr!

Peppurr's tour starts at an intersection that is X blocks east and Y blocks north of the intersection where you are currently located. Both you and Peppurr take exactly one minute to walk one full block, and must finish each minute at an intersection; neither of you can walk partial blocks.

Peppurr moves along a predefined path. Every minute, you can choose to stand still for the minute, or use it to walk a single block in any of the 4 directions (north, east, south or west). Both you and Peppurr only walk along the streets.

If you and Peppurr are at the same intersection at the same time, you can take a picture, even at the last intersection of the tour. However, Peppurr is unavailable for pictures after the tour ends, so arriving at the tour's final intersection even a single minute after the tour finishes means you will not get a picture.

Is it possible to get a picture with Peppurr? If so, how soon can you do it?

Input
The first line of the input gives the number of test cases, T. T test cases follow. Each case consists of one line containing two integers, X and Y, and a string of characters M. This represents that Peppurr's tour starts exactly X blocks east and Y blocks north of you. The string M is the sequence of moves that Peppurr will make. The i-th character in M is one of N, E, S or W, and corresponds to the direction (north, east, south, or west, respectively) in which Peppurr will walk one block during the tour's i-th minute.

Output
For each test case, output one line with Case #x: y, where x is the test case number (starting from 1). If there is no way to get a picture with Peppurr, y is IMPOSSIBLE. Otherwise, y is the smallest number of minutes from the start of the tour needed to get a picture with Peppurr.

Limits
1 ? T ? 100.
Time limit: 20 seconds per test set.
Memory limit: 1GB.
(X, Y) ? (0, 0). (The tour does not start in the same intersection as you.)

Test Set 1 (Visible Verdict)
0 ? X ? 10.
0 ? Y ? 10.
1 ? length of M ? 8.
Each character in M is an uppercase letter ? either N or S.

Test Set 2 (Visible Verdict)
0 ? X ? 1000.
0 ? Y ? 1000.
1 ? length of M ? 1000.
Each character in M is an uppercase letter ? either N or S.

Test Set 3 (Visible Verdict)
0 ? X ? 1000.
0 ? Y ? 1000.
1 ? length of M ? 1000.
Each character in M is an uppercase letter ? either N, E, S or W.

Sample

Input
 	
Output
 
5
4 4 SSSS
3 0 SNSS
2 10 NSNNSN
0 1 S
2 7 SSSSSSSS
  
Case #1: 4
Case #2: IMPOSSIBLE
Case #3: IMPOSSIBLE
Case #4: 1
Case #5: 5
  
In Sample Case #1, you can walk east four blocks and you will be able to take a picture with Peppurr on the tour's last intersection.

In Sample Case #2, the tour starts off exactly three blocks to the east of you. No matter how you move, you cannot get a picture with Peppurr.

In Sample Case #3, the tour is too far north for you to get the picture before the tour ends.

In Sample Case #4, the tour will come to you after one minute, so you don't even have to move! Enjoy the picture with Peppurr! Remember that you can only take pictures in intersections, so if you moved north while the tour moved south, which would cause you to cross paths with Peppurr outside of an intersection, you could not get your picture in 0.5 minutes.

In Sample Case #5, you can move north twice, then east twice. Then, you can stay still and you will be able to take a picture with Peppurr in the next minute. There are other paths you can take which can get you a picture with Peppurr in 5 minutes, but none which can do it sooner than that.

The following two cases could not appear in Test Set 1 or Test Set 2, but could appear in Test Set 3:

2
3 2 SSSW
4 0 NESW
The correct output for these two cases would be:

Case #1: 4
Case #2: 4
Note that in Case #1, you can take a picture with Peppurr one block to the south and two blocks to the east of your original starting point.

In Case #2, Peppurr travels in a small square. You can take a picture when Peppurr returns to the starting point of that square.

Test inputs:
7
4 4 SSSS
3 0 SNSS
2 10 NSNNSN
0 1 S
2 7 SSSSSSSS
3 2 SSSW
4 0 NESW


@see https://codingcompetitions.withgoogle.com/codejam/round/000000000019fef4/0000000000317409
*/
public class Solution {
    private static final boolean LOCAL_TESTING = true;
    private static final boolean LOCAL_TESTING_SOLUTION = true;
    private static final String OUTPUT_FORMAT = "Case #%d: %s"; //Use with String.format - 1.: number of the test case, 2.: some string
    private static final String SEPARATOR = " ";
    //the directions
    private static final char N = 'N';
    private static final char S = 'S';
    private static final char E = 'E';
    private static final char W = 'W';
    //solution
    private static final String IMPOSSIBLE = "IMPOSSIBLE";
    
    
    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        final int T = in.nextInt(); //number of test cases
        in.nextLine();
test:   for (int currentTestCase = 1; currentTestCase <= T; currentTestCase++) {
            //TODO if it moves towards me and 1 < distanceX && 1 < distanceY, I move the opposite as it?
    
            final int X = in.nextInt(); //x blocks EAST away from my start position
            final int Y = in.nextInt(); //y blocks NORTH away from my start position
            final String CAT_PATH = in.nextLine().trim();
            StringBuilder sb = new StringBuilder();
            int myCurrentX = 0;
            int myCurrentY = 0;
            int catCurrentX = goEast() * X; //I take my starting point as (0,0), so east is -1*X away
            int catCurrentY = goNorth() * Y; //I take my starting point as (0,0), so north is +1*Y away
            //lets calculate for every step of the cat, how long would it take for me to get there
            for(int i=0; i<CAT_PATH.length(); i++) {
                char catCurrentStepWillBe = CAT_PATH.charAt(i);
                //after doing cat"s current step, cat ends up at
                catCurrentX += getNextStepVectorForHorizontalDirection(catCurrentStepWillBe);
                catCurrentY += getNextStepVectorForVerticalDirection(catCurrentStepWillBe);
                //cat's current position after doing the current step is at (catCurrentX,catCurrentY)
                int catWalkCounter = i+1; //how many blocks did the cat walk after doing the current step
                //How long would it take for me to get there?
                int myWalkCounter = 0; //how many blocks we had to walk. 1 block = 1 minutes
                int currentStepsNeededBetweenMeAndCat = Math.abs(catCurrentX - myCurrentX) + Math.abs(catCurrentY - myCurrentY);
                if(catWalkCounter < currentStepsNeededBetweenMeAndCat) {
                    //we cant get there in time, lets move on to the next cat step
                    continue;
                }
                //we can reach this station within the steps the cat did so far
                //since we don't even need my path to get there, to finish this test case, it is enough to know, how many steps the cat did so far
                sb.append(catWalkCounter);
                break;
            }
            
            //print solution for current test case
            if(sb.length() == 0) {
                printSolution((X+SEPARATOR+Y+SEPARATOR+CAT_PATH), currentTestCase, IMPOSSIBLE);
            }
            else {
                //System.out.println(String.format(OUTPUT_FORMAT, currentTestCase, "TEST"));
                printSolution((X+SEPARATOR+Y+SEPARATOR+CAT_PATH), currentTestCase, sb.toString());
            }
        } //end of test cases
    } //end of main
    
    protected static int goEast() {
        return getNextStepVectorForDirection(E);
    }
    protected static int goWest() {
        return getNextStepVectorForDirection(W);
    }
    protected static int goNorth() {
        return getNextStepVectorForDirection(N);
    }
    protected static int goSouth() {
        return getNextStepVectorForDirection(S);
    }
    protected static int getNextStepVectorForHorizontalDirection(char directionToGoNext) {
        switch(directionToGoNext) {
            case E: {
                return goEast();
            }
            case W: {
                return goWest();
            }
            default: {
                return 0; //this step was not horizontal
            }
        }
    }
    protected static int getNextStepVectorForVerticalDirection(char directionToGoNext) {
        switch(directionToGoNext) {
            case N: {
                return goNorth();
            }
            case S: {
                return goSouth();
            }
            default: {
                return 0; //this step was not vertical
            }
        }
    }
    /**
     * Given a valid direction, it gives back the direction vector for it. It is just like a coordinate-system from math class.
     * 
     * @param directionToGoNext
     * @return 
     */
    private static int getNextStepVectorForDirection(char directionToGoNext) {
        switch(directionToGoNext) {
            case E: {
                return -1;
            }
            case W: {
                return 1;
            }
            case N: {
                return 1;
            }
            case S: {
                return -1;
            }
            default: {
                throw new RuntimeException("Wrong direction: " + directionToGoNext);
            }
        }
    }
    
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
            if("4 4 SSSS".equals(input)) {
                expected = "4";
                testPrint(" <-- " + (expected.equals(solution) ? "OK" : "NOT OK") + " | '" + expected + "' was expected for input: " + input);
            }
            else if("3 0 SNSS".equals(input)) {
                expected = IMPOSSIBLE;
                testPrint(" <-- " + (expected.equals(solution) ? "OK" : "NOT OK") + " | '" + expected + "' was expected for input: " + input);
            }
            else if("2 10 NSNNSN".equals(input)) {
                expected = IMPOSSIBLE;
                testPrint(" <-- " + (expected.equals(solution) ? "OK" : "NOT OK") + " | '" + expected + "' was expected for input: " + input);
            }
            else if("0 1 S".equals(input)) {
                expected = "1";
                testPrint(" <-- " + (expected.equals(solution) ? "OK" : "NOT OK") + " | '" + expected + "' was expected for input: " + input);
            }
            else if("2 7 SSSSSSSS".equals(input)) {
                expected = "5";
                testPrint(" <-- " + (expected.equals(solution) ? "OK" : "NOT OK") + " | '" + expected + "' was expected for input: " + input);
            }
            else if("3 2 SSSW".equals(input)) {
                expected = "4";
                testPrint(" <-- " + (expected.equals(solution) ? "OK" : "NOT OK") + " | '" + expected + "' was expected for input: " + input);
            }
            else if("4 0 NESW".equals(input)) {
                expected = "4";
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
            //System.err.println(s);
            //System.err.flush();
        }
    }
    /**
     * Helper method to print text while locally testing but easily switching it off in the Code Jam submission
     * @param s 
     */
    protected static void testPrint(String s) {
        if(LOCAL_TESTING) {
            System.out.print(s); 
            //System.err.print(s); 
            //System.err.flush();
        }
    }
}
