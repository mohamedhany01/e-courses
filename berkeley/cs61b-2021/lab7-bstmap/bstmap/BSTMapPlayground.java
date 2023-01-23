package bstmap;

public class BSTMapPlayground {
    public static void main(String[] args) {
        BSTMap<Integer, String> map = new BSTMap<>();
        map.put(44, "Hello");
        map.put(55, "Buz");
        map.put(22, "gggg");
        map.put(22, "DDDD");
        System.out.println(map.get(44));
    }
}
