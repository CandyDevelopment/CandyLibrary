package fit.d6.candy.nms.v1_20_R3.nbt;

import fit.d6.candy.api.nbt.NbtNumber;
import net.minecraft.nbt.NumericTag;
import net.minecraft.nbt.Tag;
import org.jetbrains.annotations.NotNull;

import java.io.DataOutput;
import java.io.IOException;

public abstract class BukkitNbtNumber extends BukkitNbtBase implements NbtNumber {

    private final NumericTag tag;

    public BukkitNbtNumber(NumericTag nms) {
        this.tag = nms;
    }

    @Override
    public void write(@NotNull DataOutput output) {
        try {
            this.tag.write(output);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getSizeInBytes() {
        return this.tag.sizeInBytes();
    }

    @Override
    public byte getId() {
        return (byte) this.getType().getId();
    }

    @Override
    public @NotNull String getAsString() {
        return this.tag.getAsString();
    }

    @Override
    public long getAsLong() {
        return this.tag.getAsLong();
    }

    @Override
    public int getAsInt() {
        return this.tag.getAsInt();
    }

    @Override
    public short getAsShort() {
        return this.tag.getAsShort();
    }

    @Override
    public byte getAsByte() {
        return this.tag.getAsByte();
    }

    @Override
    public double getAsDouble() {
        return this.tag.getAsDouble();
    }

    @Override
    public float getAsFloat() {
        return this.tag.getAsFloat();
    }

    @Override
    public @NotNull Number getAsNumber() {
        return this.tag.getAsNumber();
    }

    @Override
    public Tag getNms() {
        return this.tag;
    }

}
