import java.io.*;
import java.util.*;

public class Solution {
    FastScanner in;
    PrintWriter out;

    void solve() {
        int tc = in.nextInt();
        int n = in.nextInt();
        int m = in.nextInt();
        int[] vals = new int[]{3, 5, 7, 11, 13, 17, 16};
        final int BIG = 18;
        for (int t = 0; t < tc; t++) {
            int[] r = new int[vals.length];
            for (int i = 0; i < n; i++) {
                int pos = i % vals.length;
                int prime = vals[pos];
                for (int j = 0; j < BIG; j++) {
                    out.print(prime + " ");
                }
                out.println();
                out.flush();
                r[pos] = 0;
                for (int j = 0; j < BIG; j++) {
                    r[pos] += in.nextInt();
                }
                r[pos] %= prime;
            }
            for (int ans = 1; ans <= m; ans++) {
                boolean ok = true;
                for (int i =0 ; i < vals.length; i++) {
                    if (ans % vals[i] != r[i]) {
                        ok = false;
                        break;
                    }
                }
                if (ok) {
                    out.println(ans);
                    out.flush();
                    if (in.nextInt() != 1) {
                        throw new AssertionError();
                    }
                    break;
                }
            }
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