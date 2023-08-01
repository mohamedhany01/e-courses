package org.huffman;

public class Playground {
    public static void main(String[] args) {
        HuffmanEncoder encoder = new HuffmanEncoder("Hello, World!");
        HuffmanDecoder decoder = new HuffmanDecoder(encoder.getEncodedText(), encoder.getTree());
        System.out.println(encoder.getEncodedText());
        System.out.println(decoder.getDecodedText());
    }
}