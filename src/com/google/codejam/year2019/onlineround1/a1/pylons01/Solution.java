package com.google.codejam.year2019.onlineround1.a1.pylons01;

import java.util.*;
import java.io.*;

/**
 * <b><u>1A_01_Pylons</u></b> <br/><br/>
 */
public class Solution {
    private static enum Result {
        POSSIBLE,
        IMPOSSIBLE
    };
    /**
     * Separator character of new input lines.
     */
    private static final String LINE_SEPARATOR = System.getProperty("line.separator", "\n"); //LF is expected only
    private static final String OUTPUT_FORMAT = "Case #%d: %s" + LINE_SEPARATOR; //Use with String.format - 1.: number of the test case, 2.:the string POSSIBLE or IMPOSSIBLE
    //private static final String OUTPUT_FORMAT_2 = "%d %d"; //Use with String.format - 1.:the row number , 2.: the column number
    private static final String SEPARATOR = " ";
    //private static final String ROW = "R";
    //private static final String COLUMN = "C";
    
    private static class Cell {
        public final int ROW_INDEX;
        public final int COLUMN_INDEX;
        private String toString;
        public Cell(int rowIndex, int columnIndex) {
            this.ROW_INDEX = rowIndex;
            this.COLUMN_INDEX = columnIndex;
        }
        public static String getStringRepresentation(int r, int c) {
            return r + SEPARATOR + c;
        }
        @Override
        public String toString() {
            if(toString == null) {
                toString = getStringRepresentation(ROW_INDEX, COLUMN_INDEX);
            }
            return toString;
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
            final Cell other = (Cell) obj;
            if (this.ROW_INDEX != other.ROW_INDEX) {
                return false;
            }
            if (this.COLUMN_INDEX != other.COLUMN_INDEX) {
                return false;
            }
            return true;
        }
        
    }
    private static class ResultEval {
        public final int TEST_CASE_NUM;
        public final int ROWS;
        public final int COLUMNS;
        private final List<Cell> STEPS;
        private Result result;
        public ResultEval(int testCaseNum, int rows, int columns) {
            this.TEST_CASE_NUM = testCaseNum;
            this.ROWS = rows;
            this.COLUMNS = columns;
            this.STEPS = new LinkedList();
        }
        public void addStep(Cell step) {
            STEPS.add(step);
        }
        public List<Cell> getSteps() {
            return STEPS;
        }
        public void setResult(Result result) {
            this.result = result;
        }
        public Result getResult() {
            return result;
        }
        public String printResult() {
            StringBuilder sb = new StringBuilder();
            sb.append(String.format(OUTPUT_FORMAT, TEST_CASE_NUM, result.name()));
            if(result == Result.POSSIBLE) {
                for(Cell step : STEPS) {
                    sb.append(step.toString()).append(LINE_SEPARATOR);
                }
            }
            return sb.toString();
        }
    }
    
    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        final int T = in.nextInt(); //number of test cases
        in.nextLine();
        final List<ResultEval> answers = new ArrayList();
        for (int currentTestCase = 1; currentTestCase <= T; currentTestCase++) {
            final String R_C = in.nextLine(); //"N L"
            String[] rAndC = R_C.split(SEPARATOR);
            final int R = Integer.parseInt(rAndC[0]); //number of rows starting from 1
            final int C = Integer.parseInt(rAndC[1]); //number of columns starting from 1
            ResultEval resultEval = new ResultEval(currentTestCase, R, C);
            if((R * C == 4) || (R * C == 6) || (R * C == 8) || (R * C == 9)) {
                resultEval.setResult(Result.IMPOSSIBLE);
            }
            else {
                Map<String, Cell> freeCells = new HashMap();
                Cell cell;
                for(int r=1; r<=R; r++) {
                    for(int c=1; c<=C; c++) {
                        cell = new Cell(r,c);
                        freeCells.put(cell.toString(), cell);
                    }
                }
                int startRow = 1;
                int startColumn = 1;
                Cell firstStep = freeCells.remove(Cell.getStringRepresentation(startRow, startColumn));
                resultEval.addStep(firstStep);
                Cell nextStep;
                do {
                    nextStep = getNextCell(firstStep, R, C, freeCells);
                    resultEval.addStep(nextStep);
                    firstStep = nextStep;
                    if(freeCells.isEmpty()) {
                        break; //we managed to use up all cells
                    }
                } while(nextStep != null);
                resultEval.setResult((nextStep == null) ? Result.IMPOSSIBLE : Result.POSSIBLE);
            }
            answers.add(resultEval);
        }
        answers.forEach((resultEval) -> {
            System.out.print(resultEval.printResult());
        });
        //Test all visible solutions
        testSolution(answers);
    }
    
    private static Cell getNextCell(final Cell firstStep, final int R, final int C, final Map<String, Cell> freeCells) {
        //try knight move from chess at first
        int nextRow = firstStep.ROW_INDEX + 1;
        if(R < nextRow) {
            nextRow = 1;
        }
        int nextColumn = firstStep.COLUMN_INDEX + 2;
        if(C < nextColumn) {
            nextColumn = nextColumn - C; 
        }
        Cell tryNextCell = freeCells.remove(Cell.getStringRepresentation(nextRow, nextColumn));
        if(isCellOk(firstStep, tryNextCell)) {
            return tryNextCell;
        }
        putCellBackIfNotUsed(tryNextCell, freeCells);
        //try other knight move
        nextRow = firstStep.ROW_INDEX + 2;
        if(R < nextRow) {
            nextRow = nextRow - R;
        }
        nextColumn = firstStep.COLUMN_INDEX + 1;
        if(C < nextColumn) {
            nextColumn = 1; 
        }
        tryNextCell = freeCells.remove(Cell.getStringRepresentation(nextRow, nextColumn));
        if(isCellOk(firstStep, tryNextCell)) {
            return tryNextCell;
        }
        putCellBackIfNotUsed(tryNextCell, freeCells);
        //use bruteforce to find a proper cell from the available ones
        Iterator<Cell> it = freeCells.values().iterator();
        while(it.hasNext()) {
            tryNextCell = it.next();
            if(isCellOk(firstStep, tryNextCell)) {
                it.remove();
                return tryNextCell;
            }
        }
        return null;
    }
    
    private static void putCellBackIfNotUsed(Cell removedCell, final Map<String, Cell> freeCells) {
        if(removedCell != null) {
            freeCells.put(removedCell.toString(), removedCell);
        }
    }
    
    private static boolean isCellNotOnDiagonal(Cell firstStep, Cell secondStep) {
        if(firstStep == null || secondStep == null) {
            return false;
        }
        if((firstStep.ROW_INDEX - firstStep.COLUMN_INDEX) == (secondStep.ROW_INDEX - secondStep.COLUMN_INDEX)) {
            return false;
        }
        if((firstStep.ROW_INDEX + firstStep.COLUMN_INDEX) == (secondStep.ROW_INDEX + secondStep.COLUMN_INDEX)) {
            return false;
        }
        return true;
    }
    
    private static boolean isCellOk(Cell firstStep, Cell secondStep) {
        if(!isCellNotOnDiagonal(firstStep, secondStep)) {
            return false;
        }
        if(firstStep.ROW_INDEX == secondStep.ROW_INDEX) {
            return false;
        }
        if(firstStep.COLUMN_INDEX == secondStep.COLUMN_INDEX) {
            return false;
        }
        return true;
    }
    
    private static void testSolution(final List<ResultEval> answerResult) {
        System.out.println(LINE_SEPARATOR);
        System.out.println("Checking solutions");
OUT:    for(ResultEval resultEval : answerResult) {
            System.out.print("Case #" + resultEval.TEST_CASE_NUM + " (" + resultEval.ROWS + "," + resultEval.COLUMNS + "): ");
            if(resultEval.getResult() == Result.IMPOSSIBLE) {
                System.out.println("Ok??");
                continue;
            }
            if((resultEval.ROWS * resultEval.COLUMNS) != resultEval.getSteps().size()) {
                System.out.println("NOT OK - Not enough steps! Needed: " + (resultEval.ROWS * resultEval.COLUMNS) + ", got: " + resultEval.getSteps().size());
                continue;
            }
            Set<Cell> lookingForDuplicatedSteps = new HashSet();
            for(Cell step : resultEval.getSteps()) {
                if(!lookingForDuplicatedSteps.add(step)) {
                    System.out.println("NOT OK - Step (" + step+ ") is duplicated. " + Arrays.toString(resultEval.getSteps().toArray()));
                    continue OUT;
                }
            }
            Cell firstStep = resultEval.getSteps().get(0);
            Cell secondStep;
            for(int i=1; i<resultEval.getSteps().size(); i++) {
                secondStep = resultEval.getSteps().get(i);
                if(firstStep.ROW_INDEX == secondStep.ROW_INDEX) {
                    System.out.println("NOT OK - Step (" + firstStep + ") is in the same row as step(" + secondStep + "). " + Arrays.toString(resultEval.getSteps().toArray()));
                    continue OUT;
                }
                if(firstStep.COLUMN_INDEX == secondStep.COLUMN_INDEX) {
                    System.out.println("NOT OK - Step (" + firstStep + ") is in the same column as step(" + secondStep + "). " + Arrays.toString(resultEval.getSteps().toArray()));
                    continue OUT;
                }
                if((firstStep.ROW_INDEX - firstStep.COLUMN_INDEX) == (secondStep.ROW_INDEX - secondStep.COLUMN_INDEX)) {
                    System.out.println("NOT OK - Step (" + firstStep + ") is on the same diagolan as step(" + secondStep + "). " + Arrays.toString(resultEval.getSteps().toArray()));
                    continue OUT;
                }
                if((firstStep.ROW_INDEX + firstStep.COLUMN_INDEX) == (secondStep.ROW_INDEX + secondStep.COLUMN_INDEX)) {
                    System.out.println("NOT OK - Step (" + firstStep + ") is on the same diagolan as step(" + secondStep + "). " + Arrays.toString(resultEval.getSteps().toArray()));
                    continue OUT;
                }
                firstStep = secondStep;
            }
            System.out.println("Ok");
        }
    }
}

/*
run:
16
2 2
2 3
2 4
2 5
3 2
3 3
3 4
3 5
4 2
4 3
4 4
4 5
5 2
5 3
5 4
5 5
Case #1: IMPOSSIBLE
Case #2: IMPOSSIBLE
Case #3: IMPOSSIBLE
Case #4: POSSIBLE
1 1
2 3
1 5
2 2
1 4
2 1
1 3
2 5
1 2
2 4
Case #5: IMPOSSIBLE
Case #6: IMPOSSIBLE
Case #7: POSSIBLE
1 1
2 3
3 1
1 2
2 4
3 2
1 3
2 1
3 3
1 4
2 2
3 4
Case #8: POSSIBLE
1 1
2 3
3 5
1 2
2 4
3 1
1 4
2 1
3 3
2 5
3 2
1 3
3 4
2 2
1 5
Case #9: IMPOSSIBLE
Case #10: IMPOSSIBLE
Case #11: POSSIBLE
1 1
2 3
3 1
4 3
2 4
3 2
4 4
1 2
3 3
4 1
1 3
2 1
4 2
1 4
2 2
3 4
Case #12: POSSIBLE
1 1
2 3
3 5
4 2
1 4
2 1
3 3
4 5
1 3
2 5
3 2
4 4
1 2
2 4
3 1
4 3
1 5
2 2
3 4
4 1
Case #13: POSSIBLE
1 1
3 2
5 1
2 2
4 1
1 2
3 1
5 2
2 1
4 2
Case #14: IMPOSSIBLE
Case #15: POSSIBLE
1 1
2 3
3 1
4 3
5 1
1 3
2 1
3 3
4 1
5 3
2 4
3 2
4 4
5 2
1 4
2 2
3 4
4 2
5 4
1 2
Case #16: POSSIBLE
1 1
2 3
3 5
4 2
5 4
2 5
3 2
4 4
5 1
1 3
3 4
4 1
5 3
1 5
2 2
4 3
5 5
1 2
2 4
3 1
5 2
1 4
2 1
3 3
4 5


Checking solutions
Case #1 (2,2): Ok??
Case #2 (2,3): Ok??
Case #3 (2,4): Ok??
Case #4 (2,5): Ok
Case #5 (3,2): Ok??
Case #6 (3,3): Ok??
Case #7 (3,4): Ok
Case #8 (3,5): Ok
Case #9 (4,2): Ok??
Case #10 (4,3): Ok??
Case #11 (4,4): Ok
Case #12 (4,5): Ok
Case #13 (5,2): Ok
Case #14 (5,3): Ok??
Case #15 (5,4): Ok
Case #16 (5,5): Ok
*/