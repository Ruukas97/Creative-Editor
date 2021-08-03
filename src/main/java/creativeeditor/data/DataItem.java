package creativeeditor.data;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import creativeeditor.data.tag.TagDamage;
import creativeeditor.data.tag.TagDisplayName;
import creativeeditor.data.tag.TagItemID;
import creativeeditor.data.tag.TagItemNBT;
import creativeeditor.data.version.NBTKeys;
import lombok.Getter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SkullItem;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IItemProvider;

public class DataItem implements Data<ItemStack, CompoundNBT> {
    private @Getter
    TagItemID item;
    private @Getter
    NumberRangeInt count, slot;
    private @Getter
    TagItemNBT tag;


    public DataItem(TagItemID item, NumberRangeInt count, TagItemNBT tag, NumberRangeInt slot) {
        this.item = item;
        this.count = count;
        this.slot = slot;
        this.tag = tag;
    }


    public DataItem() {
        this(ItemStack.EMPTY);
    }

    public DataItem(CompoundNBT nbt) {
        this(ItemStack.of(nbt));
    }

    public DataItem(IItemProvider item) {
        this(new ItemStack(item));
    }


    public DataItem(ItemStack stack) {
        this(stack, 0);
    }


    public DataItem(Item item, String nbtString) throws CommandSyntaxException {
        this(item, 1, JsonToNBT.parseTag(nbtString), 0);
    }


    public DataItem(ItemStack stack, int slot) {
        this(stack.getItem(), stack.getCount(), stack.getTag(), slot);
    }


    public DataItem(Item item, int count, CompoundNBT tag, int slot) {
        this.item = new TagItemID(item);
        this.count = new NumberRangeInt(count, 1, 64);
        this.slot = new NumberRangeInt(slot, 0, 45);
        this.tag = new TagItemNBT(this, tag);
    }


    public DataItem(Item item, int count, TileEntity te, int slot) {
        this(item, count, getTETag(item, te), slot);
    }


    private static CompoundNBT getTETag(Item item, TileEntity te) {
        CompoundNBT nbt = new CompoundNBT();
        CompoundNBT teTag = te.save(new CompoundNBT());

        if (item instanceof SkullItem && teTag.contains("Owner")) {
            CompoundNBT compoundnbt2 = teTag.getCompound("Owner");
            nbt.put("SkullOwner", compoundnbt2);
        } else {
            nbt.put("BlockEntityTag", teTag);
        }
        return nbt;
    }


    @Override
    public ItemStack getData() {
        return getItemStack();
    }


    public ItemStack getItemStack() {
        return ItemStack.of(getNBT());
    }


    /**
     * This reads the map into an ItemStack including all keys. So no default checks
     * are made. This should be used mainly when the itemstack is needed in an
     * isDefault check to avoid creating endless loops.
     *
     * @return An itemstack including all data, with no cleanup.
     */
    public ItemStack getItemStackFull() {
        return ItemStack.of(getNBT());
    }


    public TagDisplayName getDisplayNameTag() {
        return getTag().getDisplay().getName();
    }


    public void clearCustomName() {
        getDisplayNameTag().reset();
    }


    public TagDamage getDamageTag() {
        return getTag().getDamage();
    }

    public DataItem split(int count) {
        int i = Math.min(count, this.count.get());
        DataItem item = new DataItem(getNBT());
        item.count.set(i);
        this.count.set(this.count.get()-count);
        return item;
    }

    @Override
    public CompoundNBT getNBT() {
        NBTKeys keys = NBTKeys.keys;
        CompoundNBT nbt = new CompoundNBT();
        if (!getSlot().isDefault())
            nbt.put(keys.stackSlot(), getSlot().getNBT());
        nbt.put(keys.stackID(), getItem().getNBT());
        nbt.put(keys.stackCount(), getCount().getNBT());
        if (!getTag().isDefault())
            nbt.put(keys.stackTag(), getTag().getNBT());
        return nbt;
    }


    @Override
    public boolean isDefault() {
        return getItemStack().isEmpty();
    }
}
