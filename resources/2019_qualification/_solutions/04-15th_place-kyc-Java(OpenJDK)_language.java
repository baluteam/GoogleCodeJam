

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Solution {

    public static void main(String[] args) throws Exception {
        Scanner input = new Scanner(System.in);
        int numCases = input.nextInt();
        for (int n = 0; n < numCases; n++) {
            int N = input.nextInt();
            int B = input.nextInt();
            int F = input.nextInt();
            int sentinel = 16 - (N % 16);

            StringBuilder sb;
            {
                sb = new StringBuilder();
                for (int i = 0; i < N + sentinel; i++)
                    sb.append(i % 32 < 16 ? '0' : '1');
                System.out.println(sb.toString().substring(0, N));
                System.out.flush();
            }

            List<Integer> numBad = new ArrayList<>();
            {
                String line = input.next() + sb.toString().substring(N);
                int index = 0;
                for (int i = 0; i < (N + sentinel) / 16; i++) {
                    char c = line.charAt(index);
                    int j = line.indexOf('0' + '1' - c, index);
                    if (j == -1)
                        j = line.length();
                    numBad.add(16 - (j - index));
                    index = j;
                }
            }

//            System.out.println("O: " + numBad);
            for (int k : new int[] { 8, 4, 2, 1 }) {
                sb = new StringBuilder();
                for (int i = 0; i < N + sentinel; i++)
                    sb.append(i % (2 * k) < k ? '0' : '1');
                System.out.println(sb.toString().substring(0, N));
                System.out.flush();

                String line = input.next() + sb.toString().substring(N);
                int index = 0;
                List<Integer> newNumBad = new ArrayList<>();
                for (int i = 0; i < numBad.size(); i++) {
                    String result = line.substring(index, index + 2 * k - numBad.get(i));
                    index += 2 * k - numBad.get(i);
                    int j = result.indexOf('1');
                    if (j == -1)
                        j = result.length();
                    newNumBad.add(k - j);
                    newNumBad.add(k - (result.length() - j));
                }
                numBad = newNumBad;
//                System.out.println(k + ": " + numBad);
            }

            List<Integer> bads = new ArrayList<>();
            for (int i = 0; i < numBad.size(); i++)
                if (numBad.get(i) == 1)
                    bads.add(i);

//            System.out.printf("Case #%d: ", n + 1);
            for (int bad : bads)
                System.out.printf("%s ", bad);
            System.out.println();

            input.nextInt();
        }
    }
}
