package infinityitemeditor.saving;

import infinityitemeditor.data.DataItem;
import infinityitemeditor.data.base.DataString;
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
import java.util.List;

public class DataItemCollection extends DataSaveable {
    @Getter
    private final TagItemList items;

    public DataItemCollection(File file, CompoundNBT nbt) {
        super(file, nbt);
        items = new TagItemList(nbt.getList("Items", Constants.NBT.TAG_COMPOUND), 27);
    }

    public static DataItemCollection fromBlockEntityTag(File file, TagBlockEntity tag, DataString name) {
        CompoundNBT nbt = tag.getNBT();
        nbt.put("Name", name.getNBT());
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
            list.set(i, DataSaveable.updateNBT(TypeReferences.ITEM_STACK, (CompoundNBT) child, version, newVersion));
        }
        return nbt;
    }

    public DataItem toShulker() {
        CompoundNBT nbt = super.getNBT();
        TagBlockEntity blockEntityTag = new TagBlockEntity();
        blockEntityTag.getItems().get().addAll(items.get());
        nbt.put("BlockEntityTag", blockEntityTag.getNBT());
        DataItem shulker = new DataItem(Blocks.PURPLE_SHULKER_BOX.asItem(), 1, nbt, 0);
        shulker.getDisplayNameTag().set(name.get());
        return shulker;
    }

    public DataItem[] toShulkers() {
        CompoundNBT nbt = super.getNBT();
        TagBlockEntity blockEntityTag = new TagBlockEntity();

        int size = items.get().size();
        int count = (int) Math.ceil(size / 27f);

        List<DataItem> list = blockEntityTag.getItems().get();

        DataItem[] shulkers = new DataItem[count];
        int added = 0;
        for (int i = 0; i < count; i++) {
            list.clear();
            for (int j = 27 * i; j < 27 * (i + 1) && added < size; j++, added++) {
                list.add(items.get().get(j));
            }
            nbt.put("BlockEntityTag", blockEntityTag.getNBT());
            DataItem shulker = new DataItem(Blocks.PURPLE_SHULKER_BOX.asItem(), 1, nbt, 0);
            shulker.getDisplayNameTag().set(name.get() + " " + (i+1) + "/" + count);
            shulkers[i] = shulker;
        }
        return shulkers;
    }
}
