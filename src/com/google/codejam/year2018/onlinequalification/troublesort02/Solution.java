/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.codejam.year2018.onlinequalification.troublesort02;

import java.util.*;
import java.io.*;

/**
 *
 * @author Balu_ADMIN
 */
public class Solution {
    
    private static final boolean LOCAL_TEST = false;
    
    private static final String OK_RESULT = "OK";
    
    private static class ResultWrapper {
        private boolean wasChangeNeeded;
        private int currentBeginning;
        private int currentEnd;
        private int[] currentNumbers;
        public ResultWrapper(int[] numbers) {
            currentNumbers = numbers;
        }
        @Override
        public String toString() {
            return "wasChangeNeeded=" + wasChangeNeeded + ", currentBeginning=" + currentBeginning + ", currentEnd=" + currentEnd + ", currentNumbers=" + Arrays.toString(currentNumbers);
        }
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        if(LOCAL_TEST) {
            //Mimic the input
            final String MIMICED_INPUT = 
                "2\n" +
                "5\n" +
                "5 6 8 4 3\n" +
                "3\n" +
                "8 9 7"
            ;
            in = new Scanner(MIMICED_INPUT);
        }
        int testCases = in.nextInt();
        for (int testCase = 1; testCase <= testCases; ++testCase) {
            int numberOfElementsInList = in.nextInt(); 
            in.nextLine();
            String numbersStrLine = in.nextLine().trim();
            String[] numbersStr = numbersStrLine.split(" ");
            int[] numbers = new int[numberOfElementsInList];
            for(int i=0; i<numberOfElementsInList; i++) {
                numbers[i] = Integer.parseInt(numbersStr[i]);
            }
            //
            //execute troubleSort
            int[] troubleSoirtedNumbers = troubleSortIteration(numbers);
            Integer firstIndexOfWronglySortedNumber = checkIfProperlySorted(troubleSoirtedNumbers);
            println("Case #" + testCase + ": " + ((firstIndexOfWronglySortedNumber == null) ? OK_RESULT : firstIndexOfWronglySortedNumber));
        }
    }
    
    private static void println(String s) {
        System.out.println(s);
    }
    
    /**
     * Checks if sorting was properly done.
     * 
     * @param troubleSoirtedNumbers
     * @return null if sorting was properly done, otherwise the first index of a wrongly sorted number 
     */
    private static Integer checkIfProperlySorted(int[] troubleSoirtedNumbers) {
        boolean properlySorted = true;
        for(int i=0; i<troubleSoirtedNumbers.length-1; i++) {
            if(troubleSoirtedNumbers[i+1] < troubleSoirtedNumbers[i]) {
                return i;
            }
        }
        return null;
    }
    
    private static int[] troubleSortIteration(int[] numbers) {
        ResultWrapper result = new ResultWrapper(numbers);
        do {
            result.wasChangeNeeded = false;
            result.currentBeginning = 0;
            result.currentEnd = result.currentBeginning + 2;
            result = troubleSort(result);
            //println(Arrays.toString(result.currentNumbers));
        } while(result.wasChangeNeeded);
        return result.currentNumbers;
    }
    
    private static ResultWrapper troubleSort(ResultWrapper result) {
        //println(result.toString());
        if(result.currentNumbers.length <= result.currentEnd) {
            return result;
        }
        if(result.currentNumbers[result.currentEnd] < result.currentNumbers[result.currentBeginning]) {
            int temp = result.currentNumbers[result.currentEnd];
            result.currentNumbers[result.currentEnd] = result.currentNumbers[result.currentBeginning];
            result.currentNumbers[result.currentBeginning] = temp;
            result.wasChangeNeeded = true;
        }
        result.currentBeginning++;
        result.currentEnd++;
        return troubleSort(result);
    }
    
}
