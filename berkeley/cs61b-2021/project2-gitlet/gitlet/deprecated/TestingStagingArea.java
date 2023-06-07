package gitlet.deprecated;

import gitlet.trees.staging.StagingArea;

public class TestingStagingArea {
    public static void main(String[] args) {
        StagingArea stagingArea = new StagingArea();
        for (String x : stagingArea.getUntrackedFiles()) {
            System.out.println(x);
        }
    }
}
