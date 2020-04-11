package com.google.codejam.year2019.onlineround1.c1.robotprogrammingstrategy01;

import java.util.*;
import java.io.*;

/**
 * <b><u>1C_01_Robot_Programming_Strategy</u></b> <br/><br/>
 */
public class Solution {
    /**
     * Separator character of new input lines.
     */
    private static final String OUTPUT_FORMAT = "Case #%d: %s"; //Use with String.format - 1.: number of the test case, 2.: the winning RPS code or IMPOSSIBLE
    private static final char R = 'R'; //rock
    private static final Character RC = 'R'; //rock
    private static final char P = 'P'; //paper
    private static final Character PC = 'P'; //paper
    private static final char S = 'S'; //scissors
    private static final Character SC = 'S'; //scissors
    private static final String IMPOSSIBLE = "IMPOSSIBLE";
    private static final int MAX_STEPS_BEFORE_IMPOSSIBLE = 500;
    
    private static enum RockPaperScissors {
        R, //rock
        P, //paper
        S; //scissors
        public Boolean isBeating(RockPaperScissors rps) {
            if(this == R) {
                if(rps == R) return null;
                if(rps == P) return Boolean.FALSE;
                if(rps == S) return Boolean.TRUE;
            }
            if(this == P) {
                if(rps == R) return Boolean.TRUE;
                if(rps == P) return null;
                if(rps == S) return Boolean.FALSE;
            }
            if(this == S) {
                if(rps == R) return Boolean.FALSE;
                if(rps == P) return Boolean.TRUE;
                if(rps == S) return null;
            }
            throw new IllegalArgumentException(this + " VS " + rps + " is unknown.");
        }
    }
    
    public static char getIthStepOfCode(int stepI, String robotCodeI) {
        //1 <= stepI <= MAX_STEPS_BEFORE_IMPOSSIBLE
        int whichCharacter = stepI % robotCodeI.length();
        int charAt = (whichCharacter == 0) ? (robotCodeI.length()-1) : (whichCharacter-1);
        return robotCodeI.charAt(charAt);
    }
    
    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        final int T = in.nextInt(); //number of test cases
        in.nextLine();
        final String[] solution = new String[T];
        for (int currentTestCase = 1; currentTestCase <= T; currentTestCase++) {
            final int A = in.nextInt(); //other robots
            in.nextLine();
            final String[] CI = new String[A]; //the i-th robot's code/program
            for(int i=0; i<A; i++) {
                String codeI = in.nextLine();
                CI[i] = codeI;
            }
            //for(int i=0; i<5; i++) { System.out.println((i+1) + ". command: " + getIthStepOfCode((i+1), CI[0])); }
            StringBuilder myCode = new StringBuilder();
            Set<Character> otherProgramStepped = new HashSet();
            int currentStep = 0; // == myCode.length()
steps:      while(currentStep <= MAX_STEPS_BEFORE_IMPOSSIBLE) { //we do maximal 500 steps
                for (String otherRobotsCode : CI) { //at every iteration start checking every robot's code
                    char otherIthRobotStepAt = getIthStepOfCode((currentStep+1), otherRobotsCode);
                    otherProgramStepped.add(otherIthRobotStepAt);
                    if(otherProgramStepped.size() == 3) { //if in this iteration every R, P or S is already added, we can't wint
                        myCode.delete(0, myCode.length());
                        myCode.append(IMPOSSIBLE);
                        break steps;
                    }
                }
                char myCurrentStep;
                if(!otherProgramStepped.contains(RC)) { //if there were no R steps from other programs, we make this step to beat them
                    myCurrentStep = R;
                }
                else if(!otherProgramStepped.contains(PC)) {
                    myCurrentStep = P;
                }
                else {
                    myCurrentStep = S;
                }
                // ???
                myCode.append(myCurrentStep);
                // ???
                otherProgramStepped.clear();
                currentStep++;
                break steps;
            }
            solution[currentTestCase-1] = myCode.toString();
        }
        for (int currentTestCase = 1; currentTestCase <= T; currentTestCase++) {
            System.out.println(String.format(OUTPUT_FORMAT, currentTestCase, solution[currentTestCase-1]));
        }
    }
}