import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.io.BufferedWriter;
import java.util.InputMismatchException;
import java.io.IOException;
import java.io.Writer;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.io.InputStream;

/**
 * Built using CHelper plug-in
 * Actual solution is at the top
 *
 * @author Egor Kulikov (egor@egork.net)
 */
public class Solution {
    public static void main(String[] args) {
        InputStream inputStream = System.in;
        OutputStream outputStream = System.out;
        InputReader in = new InputReader(inputStream);
        OutputWriter out = new OutputWriter(outputStream);
        QualificationRound2019GoogleCodeJam2019 solver = new QualificationRound2019GoogleCodeJam2019();
        int testCount = Integer.parseInt(in.next());
        for (int i = 1; i <= testCount; i++) {
            solver.solve(i, in, out);
        }
        out.close();
    }

    static class QualificationRound2019GoogleCodeJam2019 {
        public void solve(int testNumber, InputReader in, OutputWriter out) {
            in.readString();
            int l = in.readInt();
            BigInteger[] cipher = new BigInteger[l];
            for (int i = 0; i < l; i++) {
                cipher[i] = in.readBigInteger();
            }
            BigInteger[] numbers = new BigInteger[l + 1];
            for (int i = 1; i < l; i++) {
                if (!cipher[i].equals(cipher[0])) {
                    numbers[i - 1] = cipher[i - 1].divide(cipher[i - 1].gcd(cipher[i]));
                    for (int j = i - 2; j >= 0; j--) {
                        numbers[j] = cipher[j].divide(numbers[j + 1]);
                    }
                    break;
                }
            }
            for (int i = 0; i < l; i++) {
                numbers[i + 1] = cipher[i].divide(numbers[i]);
            }
            BigInteger[] all = numbers.clone();
            Arrays.sort(all);
            int at = 0;
            for (int i = 0; i <= l; i++) {
                if (i == 0 || !all[i].equals(all[i - 1])) {
                    all[at++] = all[i];
                }
            }
            all = Arrays.copyOf(all, at);
            char[] answer = new char[l + 1];
            for (int i = 0; i <= l; i++) {
                answer[i] = (char) ('A' + Arrays.binarySearch(all, numbers[i]));
            }
            out.printLine("Case #" + testNumber + ":", new String(answer));
        }

    }

    static class OutputWriter {
        private final PrintWriter writer;

        public OutputWriter(OutputStream outputStream) {
            writer = new PrintWriter(new BufferedWriter(new OutputStreamWriter(outputStream)));
        }

        public OutputWriter(Writer writer) {
            this.writer = new PrintWriter(writer);
        }

        public void print(Object... objects) {
            for (int i = 0; i < objects.length; i++) {
                if (i != 0) {
                    writer.print(' ');
                }
                writer.print(objects[i]);
            }
        }

        public void printLine(Object... objects) {
            print(objects);
            writer.println();
        }

        public void close() {
            writer.close();
        }

    }

    static class InputReader {
        private InputStream stream;
        private byte[] buf = new byte[1024];
        private int curChar;
        private int numChars;
        private InputReader.SpaceCharFilter filter;

        public InputReader(InputStream stream) {
            this.stream = stream;
        }

        public int read() {
            if (numChars == -1) {
                throw new InputMismatchException();
            }
            if (curChar >= numChars) {
                curChar = 0;
                try {
                    numChars = stream.read(buf);
                } catch (IOException e) {
                    throw new InputMismatchException();
                }
                if (numChars <= 0) {
                    return -1;
                }
            }
            return buf[curChar++];
        }

        public int readInt() {
            int c = read();
            while (isSpaceChar(c)) {
                c = read();
            }
            int sgn = 1;
            if (c == '-') {
                sgn = -1;
                c = read();
            }
            int res = 0;
            do {
                if (c < '0' || c > '9') {
                    throw new InputMismatchException();
                }
                res *= 10;
                res += c - '0';
                c = read();
            } while (!isSpaceChar(c));
            return res * sgn;
        }

        public String readString() {
            int c = read();
            while (isSpaceChar(c)) {
                c = read();
            }
            StringBuilder res = new StringBuilder();
            do {
                if (Character.isValidCodePoint(c)) {
                    res.appendCodePoint(c);
                }
                c = read();
            } while (!isSpaceChar(c));
            return res.toString();
        }

        public boolean isSpaceChar(int c) {
            if (filter != null) {
                return filter.isSpaceChar(c);
            }
            return isWhitespace(c);
        }

        public static boolean isWhitespace(int c) {
            return c == ' ' || c == '\n' || c == '\r' || c == '\t' || c == -1;
        }

        public BigInteger readBigInteger() {
            try {
                return new BigInteger(readString());
            } catch (NumberFormatException e) {
                throw new InputMismatchException();
            }
        }

        public String next() {
            return readString();
        }

        public interface SpaceCharFilter {
            public boolean isSpaceChar(int ch);

        }

    }
}

