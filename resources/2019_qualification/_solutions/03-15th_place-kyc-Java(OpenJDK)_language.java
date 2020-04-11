

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class Solution {

    public static void main(String[] args) throws Exception {
        Scanner input = new Scanner(System.in);
        int numCases = input.nextInt();
        for (int n = 0; n < numCases; n++) {
            String N = input.next();
            int K = input.nextInt();
            BigInteger[] nums = new BigInteger[K];
            for (int i = 0; i < K; i++)
                nums[i] = new BigInteger(input.next());

            int start = 0;
            while (nums[start].equals(nums[start + 1]))
                start++;

            BigInteger[] ans = new BigInteger[K + 1];
            ans[start + 1] = gcd(nums[start], nums[start + 1]);
            for (int i = start; i >= 0; i--)
                ans[i] = nums[i].divide(ans[i + 1]);
            for (int i = start + 2; i <= K; i++)
                ans[i] = nums[i - 1].divide(ans[i - 1]);

            List<BigInteger> sorted = new ArrayList<>(new HashSet<>(Arrays.asList(ans)));
            Collections.sort(sorted);
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i <= K; i++)
                sb.append((char) (65 + sorted.indexOf(ans[i])));

            System.out.printf("Case #%d: ", n + 1);
            System.out.println(sb.toString());
        }
    }

    public static BigInteger gcd(BigInteger a, BigInteger b) {
        return b.equals(BigInteger.ZERO) ? a : gcd(b, a.mod(b));
    }
}
