package infinityitemeditor.saving;

import infinityitemeditor.data.DataItem;
import infinityitemeditor.data.tag.TagItemList;
import infinityitemeditor.data.tag.block.TagBlockEntity;
import lombok.Getter;
import net.minecraft.block.Blocks;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.datafix.TypeReferences;
import net.minecraftforge.common.util.Constants;

import java.io.File;

public class DataItemCollection extends DataSaveable {
    @Getter
    private final TagItemList items;

    public DataItemCollection(File file, CompoundNBT nbt) {
        super(file, nbt);
        items = new TagItemList(nbt.getList("Items", Constants.NBT.TAG_COMPOUND), 27);
    }

    public static DataItemCollection fromBlockEntityTag(File file, TagBlockEntity tag) {
        CompoundNBT nbt = tag.getNBT();
        if (nbt.contains("CustomName", Constants.NBT.TAG_STRING)) {
            nbt.put("Name", nbt.get("CustomName"));
            nbt.remove("CustomName");
        }
        nbt.putInt("DataVersion", SharedConstants.getCurrentVersion().getWorldVersion());
        return new DataItemCollection(file, nbt);
    }

    @Override
    public CompoundNBT getNBT() {
        CompoundNBT nbt = super.getNBT();
        nbt.put("Items", items.getNBT());
        return nbt;
    }

    @Override
    public CompoundNBT update(CompoundNBT nbt, int version, int newVersion) {
        nbt = super.update(nbt, version, newVersion);
        ListNBT list = nbt.getList("Items", Constants.NBT.TAG_COMPOUND);
        for (int i = 0; i < list.size(); i++) {
            INBT child = list.get(i);
            if (!(child instanceof CompoundNBT)) {
                continue;
            }
            list.set(i, DataSaveable.updateNBT(TypeReferences.ITEM_STACK, nbt, version, newVersion));
        }
        return nbt;
    }

    public DataItem toShulker() {
        CompoundNBT nbt = super.getNBT();
        TagBlockEntity blockEntityTag = new TagBlockEntity();
        blockEntityTag.getCustomName().set(name.get());
        blockEntityTag.getItems().get().addAll(items.get());
        nbt.put("BlockEntityTag", blockEntityTag.getNBT());
        DataItem shulker = new DataItem(Blocks.PURPLE_SHULKER_BOX.asItem(), 1, nbt, 0);
        shulker.getDisplayNameTag().set(name.get());
        return shulker;
    }
}
