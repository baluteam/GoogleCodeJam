/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.google.codejam.year2020.onlinequalification.esabatad04;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/*
Problem
Last year, a research consortium had some trouble with a distributed database system that sometimes lost pieces of the data. You do not need to read or understand that problem in order to solve this one!

The consortium has decided that distributed systems are too complicated, so they are storing B bits of important information in a single array on one awesome machine. As an additional layer of security, they have made it difficult to obtain the information quickly; the user must query for a bit position between 1 and B, and then they receive that bit of the stored array as a response.

Unfortunately, this ultra-modern machine is subject to random quantum fluctuations! Specifically, after every 1st, 11th, 21st, 31st... etc. query is sent, but before the response is given, quantum fluctuation causes exactly one of the following four effects, with equal probability:

25% of the time, the array is complemented: every 0 becomes a 1, and vice versa.
25% of the time, the array is reversed: the first bit swaps with the last bit, the second bit swaps with the second-to-last bit, and so on.
25% of the time, both of the things above (complementation and reversal) happen to the array. (Notice that the order in which they happen does not matter.)
25% of the time, nothing happens to the array.
Moreover, there is no indication of what effect the quantum fluctuation has had each time. The consortium is now concerned, and it has hired you to get its precious data back, in whatever form it is in! Can you find the entire array, such that your answer is accurate as of the time that you give it? Answering does not count as a query, so if you answer after your 30th query, for example, the array will be the same as it was after your 21st through 30th queries.

Input and output
This is an interactive problem. You should make sure you have read the information in the Interactive Problems section of our FAQ.

Initially, your program should read a single line containing two integers T and B: the number of test cases and the number of bits in the array, respectively. Note that B is the same for every test case.

Then, you need to process T test cases. In each case, the judge begins with a predetermined B-bit array; note that this array can vary from test case to test case, and is not necessarily chosen at random. Then, you may make up to 150 queries of the following form:

Your program outputs one line containing a single integer P between 1 and B, inclusive, indicating which position in the array you wish to look at.
If the number of queries you have made so far ends with a 1, the judge chooses one of the four possibilities described above (complementation, reversal, complementation + reversal, or nothing), uniformly at random and independently of all other choices, and alters the stored array accordingly. (Notice that this will happen on the very first query you make.)
The judge responds with one line containing a single character 0 or 1, the value it currently has stored at bit position P, or N if you provided a malformed line (e.g., an invalid position).
Then, after you have made as many of the 150 queries above as you want, you must make one more exchange of the following form:

Your program outputs one line containing a string of B characters, each of which is 0 or 1, representing the bits currently stored in the array (which will not necessarily match the bits that were initially present!)
The judge responds with one line containing a single letter: uppercase Y if your answer was correct, and uppercase N if it was not (or you provided a malformed line). If you receive Y, you should begin the next test case, or stop sending input if there are no more test cases.
After the judge sends N to your input stream, it will not send any other output. If your program continues to wait for the judge after receiving N, your program will time out, resulting in a Time Limit Exceeded error. Notice that it is your responsibility to have your program exit in time to receive a Wrong Answer judgment instead of a Time Limit Exceeded error. As usual, if the memory limit is exceeded, or your program gets a runtime error, you will receive the appropriate judgment.

Limits
Time limit: 40 seconds per test set.
Memory limit: 1GB.
1 ? T ? 100.

Test set 1 (Visible Verdict)
B = 10.

Test set 2 (Visible Verdict)
B = 20.

Test set 3 (Hidden Verdict)
B = 100.

Testing Tool
You can use this testing tool to test locally or on our servers. To test locally, you will need to run the tool in parallel with your code; you can use our interactive runner for that. The interactive runner was changed after the 2019 contest. Be sure to download the latest version. For more information, read the Interactive Problems section of the FAQ.

Local Testing Tool
To better facilitate local testing, we provide you the following script. Instructions are included inside. You are encouraged to add more test cases for better testing. Please be advised that although the testing tool is intended to simulate the judging system, it is NOT the real judging system and might behave differently.

If your code passes the testing tool but fails the real judge, please check the Coding section of our FAQ to make sure that you are using the same compiler as us.

Download local testing tool

Sample Interaction
The following interaction corresponds to Test Set 1.

  t, b = readline_int_list()      // reads 100 into t and 10 into b.
  // The judge starts with the predetermined array for this test case:
  // 0001101111. (Note: the actual Test Set 1 will not necessarily
  // use this array.)
  printline 1 to stdout   // we ask about position 1.
  flush stdout
  // Since this is our 1st query, and 1 is 1 mod 10, the judge secretly and
  // randomly chooses one of the four possible quantum fluctuation effects, as
  // described above. It happens to choose complementation + reversal, so now
  // the stored value is 0000100111.
  r = readline_chr()      // reads 0.
  printline 6 to stdout   // we ask about position 6.
  flush stdout
  // Since this is our 2nd query, and 2 is 2 mod 10, the judge does not choose
  // a quantum fluctuation effect.
  r = readline_chr()      // reads 0.
  ...
  // We have omitted the third through tenth queries in this example.
  ...
  printline 1 to stdout   // we decide to ask about position 1 again.
  flush stdout
  // Since this is our 11th query, and 11 is 1 mod 10, the judge secretly and
  // randomly chooses a quantum fluctuation effect, and happens to get
  // reversal, so now the stored value is 1110010000.
  r = readline_chr()      // reads 1.
  printline 1110110000 to stdout   // we try to answer. why?!?!
  flush stdout
  ok = readline_chr()     // reads N -- we have made a mistake!
  exit                    // exits to avoid an ambiguous TLE error


Google live test run: 
1
10
1
0
10
1
2
0
9
1
3
0
8
1
4
0
7
0
5
1
6
0
0000100111

Google's SOLUTZON:
Analysis
Test Set 1
In Test Set 1, there are only 10 positions in the string. We can query for each of them and then submit the complete string, without having to worry about any quantum fluctuations (which would only happen if we submitted an 11th query).

Test Set 2
Here is one of various ways to solve the second test set. We begin by querying for the first ten positions in the real string, then create a "possibility set" containing all 1024 20-character strings that begin with those 10 characters. Then we update our "possibility set" to contain all strings that could have arisen from those strings after the next quantum fluctuation. The correct answer is in here somewhere ? now we need to narrow the set down!

Before making each subsequent query, we first find the string index (between 1 and 20) at which the proportion of 0s and 1s among the strings in our possibility set is most nearly even. Then we query the real string at that index, and eliminate from the possibility set any strings that are not consistent with that information. Whenever we can indeed find a position with even proportions, we are guaranteed to cut the size of the set in half, but if there is no such position, we may not be able to eliminate that many possibilities. We can continue in this way, remembering to expand the possibility set every time there is a quantum fluctuation, until only one possibility remains, which must be the answer.

It is not easy to prove that this strategy will converge upon an answer. Intuitively, we can observe that a quantum fluctuation increases the size of the possibility set by at most 4, and even if we somehow only cut the possiblity set by 20% with each pruning, we would still easily beat that factor-of-4 increase and make enough progress to finish within 150 queries. Moreover, it would not be possible for the strings in the possibility set to all be distinct while being so similar at every individual position (recall that we always pick the position that will be most useful to us in the worst case). Also, Test Set 2 is a Visible Verdict set, so we might as well just submit our answer and see.

Test Set 3
The above strategy will not work for 100-character strings, since the possibility set would be astronomically huge. Fortunately, there is a much simpler approach.

Observe that if we can find two positions that are equidistant from the center of the string and have the same value, we can use them to detect when a quantum fluctuation has included a complementation (with or without a reversal). Suppose, for example, that the two ends of the string are 0 just before a quantum fluctuation. After the fluctuation, we can check the first one. If it is 1, then there was a complementation; if not, there wasn't one. This is true regardless of whether that quantum fluctuation included a reversal.

Now suppose that we continue to check pairs of positions in this way, moving inward one step at a time. After every quantum fluctuation, we must spend one query to check for complementation so we can update our existing knowledge about the string if there has been one. If every pair turns out to be a "same pair" like the first pair, then we never needed to care about reversals anyway (since the string is palindromic), and we are done.

But what if, in the course of this, we find a "different pair"? Such pairs are helpful in their own way! If we query the first position of a "different pair" after a quantum fluctuation and we find that that bit has changed, then we know that either a complementation or reversal has happened, but not both.

Once we have such a "different pair", we can use it in conjunction with the "same pair", spending 2 out of every 10 queries to learn exactly what happened in each quantum fluctuation. For example, if the first position of our "same pair" stayed the same but the first position of our "different pair" did not, we know that the quantum fluctuation included a reversal but no complementation.

In the above analysis, we assumed we would encounter a "same pair" first. If the first pair is different, though, we can proceed until we encounter a "same pair"; if we never encounter one, then we do not care about the distinction between complementation and reversal, because the operations are equivalent for that particular string. If we do encounter a "same pair", though, then we can proceed as above.

How many queries will we need in the worst case? We can use all of our first 10 to gather data, since whatever happened in the quantum fluctuation at the start of the problem is unknowable and does not matter. After that, we may need to use up to 2 out of every 10 queries to reorient ourselves before spending the remaining 8 gathering data. So, to be sure we can find the entire string, we will need 10 queries, plus 11 more sets of 10 queries in which we learn 8 positions each time, (to get us to 98 positions known), plus 2 more queries for a final reorientation, plus 2 more to get the last two positions. That is a total of 124, which is well within the allowed limit of 150.

Regarding the name...
Last year, we had the Dat Bae problem about deletions from a string in a database; the name was Data Base, altered in a way that reflected the theme. ESAb ATAd is similar, with case change serving as a rough equivalent of complementation. (Imagine how much the Code Jam team has enjoyed trying to type the name correctly each time!)


@see https://codingcompetitions.withgoogle.com/codejam/round/000000000019fd27/0000000000209a9e
*/
public class Solution {
    private static final int MAX_QUERIES = 150;
    private static final byte ONE = 1;
    private static final String ONE_STR = "1";
    private static final byte ZERO = 0;
    private static final String ERROR = "N";
    private static final String OK_SOLUTION = "Y";
    private static final String NULL = "?";
    private static final byte NULL_BYTE = -1;
    private static final int QUANTUM_FLUCTUATION_PERIOD = 10;
    
    private static final class Bit {
        public final byte BIT_VALUE;
        public final int NUMBER_OF_THIS_QUERY;
        public final int POSITION;
        public boolean doesNeedToFluctuate;
        
        /**
         * 
         * @param position
         * @param value
         * @param numberOfQuery 
         */
        public Bit(int position, byte value, int numberOfQuery) {
            POSITION = position;
            BIT_VALUE = value;
            NUMBER_OF_THIS_QUERY = numberOfQuery;
            doesNeedToFluctuate = false;
        }
        
        public boolean wasThisQueryFluctuated() {
            return (NUMBER_OF_THIS_QUERY % QUANTUM_FLUCTUATION_PERIOD == 1);
        }
        
        @Override
        public String toString() {
            return "Bit{" + "BIT_VALUE=" + BIT_VALUE + ", NUMBER_OF_THIS_QUERY=" + NUMBER_OF_THIS_QUERY + ", POSITION=" + POSITION + '}';
        }
    }
    
    private static enum QuantumFluctoation {
        COMPLEMENTED,
        REVERSED,
        COMPLEMENTED_AND_REVERSED,
        NOTHING,
        UNKNOWN
    }
    
    private static final class TooManyQueriesException extends RuntimeException {
        //
    }
    
    private static final class MalformedQueryException extends RuntimeException {
        //
    }
    
    private static class Database {
        public final int B; //size of the bit array for every test case
        private int numberOfQueries;
        private Scanner input;
        
        public Database(int bitSizeOfArrays, Scanner input) {
            numberOfQueries = 0;
            B = bitSizeOfArrays;
            this.input = input;
        }
        
        /**
        * Method to query the value at the given position.
        * 
        * @param position between 1 and B
        * @return 
        */
        public Bit query(int position) {
            increaseNumberOfQueries();
            if(MAX_QUERIES < getNumberOfQueries()) {
                throw new TooManyQueriesException();
            }
            System.out.println(position); //flush is called automatically
            String judgeResponse = input.next().trim();
            if(ERROR.equals(judgeResponse)) {
                throw new MalformedQueryException();
            }
            byte answer = (byte) Integer.parseInt(judgeResponse);
            return new Bit(position, answer, getNumberOfQueries());
        }
        
        /**
         * Checks the response of the judge for our guess of the solution for the current array content.
         * 
         * @param solution
         * @return 
         */
        public String getResponseForSolution(String solution) {
            return input.next().trim();
        }
        
        public void increaseNumberOfQueries() {
            numberOfQueries += 1;
        }

        public int getNumberOfQueries() {
            return numberOfQueries;
        }

        public Scanner getInput() {
            return input;
        }
    }
    
    private static final class MockedDatabase extends Database {
        private final byte[] ORIGINAL_ARRAY;
        private byte[] array;
        
        public MockedDatabase(int bitSizeOfArrays, Scanner input) {
            super(bitSizeOfArrays, input);
            ORIGINAL_ARRAY = generateTestArray(super.B);
            array = Arrays.copyOf(ORIGINAL_ARRAY, ORIGINAL_ARRAY.length);
            testPrint(Arrays.toString(array) + " : original\n");
            //testPrint(Arrays.toString(complement(Arrays.copyOf(array, array.length))) + " : complemented");
            //testPrint(Arrays.toString(reverse(Arrays.copyOf(array, array.length))) + " : reversed");
            //testPrint(Arrays.toString(complementAndReverse(Arrays.copyOf(array, array.length))) + " : complemented and reversed");
            //testPrint(Arrays.toString(doNothing(Arrays.copyOf(array, array.length))) + " : did nothing");
        }
        
        /**
        * Helper test method to query the array for a position and get back the value from there. The random quantum fluctuation can occur as described in the rules.
        * 
        * @param position
        * @return 
        */
        @Override
        public Bit query(int position) {
            increaseNumberOfQueries();
            if(MAX_QUERIES < getNumberOfQueries()) {
                throw new TooManyQueriesException();
            }
            testPrint("Query at position:" + position + ", number of queries: " + getNumberOfQueries());
            byte[] newArray = array;
            if(getNumberOfQueries() % QUANTUM_FLUCTUATION_PERIOD == 1) {
                newArray = quantumFluctuation(array);
                testPrint("original:\n" + Arrays.toString(array) + "\nfluctuated:");
            }
            testPrint(Arrays.toString(newArray));
            array = newArray;
            if(position < 1 || B < position) {
                throw new MalformedQueryException();
            }
            return new Bit(position, newArray[position-1], getNumberOfQueries());
        }
        
        /**
         * Test method to check our given solution for the current array content if we guessed right.
         * 
         * @param solution
         * @return 
         */
        @Override
        public String getResponseForSolution(String solution) {
            StringBuilder sb = new StringBuilder();
            for(byte bit : array) {
                sb.append(bit);
            }
            testPrint(solution + " : solution\n" + sb.toString() + " : current array");
            if(sb.toString().equals(solution)) {
                return OK_SOLUTION;
            }
            return ERROR;
        }
       
        public byte[] getCurrentArray() {
            return array;
        }
        public byte[] getOriginalArray() {
            return ORIGINAL_ARRAY;
        }
    }
    
    public static void main(String args[]) {
        Scanner input = new Scanner(System.in);
        int T = input.nextInt(); //number of test cases
        int B = input.nextInt(); //size of the bit array for every test case
        try {
            for (int testCase = 1; testCase <= T; testCase++) {
                Bit[] solution = new Bit[B];
                Bit[] previousSolution = null; //after every fluctuation we store the previous iteration as history reference
                //TODO 
                //Database db = new Database(B, input);
                Database db = new MockedDatabase(B, input);
                int positionAtStart = 1; //between 1 and B
                int positionAtEnd = B; //between 1 and B
                int iterations = MAX_QUERIES / QUANTUM_FLUCTUATION_PERIOD; //15
                for(int tries=0; tries<iterations+1; tries++) { //we iterate more than allowed to catch an exception and force us to guess
                    for(int i=0; i<QUANTUM_FLUCTUATION_PERIOD; i++) {
                        Bit currentBit = null;
                        try {
                            if(i%2 == 0) { //at every step we change which end of the array we want to query
                                currentBit = db.query(positionAtStart); //every time this for cyclus starts, the array will be fluctuated
                                solution[positionAtStart - 1] = currentBit;
                                positionAtStart++;
                            }
                            else {
                                currentBit = db.query(positionAtEnd);
                                solution[positionAtEnd - 1] = currentBit;
                                positionAtEnd--;
                            }
                        }
                        catch(TooManyQueriesException e) {
                            //we could not guess the array in 150 queries, we need to guess
                            String currentSolution = getSolution(solution);
                            StringBuilder sb2 = new StringBuilder(currentSolution);
                            int indexOfUnkown = sb2.indexOf(NULL);
                            while(indexOfUnkown != -1) {
                                byte randomBit = (byte)Math.round(Math.random());
                                sb2.replace(indexOfUnkown, indexOfUnkown+1, randomBit+"");
                                indexOfUnkown = sb2.indexOf(NULL);
                            }
                            currentSolution = sb2.toString();
                            System.out.println(currentSolution);
                            //check answer of judge
                            String judgeResponse = db.getResponseForSolution(currentSolution);
                            if(ERROR.equals(judgeResponse)) {
                                throw new MalformedQueryException();
                            }
                            else if(OK_SOLUTION.equals(judgeResponse)) {
                                tries = iterations+1;
                            }
                        }
                        //String currentSolution = getSolution(solution);
                        //testPrint(currentSolution);
                        if(previousSolution != null) {
                            //if this is not the first fluctuation, we need to try to guess exactly what happened
                            QuantumFluctoation guess = guessFluctuation(solution, previousSolution);
                            if(guess != null && guess != QuantumFluctoation.UNKNOWN) {
                                //we found which kind of fluctuation happened, we can drop the previous history and execute this fluctuation on the current list
                                previousSolution = null;
                                for(int b=0; b<solution.length; b++) {
                                    Bit bit = solution[b];
                                    if(bit != null && bit.doesNeedToFluctuate) {
                                        switch(guess) {
                                            case COMPLEMENTED: {
                                                byte newBitValue = complement(bit);
                                                solution[b] = new Bit(bit.POSITION, newBitValue, bit.NUMBER_OF_THIS_QUERY);
                                                break;
                                            }
                                            case REVERSED: {
                                                Bit bitToUse = solution[solution.length - 1 - b];
                                                solution[solution.length - 1 - b] = solution[b];
                                                solution[solution.length - 1 - b].doesNeedToFluctuate = false;
                                                solution[b] = bitToUse;
                                                if(solution[b] != null) {
                                                    solution[b].doesNeedToFluctuate = false;
                                                }
                                                break;
                                            }
                                            case COMPLEMENTED_AND_REVERSED: {
                                                //complement both values
                                                byte newBitValue = complement(bit);
                                                solution[b] = new Bit(bit.POSITION, newBitValue, bit.NUMBER_OF_THIS_QUERY);
                                                bit = solution[solution.length - 1 - b];
                                                newBitValue = complement(bit);
                                                solution[solution.length - 1 - b] = new Bit(bit.POSITION, newBitValue, bit.NUMBER_OF_THIS_QUERY);
                                                //reverse both values
                                                Bit bitToUse = solution[solution.length - 1 - b];
                                                solution[solution.length - 1 - b] = solution[b];
                                                solution[b] = bitToUse;
                                                //fluctuation indicator is set to false already by both because of the constructor
                                                break;
                                            }
                                            case NOTHING: {
                                                solution[b].doesNeedToFluctuate = false;
                                                break;
                                            }
                                            default: {
                                                break;
                                            }
                                        }
                                        //solution[b].doesNeedToFluctuate = false; //constructor sets it to false
                                    }
                                } //end of executing the previous fluctuation on the current solution
                                //let's find the positions where to continue the queries from
                                for(int b=0; b<solution.length; b++) {
                                    Bit bit = solution[b];
                                    if(bit == null || bit.BIT_VALUE == NULL_BYTE) {
                                        positionAtStart = b + 1;
                                        break;
                                    }
                                }
                                for(int b=solution.length-1; 0<=b; b--) {
                                    Bit bit = solution[b];
                                    if(bit == null || bit.BIT_VALUE == NULL_BYTE) {
                                        positionAtEnd = b + 1;
                                        break;
                                    }
                                }
                            } //fluctuation could be guessed if condition's end
                        } //previousSolution was not null if condition's end
                    } //end of quantum fluctuation's period for cyclus
                    String currentSolution = getSolution(solution);
                    testPrint(currentSolution);
                    if(!currentSolution.contains(NULL)) {
                        //if there are no unknown bits any longer and we are still here before the next fluctioation, we are done
                        System.out.println(currentSolution);
                        //check answer of judge
                        String judgeResponse = db.getResponseForSolution(currentSolution);
                        if(ERROR.equals(judgeResponse)) {
                            throw new MalformedQueryException();
                        }
                        else if(OK_SOLUTION.equals(judgeResponse)) {
                            tries = iterations+1;
                        }
                    }
                    else {
                        //we dont know all of the bits yet, and the next iteration will fluctuate the array
                        for(Bit bit : solution) {
                            if(bit != null) {
                                bit.doesNeedToFluctuate = true;
                            }
                        }
                        previousSolution = Arrays.copyOf(solution, solution.length); //we make a copy of the current state
                        //we set back the position indicators to the both end of the array. We need to query them again to figure out what was the fluctuation
                        positionAtStart = 1;
                        positionAtEnd = B;
                    }
                }
            }
        }
        catch(MalformedQueryException e) {
            ;// judge system does not send any response any longer, we need to terminate to see the warning and not get timeout exception instead
        }
    }
    
    /**
     * Helper method to convert the current solution into string and compare that with every different fluctuation of the previous solution as string.
     * If there is only one fluctuation to get the same strings, then we figured out what happened.
     * 
     * @param solution
     * @param previousSolution
     * @return 
     */
    private static QuantumFluctoation guessFluctuation(Bit[] solution, Bit[] previousSolution) {
        Bit[] fixedSolution = Arrays.copyOf(solution, solution.length);
        for(int i=0; i<fixedSolution.length; i++) {
            if(fixedSolution[i] != null && fixedSolution[i].doesNeedToFluctuate) {
                fixedSolution[i] = null;
            }
        }
        String currentSolution = getSolution(fixedSolution);
        String prevSolution = getSolution(previousSolution);
        byte[] previousSolutionBytes = convertStringSolution(prevSolution);
        String prevSolComplemented = getSolution(complement(Arrays.copyOf(previousSolutionBytes, previousSolutionBytes.length)));
        String prevSolReversed = getSolution(reverse(Arrays.copyOf(previousSolutionBytes, previousSolutionBytes.length)));
        String prevSolComplementedAndReversed = getSolution(complementAndReverse(Arrays.copyOf(previousSolutionBytes, previousSolutionBytes.length)));
        List<QuantumFluctoation> guesses = new ArrayList();
        if(mightMatchSolutions(currentSolution, prevSolComplemented)) {
            guesses.add(QuantumFluctoation.COMPLEMENTED);
        }
        if(mightMatchSolutions(currentSolution, prevSolReversed)) {
            guesses.add(QuantumFluctoation.REVERSED);
        }
        if(mightMatchSolutions(currentSolution, prevSolComplementedAndReversed)) {
            guesses.add(QuantumFluctoation.COMPLEMENTED_AND_REVERSED);
        }
        if(mightMatchSolutions(currentSolution, prevSolution)) {
            guesses.add(QuantumFluctoation.NOTHING);
        }
        testPrint("guessFluctuation:\n" + currentSolution + " : currentSolution\n" + prevSolution + " : prevSolution\n" + prevSolComplemented + " : prevSolComplemented\n" + prevSolReversed + " : prevSolReversed\n" + prevSolComplementedAndReversed + " : prevSolComplementedAndReversed");
        testPrint(Arrays.toString(guesses.toArray()));
        return (guesses.size() == 1) ? guesses.get(0) : QuantumFluctoation.UNKNOWN;
    }
    
    /**
     * Helper method to compare the string representation of to solutions, where the current one bits are known.
     * 
     * @param currentSolution
     * @param previousFluctuatedSolution
     * @return 
     */
    private static boolean mightMatchSolutions(String currentSolution, String previousFluctuatedSolution) {
        for(int i=0; i<currentSolution.length(); i++) {
            String bit = currentSolution.substring(i, i+1);
            if(!NULL.equals(bit)) {
                //if it is not a question mark
                if(!bit.equals(previousFluctuatedSolution.substring(i, i+1))) {
                    //if a bit at any place does not match, it is no match
                    return false;
                }
            }
        }
        return true;
    }
    
    /**
     * Helper method to convert a solution string like "0010????1100" into its corresponding byte array with NULL_BYTE=-1 where questionmark was.
     * 
     * @param solutionStr
     * @return 
     */
    private static byte[] convertStringSolution(String solutionStr) {
        byte[] solution = new byte[solutionStr.length()];
        for(int i=0; i<solutionStr.length(); i++) {
            String bit = solutionStr.substring(i, i+1);
            if(NULL.equals(bit)) {
                solution[i] = NULL_BYTE;
            }
            else if(ONE_STR.equals(bit)) {
                solution[i] = ONE;
            }
            else {
                solution[i] = ZERO;
            }
        }
        return solution;
    }
    
    /**
     * Helper method to return our current solution with probably some unknown bits still. E.g.: "0011???1011"
     * 
     * @param solution 
     */
    private static String getSolution(Bit[] solution) {
        StringBuilder sb = new StringBuilder();
        for(Bit bit : solution) {
            sb.append((bit == null || bit.BIT_VALUE == NULL_BYTE) ? NULL : bit.BIT_VALUE);
        }
        return sb.toString();
    }
    
    /**
     * Helper method to return our current solution with probably some unknown bits still. E.g.: "0011???1011"
     * 
     * @param solution 
     */
    private static String getSolution(byte[] solution) {
        StringBuilder sb = new StringBuilder();
        for(byte bit : solution) {
            sb.append((bit == NULL_BYTE) ? NULL : bit);
        }
        return sb.toString();
    }
    
    /**
     * Helper test method to cause fluctuation
     * @param originalArray
     * @return 
     */
    private static byte[] quantumFluctuation(byte[] originalArray) {
        byte[] array = Arrays.copyOf(originalArray, originalArray.length);
        long event = Math.round(Math.random() * 100);
        if(event < 25) {
            testPrint("complement");
            return complement(array);
        }
        else if(event < 50) {
            testPrint("reverse");
            return reverse(array);
        }
        else if(event < 75) {
            testPrint("complementAndReverse");
            return complementAndReverse(array);
        }
        else {
            testPrint("doNothing");
            return doNothing(array);
        }
    }
    
    /**
     * Helper method for testing to return a random bit array of the given size.
     * 
     * @param size
     * @return 
     */
    private static byte[] generateTestArray(int size) {
        byte[] array = new byte[size];
        for(int i=0; i<size; i++) {
            array[i] = (byte) Math.round(Math.random());
        }
        return array;
        //return new byte[]{0,0,0,1,1,0,1,1,1,1};
    }
    
    /**
     * Helper test method to complement the given array, that is to make from each 1 bit to a 0 and vica versa.
     * 
     * @param array
     * @return 
     */
    private static byte[] complement(byte[] array) {
        for(int i=0; i<array.length; i++) {
            if(array[i] == NULL_BYTE) {
                continue;
            }
            array[i] = (array[i] == ZERO) ? ONE : ZERO;
        }
        return array;
    }
    
    /**
     * Helper method to return the complement of this bit.
     * 
     * @param bit
     * @return 
     */
    private static byte complement(Bit bit) {
        if(bit.BIT_VALUE == NULL_BYTE) {
            return NULL_BYTE;
        }
        else if(bit.BIT_VALUE == ONE) {
            return ZERO;
        }
        return ONE;
    }
    
    /**
     * Helper test method to reverse the given array.
     * 
     * @param array
     * @return 
     */
    private static byte[] reverse(byte[] array) {
        final int HALF_OF_ARRAY = array.length / 2; //int rounds down
        for(int i=0; i<HALF_OF_ARRAY; i++) {
            byte temp = array[i];
            array[i] = array[array.length - 1 - i];
            array[array.length - 1 - i] = temp;
        }
        return array;
    }
    
    /**
     * Helper test method to complement the given array, that is to make from each 1 bit to a 0 and vica versa, and then to reverse it.
     * (complement+reverse = reverse+complement)
     * 
     * @param array
     * @return 
     */
    private static byte[] complementAndReverse(byte[] array) {
        byte[] temp = complement(array);
        temp = reverse(temp);
        return temp;
    }
    
    /**
     * Helper test method to simulate the 4th case, when nothing happens to the array.
     * 
     * @param array
     * @return 
     */
    private static byte[] doNothing(byte[] array) {
        return array;
    }
    
    /**
     * Helper test method for printing debug messages, which can be turned off easily only changing this 1 line here not to do anything.
     * 
     * @param array 
     */
    private static void testPrint(String str) {
        System.err.println(str);
    }
}
