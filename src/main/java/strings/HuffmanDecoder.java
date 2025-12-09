package strings;


public class HuffmanDecoder {

    public static String decode(String s, Node root) {
        // BEGIN STRIP
        if (s.isEmpty()) return null;
        StringBuilder decoded = new StringBuilder();
        Node current = root;

        char[] chararray = s.toCharArray();
        if (chararray.length == 1) return root.symbol;

        for (char bit : chararray) {
            if (bit == '0') current = current.left;
            else current = current.right;

            if (current.left == null && current.right == null) {
                decoded.append(current.symbol);
                current = root;
            }
        }

        return decoded.toString();
        // END STRIP
        // STUDENT return null;
    }


    public static class Node implements Comparable<Node> {
        String symbol;
        int freq;
        Node left;
        Node right;

        public Node(String symbol, int frequency, Node left, Node right) {
            this.symbol = symbol;
            this.freq = frequency;
            this.left = left;
            this.right = right;
        }

        @Override
        public int compareTo(Node other) {
            return this.freq - other.freq;
        }
    }

}