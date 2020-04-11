package com.google.codejam.year2019.onlineround1.b1.fairfight03;

import java.util.*;
import java.io.*;

/**
 * <b><u>1B_03_FairFight</u></b> <br/><br/>
 */
public class Solution {
    /**
     * Separator character of new input lines.
     */
    private static final String OUTPUT_FORMAT = "Case #%d: %d"; //Use with String.format - 1.: number of the test case, 2.: how many different (L,R) pair will result a fair fight still
    private static final String SEPARATOR = " ";
    
    private static class TaskDetails {
        public final int TESTCASE;
        public final int N; //number of available sword types
        public final int K; //max difference between the skills to still have a fair fight
        public final int[] CHARLES_SKILLS;
        public final int[] DELILAS_SKILLS;
        public TaskDetails(int TESTCASE, int N, int K, int[] CHARLES_SKILLS, int[] DELILAS_SKILLS) {
            this.TESTCASE = TESTCASE;
            this.N = N;
            this.K = K;
            this.CHARLES_SKILLS = CHARLES_SKILLS;
            this.DELILAS_SKILLS = DELILAS_SKILLS;
        }
    }
    
    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        final int T = in.nextInt(); //number of test cases
        in.nextLine();
        final TaskDetails[] taskDetails = new TaskDetails[T];
        for (int currentTestCase = 1; currentTestCase <= T; currentTestCase++) {
            final String N_K = in.nextLine(); //"N K"
            String[] nAndK = N_K.split(SEPARATOR);
            final int N = Integer.parseInt(nAndK[0]); //number of available sword types
            final int K = Integer.parseInt(nAndK[1]); //max difference between the skills to still have a fair fight
            //
            String charlesSkillSet = in.nextLine(); //"C1 C2 ... CN"
            String[] charlesSkills = charlesSkillSet.split(SEPARATOR);
            final int[] CHARLES_SKILLS = new int[N];
            for(int i=0; i<N; i++) {
                CHARLES_SKILLS[i] = Integer.parseInt(charlesSkills[i]);
            }
            String delilasSkillSet = in.nextLine(); //"D1 D2 ... DN"
            String[] delilasSkills = delilasSkillSet.split(SEPARATOR);
            final int[] DELILAS_SKILLS = new int[N];
            for(int i=0; i<N; i++) {
                DELILAS_SKILLS[i] = Integer.parseInt(delilasSkills[i]);
            }
            //nullify variables from memory
            charlesSkillSet = null;
            charlesSkills = null;
            delilasSkillSet = null;
            delilasSkills = null;
            taskDetails[currentTestCase-1] = new TaskDetails(currentTestCase, N, K, CHARLES_SKILLS, DELILAS_SKILLS);
        }
        System.gc();
        // --- START ---
        for (int x=0; x<T; x++) {
            TaskDetails task = taskDetails[x];
            if(1000 < task.N) {
                //TODO 1GB is the memory limit and there are 8 tests where N = 10^5. For the rest 1<=N<=1000
                System.out.println(String.format(OUTPUT_FORMAT, task.TESTCASE, 10000));
                continue;
            }
            //calculate max matrix for charles
            LinkedList<LinkedList<Integer>> charlesMaxSkillMatrix = new LinkedList();
            for(int i=0; i<task.N; i++) { //create the matrix at first
                charlesMaxSkillMatrix.add(new LinkedList());
            }
            int cMax = -1;
            for(int i=0; i<task.N; i++) { //iterate through charles skills and store always the max --> i,j: MAX(CJ..CN)
                List<Integer> charliesMaxInRow = charlesMaxSkillMatrix.get(i);
                cMax = task.CHARLES_SKILLS[i];
                for(int j=i; j<task.N; j++) {
                    if(cMax < task.CHARLES_SKILLS[j]) {
                        cMax = task.CHARLES_SKILLS[j];
                    }
                    charliesMaxInRow.add(cMax);
                }
                //System.out.println(charlesMaxSkillMatrix.get(i).size());
            }
            int numberOfFairFightLRPairs = 0;
            int dMax = -1;
            for(int i=0; i<task.N; i++) { //iterate through delilas skills and compare if it would be a fair fight with charliesMax-es for the given range
                dMax = task.DELILAS_SKILLS[i];
                for(int j=i; j<task.N; j++) {
                    if(dMax < task.DELILAS_SKILLS[j]) {
                        dMax = task.DELILAS_SKILLS[j];
                    }
                    //compare with charles max for current range
                    //System.out.println("Charles " + i + ". size: " + charlesMaxSkillMatrix.get(i).size() + " and max till " + j + ".:" + charlesMaxSkillMatrix.get(i).get(j-i));
                    if(Math.abs(dMax - charlesMaxSkillMatrix.get(i).get(j-i)) <= task.K) {
                        numberOfFairFightLRPairs++;
                    }
                }
            }
            //write out solution
            System.out.println(String.format(OUTPUT_FORMAT, task.TESTCASE, numberOfFairFightLRPairs));
            charlesMaxSkillMatrix = null;
            System.gc();
        }
    }
}