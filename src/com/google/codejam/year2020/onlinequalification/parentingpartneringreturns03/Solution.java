/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.codejam.year2020.onlinequalification.parentingpartneringreturns03;

import java.util.*;
import java.io.*;

/*
Problem
Cameron and Jamie's kid is almost 3 years old! However, even though the child is more independent now, scheduling kid activities and domestic necessities is still a challenge for the couple.

Cameron and Jamie have a list of N activities to take care of during the day. Each activity happens during a specified interval during the day. They need to assign each activity to one of them, so that neither of them is responsible for two activities that overlap. An activity that ends at time t is not considered to overlap with another activity that starts at time t.

For example, suppose that Jamie and Cameron need to cover 3 activities: one running from 18:00 to 20:00, another from 19:00 to 21:00 and another from 22:00 to 23:00. One possibility would be for Jamie to cover the activity running from 19:00 to 21:00, with Cameron covering the other two. Another valid schedule would be for Cameron to cover the activity from 18:00 to 20:00 and Jamie to cover the other two. Notice that the first two activities overlap in the time between 19:00 and 20:00, so it is impossible to assign both of those activities to the same partner.

Given the starting and ending times of each activity, find any schedule that does not require the same person to cover overlapping activities, or say that it is impossible.

Input
The first line of the input gives the number of test cases, T. T test cases follow. Each test case starts with a line containing a single integer N, the number of activities to assign. Then, N more lines follow. The i-th of these lines (counting starting from 1) contains two integers Si and Ei. The i-th activity starts exactly Si minutes after midnight and ends exactly Ei minutes after midnight.

Output
For each test case, output one line containing Case #x: y, where x is the test case number (starting from 1) and y is IMPOSSIBLE if there is no valid schedule according to the above rules, or a string of exactly N characters otherwise. The i-th character in y must be C if the i-th activity is assigned to Cameron in your proposed schedule, and J if it is assigned to Jamie.

If there are multiple solutions, you may output any one of them. (See "What if a test case has multiple correct solutions?" in the Competing section of the FAQ. This information about multiple solutions will not be explicitly stated in the remainder of the 2020 contest.)

Limits
Time limit: 20 seconds per test set.
Memory limit: 1GB.
1 ? T ? 100.
0 ? Si < Ei ? 24 × 60.

Test set 1 (Visible Verdict)
2 ? N ? 10.

Test set 2 (Visible Verdict)
2 ? N ? 1000.

Sample

Input
 	
Output
 
4
3
360 480
420 540
600 660
3
0 1440
1 3
2 4
5
99 150
1 100
100 301
2 5
150 250
2
0 720
720 1440

Case #1: CJC
Case #2: IMPOSSIBLE
Case #3: JCCJJ
Case #4: CC
  
Sample Case #1 is the one described in the problem statement. As mentioned above, there are other valid solutions, like JCJ and JCC.

In Sample Case #2, all three activities overlap with each other. Assigning them all would mean someone would end up with at least two overlapping activities, so there is no valid schedule.

In Sample Case #3, notice that Cameron ends an activity and starts another one at minute 100.

In Sample Case #4, any schedule would be valid. Specifically, it is OK for one partner to do all activities.

My own calculations and inputs:
360-420, 420-430, 440-470, 441-490, 470-520
C: 360-420 420-430 440-470 
                           440- --> wrong
                           441- --> wrong
                           442- --> wrong
                           470- --> can be assigned to Cameron again
J:                 441-490

5
360 420
420 430
440 470
441 490
470 520
*/
public class Solution {
    private static final String OUTPUT_FORMAT = "Case #%d: %s"; //Use with String.format - 1.: number of the test case, 2.:the string concatenation of the solution
    private static final String SEPARATOR = " ";
    private static final String IMPOSSIBLE = "IMPOSSIBLE";
    private static final String CAMERON = "C";
    private static final String JAMIE = "J";
    
    private static final class ActivityPeriod implements Comparable<ActivityPeriod> {
        public final int START;
        public final int END;
        public final int ORIGINAL_INDEX;
        private String assignee;
        public ActivityPeriod(String startStr, String endStr, int originalIndex) {
            START = Integer.parseInt(startStr.trim());
            END = Integer.parseInt(endStr.trim());
            ORIGINAL_INDEX = originalIndex;
        }
        @Override
        public int compareTo(ActivityPeriod t) {
            return this.START - t.START;
        }
        @Override
        public String toString() {
            return "ActivityPeriod{" + "ORIGINAL_INDEX=" + ORIGINAL_INDEX + "START=" + START + ", END=" + END + '}';
        }
        /**
         * Helper method to figure out if 2 activities overlap.
         * 
         * @param nextActivity
         * @return 
         */
        public boolean doesOverlap(ActivityPeriod nextActivity) {
            return nextActivity.START < this.END;
        }
        public String getAssignee() {
            return assignee;
        }

        public void setAssignee(String assignee) {
            this.assignee = assignee;
        }
    }
    
    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        final int T = in.nextInt(); //number of test cases
        in.nextLine();
        for (int currentTestCase = 1; currentTestCase <= T; currentTestCase++) {
            final int N = in.nextInt(); //number of activities
            in.nextLine();
            List<ActivityPeriod> allActivities = new ArrayList();
            for(int activityIndex=0; activityIndex<N; activityIndex++) {
                String activityStr = in.nextLine();
                String[] activity = activityStr.split(SEPARATOR);
                allActivities.add(new ActivityPeriod(activity[0], activity[1], activityIndex));
            }
            ActivityPeriod[] solution = new ActivityPeriod[N];
            //start solution
            try {
                //printForTesting(Arrays.toString(allActivities.toArray()));
                Collections.sort(allActivities); //sort using start time in case it would not be sorted
                //printForTesting(Arrays.toString(allActivities.toArray()));
                Map<String, Stack<ActivityPeriod>> activitiesAssigned = new HashMap();
                //init the hashmap with the available parents and an empty activities stack
                activitiesAssigned.put(CAMERON, new Stack());
                activitiesAssigned.put(JAMIE, new Stack());
                //ActivityPeriod lastAssignedActivity = allActivities.get(0);
                String lastAssignedTo = CAMERON;
                ActivityPeriod firstActivity = allActivities.get(0);
                firstActivity.setAssignee(lastAssignedTo);
                activitiesAssigned.get(lastAssignedTo).push(allActivities.get(0));
                solution[firstActivity.ORIGINAL_INDEX] = firstActivity;
                for(int i=1; i<allActivities.size(); i++) {
                    ActivityPeriod activity = allActivities.get(i);
                    ActivityPeriod lastAssignedActivity = activitiesAssigned.get(lastAssignedTo).pop();
                    if(!lastAssignedActivity.doesOverlap(activity)) {
                        //if it does not overlap, then this last assigned activity was finished, the last assignee can get the current task
                        activitiesAssigned.get(lastAssignedTo).push(activity);
                        activity.setAssignee(lastAssignedTo);
                        solution[activity.ORIGINAL_INDEX] = activity;
                    }
                    else {
                        //we need to push back the last assigned activity, because it overlaps, it was not done yet
                        if(!activitiesAssigned.get(lastAssignedTo).isEmpty()) {
                            //but if this parent already is in the middle of an activity, it is impossible
                            throw new RuntimeException(IMPOSSIBLE);
                        }
                        activitiesAssigned.get(lastAssignedTo).push(lastAssignedActivity);
                        String availableParent = getNextAvailableParentAndRemoveFinishedTasks(lastAssignedTo, activity, activitiesAssigned);
                        if(availableParent == null) {
                            //no available parent left
                            throw new RuntimeException(IMPOSSIBLE);
                        }
                        activitiesAssigned.get(availableParent).push(activity);
                        lastAssignedTo = availableParent;
                        activity.setAssignee(lastAssignedTo);
                        solution[activity.ORIGINAL_INDEX] = activity;
                    } // end of dealing with if previous parent could not take this next activity
                } //end of assigning all of the activities
            }
            catch(RuntimeException e) {
                System.out.println(String.format(OUTPUT_FORMAT, currentTestCase, e.getMessage()));
                continue;
            }
            //end of solution
            StringBuilder sb = new StringBuilder();
            for(ActivityPeriod activityInOriginalOrder : solution) {
                sb.append(activityInOriginalOrder.getAssignee());
            }
            System.out.println(String.format(OUTPUT_FORMAT, currentTestCase, sb.toString()));
        } //end of test cases
    }
    
    /**
     * Helper method to remove all finished tasks and to get back the next available parent from all of the activities assigned to a parent or null if there is none left. 
     * 
     * @param currentlyActiveParent the string key of the currently active parent, which we cant return
     * @param activityToDoNext the next activity for which we are looking for a parent
     * @param activitiesAssigned all of the assigned tasks to all of the parents
     * @return the next available parent or null if there in none left
     */
    private static String getNextAvailableParentAndRemoveFinishedTasks(String currentlyActiveParent, ActivityPeriod activityToDoNext, Map<String, Stack<ActivityPeriod>> activitiesAssigned) {
        //return currentlyActiveParent.equals(CAMERON) ? JAMIE : CAMERON;
        String nextAvailableParent = null;
        for(Map.Entry<String, Stack<ActivityPeriod>> parentActivity : activitiesAssigned.entrySet()) {
            String parentStr = parentActivity.getKey();
            Stack<ActivityPeriod> activityStack = parentActivity.getValue();
            //we remove every activity which has been done until this current task
            if(!activityStack.isEmpty()) {
                ActivityPeriod parentsLastActivity = activityStack.pop();
                if(activityToDoNext.START < parentsLastActivity.END) {
                    //this task for this parent is not done yet, we need to push it back into the stack
                    activityStack.push(parentsLastActivity);
                }
            }
            if(nextAvailableParent == null && !parentStr.equals(currentlyActiveParent) && activityStack.isEmpty()) {
                nextAvailableParent = parentStr;
            }
        }
        return nextAvailableParent;
    }
    
    /**
     * Just a helper method to print, which can be switched off easily when submitting the code.
     * @param str 
     */
    private static void printForTesting(String str) {
        //System.out.println(str);
    }
}
