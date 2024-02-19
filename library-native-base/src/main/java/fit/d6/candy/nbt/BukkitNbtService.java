package fit.d6.candy.nbt;

import com.google.gson.JsonObject;
import fit.d6.candy.BukkitService;
import fit.d6.candy.api.nbt.*;
import fit.d6.candy.nms.NmsAccessor;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BukkitNbtService extends BukkitService implements NbtService {

    @Override
    public @NotNull String getId() {
        return "nbt";
    }

    @Override
    public @NotNull NbtCompound compoundNbt() {
        return NmsAccessor.getAccessor().compoundNbt();
    }

    @Override
    public @NotNull NbtList listNbt() {
        return NmsAccessor.getAccessor().listNbt();
    }

    @Override
    public @NotNull NbtEnd endNbt() {
        return NmsAccessor.getAccessor().endNbt();
    }

    @Override
    public @NotNull NbtString stringNbt(String value) {
        return NmsAccessor.getAccessor().stringNbt(value);
    }

    @Override
    public @NotNull NbtByte byteNbt(byte value) {
        return NmsAccessor.getAccessor().byteNbt(value);
    }

    @Override
    public @NotNull NbtShort shortNbt(short value) {
        return NmsAccessor.getAccessor().shortNbt(value);
    }

    @Override
    public @NotNull NbtInt intNbt(int value) {
        return NmsAccessor.getAccessor().intNbt(value);
    }

    @Override
    public @NotNull NbtLong longNbt(long value) {
        return NmsAccessor.getAccessor().longNbt(value);
    }

    @Override
    public @NotNull NbtFloat floatNbt(float value) {
        return NmsAccessor.getAccessor().floatNbt(value);
    }

    @Override
    public @NotNull NbtDouble doubleNbt(double value) {
        return NmsAccessor.getAccessor().doubleNbt(value);
    }

    @Override
    public @NotNull NbtByteArray byteArrayNbt(byte[] value) {
        return NmsAccessor.getAccessor().byteArrayNbt(value);
    }

    @Override
    public @NotNull NbtByteArray byteArrayNbt(List<Byte> value) {
        return NmsAccessor.getAccessor().byteArrayNbt(value);
    }

    @Override
    public @NotNull NbtIntArray intArrayNbt(int[] value) {
        return NmsAccessor.getAccessor().intArrayNbt(value);
    }

    @Override
    public @NotNull NbtIntArray intArrayNbt(List<Integer> value) {
        return NmsAccessor.getAccessor().intArrayNbt(value);
    }

    @Override
    public @NotNull NbtLongArray longArrayNbt(long[] value) {
        return NmsAccessor.getAccessor().longArrayNbt(value);
    }

    @Override
    public @NotNull NbtLongArray longArrayNbt(List<Long> value) {
        return NmsAccessor.getAccessor().longArrayNbt(value);
    }

    @Override
    public @NotNull JsonObject asJson(@NotNull NbtCompound compound) {
        return NmsAccessor.getAccessor().asJson(compound);
    }

    @Override
    public @NotNull NbtCompound asNbt(@NotNull JsonObject json) {
        return NmsAccessor.getAccessor().asNbt(json);
    }

}
