/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.codejam.year2020.onlinequalification.vestigium01;

import java.util.*;
import java.io.*;

/*
Problem
Vestigium means "trace" in Latin. In this problem we work with Latin squares and matrix traces.

The trace of a square matrix is the sum of the values on the main diagonal (which runs from the upper left to the lower right).

An N-by-N square matrix is a Latin square if each cell contains one of N different values, and no value is repeated within a row or a column. In this problem, we will deal only with "natural Latin squares" in which the N values are the integers between 1 and N.

Given a matrix that contains only integers between 1 and N, we want to compute its trace and check whether it is a natural Latin square. To give some additional information, instead of simply telling us whether the matrix is a natural Latin square or not, please compute the number of rows and the number of columns that contain repeated values.

Input
The first line of the input gives the number of test cases, T. T test cases follow. Each starts with a line containing a single integer N: the size of the matrix to explore. Then, N lines follow. The i-th of these lines contains N integers Mi,1, Mi,2 ..., Mi,N. Mi,j is the integer in the i-th row and j-th column of the matrix.

Output
For each test case, output one line containing Case #x: k r c, where x is the test case number (starting from 1), k is the trace of the matrix, r is the number of rows of the matrix that contain repeated elements, and c is the number of columns of the matrix that contain repeated elements.

Limits
Test set 1 (Visible Verdict)
Time limit: 20 seconds per test set.
Memory limit: 1GB.
1 ? T ? 100.
2 ? N ? 100.
1 ? Mi,j ? N, for all i, j.

Sample

Input
 	
Output
 
3
4
1 2 3 4
2 1 4 3
3 4 1 2
4 3 2 1
4
2 2 2 2
2 3 2 3
2 2 2 3
2 2 2 2
3
2 1 3
1 3 2
1 2 3
  
Case #1: 4 0 0
Case #2: 9 4 4
Case #3: 8 0 2
  
In Sample Case #1, the input is a natural Latin square, which means no row or column has repeated elements. All four values in the main diagonal are 1, and so the trace (their sum) is 4.

In Sample Case #2, all rows and columns have repeated elements. Notice that each row or column with repeated elements is counted only once regardless of the number of elements that are repeated or how often they are repeated within the row or column. In addition, notice that some integers in the range 1 through N may be absent from the input.

In Sample Case #3, the leftmost and rightmost columns have repeated elements.

@see https://codingcompetitions.withgoogle.com/codejam/round/000000000019fd27/000000000020993c
*/
public class Solution {
    private static final String OUTPUT_FORMAT = "Case #%d: %d %d %d"; //Use with String.format - 1.: number of the test case, 2.: is the trace of the matrix, 3.: is the number of rows of the matrix that contain repeated elements, 4.: is the number of columns of the matrix that contain repeated elements
    private static final String SEPARATOR = " ";
    
    public static void main(String[] args) {
        Scanner in = new Scanner(new BufferedReader(new InputStreamReader(System.in)));
        final int T = in.nextInt(); //number of test cases
        in.nextLine();
        for (int currentTestCase = 1; currentTestCase <= T; currentTestCase++) {
            int k = 0; //the trace of the matrix
            int r = 0; //the number of rows of the matrix that contain repeated elements
            int c = 0; //the number of columns of the matrix that contain repeated elements
            final int N = in.nextInt(); //natural matrix size
            in.nextLine();
            final int[][] matrix = new int[N][N];
            for(int matrixRowIndex = 0; matrixRowIndex < N; matrixRowIndex++) {
                String matrixRowStr = in.nextLine();
                String[] matrixCellStrs = matrixRowStr.split(SEPARATOR);
                int matrixColumnIndex = 0;
                Set<Integer> rowSet = new HashSet(); //to check if rows contain duplicated elements
                for(String cellStr : matrixCellStrs) {
                    int cell = Integer.parseInt(cellStr.trim());
                    matrix[matrixRowIndex][matrixColumnIndex++] = cell;
                    rowSet.add(cell);
                }
                if(rowSet.size() != N) { //if there are less element in the set, there were duplicated ones
                    r++;
                }
            } //end of parsing the input matrix
            //calculate the trace of the matrix
            for(int i=0; i<N; i++) {
                k += matrix[i][i];
            }
            //check for columns whit repeated elements
            for(int columnIndex=0; columnIndex<N; columnIndex++) {
                Set<Integer> columnSet = new HashSet(); //to check if columns contain duplicated elements
                for(int rowIndex=0; rowIndex<N; rowIndex++) {
                    columnSet.add(matrix[rowIndex][columnIndex]);
                }
                if(columnSet.size() != N) { //if there are less element in the set, there were duplicated ones
                    c++;
                }
            }
            System.out.println(String.format(OUTPUT_FORMAT, currentTestCase, k, r, c));
        } //end of test cases
    }
}
