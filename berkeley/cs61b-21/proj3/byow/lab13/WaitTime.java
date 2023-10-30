package byow.lab13;

public enum WaitTime {
    HALF_SECOND(500),
    ONE_SECOND(1000),
    ONE_AND_A_HALF_SECONDS(1500);

    public final int value;

    WaitTime(int value) {
        this.value = value;
    }
}
