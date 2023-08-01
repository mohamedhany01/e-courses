package org.huffman;

import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class HuffmanEncoder {
    private String encodedText;
    private String text;
    private Node root;

    public HuffmanEncoder(String text) {
        this.text = text;
        this.root = buildEncodedTree(builtFrequencyTable(text));
        this.encodedText = getEncodedText(text, buildCompressedMap(this.root));
    }

    public String getEncodedText() {
        return encodedText;
    }

    public String getText() {
        return text;
    }

    public Node getTree() {
        return root;
    }

    private String getEncodedText(String text, HashMap<Character, String> compressedMap) {
        StringBuilder encodedTextBuilder = new StringBuilder();
        char[] characters = text.toCharArray();

        for (Character character : characters) {
            encodedTextBuilder.append(compressedMap.get(character));
        }

        return encodedTextBuilder.toString();
    }

    private HashMap<Character, Integer> builtFrequencyTable(String text) {
        if (text.isEmpty()) {
            throw new RuntimeException("Can't encode empty text!");
        }

        HashMap<Character, Integer> frequenciesMap = new HashMap<>();

        if (text.length() == 1) {
            frequenciesMap.put(text.charAt(0), 1);

            return frequenciesMap;
        }

        for (int i = 0; i < text.length(); i++) {
            Character key = text.charAt(i);
            if (frequenciesMap.containsKey(key)) {
                Integer oldFrequency = frequenciesMap.get(text.charAt(i)) + 1;
                frequenciesMap.put(key, oldFrequency);
            } else {
                frequenciesMap.put(key, 1);
            }
        }
        return frequenciesMap;
    }

    private HashMap<Character, String> buildCompressedMap(Node tree) {
        HashMap<Character, String> compressedMap = new HashMap<>();
        String encodedLetter = "";
        buildCompressedMap(tree, compressedMap, encodedLetter);
        return compressedMap;
    }

    private void buildCompressedMap(Node node, HashMap<Character, String> compressedMap, String encodedLetter) {
        if (node.isLeaf()) {
            compressedMap.put(node.getLetter(), encodedLetter);
        } else {
            buildCompressedMap(node.getLeftNode(), compressedMap, encodedLetter + BINARY.ZERO.label);
            buildCompressedMap(node.getRightNode(), compressedMap, encodedLetter + BINARY.ONE.label);
        }
    }

    private Node buildEncodedTree(HashMap<Character, Integer> frequencyTable) {
        if (frequencyTable == null) {
            throw new RuntimeException("The frequency table is NULL!");
        }

        if (frequencyTable.isEmpty()) {
            throw new RuntimeException("The frequency table is Empty!");
        }

        PriorityQueue<Node> frequencyQueue = new PriorityQueue<>();
        Node root;
        for (Map.Entry<Character, Integer> table : frequencyTable.entrySet()) {
            Character letter = table.getKey();
            Integer frequency = table.getValue();

            // Insert the letters using their frequencies from smallest to largest
            frequencyQueue.add(new Node(null, null, frequency, letter));

        }

        // Start building the try from button-up
        while (frequencyQueue.size() > 1) {
            Node leftNode = frequencyQueue.poll();
            Node rightNode = frequencyQueue.poll();

            Integer parentFrequency = leftNode.getFrequency() + rightNode.getFrequency();

            frequencyQueue.add(new Node(leftNode, rightNode, parentFrequency, '\0'));
        }

        // Catch the root
        root = frequencyQueue.poll();

        return root;
    }

    @Override
    public String toString() {
        return "HuffmanEncoder{" +
                "encodedText='" + encodedText + '\'' +
                ", text='" + text + '\'' +
                ", root=" + root +
                '}';
    }

    private enum BINARY {
        ZERO("0"),
        ONE("1");
        public final String label;

        private BINARY(String label) {
            this.label = label;
        }
    }
}
