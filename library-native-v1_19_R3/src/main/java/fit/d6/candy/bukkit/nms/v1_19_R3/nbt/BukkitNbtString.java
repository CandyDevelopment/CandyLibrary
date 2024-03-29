package fit.d6.candy.bukkit.nms.v1_19_R3.nbt;

import fit.d6.candy.api.nbt.NbtString;
import fit.d6.candy.api.nbt.NbtType;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import org.jetbrains.annotations.NotNull;

import java.io.DataOutput;
import java.io.IOException;

public class BukkitNbtString extends BukkitNbtBase implements NbtString {

    private final StringTag tag;

    public BukkitNbtString(StringTag nms) {
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
    public @NotNull NbtType getType() {
        return NbtType.STRING;
    }

    @Override
    public @NotNull String getAsString() {
        return this.tag.getAsString();
    }

    @Override
    public int length() {
        return this.getAsString().length();
    }

    @Override
    public @NotNull NbtString copy() {
        return new BukkitNbtString(this.tag.copy());
    }

    @Override
    public Tag getNms() {
        return this.tag;
    }

}
