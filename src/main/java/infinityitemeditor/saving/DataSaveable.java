package infinityitemeditor.saving;

import com.mojang.datafixers.DSL;
import com.mojang.datafixers.DataFixer;
import com.mojang.serialization.Dynamic;
import infinityitemeditor.data.Data;
import infinityitemeditor.data.base.DataInteger;
import infinityitemeditor.data.base.DataLong;
import infinityitemeditor.data.base.DataString;
import infinityitemeditor.data.base.DataUUID;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.IntNBT;
import net.minecraft.nbt.NBTDynamicOps;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.util.Constants;

import java.io.File;
import java.io.IOException;

public class DataSaveable implements Data<DataSaveable, CompoundNBT> {
    public static final int FORMAT_VERSION = 1;

    @Getter
    @Setter
    protected File file;
    @Getter
    protected final DataString name;
    @Getter
    protected final DataLong createdAt;
    @Getter
    protected final DataLong modifiedAt;
    @Getter
    protected final DataUUID createdBy;
    @Getter
    protected final DataInteger formatVersion;
    @Getter
    protected final DataInteger dataVersion;

    public DataSaveable(File file) throws IOException {
        this.file = file;
        name = new DataString();
        createdAt = new DataLong();
        modifiedAt = new DataLong();
        createdBy = new DataUUID();
        formatVersion = new DataInteger();
        dataVersion = new DataInteger();
        load();
    }

    public DataSaveable(File file, CompoundNBT nbt) {
        this.file = file;
        nbt = update(nbt);
        name = new DataString(nbt.getString("Name"));
        long time = System.currentTimeMillis();
        createdAt = new DataLong(nbt.contains("CreatedAt", Constants.NBT.TAG_LONG) ? nbt.getLong("CreatedAt") : time);
        modifiedAt = new DataLong(nbt.contains("ModifiedAt", Constants.NBT.TAG_LONG) ? nbt.getLong("ModifiedAt") : time);
        if (nbt.contains("CreatedBy", Constants.NBT.TAG_INT_ARRAY) && !DataUUID.EMPTY.toString().equals(new DataUUID(nbt.get("CreatedBy")).getData().toString())) {
            createdBy = new DataUUID(nbt.get("CreatedBy"));
        } else if (Minecraft.getInstance().player != null) {
            createdBy = new DataUUID(Minecraft.getInstance().player.getUUID());
        } else {
            createdBy = new DataUUID(DataUUID.EMPTY);
        }
        formatVersion = new DataInteger(nbt.contains("FormatVersion", Constants.NBT.TAG_ANY_NUMERIC) ? nbt.getInt("FormatVersion") : FORMAT_VERSION);
        dataVersion = new DataInteger(nbt.getInt("DataVersion"));
    }

    @Override
    public DataSaveable getData() {
        return this;
    }

    @Override
    public boolean isDefault() {
        return false;
    }

    @Override
    public CompoundNBT getNBT() {
        modifiedAt.set(System.currentTimeMillis());
        CompoundNBT nbt = new CompoundNBT();
        nbt.put("Name", name.getNBT());
        nbt.put("CreatedAt", createdAt.getNBT());
        nbt.put("ModifiedAt", modifiedAt.getNBT());
        nbt.put("CreatedAt", createdAt.getNBT());
        nbt.put("CreatedBy", createdBy.getNBT());
        nbt.put("FormatVersion", IntNBT.valueOf(FORMAT_VERSION));
        nbt.put("DataVersion", IntNBT.valueOf(SharedConstants.getCurrentVersion().getWorldVersion()));
        return nbt;
    }

    @Override
    public ITextComponent getPrettyDisplay(String space, int indentation) {
        return Data.super.getPrettyDisplay(space, indentation);
    }

    public void save() throws IOException {
        file.getParentFile().mkdirs();
        CompressedStreamTools.write(getNBT(), file);
    }

    public void load() throws IOException {
        CompoundNBT nbt = CompressedStreamTools.read(file);
        if (nbt == null) {
            nbt = new CompoundNBT();
        }
        loadFromNBT(nbt);
    }

    public void loadFromNBT(CompoundNBT nbt) {
        if (nbt == null) {
            nbt = new CompoundNBT();
        }
        nbt = update(nbt);
        long time = System.currentTimeMillis();
        name.set(nbt.getString("Name"));
        createdAt.set(nbt.contains("CreatedAt", Constants.NBT.TAG_LONG) ? nbt.getLong("CreatedAt") : time);
        modifiedAt.set(nbt.contains("ModifiedAt", Constants.NBT.TAG_LONG) ? nbt.getLong("ModifiedAt") : time);
        if (nbt.contains("CreatedBy", Constants.NBT.TAG_INT_ARRAY) && !DataUUID.EMPTY.toString().equals(new DataUUID(nbt.get("CreatedBy")).getData().toString())) {
            createdBy.setFromUUID(DataUUID.loadUUID(nbt.get("CreatedBy")));
        } else if (Minecraft.getInstance().player != null) {
            createdBy.setFromUUID(Minecraft.getInstance().player.getUUID());
        } else {
            createdBy.setFromUUID(DataUUID.EMPTY);
        }
        formatVersion.set(nbt.contains("FormatVersion", Constants.NBT.TAG_ANY_NUMERIC) ? nbt.getInt("FormatVersion") : FORMAT_VERSION);
        dataVersion.set(nbt.getInt("DataVersion"));
    }

    public CompoundNBT update(CompoundNBT nbt) {
        if (!nbt.contains("DataVersion", 99)) {
            nbt.putInt("DataVersion", 1343);
        }
        int version = nbt.getInt("DataVersion");
        int currentVersion = SharedConstants.getCurrentVersion().getWorldVersion();
        if (version < currentVersion) {
            nbt = update(nbt, version, currentVersion);
        }
        return nbt;
    }

    public CompoundNBT update(CompoundNBT nbt, int version, int newVersion) {
        return nbt;
    }

    public static CompoundNBT updateNBT(DSL.TypeReference type, CompoundNBT nbt, int version, int newVersion) {
        DataFixer fixer = Minecraft.getInstance().getFixerUpper();
        return (CompoundNBT) fixer.update(type, new Dynamic<>(NBTDynamicOps.INSTANCE, nbt), version, newVersion).getValue();
    }
}
