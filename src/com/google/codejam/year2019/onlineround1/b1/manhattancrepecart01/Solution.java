package com.google.codejam.year2019.onlineround1.b1.manhattancrepecart01;

import java.util.*;
import java.io.*;

/**
 * <b><u>1B_01_ManhattanCrepeCart</u></b> <br/><br/>
 */
public class Solution {
    /**
     * Separator character of new input lines.
     */
    private static final String OUTPUT_FORMAT = "Case #%d: %s"; //Use with String.format - 1.: number of the test case, 2.: %d %d - the coordinates
    private static final String SEPARATOR = " ";
    
    private static class Coordinates {
        private final int X;
        private final int Y;
        private String key;
        public Coordinates(int x, int y) {
            this.X = x;
            this.Y = y;
        }
        public int getX() {
            return X;
        }
        public int getY() {
            return Y;
        }
        @Override
        public String toString() {
            if(key == null) {
                StringBuilder sb = new StringBuilder();
                sb.append(X).append(SEPARATOR).append(Y);
                key = sb.toString();
            }
            return key;
        }
        @Override
        public int hashCode() {
            return this.toString().hashCode();
        }
        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final Coordinates other = (Coordinates) obj;
            return this.toString().equals(obj.toString());
        }
        
    }
    
    private static enum Direction {
        N(0,1), //north
        S(0,-1), //south
        W(-1,0), //west
        E(1,0); //east
        public final int TO_X;
        public final int TO_Y;
        private Direction(int toX, int toY) {
            this.TO_X = toX;
            this.TO_Y = toY;
        }
        public static Coordinates getNewCoordinates(Coordinates currentPos, String direction) {
            Direction dir = Direction.valueOf(direction);
            return new Coordinates(currentPos.getX() + dir.TO_X , currentPos.getY() + dir.TO_Y);
        }
    }
    
    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        final int T = in.nextInt(); //number of test cases
        in.nextLine();
        final String[] solution = new String[T];
        for (int currentTestCase = 1; currentTestCase <= T; currentTestCase++) {
            final String P_Q = in.nextLine(); //"PeopleNum QuadraticMatrixSize"
            String[] pAndQ = P_Q.split(SEPARATOR);
            final int P = Integer.parseInt(pAndQ[0]); //number of people
            final int Q = Integer.parseInt(pAndQ[1]); //size of the quadratic Manhattan matrix
            List<Coordinates> newCoordinates = new LinkedList();
            Map<Coordinates, Integer> scoredIntersections = new HashMap();
            Coordinates currentPos;
            Coordinates newPos;
            for(int i=0; i<P; i++) {
                String peopleDetail = in.nextLine(); //xCoordinate yCoordinate direction
                String[] details = peopleDetail.split(SEPARATOR);
                int currentX = Integer.parseInt(details[0]);
                int currentY = Integer.parseInt(details[1]);
                currentPos = new Coordinates(currentX, currentY);
                newPos = Direction.getNewCoordinates(currentPos, details[2]);
                newCoordinates.add(newPos);
                scoredIntersections.put(newPos, (scoredIntersections.containsKey(newPos) ? (scoredIntersections.get(newPos) + 1) : 1));
            }
            int maxScore = Collections.max(scoredIntersections.values());
            List<Coordinates> intersectionsWhichCount = new ArrayList();
            for(Coordinates coord : scoredIntersections.keySet()) {
                if(scoredIntersections.get(coord) == maxScore) {
                    intersectionsWhichCount.add(coord);
                }
            }
            if(intersectionsWhichCount.size() == 1) {
                solution[currentTestCase-1] = intersectionsWhichCount.get(0).toString();
            }
            else {
                Collections.sort(intersectionsWhichCount, (Coordinates c1, Coordinates c2) -> {
                    if(c1.X < c2.X) {
                        return -1;
                    }
                    if(c1.X == c2.X) {
                        if(c1.Y < c2.Y) {
                            return -1;
                        }
                        if(c1.Y == c2.Y) {
                            return 0;
                        }
                    }
                    return 1;
                });
                solution[currentTestCase-1] = intersectionsWhichCount.get(0).toString();
            }
        }
        for (int currentTestCase = 1; currentTestCase <= T; currentTestCase++) {
            System.out.println(String.format(OUTPUT_FORMAT, currentTestCase, solution[currentTestCase-1]));
        }
    }
}