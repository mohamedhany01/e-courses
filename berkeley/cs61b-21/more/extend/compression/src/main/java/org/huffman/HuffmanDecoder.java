package org.huffman;

public class HuffmanDecoder {
    private String decodedText;

    public HuffmanDecoder(String encodedText, Node tree) {
        this.decodedText = decodeText(encodedText, tree);
    }

    @Override
    public String toString() {
        return "HuffmanDecoder{" +
                "decodedText='" + decodedText + '\'' +
                '}';
    }

    public String getDecodedText() {
        return decodedText;
    }

    private String decodeText(String encodedText, Node tree) {
        StringBuilder encodedTexBuilder = new StringBuilder();
        Node root = tree;
        int textLength = encodedText.length();
        int i = 0;
        while (i < textLength) {
            while (!root.isLeaf()) {

                char bit = encodedText.charAt(i);

                if (bit == '0') {
                    root = root.getLeftNode();
                } else if (bit == '1') {
                    root = root.getRightNode();

                } else {
                    throw new RuntimeException("Invalid bit: " + bit);
                }

                i++;
            }

            encodedTexBuilder.append(root.getLetter());

            // Back to the top of the tree aging
            root = tree;
        }

        return encodedTexBuilder.toString();
    }
}
