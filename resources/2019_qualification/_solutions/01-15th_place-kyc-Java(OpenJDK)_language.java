import java.math.BigInteger;
import java.util.Scanner;

public class Solution {

    public static void main(String[] args) throws Exception {
        Scanner input = new Scanner(System.in);
        int numCases = input.nextInt();
        for (int n = 0; n < numCases; n++) {
            String N = input.next();

            StringBuilder sb = new StringBuilder();
            for (char c : N.toCharArray()) {
                if (c == '4')
                    sb.append('1');
                else sb.append(0);
            }
            BigInteger a = new BigInteger(sb.toString());
            BigInteger c = new BigInteger(N);
            BigInteger b = c.subtract(a);

            System.out.printf("Case #%d: ", n + 1);
            System.out.println(a + " " + b);
        }
    }
}
