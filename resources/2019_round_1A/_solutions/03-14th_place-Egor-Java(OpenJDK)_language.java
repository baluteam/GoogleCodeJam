import java.io.OutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.util.InputMismatchException;
import java.io.IOException;
import java.io.Writer;
import java.io.OutputStreamWriter;
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
        AlienRhyme solver = new AlienRhyme();
        int testCount = Integer.parseInt(in.next());
        for (int i = 1; i <= testCount; i++) {
            solver.solve(i, in, out);
        }
        out.close();
    }

    static class AlienRhyme {
        public void solve(int testNumber, InputReader in, OutputWriter out) {
            int n = in.readInt();
            String[] w = in.readStringArray(n);
            for (int i = 0; i < n; i++) {
                w[i] = StringUtils.reverse(w[i]);
            }
            Trie trie = new Trie();
            for (String s : w) {
                trie.add(s);
            }
            Trie.Node root = trie.root;
            int answer = n;
            for (int i = 0; i < root.links.length; i++) {
                answer -= go(root.links[i]);
            }
            out.printLine("Case #" + testNumber + ":", answer);
        }

        private int go(Trie.Node node) {
            if (node == null) {
                return 0;
            }
            int result = 0;
            if (node.leaf) {
                result++;
            }
            for (int i = 0; i < node.links.length; i++) {
                result += go(node.links[i]);
            }
            if (result >= 2) {
                result -= 2;
            }
            return result;
        }

    }

    static class Trie {
        public final Trie.Node root = new Trie.Node();

        public void add(CharSequence word) {
            Trie.Node current = root;
            int length = word.length();
            for (int i = 0; i < length; i++) {
                char letter = word.charAt(i);
                if (current.links[letter] == null) {
                    current.links[letter] = new Trie.Node();
                }
                current = current.links[letter];
            }
            current.leaf = true;
        }

        public static class Node {
            public final Trie.Node[] links = new Trie.Node[128];
            public boolean leaf = false;

        }

    }

    static class StringUtils {
        public static String reverse(String sample) {
            StringBuilder result = new StringBuilder(sample);
            result.reverse();
            return result.toString();
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

        public String[] readStringArray(int size) {
            String[] array = new String[size];
            for (int i = 0; i < size; i++) {
                array[i] = readString();
            }
            return array;
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

        public String next() {
            return readString();
        }

        public interface SpaceCharFilter {
            public boolean isSpaceChar(int ch);

        }

    }
}

