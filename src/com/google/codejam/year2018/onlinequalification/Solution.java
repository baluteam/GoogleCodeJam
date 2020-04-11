/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.codejam.year2018.onlinequalification;

import java.util.*;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Balu_ADMIN
 */
public class Solution {
    
    private static boolean LOCAL_TEST = true;
    
    private static final String IMPOSSIBLE = "IMPOSSIBLE";
    private static final String INVALID_INPUT = "INVALID_INPUT: ";
    private static final int MIN_TESTCASES = 1;
    private static final int MAX_TESTCASES = 100;
    private static final int MIN_SHIELD_DURABILITY = 1;
    private static final long MAX_SHIELD_DURABILITY = (long)Math.pow(10, 9);
    private static final int MIN_ROBOT_PROGRAM_LENGTH = 2;
    private static final int MAX_ROBOT_PROGRAM_LENGTH = 30;
    private static final Pattern VALID_ROBOT_PROGRAM_PATTERN = Pattern.compile("[CS]+");
    private static final int STARTING_DAMAGE = 1;
    private static final char CHARGE = 'C';
    private static final char SHOOT = 'S';
    private static final String CHARGE_SHOOT = CHARGE + "" + SHOOT;
    
    private static class ProgramResult {
        private long currentDamage;
        private long currentShieldDurability;
        private int numberOfShoots;
        private boolean wouldDieWithoutHack;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        
        if(LOCAL_TEST) {
            //Mimic the input
            final String MIMICED_INPUT = 
                "6\n" +
                "1 CS\n" +
                "2 CS\n" +
                "1 SS\n" +
                "6 SCCSSC\n" +
                "2 CC\n" +
                "3 CSCSS"
            ;
            in = new Scanner(MIMICED_INPUT);
        }
        
        int numberOfInputs = in.nextInt();
        if(LOCAL_TEST && (numberOfInputs < MIN_TESTCASES || MAX_TESTCASES < numberOfInputs)) {
            System.out.println(INVALID_INPUT + "number of test cases: " + numberOfInputs);
            System.exit(-1);
        }
        for (int testCase = 1; testCase <= numberOfInputs; ++testCase) {
            final int SHIELD_DURABILITY = in.nextInt();
            if(LOCAL_TEST && (SHIELD_DURABILITY < MIN_SHIELD_DURABILITY || MAX_SHIELD_DURABILITY < SHIELD_DURABILITY)) {
                System.out.println(INVALID_INPUT + "shiled durability: " + SHIELD_DURABILITY);
                System.exit(-1);
            }
            final String ROBOT_PROGRAM = in.nextLine().trim();
/*        System.out.println("Case #" + testCase + " - durability: " + SHIELD_DURABILITY + ", program '" + ROBOT_PROGRAM + "'"); //*/
            int robotProgramLength = ROBOT_PROGRAM.length();
            if(LOCAL_TEST && (robotProgramLength < MIN_ROBOT_PROGRAM_LENGTH || MAX_ROBOT_PROGRAM_LENGTH < robotProgramLength)) {
                System.out.println(INVALID_INPUT + "robot program length(" + robotProgramLength + "): " + ROBOT_PROGRAM);
                System.exit(-1);
            }
            if(LOCAL_TEST) {
                Matcher matcher = VALID_ROBOT_PROGRAM_PATTERN.matcher(ROBOT_PROGRAM);
                if(!matcher.matches()) {
                    System.out.println(INVALID_INPUT + ROBOT_PROGRAM);
                    System.exit(-1);
                }
            }
            
            /*
                ALGORITHM STARTS FROM HERE
            */
            ProgramResult result = executeCurrentProgram(ROBOT_PROGRAM, STARTING_DAMAGE, SHIELD_DURABILITY);
            if(!result.wouldDieWithoutHack) {
                //if current program would not destroy earth, there is no need for hack
                printResultOfHackSolution(testCase,0);
                continue;
            }
            if(SHIELD_DURABILITY < result.numberOfShoots) {
                //if every shoot would be positioned as first shoots with damage 1, but the shield cant whitstand it
                printResultOfHackSolution(testCase,IMPOSSIBLE);
                continue;
            }
            //Hacking algorithm START
            int numberOfHacksNeeded = 0;
            StringBuilder currentProgram = new StringBuilder(ROBOT_PROGRAM);
            do {
                //the last shoot is the bigest one, so we should swap always the last one with a charge to decrase damage
                int lastIndexOfChareAndShoot = currentProgram.lastIndexOf(CHARGE_SHOOT);
                //System.out.println(lastIndexOfChareAndShoot);
                if(lastIndexOfChareAndShoot == -1) {
                    break;
                }
                numberOfHacksNeeded++;
                currentProgram.setCharAt(lastIndexOfChareAndShoot, SHOOT);
                currentProgram.setCharAt(lastIndexOfChareAndShoot+1, CHARGE);
                //System.out.println("numberOfHacksNeeded: " + numberOfHacksNeeded + ", currentProgram: " + currentProgram);
                result = executeCurrentProgram(currentProgram.toString(), STARTING_DAMAGE, SHIELD_DURABILITY);
            } while(result.wouldDieWithoutHack);
            
            printResultOfHackSolution(testCase,numberOfHacksNeeded);
        }
    }
    
    private static void printResultOfHackSolution(int testCase, String result) {
        StringBuilder sb = (new StringBuilder("Case #")).append(testCase).append(": ").append(result);
        System.out.println(sb);
    }
    
    private static void printResultOfHackSolution(int testCase, int numberOfHacks) {
        StringBuilder sb = (new StringBuilder("Case #")).append(testCase).append(": ").append(numberOfHacks);
        System.out.println(sb);
    }
    
    private static void printCurrentState(String prefix, long currentDamage, long currentShieldDurability) {
        System.out.println(prefix + "currentDamage: " + currentDamage + ", shieldDurability: " + currentShieldDurability);
    }
    
    private static void printCurrentState(long currentDamage, long currentShieldDurability) {
        printCurrentState("",currentDamage,currentShieldDurability);
    }
    
    private static long increaseDamage(long currentDamage) {
        //damage is doubled every time
        return currentDamage * 2;
    }
    
    private static ProgramResult executeCurrentProgram(String currentRobotProgram, long startingDamage, long startingShieldDurability) {
        ProgramResult result = new ProgramResult();
        result.currentDamage = startingDamage;
        result.currentShieldDurability = startingShieldDurability;
        for(int programIndex = 0; programIndex<currentRobotProgram.length(); programIndex++) {
            char programCode = currentRobotProgram.charAt(programIndex);
            if(programCode == CHARGE) {
                result.currentDamage = increaseDamage(result.currentDamage);
            }
            if(programCode == SHOOT) {
                result.currentShieldDurability -= result.currentDamage;
                result.numberOfShoots++;
                if(result.currentShieldDurability < 0) {
                    result.wouldDieWithoutHack = true;
                }
            }
        }
        return result;
    }
}
