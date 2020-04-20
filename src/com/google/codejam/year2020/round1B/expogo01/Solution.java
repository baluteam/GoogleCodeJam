/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.codejam.year2020.round1B.expogo01;

import java.util.*;
import java.io.*;

/*
Problem
You have just received the best gift ever: an Expogo stick. You can stand on it and use it to make increasingly large jumps.

You are currently standing on point (0, 0) in your infinite two-dimensional backyard, and you are trying to reach a goal point (X, Y), with integer coordinates, in as few jumps as possible. You must land exactly on the goal point; it is not sufficient to pass over it on a jump.

Each time you use your Expogo stick to jump, you pick a cardinal direction: north, south, east, or west. The i-th jump with your Expogo stick moves you 2(i-1) units in the chosen direction, so your first jump takes you 1 unit, your second jump takes you 2 units, your third jump takes you 4 units, and so on.

Given a goal point (X, Y), determine whether it is possible to get there, and if so, demonstrate how to do it using as few jumps as possible.

Input
The first line of the input gives the number of test cases, T. T test cases follow. Each consists of a single line with two integers X and Y: the coordinates of the goal point.

Output
For each test case, output one line containing Case #x: y, where x is the test case number (starting from 1) and y is IMPOSSIBLE if the goal point cannot be reached. Otherwise, y must be a string of one or more characters, each of which is either N (north), S (south), E (east), or W (west), representing the directions of the jumps that you will make, in order. This sequence of jumps must reach the goal point at the end of the final jump, and it must be as short as possible.

Limits
Time limit: 20 seconds per test set.
Memory limit: 1GB.
(X, Y) ? (0, 0).

Test set 1 (Visible Verdict)
1 ? T ? 80.
-4 ? X ? 4.
-4 ? Y ? 4.

Test set 2 (Visible Verdict)
1 ? T ? 100.
-100 ? X ? 100.
-100 ? Y ? 100.

Test set 3 (Visible Verdict)
1 ? T ? 100.
-109 ? X ? 109.
-109 ? Y ? 109.

Sample

Input
 	
Output
 
4
2 3
-2 -3
3 0
-1 1

  
Case #1: SEN
Case #2: NWS
Case #3: EE
Case #4: IMPOSSIBLE

  
In Sample Case #1, you can jump south from (0, 0) to (0, -1), then jump east to (2, -1), then jump north to (2, 3).

We can be sure there is not a more efficient solution (two moves or fewer) because at least 2 + 3 = 5 units of distance are needed to reach the goal point, but the first two jumps combined only give us 3 units of distance.

Notice that Sample Case #2 is like Sample Case #1 but reflected across both axes, and so the answer comes from reflecting all directions in Sample Case #1's answer.

In Sample Case #3, notice that EWE would not be a valid answer, even though it reaches the target, because there is a way to get there using fewer jumps.

We leave it to you to determine why it is impossible to reach the target in Sample Case #4.

Google analyzis:
Analysis
Test Set 1
Test Set 1 is small enough to solve by hand. We can speed this up with a couple of observations:

We can notice that every position with an even (X + Y) (apart from the origin) ? hereafter an "even" position ? seems to be unreachable. We can prove to ourselves that this is true: our initial X and Y coordinates of (0, 0) are both even, but only the first of our possible jumps (the 1-unit one) is of an odd length, and all jumps after that are of even lengths. So there is no way to reach any other "even" position starting from the origin, no matter how much jumping we do.
We can find that all "odd" positions, on the other hand, can be reached using no more than 3 moves.
To speed up solving the "odd" positions, we can take advantage of symmetry, as suggested in the explanation for Sample Case #2. For example, if we learn that EEN is a solution for (3, 4), then we also know that WWS is a solution for (-3, -4), and EES is a solution for (3, -4), and so on. Because of all the horizontal, vertical, and diagonal symmetry, there are really only six fundamentally different cases!
We can check that our solutions are optimally short by using an argument like the one in the explanation for Sample Case #1. Any position with a Manhattan distance (that is, |X| + |Y|) of 1 cannot be reached in fewer than one jump; positions with Manhattan distances up to 3 and 7 require at least two or three jumps, respectively. If our solution lengths match these lower bounds ? and they probably do unless we have jumped in an unusually indirect way ? then they are valid.
Test Set 2
Based on the observations above, we may think to try a breadth-first search of all possible jumping paths, and continue until every "odd" (X, Y) position (with -100 ? X ? 100 and -100 ? Y ? 100) has been reached. It turns out that each such position is reachable in no more than 8 moves. We know that these solutions are optimally short because of the breadth-first nature of the search.

Test Set 3
Suppose that (X, Y) = (7, 10). In what direction should we make our initial 1-unit jump? As we saw above, we need our final X coordinate to be odd, but it is currently even, and we have only one chance to go from even to odd. Moving north or south will make our Y coordinate odd, but then we will never have another chance to make that even and the X coordinate odd. So we should either move west or east. For now, let's guess that we will go west; we will revisit the other possibility later.

That jump will take us to (-1, 0), and we will next need to make a 2-unit jump. Notice that we can make this look identical to the original problem setup, if we make two changes:

Shift (-1, 0) to be the new (0, 0). Then the goal becomes (8, 10) rather than (7, 10).
Transform the scale of the problem such that a 2-unit jump (to a "neighboring" cell) becomes the new 1-unit jump. Then the goal becomes (4, 5) instead of (8, 10).
With this in mind, let's revisit our original decision to jump to the west. If we had jumped east instead, we would have ended up at (1, 0), and if we had changed the problem in the same way we did above, our new goal would have been (3, 5). But that would be an "even" position (after rescaling), which cannot be reached! So we had no choice after all; we had to move west to be able to eventually reach the goal. It's a good thing we were so lucky!

So now the problem has "reset", and we are at (0, 0) and trying to get to (4, 5). In what direction should we make our "first" jump? Now we know we must move vertically, since 5 is odd and we will only have "one chance" to go from even to odd. If we jump north, the next rescaling will have a target of (2, 2), but if we jump south, the target will be (2, 3), which is the "odd" position that we want. From there, we should jump south to change the target to (1, 2), then east to change the target to (0, 1). At that point, we have a choice between jumping north and reaching the goal, and jumping south (which could still allow us to reach the goal after some further moves, e.g. one more to the south and then one more to the north). But the problem requires that we choose the shortest solution, so we should jump right to the goal! Therefore, the answer in this case is WSSEN.

Notice that this method is deterministic: we always have only one choice out of the four possible directions. We can rule out two of them because they will not make the correct coordinate odd. Of the other two, the new goal states they would leave us with must differ only in one of the coordinates and only by exactly 1 unit, and therefore one must be an "odd" position and the other must be an "even" position. It is possible that that "even" position is the goal, in which case we should jump there, but otherwise, we must choose the "odd" position.

The above analysis also shows that the only time we have a choice is when one of those options is to jump directly to the goal, in which case we obviously should. So we can be confident that our method produces the shortest possible solution. (We also know that that solution is unique, since if we were to choose to not jump directly to the goal when we had that option, we would only end up with a longer solution.)

Our method has a running time which is logarithmic in the magnitudes of the coordinates, so it will solve this test set blazingly fast!

A Code Jam callback!
This problem is a riff on the Pogo problem from Round 1C in 2013. If you were familiar with that problem, the analysis might have helped a bit with this one... but, like a well-designed pogo stick, Expogo is not too difficult to get a handle on anyway.

??
Syntax pre-check
Show Test Input

My test cases:
13
2 3
3 2
-2 -3
3 0
-1 1
1 4
4 1
3 4
3 6
3 8
3 10
3 12
3 14


@see https://codingcompetitions.withgoogle.com/codejam/round/000000000019fef2/00000000002d5b62
*/
public class Solution {
    private static final boolean LOCAL_TESTING = false;
    private static final boolean LOCAL_TESTING_SOLUTION = false;
    private static final String OUTPUT_FORMAT = "Case #%d: %s"; //Use with String.format - 1.: number of the test case, 2.: solution
    private static final String SEPARATOR = " ";
    private static final String IMPOSSIBLE = "IMPOSSIBLE";
    private static final char NORTH_C = 'N';
    private static final char SOUTH_C = 'S';
    private static final char EAST_C = 'E';
    private static final char WEST_C = 'W';
    private static final String NORTH = NORTH_C+"";
    private static final String SOUTH = SOUTH_C+"";
    private static final String EAST = EAST_C+"";
    private static final String WEST = WEST_C+"";
    private static final List<Integer> POWERS_OF_2 = new ArrayList();
    static {
        int max_pow = 32;
        for(int pow=0; pow<32; pow++) {
            int pow_of_2 = (int)Math.pow(2, pow);
            testPrintln("2^" + pow + " = " + pow_of_2);
            POWERS_OF_2.add(pow_of_2);
        }
    }
    
    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        final int T = in.nextInt(); //number of test cases
        in.nextLine();
test:   for (int currentTestCase = 1; currentTestCase <= T; currentTestCase++) {
            StringBuilder sb = new StringBuilder();
            final int X = in.nextInt(); //X coordinate to reach (10^9 fits at least 4 times into an int)
            final int Y = in.nextInt(); //Y coordinate to reach
            in.nextLine();
            testPrintln("Goal: " + X + "," + Y);
            final int X_ABS = Math.abs(X);
            final int Y_ABS = Math.abs(Y);
            if((X_ABS + Y_ABS) % 2 == 0) {
                //even position, we cant reach it
                //System.out.println(String.format(OUTPUT_FORMAT, currentTestCase, IMPOSSIBLE));
                printSolution(X+" "+Y, currentTestCase, IMPOSSIBLE);
                continue test;
            }
            //lets solve only for the positive quarter and we just need to negate it later
            if(X == 0 || Y == 0) { //both are not allowed to be 0
                int notNullValue;
                notNullValue = (X == 0) ? Y_ABS : X_ABS;
                int powerOf2Pos = -1;
                for(int poz=0; poz<POWERS_OF_2.size(); poz++) {
                    int powerOf2 = POWERS_OF_2.get(poz);
                    testPrintln(notNullValue + " - " + powerOf2 + " = " + (notNullValue - powerOf2) + " < 0? ->" + (notNullValue - powerOf2 < 0));
                    if(notNullValue - powerOf2 < 0) {
                        //the previous power is what we are looking for, the current is too much
                        powerOf2Pos = poz - 1;
                        testPrintln("Found power of 2 poz to use:" + powerOf2Pos + ". (" + notNullValue + " - " + "2^" + powerOf2Pos + " = " + (notNullValue-POWERS_OF_2.get(powerOf2Pos)) + ")");
                        break;
                    }
                }
                if(powerOf2Pos == -1) {
                    //we did not find it
                    //System.out.println(String.format(OUTPUT_FORMAT, currentTestCase, IMPOSSIBLE));
                    printSolution(X+" "+Y, currentTestCase, IMPOSSIBLE);
                    continue test;
                }
                int yRest = notNullValue;
                int direction = -1;
                for(int poz=powerOf2Pos; 0<=poz; poz--) {
                    //lets go backward
                    testPrintln(yRest + (direction == -1 ? " - " : " + ") + "2^" + poz + "=" + POWERS_OF_2.get(poz));
                    yRest = (direction == -1) ? (yRest - POWERS_OF_2.get(poz)) : (yRest + POWERS_OF_2.get(poz));
                    testPrintln("yRest = " + yRest);
                    sb.insert(0, ((direction == -1) ? NORTH : SOUTH));
                    if(yRest == 0) {
                        //System.out.println(String.format(OUTPUT_FORMAT, currentTestCase, sb.toString()));
                        //continue test;
                        break; // we reached the goal, we just need to negate the path according to the proper values
                    }
                    else if(0 < yRest && direction == -1) {
                        //still need to reduce = nort steps
                    }
                    else if(yRest < 0 && direction == -1) {
                        //we went over it, we need south steps and to change to addition
                        direction *= -1;
                    }
                    else if(0 < yRest && direction == +1) {
                        //we went over it, we need north steps and to change to addition
                        direction *= -1;
                    }
                    else if(yRest < 0 && direction == +1) {
                        //still need to reduce = nort steps
                    }
                }
                if(X == 0) {
                    if(0 < Y) {
                        //we calculated this in the previous section
                        //System.out.println(String.format(OUTPUT_FORMAT, currentTestCase, sb.toString()));
                        printSolution(X+" "+Y, currentTestCase, sb.toString());
                    }
                    else {
                        //we need to negate
                        StringBuilder sb2 = negate(sb);
                        //System.out.println(String.format(OUTPUT_FORMAT, currentTestCase, sb2.toString()));
                        printSolution(X+" "+Y, currentTestCase, sb2.toString());
                    }
                    continue test;
                }
                else if(Y == 0) {
                    StringBuilder sb2 = rotate90Degree(sb);
                    if(X < 0) {
                        //System.out.println(String.format(OUTPUT_FORMAT, currentTestCase, sb2.toString()));
                        printSolution(X+" "+Y, currentTestCase, sb2.toString());
                    }
                    else {
                        //we need to negate
                        StringBuilder sb3 = negate(sb2);
                        //System.out.println(String.format(OUTPUT_FORMAT, currentTestCase, sb3.toString()));
                        printSolution(X+" "+Y, currentTestCase, sb3.toString());
                    }
                    continue test;
                }
            } //if at least one of the numbers were null
            else {
                //boolean xIsOdd = (X_ABS % 2 == 0) ? false : true; //both can't be even neither odd, one of them has to be even and the other odd
                //boolean yIsOdd = (Y_ABS % 2 == 0) ? false : true; //both can't be even neither odd, one of them has to be even and the other odd
                //testPrintln(X + ":" + (xIsOdd ? "odd" : "even") + " , " + Y + ":" + (yIsOdd ? "odd" : "even"));
                //we just need to calculate for the pozitive values where X_ABS < Y_ABS, after that we can mirror to get Y_ABS < X_ABS and/or negate to get from the other 3 slices
                int xAbsMirror = (X_ABS < Y_ABS) ? X_ABS : Y_ABS;
                int yAbsMirror = (X_ABS < Y_ABS) ? Y_ABS : X_ABS;
                testPrintln("We look for solution for " + xAbsMirror + "," + yAbsMirror + " instead of " + X + "," + Y);
                int euclideanDistance = X_ABS + Y_ABS; //we have to start substracting from Y with the power of 2 where this number is greater with the current but lesst than the next power of 2
                int powerOf2PozToStartWith = -1;
                for(int powerPoz=0; powerPoz<POWERS_OF_2.size()-1; powerPoz++) {
                    int currentPowerOf2 = POWERS_OF_2.get(powerPoz);
                    int nextPowerOf2 = POWERS_OF_2.get(powerPoz+1);
                    testPrintln(currentPowerOf2 + " <? " + euclideanDistance + " <? " + nextPowerOf2 + " --> use 2^" + powerPoz + "=" + currentPowerOf2 + "?");
                    if(currentPowerOf2 < euclideanDistance && euclideanDistance < nextPowerOf2) {
                        powerOf2PozToStartWith = powerPoz;
                        break;
                    }
                }
                if(powerOf2PozToStartWith == -1) {
                    //System.out.println(String.format(OUTPUT_FORMAT, currentTestCase, IMPOSSIBLE));
                    printSolution(X+" "+Y, currentTestCase, IMPOSSIBLE);
                    continue test;
                }
                int directionX = -1;
                int directionY = -1;
                //because we are in the pozitive quarter under its diagonal, we have to start with substracting from Y
                int xRest = xAbsMirror;
                int yRest = yAbsMirror;
                boolean goVertical = true; //false means horizontal movement
                testPrintln("Starting dynamic solution:");
dynSol:         for(int poz=powerOf2PozToStartWith; 0<=poz; poz--) {
                    int currentJumpDistance = POWERS_OF_2.get(poz);
                    testPrint(xRest + "," + yRest);
                    if(goVertical) {
                        if(directionY == -1) {
                            testPrint(" - 0," + currentJumpDistance + " = ");
                            yRest = yRest - currentJumpDistance;
                        }
                        else {
                            testPrint(" + 0," + currentJumpDistance + " = ");
                            yRest = yRest + currentJumpDistance;
                        }
                        sb.insert(0, ((directionY == -1) ? NORTH : SOUTH));
                    }
                    else {
                        //go horizontal
                        if(directionX == -1) {
                            testPrint(" - " + currentJumpDistance + ",0 = ");
                            xRest = xRest - currentJumpDistance;
                        }
                        else {
                            testPrint(" + " + currentJumpDistance + ",0 = ");
                            xRest = xRest + currentJumpDistance;
                        }
                        sb.insert(0, ((directionX == -1) ? EAST : WEST));
                    }
                    testPrintln(xRest + "," + yRest);
                    if(xRest == 0 && yRest == 0) {
                        //we are done, we just need to mirror/negate if needed
                        break dynSol;
                    }
                    if(Math.abs(yRest) < Math.abs(xRest)) {
                        //we need to go horizontally the next step
                        goVertical = false;
                    }
                    else {
                        //we need to go vertically the next step
                        goVertical = true;
                    }
                    directionX = (xRest < 0) ? +1 : -1;
                    directionY = (yRest < 0) ? +1 : -1;
                } // end of dynamic solution's for cyclus
                if(!(xRest == 0 && yRest == 0)) {
                    //System.out.println(String.format(OUTPUT_FORMAT, currentTestCase, IMPOSSIBLE));
                    printSolution(X+" "+Y, currentTestCase, IMPOSSIBLE);
                    continue test;
                }
                // mirroring if needed
                if(Y_ABS < X_ABS) {
                    testPrintln("Mirroring: " + sb.toString());
                    sb = mirrorInThePozitiveQuarterToTheDiagonal(sb);
                    testPrintln("into:      " + sb.toString());
                }
                // negating if needed
                StringBuilder sb2 = new StringBuilder();
                for(int cI=0; cI<sb.length(); cI++) {
                    char charI = sb.charAt(cI);
                    if(X < 0) { //if original goal was on the left side
                        if(charI == EAST_C) {
                            charI = WEST_C;
                        }
                        else if(charI == WEST_C) {
                            charI = EAST_C;
                        }
                    }
                    if(Y < 0) { //if original goal was on the top side
                        if(charI == NORTH_C) {
                            charI = SOUTH_C;
                        }
                        else if(charI == SOUTH_C) {
                            charI = NORTH_C;
                        }
                    }
                    sb2.append(charI);
                }
                sb = sb2;
                //System.out.println(String.format(OUTPUT_FORMAT, currentTestCase, sb.toString()));
                printSolution(X+" "+Y, currentTestCase, sb.toString());
                continue test;
            } //none of the goal parameters were 0
            //System.out.println(String.format(OUTPUT_FORMAT, currentTestCase, sb.toString()));
        } //end of test cases
    } //end of main
    
    /**
     * Helper method to mirror the values from the pozitive quarter to its diagonal from the bottom where X_ABS is less than Y_ABS values.
     * 
     * @param direction
     * @return 
     */
    private static StringBuilder mirrorInThePozitiveQuarterToTheDiagonal(StringBuilder direction) {
        StringBuilder sb = new StringBuilder();
        for(int charPoz=0; charPoz<direction.length(); charPoz++) {
            String rotatedValue = null;
            switch(direction.charAt(charPoz)) {
                case NORTH_C : {
                    rotatedValue = EAST;//
                    break;
                }
                case WEST_C : {
                    rotatedValue = SOUTH;
                    break;
                }
                case SOUTH_C : {
                    rotatedValue = WEST;//
                    break;
                }
                case EAST_C : {
                    rotatedValue = NORTH;//
                    break;
                }
                default: {
                    throw new RuntimeException("Not available direction: " + direction.charAt(charPoz));
                }
            }
            sb.append(rotatedValue);
        }
        return sb;
    }
    
    /**
     * Helper method to rotate the directions in the whole direction 90 degree.
     * 
     * @param direction
     * @return 
     */
    private static StringBuilder rotate90Degree(StringBuilder direction) {
        StringBuilder sb = new StringBuilder();
        for(int charPoz=0; charPoz<direction.length(); charPoz++) {
            String rotatedValue = null;
            switch(direction.charAt(charPoz)) {
                case NORTH_C : {
                    rotatedValue = WEST;
                    break;
                }
                case WEST_C : {
                    rotatedValue = SOUTH;
                    break;
                }
                case SOUTH_C : {
                    rotatedValue = EAST;
                    break;
                }
                case EAST_C : {
                    rotatedValue = NORTH;
                    break;
                }
                default: {
                    throw new RuntimeException("Not available direction: " + direction.charAt(charPoz));
                }
            }
            sb.append(rotatedValue);
        }
        return sb;
    }
    
    /**
     * Helper method to return the negate of a whole direction.
     * 
     * @param direction
     * @return 
     */
    private static StringBuilder negate(StringBuilder direction) {
        StringBuilder sb = new StringBuilder();
        for(int charPoz=0; charPoz<direction.length(); charPoz++) {
            sb.append(negate(direction.charAt(charPoz)));
        }
        return sb;
    }
    
    /**
     * Helper method to return the negate of a direction.
     * 
     * @param direction
     * @return 
     */
    private static String negate(char direction) {
        return negate(String.valueOf(direction));
    }
    
    /**
     * Helper method to return the negate of a direction.
     * 
     * @param direction
     * @return 
     */
    private static String negate(String direction) {
        switch(direction) {
            case EAST: {
                return WEST;
            }
            case WEST: {
                return EAST;
            }
            case NORTH: {
                return SOUTH;
            }
            case SOUTH: {
                return NORTH;
            }
        }
        throw new RuntimeException("Not available direction: " + direction);
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
                System.out.print(" <-- " + (expected.equals(solution) ? "OK" : "NOT OK") + " | '" + expected + "' was expected for input: " + input);
            }
            else if("3 2".equals(input)) {
                expected = "WNE";
                System.out.print(" <-- " + (expected.equals(solution) ? "OK" : "NOT OK") + " | '" + expected + "' was expected for input: " + input);
            }
            else if("-2 -3".equals(input)) {
                expected = "NWS";
                System.out.print(" <-- " + (expected.equals(solution) ? "OK" : "NOT OK") + " | '" + expected + "' was expected for input: " + input);
            }
            else if("3 0".equals(input)) {
                expected = "EE";
                System.out.print(" <-- " + (expected.equals(solution) ? "OK" : "NOT OK") + " | '" + expected + "' was expected for input: " + input);
            }
            else if("-1 1".equals(input)) {
                expected = "IMPOSSIBLE";
                System.out.print(" <-- " + (expected.equals(solution) ? "OK" : "NOT OK") + " | '" + expected + "' was expected for input: " + input);
            }
            else if("1 4".equals(input)) {
                expected = "WEN";
                System.out.print(" <-- " + (expected.equals(solution) ? "OK" : "NOT OK") + " | '" + expected + "' was expected for input: " + input);
            }
            else if("4 1".equals(input)) {
                expected = "SNE";
                System.out.print(" <-- " + (expected.equals(solution) ? "OK" : "NOT OK") + " | '" + expected + "' was expected for input: " + input);
            }
            else if("3 4".equals(input)) {
                expected = "EEN";
                System.out.print(" <-- " + (expected.equals(solution) ? "OK" : "NOT OK") + " | '" + expected + "' was expected for input: " + input);
            }
            else if("3 6".equals(input)) {
                expected = "WSEN";
                System.out.print(" <-- " + (expected.equals(solution) ? "OK" : "NOT OK") + " | '" + expected + "' was expected for input: " + input);
            }
            else if("3 8".equals(input)) {
                expected = "EWEN";
                System.out.print(" <-- " + (expected.equals(solution) ? "OK" : "NOT OK") + " | '" + expected + "' was expected for input: " + input);
            }
            else if("3 10".equals(input)) {
                expected = "WNEN";
                System.out.print(" <-- " + (expected.equals(solution) ? "OK" : "NOT OK") + " | '" + expected + "' was expected for input: " + input);
            }
            else if("3 12".equals(input)) {
                expected = "EENN";
                System.out.print(" <-- " + (expected.equals(solution) ? "OK" : "NOT OK") + " | '" + expected + "' was expected for input: " + input);
            }
            else if("3 14".equals(input)) {
                expected = "WSWEN";
                System.out.print(" <-- " + (expected.equals(solution) ? "OK" : "NOT OK") + " | '" + expected + "' was expected for input: " + input);
            }
            System.out.println();
        }
    }
}
