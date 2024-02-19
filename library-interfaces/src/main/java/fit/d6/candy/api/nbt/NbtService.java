package fit.d6.candy.api.nbt;

import com.google.gson.JsonObject;
import fit.d6.candy.api.CandyLibrary;
import fit.d6.candy.api.Service;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface NbtService extends Service {

    @NotNull
    NbtCompound compoundNbt();

    @NotNull
    NbtList listNbt();

    @NotNull
    NbtEnd endNbt();

    @NotNull
    NbtString stringNbt(String value);

    @NotNull
    NbtByte byteNbt(byte value);

    @NotNull
    NbtShort shortNbt(short value);

    @NotNull
    NbtInt intNbt(int value);

    @NotNull
    NbtLong longNbt(long value);

    @NotNull
    NbtFloat floatNbt(float value);

    @NotNull
    NbtDouble doubleNbt(double value);

    @NotNull
    NbtByteArray byteArrayNbt(byte[] value);

    @NotNull
    NbtByteArray byteArrayNbt(List<Byte> value);

    @NotNull
    NbtIntArray intArrayNbt(int[] value);

    @NotNull
    NbtIntArray intArrayNbt(List<Integer> value);

    @NotNull
    NbtLongArray longArrayNbt(long[] value);

    @NotNull
    NbtLongArray longArrayNbt(List<Long> value);

    @NotNull
    JsonObject asJson(@NotNull NbtCompound compound);

    @NotNull
    NbtCompound asNbt(@NotNull JsonObject json);

    @NotNull
    static NbtService getService() {
        return CandyLibrary.getLibrary().getService(NbtService.class);
    }

}
