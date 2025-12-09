package strings;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.PriorityQueue;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;


public class HuffmanDecoderGenerator {
    private static Hashtable<Character, String> codes;

    public static void main(String [] args) throws Exception{
        HashMap<Integer, String> sentences = new HashMap<>();
        BufferedReader br = new BufferedReader(new FileReader("data/strings.HuffmanDecoder/LoremIpsum.txt"));

        int LignNumber = 0;
        String ligne;
        while ((ligne = br.readLine()) != null) {
            sentences.put(LignNumber, ligne);
            LignNumber++;
        }
        br.close();

        for (int instance_id = 0; instance_id < LignNumber; instance_id++) {
            String randsentence = sentences.get(instance_id);
            HuffmanTree(randsentence);
            String encodedsentence = encode(randsentence);
            String instance_file = "data/strings.HuffmanDecoder/in_" + instance_id;
            writeInstance(instance_file, randsentence, encodedsentence);
        }
    }


    public static HuffmanDecoder.Node HuffmanTree(String s) {
        /**
         * Builds a Huffman tree and return the encoded sentence s
         */
        Hashtable<Character,Integer> uniquesymbolfreq = new Hashtable<>();

        for (char c : s.toCharArray()) {
            uniquesymbolfreq.put(c, uniquesymbolfreq.getOrDefault(c, 0) + 1);
        }

        PriorityQueue<HuffmanDecoder.Node> pq = new PriorityQueue<>();
        for (Character c : uniquesymbolfreq.keySet()) {
            pq.add(new HuffmanDecoder.Node(String.valueOf(c), uniquesymbolfreq.get(c), null, null));
        }

        while (pq.size() > 1) {
            HuffmanDecoder.Node left = pq.poll();
            HuffmanDecoder.Node right = pq.poll();
            pq.add(new HuffmanDecoder.Node(left.symbol + right.symbol, left.freq + right.freq, left, right));
        }

        HuffmanDecoder.Node root = pq.poll();
        codes = new Hashtable<>();
        generateCodes(root, "");

        return root;
    }

    private static void generateCodes(HuffmanDecoder.Node node, String code) {
        if (node == null) return;
        if (node.left == null && node.right == null) {
            codes.put(node.symbol.charAt(0), code.isEmpty() ? "0" : code);
            return;
        }
        generateCodes(node.left, code + "0");
        generateCodes(node.right, code + "1");
    }

    public static String encode(String s) {
        StringBuilder encoded = new StringBuilder();
        for (char c : s.toCharArray()) {
            encoded.append(codes.get(c));
        }
        return encoded.toString();
    }


    private static void writeInstance(String file, String randsentence, String encodedsentence) {
        try {
            PrintWriter p = new PrintWriter(new FileOutputStream(file));
            p.println(randsentence);
            p.println(encodedsentence);
            p.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
