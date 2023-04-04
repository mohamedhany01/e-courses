package gitlet.interfaces;

import java.util.HashMap;

public interface IHEAD {
    String updateHEAD(String newHash);

    String getHEAD();

    HashMap<String, String> getCommitFiles();
}
