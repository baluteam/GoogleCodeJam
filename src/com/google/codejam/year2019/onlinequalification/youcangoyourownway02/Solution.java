package com.google.codejam.year2019.onlinequalification.youcangoyourownway02;

import java.util.*;
import java.io.*;

/**
 * <b><u>02_You Can Go Your Own Way</u><b/> <br/><br/>
 * 
 * Solution in Hungarian:
1. Vegen az ellenfel E-vel ment be? --> Akkor nekem (-1*E) S-sel kell.
	opponentLastStep = E
	myLastStep := -1 * E = S
2. Az elejen az ellenfel hogy nyitott?
	Az ellentetevel, mint amivel bement, (-1 * opponentLastStep) = myLastStep(S)-sel? 
		--> Huzzak ki a matrix vegeig (N-1)-szer opponentLastStep-pel majd (N-1)-szer myLastStep
	Ugyan azzal, amivel bement, opponentLastStep(E)-vel? 
		--> Hol van az elso olyan, ahol myLastStep-->myLastStep (SS) van? Ott kell atvagni
			lepese: 				12345678
			ove: 					EESSSESE
			hol?: 					  3
			en lepeseim legyenek:	
				(*-1) annyiszor (ahany szimpla S volt neki SS-ig + 1) => (0+1) db S
				utana akkor atmehetunk a vegeig: (N-1) * opponentLastStep(E)
				majd a maradek myLastStep(S)
								SEEEESSS
				numberOfStepsLeftToTheEast:    443210000
				numberOfStepsLeftToTheSouth:   433333210
 */
public class Solution {
    private static final String OUTPUT_FORMAT = "Case #%d: %s"; //Use with String.format - 1.: number of the test case, 2.:the string concatenation of the path to travel
    private static enum Direction {
        E("East"),
        S("South")
        ;
        private final String NAME;
        private Direction(String name) {
            this.NAME = name;
        }
        /**
         * Returns the opposite of the given direction.
         * 
         * @param direction
         * @return 
         */
        public Direction getOpposite(Direction direction) {
            if(direction == null) {
                return null;
            }
            return (direction == E) ? S : E;
        }
        /**
         * Returns the enum equivalent of the given String.
         * 
         * @param directionStr
         * @return 
         */
        public Direction valueFor(String directionStr) {
            for(Direction value : Direction.values()) {
                if(value.name().equalsIgnoreCase(directionStr)) {
                    return value;
                }
            }
            //return Enum.valueOf(Direction.class, directionStr);
            //return Direction.valueOf(directionStr);
            return null;
        }
    }
    private static final String E = Direction.E.name();
    private static final String S = Direction.S.name();
    
    /**
     * Helper method to speed up everything by not using the pretty enums.
     * 
     * @param direction
     * @return 
     */
    private static String getOpposite(String direction) {
        if(E.equals(direction)) {
            return S;
        }
        return E;
    }
    /**
     * Makes a String path of the given direction with the length of the given numberOfTimes.
     * 
     * @param direction
     * @param numberOfTimes
     * @return 
     */
    private static String makePath(String direction, int numberOfTimes) {
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<numberOfTimes; i++) {
            sb.append(direction);
        }
        return sb.toString();
    }
    
    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        final int T = in.nextInt(); //number of test cases
        for (int currentTestCase = 1; currentTestCase <= T; currentTestCase++) {
            final int N = in.nextInt(); //maze size: NxN matrix
            final String P = in.next(); //opponent's path String containing only 'S' for South and 'E' for East. It's length is (2*N - 2)
            final String OPPONENTS_LAST_STEP = P.substring(P.length()-1); //the last step/char: S or E
            final String OPPONENTS_FIRST_STEP = P.substring(0, 1); //the first step/char: S or E
            final String MY_LAST_STEP = getOpposite(OPPONENTS_LAST_STEP); //the opposite of the above how I should step into the last cell
            //final String MY_FIRST_STEP = getOpposite(OPPONENTS_FIRST_STEP);
            StringBuilder myPath = new StringBuilder();
            if(OPPONENTS_FIRST_STEP.equals(MY_LAST_STEP)) {
                int numberOfStepsIntoEachDirectionFromBeginning = N - 1;
                myPath.append(makePath(OPPONENTS_LAST_STEP, numberOfStepsIntoEachDirectionFromBeginning));
                myPath.append(makePath(MY_LAST_STEP, numberOfStepsIntoEachDirectionFromBeginning));
            }
            else {
                int numberOfStepsLeftToOneDirection = N - 1;
                int numberOfStepsLeftToTheOtherDirection = N - 1;
                String pathOfOpponentToCrossOver = makePath(MY_LAST_STEP,2);
                int indexOfOpponentWhereToCrossOver = P.indexOf(pathOfOpponentToCrossOver); //there most be at least 1 solution as promised
                int numberOfMyLastStepsTillCrossingOver = 1; //at least 1, because in the first cell we need to go into the different direction
                char myLastStep = MY_LAST_STEP.charAt(0);
                for(int i=0; i<indexOfOpponentWhereToCrossOver; i++) {
                    if(P.charAt(i) == myLastStep) {
                        numberOfMyLastStepsTillCrossingOver++; //we increase that we need to go to this direction one time more to find the place where to cross over
                    }
                }
                myPath.append(makePath(MY_LAST_STEP, numberOfMyLastStepsTillCrossingOver));
                numberOfStepsLeftToOneDirection -= numberOfMyLastStepsTillCrossingOver;
                myPath.append(makePath(OPPONENTS_LAST_STEP, numberOfStepsLeftToTheOtherDirection));
                //numberOfStepsLeftToTheOtherDirection -= numberOfStepsLeftToTheOtherDirection;
                myPath.append(makePath(MY_LAST_STEP, numberOfStepsLeftToOneDirection));
                //numberOfStepsLeftToOneDirection -= numberOfStepsLeftToOneDirection;
            }
            System.out.println(String.format(OUTPUT_FORMAT, currentTestCase, myPath.toString()));
        }
    }
}