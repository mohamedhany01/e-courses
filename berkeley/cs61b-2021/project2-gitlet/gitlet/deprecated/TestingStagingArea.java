package gitlet.deprecated;

import gitlet.trees.staging.GLStagingArea;

public class TestingStagingArea {
    public static void main(String[] args) {
        GLStagingArea stagingArea = new GLStagingArea();
        for (String x : stagingArea.getUntrackedFiles()) {
            System.out.println(x);
        }
    }
}