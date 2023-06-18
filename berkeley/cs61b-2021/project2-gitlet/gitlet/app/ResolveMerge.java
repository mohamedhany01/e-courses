package gitlet.app;

public class ResolveMerge {
    private static void step4() {
        StringBuilder message = new StringBuilder();

        String[] add = new String[]{
                "a.txt",
                "x.txt",
        };

        for (int i = 0; i < add.length; i++) {
            App.add(add[i]);
            message.append(add[i] + " [U], ");
        }

        String[] rm = new String[]{
                "d.txt",
        };

        for (int i = 0; i < rm.length; i++) {
            App.rm(rm[i]);
            message.append(add[i] + " [R], ");
        }
        App.status();

        App.commit(message.toString());

        App.log();
    }

    public static void main(String[] args) {
        // Handle conflicts
        step4();
    }
}
