import java.util.Scanner;

public class Solution {

    public static void main(String[] args) throws Exception {
        Scanner input = new Scanner(System.in);
        int numCases = input.nextInt();
        for (int n = 0; n < numCases; n++) {
            int N = input.nextInt();
            String p = input.next();

            StringBuilder sb = new StringBuilder();
            for (char c : p.toCharArray()) {
                if (c == 'E')
                    sb.append('S');
                else sb.append('E');
            }

            System.out.printf("Case #%d: ", n + 1);
            System.out.println(sb.toString());
        }
    }
}
