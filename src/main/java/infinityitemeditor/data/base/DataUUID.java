package infinityitemeditor.data.base;

import infinityitemeditor.data.Data;
import io.netty.util.internal.ThreadLocalRandom;
import lombok.Getter;
import net.minecraft.item.Item;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.IntArrayNBT;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import java.util.UUID;

public class DataUUID implements Data<UUID, IntArrayNBT> {
    private static Item item = new Item(new Item.Properties());
    public static final UUID EMPTY = UUID.fromString("00000000-0000-0000-0000-000000000000");
    public static final UUID BASE_ATTACK_DAMAGE_UUID = UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF");
    public static final UUID BASE_ATTACK_SPEED_UUID = UUID.fromString("FA233E1C-4180-4865-B01B-BCCE9785ACA3");
    public static final UUID[] ARMOR_MODIFIER_UUID_PER_SLOT = new UUID[]{UUID.fromString("845DB27C-C624-495F-8C9F-6020A9A58B6B"), UUID.fromString("D8499B04-0E66-4726-AB29-64469D734E0D"), UUID.fromString("9F3D476D-C118-4544-8365-64846904B48E"), UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150")};


    @Getter
    private final DataLong mostSignificantBits;
    @Getter
    private final DataLong leastSignificantBits;

    public DataUUID() {
        this(MathHelper.createInsecureUUID(ThreadLocalRandom.current()));
    }

    public DataUUID(UUID uuid) {
        this.mostSignificantBits = new DataLong(uuid.getMostSignificantBits());
        this.leastSignificantBits = new DataLong(uuid.getLeastSignificantBits());
    }

    public DataUUID(INBT nbt) {
        this(loadUUID(nbt));
    }

    public DataUUID(String uuid) {
        this(loadUUID(uuid));
    }

    public static UUID loadUUID(INBT nbt) {
        try {
            return NBTUtil.loadUUID(nbt);
        } catch (IllegalArgumentException e) {
            return MathHelper.createInsecureUUID(ThreadLocalRandom.current());
        }
    }

    public static UUID loadUUID(String uuid) {
        try {
            return UUID.fromString(uuid);
        } catch (IllegalArgumentException e) {
            return MathHelper.createInsecureUUID(ThreadLocalRandom.current());
        }
    }

    public void setFromUUID(UUID uuid) {
        this.mostSignificantBits.set(uuid.getMostSignificantBits());
        this.leastSignificantBits.set(uuid.getLeastSignificantBits());
    }

    @Override
    public UUID getData() {
        return new UUID(mostSignificantBits.get(), leastSignificantBits.get());
    }

    @Override
    public boolean isDefault() {
        return mostSignificantBits.get() == 0L && leastSignificantBits.get() == 0L;
    }

    @Override
    public IntArrayNBT getNBT() {
        return NBTUtil.createUUID(getData());
    }

    @Override
    public ITextComponent getPrettyDisplay(String space, int indentation) {
        return (new StringTextComponent(getData().toString())).withStyle(SYNTAX_HIGHLIGHTING_NUMBER);
    }

    public void randomize() {
        setFromUUID(MathHelper.createInsecureUUID(ThreadLocalRandom.current()));
    }
}
