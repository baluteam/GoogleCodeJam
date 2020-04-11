import java.io.*;
import java.util.*;

public class Solution {
    FastScanner in;
    PrintWriter out;

    class Node {
        Node[] next;
        boolean endHere;

        Node() {
            next = new Node[26];
        }

        void add(String s, int pos) {
            if (pos == s.length()) {
                endHere = true;
            } else {
                int to = s.charAt(pos) - 'A';
                if (next[to] == null) {
                    next[to] = new Node();
                }
                next[to].add(s, pos + 1);
            }
        }

        int[] getAns(boolean root) {
            int[] ans = new int[]{0, endHere ? 1 : 0};
            for (int i = 0; i < next.length; i++) {
                if (next[i] != null) {
                    int[] ch = next[i].getAns(false);
                    ans[0] += ch[0];
                    ans[1] += ch[1];
                }
            }
            if (ans[1] >= 2 && !root) {
                ans[0]++;
                ans[1] -= 2;
            }
            return ans;
        }
    }

    void solve() {
        int tc = in.nextInt();
        for (int t = 0; t < tc; t++) {
            int n = in.nextInt();
            Node root = new Node();
            for (int i = 0; i < n; i++) {
                String s = new StringBuilder(in.next()).reverse().toString();
                root.add(s, 0);
            }
            out.println("Case #" + (t + 1) + ": " + root.getAns(true)[0] * 2);
        }
    }

    void run() {
        try {
            in = new FastScanner(new File("Solution.in"));
            out = new PrintWriter(new File("Solution.out"));

            solve();

            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    void runIO() {

        in = new FastScanner(System.in);
        out = new PrintWriter(System.out);

        solve();

        out.close();
    }

    class FastScanner {
        BufferedReader br;
        StringTokenizer st;

        public FastScanner(File f) {
            try {
                br = new BufferedReader(new FileReader(f));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        public FastScanner(InputStream f) {
            br = new BufferedReader(new InputStreamReader(f));
        }

        String next() {
            while (st == null || !st.hasMoreTokens()) {
                String s = null;
                try {
                    s = br.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (s == null)
                    return null;
                st = new StringTokenizer(s);
            }
            return st.nextToken();
        }

        boolean hasMoreTokens() {
            while (st == null || !st.hasMoreTokens()) {
                String s = null;
                try {
                    s = br.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (s == null)
                    return false;
                st = new StringTokenizer(s);
            }
            return true;
        }

        int nextInt() {
            return Integer.parseInt(next());
        }

        long nextLong() {
            return Long.parseLong(next());
        }

        double nextDouble() {
            return Double.parseDouble(next());
        }
    }

    public static void main(String[] args) {
        new Solution().runIO();
    }
}