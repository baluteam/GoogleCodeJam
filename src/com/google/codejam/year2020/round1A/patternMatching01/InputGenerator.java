/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.codejam.year2020.round1A.patternMatching01;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Balu_ADMIN
 */
public class InputGenerator {
    
    public static void main(String[] args) {
        //configurable parameters
        final Integer T = 100; //null; //20;
        final Integer N = 2; //null; //30;
        final Integer PI_LENGTH = 5; //null; //10;
        final int TEST_SET = 3; //1 or 2 or 3
        //limits
        final int T_MIN = 1;
        final int T_MAX = 100;
        final int N_MIN = 2;
        final int N_MAX = 50;
        final int PI_MIN = 2;
        final int PI_MAX = 100;
        final int MIN_NUMBER_OF_LETTERS = 1;
        final String ASTERISK = "*";
        final String[] LETTERS = new String[] {"A","B","C"};
        assert (T_MIN <= T) : "1 ? T ? 100";
        assert (T <= T_MAX) : "1 ? T ? 100";
        assert (N_MIN <= N) : "2 ? N ? 50";
        assert (N <= N_MAX) : "2 ? N ? 50";
        assert (PI_MIN <= PI_LENGTH) : "2 ? length of Pi ? 100, for all i";
        assert (PI_LENGTH <= PI_MAX) : "2 ? length of Pi ? 100, for all i";
        assert (TEST_SET == 1 || TEST_SET == 2 || TEST_SET == 3) : "There are 3 test sets.";
        StringBuilder sb = new StringBuilder();
        final int TEST_CASES = (T != null) ? T : ((int) Math.round(Math.random() * (T_MAX - T_MIN)) + T_MIN);
        sb.append(TEST_CASES).append(System.lineSeparator());
        for(int i=0; i<TEST_CASES; i++) {
            final int NUMBER_OF_PATTERNS = (N != null) ? N : ((int) Math.round(Math.random() * (N_MAX - N_MIN)) + N_MIN);
            sb.append(NUMBER_OF_PATTERNS).append(System.lineSeparator());
            for(int n=0; n<NUMBER_OF_PATTERNS; n++) {
                if(TEST_SET == 1) {
                    sb.append(ASTERISK);
                    int patternLength = (PI_LENGTH != null) ? PI_LENGTH : ((int) Math.round(Math.random() * (PI_MAX - PI_MIN)) + PI_MIN);
                    for(int pi=1; pi<patternLength; pi++) {
                        int letterIndex = (int) Math.floor(Math.random() * LETTERS.length);
                        sb.append(LETTERS[letterIndex]);
                    }
                    sb.append(System.lineSeparator());
                }
                else if(TEST_SET == 2) {
                    int patternLength = (PI_LENGTH != null) ? PI_LENGTH : ((int) Math.round(Math.random() * (PI_MAX - PI_MIN)) + PI_MIN);
                    int asteriskPosition = (int) Math.floor(Math.random() * patternLength);
                    for(int pi=0; pi<patternLength; pi++) {
                        if(pi == asteriskPosition) {
                            sb.append(ASTERISK);
                            continue;
                        }
                        int letterIndex = (int) Math.floor(Math.random() * LETTERS.length);
                        sb.append(LETTERS[letterIndex]);
                    }
                    sb.append(System.lineSeparator());
                }
                else if(TEST_SET == 3) {
                    int patternLength = (PI_LENGTH != null) ? PI_LENGTH : ((int) Math.round(Math.random() * (PI_MAX - PI_MIN)) + PI_MIN);
                    int numberOfAsterisks = (int) Math.round(Math.random() * (patternLength - 1 - MIN_NUMBER_OF_LETTERS)) + 1;
                    List<Integer> asteriskPositions = new ArrayList();
                    for(int numOfAsterisk=0; numOfAsterisk<numberOfAsterisks; numOfAsterisk++) {
                        int asteriskPosition = (int) Math.floor(Math.random() * patternLength);
                        asteriskPositions.add(asteriskPosition);
                    }
                    for(int pi=0; pi<patternLength; pi++) {
                        if(asteriskPositions.contains(pi)) {
                            sb.append(ASTERISK);
                            continue;
                        }
                        int letterIndex = (int) Math.floor(Math.random() * LETTERS.length);
                        sb.append(LETTERS[letterIndex]);
                    }
                    sb.append(System.lineSeparator());
                }
                else {
                    assert true : "No other test case is available";
                }
            }
        }
        System.out.println(sb.toString());
    }
}
