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
    private static final boolean LOCAL_TESTING = false;
    private static final boolean LOCAL_TESTING_SOLUTION = false;
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
            int walkCounter = 0; //how many blocks we had to walk. 1 block = 1 minutes
            /*
            //Test set 1:
            boolean testSet1 = 
                    (X <= 10) &&
                    (Y <= 10) &&
                    (CAT_PATH.length() <= 8) &&
                    (!(CAT_PATH.contains(E+"") || CAT_PATH.contains(W+"")))
            ;
            boolean testSet2 = 
                    !testSet1 && 
                    (X <= 1000) &&
                    (Y <= 1000) &&
                    (CAT_PATH.length() <= 1000) &&
                    (!(CAT_PATH.contains(E+"") || CAT_PATH.contains(W+"")))
            ;
            boolean testSet3 = !(testSet1 || testSet2);
            */
            boolean CAT_WALKS_ONLY_VERTICAL = !(CAT_PATH.contains(E+"") || CAT_PATH.contains(W+""));
            //I am starting from 0,0. The cat starts east from me and north from me
            int catX = (-1) * X;
            int catY = (-1) * Y;
            int catEndX = catX;
            int catEndY = catY;
            for(int i=0; i<CAT_PATH.length(); i++) {
                char direction = CAT_PATH.charAt(i);
                switch(direction) {
                    case N: {
                        catEndY -= 1;
                        break;
                    }
                    case S: {
                        catEndY += 1;
                        break;
                    }
                    case E: {
                        catEndX -= 1;
                        break;
                    }
                    case W: {
                        catEndX += 1;
                        break;
                    }
                }
            }
            testPrintln("From (" + X + "," + Y + ") that is (" + catX + "," + catY + ") the path '" + CAT_PATH + "' lead to (" + catEndX + "," + catEndY + ").");
            int CAT_END_AWAY_IN_MINUTES = Math.abs(catEndX) + Math.abs(catEndY);
            final int CAT_TOUR_LENGTH_IN_MINUTES = CAT_PATH.length();
            testPrintln("End of tour is in " + CAT_END_AWAY_IN_MINUTES + " blocks/minutes away from me.");
            if(false && CAT_TOUR_LENGTH_IN_MINUTES < CAT_END_AWAY_IN_MINUTES) {
                sb.append(IMPOSSIBLE);
            }
            else { 
                int catMoveX = 0;
                int catMoveY = 0;
                int catMoveNextX = 0;
                int catMoveNextY = 0;
                int myX = 0;
                int myY = 0;
steps:          for(int i=0; i<CAT_PATH.length(); i++) {
                    char direction = CAT_PATH.charAt(i);
                    Character nextDirection = (i+1 < CAT_PATH.length()) ? CAT_PATH.charAt(i+1) : null;
                    switch(direction) {
                        case N: {
                            catMoveX = 0;
                            catMoveY = -1;
                            break;
                        }
                        case S: {
                            catMoveX = 0;
                            catMoveY = 1;
                            break;
                        }
                        case E: {
                            catMoveX = -1;
                            catMoveY = 0;
                            break;
                        }
                        case W: {
                            catMoveX = 1;
                            catMoveY = 0;
                            break;
                        }
                    }
                    if(nextDirection == null) {
                        catMoveNextX = 0;
                        catMoveNextY = 0;
                    }
                    else {
                        switch(nextDirection) {
                            case N: {
                                catMoveNextY = -1;
                                break;
                            }
                            case S: {
                                catMoveNextY = 1;
                                break;
                            }
                            case E: {
                                catMoveNextX = -1;
                                break;
                            }
                            case W: {
                                catMoveNextX = 1;
                                break;
                            }
                        }
                    }
                    //move myself ()neither me, nor the cat has moved in this step yet. Should I wait or should I go?
                    int diffDirectionX = myX - catX;
                    int diffDirectionY = myY - catY;
                    int diffX = Math.abs(diffDirectionX);
                    int diffY = Math.abs(diffDirectionY);
                    if(myX != catX) { //if are X did not meet yet, we go this way and only after in Y direction
                        if(2 <= diffX) { //I can move towards the cat, we wont cross within 1 step
                            if(0 < diffDirectionX) { //to the EAST from me: 0--4 || -1--4 || 3--4
                                myX += -1;
                            }
                            else if(diffDirectionX == 0) { //stay: -4--4 || 3-3
                                myX += 0;
                                throw new RuntimeException("Can't happen, or it means bug in my logical solution");
                            }
                            else {
                                myX += 1;
                            }
                        }
                        else if(1 == diffX) { //I need to check if its next step comes back to me and I should rather not move or move differently
                            String catsPathReminder = CAT_PATH.substring(i, CAT_PATH.length());
                            boolean willstillComeTowardsMe = false;
                            if(0 < diffDirectionX && catsPathReminder.contains(W+"")) { //if cat is to the east from me, but will still come to the west some time
                                willstillComeTowardsMe = true;
                            }
                            else if(diffDirectionX < 0 && catsPathReminder.contains(E+"")) { //if cat is to the west from me, but will still come to the east some time
                                willstillComeTowardsMe = true;
                            }
                            if(willstillComeTowardsMe) { //it will come towards me, lets move rather vertically with it
                                myY += catMoveY; //I move with the cat
                            }
                            else { //wont come towards me
                                int catPozNewX = catX + catMoveX;
                                if(catPozNewX == myX) { //I should not move, cat comes back
                                    myX += 0;
                                }
                                else {
                                    if(0 < diffDirectionX) { //to the EAST from me: 0--4 || -1--4 || 3--4
                                        myX += -1;
                                    }
                                    else if(diffDirectionX == 0) { //stay: -4--4 || 3-3
                                        myX += 0;
                                        throw new RuntimeException("Can't happen, or it means bug in my logical solution");
                                    }
                                    else {
                                        myX += 1;
                                    }
                                }
                            }
                        }
                        else {
                            //myX == catX, can't come here, because we go vertical instead
                        }
                    }
                    else { //our X is the same, move towards Y
                        if(2 <= diffY) { //I can move towards the cat, we wont cross within 1 step
                            if(0 < diffDirectionY) { //to the NORTH from me: 0--4 || -1--4 || 3--4
                                myY += -1;
                            }
                            else if(diffDirectionY == 0) { //stay: -4--4 || 3-3
                                myY += 0;
                                //we reached the cat's position
                            }
                            else {
                                myY += 1;
                            }
                        }
                        else if(1 == diffY) { //I need to check if its next step comes back to me and I should rather not move
                            String catsPathReminder = CAT_PATH.substring(i, CAT_PATH.length());
                            boolean willstillComeTowardsMe = false;
                            if(0 < diffDirectionY && catsPathReminder.contains(S+"")) { //if cat is to the north from me, but will still come to the south some time
                                willstillComeTowardsMe = true;
                            }
                            else if(diffDirectionY < 0 && catsPathReminder.contains(N+"")) { //if cat is to the south from me, but will still come to the north some time
                                willstillComeTowardsMe = true;
                            }
                            if(willstillComeTowardsMe) { //it will come towards me, lets move rather horizontally with it
                                myX += catMoveX; //I move with the cat
                            }
                            else { //wont come towards me
                                int catPozNewY = catY + catMoveY;
                                if(catPozNewY == myY) { //I should not move, cat comes back
                                    myY += 0;
                                }
                                else {
                                    if(0 < diffDirectionY) { //to the NORTH from me: 0--4 || -1--4 || 3--4
                                        myY += -1;
                                    }
                                    else if(diffDirectionY == 0) { //stay: -4--4 || 3-3
                                        myY += 0;
                                        //we reached the cat's position
                                    }
                                    else {
                                        myY += 1;
                                    }
                                }
                            }
                        }
                    }
                    
                    //move the cat according to its path
                    catX += catMoveX;
                    catY += catMoveY;
                    
                    testPrintln("Cat moved to (" + catX + "," + catY + ") and I moved to (" + myX + "," + myY + ").");
                    walkCounter++;
                    if(catX == myX && catY == myY) { //after moving, if at any step we meet, I am done
                        sb.append(walkCounter);
                        printSolution((X+SEPARATOR+Y+SEPARATOR+CAT_PATH), currentTestCase, sb.toString());
                        continue test;
                    }
                } //end of cat tour cyclus
                sb.append(IMPOSSIBLE);
            }
            //System.out.println(String.format(OUTPUT_FORMAT, currentTestCase, "TEST"));
            printSolution((X+SEPARATOR+Y+SEPARATOR+CAT_PATH), currentTestCase, sb.toString());
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
            if("4 4 SSSS".equals(input)) {
                expected = "4";
                testPrint(" <-- " + (expected.equals(solution) ? "OK" : "NOT OK") + " | '" + expected + "' was expected for input: " + input);
            }
            else if("3 0 SNSS".equals(input)) {
                expected = "IMPOSSIBLE";
                testPrint(" <-- " + (expected.equals(solution) ? "OK" : "NOT OK") + " | '" + expected + "' was expected for input: " + input);
            }
            else if("2 10 NSNNSN".equals(input)) {
                expected = "IMPOSSIBLE";
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
            System.err.println(s);
            System.err.flush();
        }
    }
    /**
     * Helper method to print text while locally testing but easily switching it off in the Code Jam submission
     * @param s 
     */
    protected static void testPrint(String s) {
        if(LOCAL_TESTING) {
            System.err.print(s); 
            System.err.flush();
        }
    }
}
