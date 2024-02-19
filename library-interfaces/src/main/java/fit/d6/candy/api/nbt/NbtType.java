package fit.d6.candy.api.nbt;

public enum NbtType {

    END(0, 0),
    BYTE(99, 1),
    SHORT(99, 2),
    INT(99, 3),
    LONG(99, 4),
    FLOAT(99, 5),
    DOUBLE(99, 6),
    BYTE_ARRAY(7, 7),
    STRING(8, 8),
    LIST(9, 9),
    COMPOUND(10, 10),
    INT_ARRAY(11, 11),
    LONG_ARRAY(12, 12),
    ANY_NUMBER(99, 99);

    private final int category;
    private final int id;

    NbtType(int category, int id) {
        this.category = category;
        this.id = id;
    }

    public int getCategory() {
        return category;
    }

    public int getId() {
        return id;
    }

    public static NbtType getById(int id) {
        for (NbtType type : NbtType.values())
            if (type.id == id)
                return type;
        throw new RuntimeException("Unknown nbt type id");
    }

}
