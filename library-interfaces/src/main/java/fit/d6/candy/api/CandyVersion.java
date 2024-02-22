package fit.d6.candy.api;

public enum CandyVersion {

    V1_17_R1,
    V1_18_R1,
    V1_18_R2,
    V1_19_R1,
    V1_19_R2,
    V1_19_R3(true, true),
    V1_20_R1(true, true),
    V1_20_R2(true, true),
    V1_20_R3(true, true),
    V1_21(true, true);


    private final boolean foliaSchedulerSupport;
    private final boolean worldEnvironemtnSupport;

    CandyVersion() {
        this(false, false);
    }

    CandyVersion(boolean foliaSchedulerSupport, boolean worldEnvironmentSupport) {
        this.foliaSchedulerSupport = foliaSchedulerSupport;
        this.worldEnvironemtnSupport = worldEnvironmentSupport;
    }

    public boolean isFoliaSchedulerSupport() {
        return foliaSchedulerSupport;
    }

    public boolean isWorldEnvironemtnSupport() {
        return worldEnvironemtnSupport;
    }

}
