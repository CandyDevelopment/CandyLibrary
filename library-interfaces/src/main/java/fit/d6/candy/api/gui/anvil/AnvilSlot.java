package fit.d6.candy.api.gui.anvil;

public enum AnvilSlot {

    LEFT_INPUT(0),
    RIGHT_INPUT(1),
    RESULT(2);

    private final int index;

    AnvilSlot(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

}
