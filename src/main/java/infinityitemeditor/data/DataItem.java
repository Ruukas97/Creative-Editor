package infinityitemeditor.data;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import infinityitemeditor.data.tag.TagDamage;
import infinityitemeditor.data.tag.TagDisplayName;
import infinityitemeditor.data.tag.TagItemID;
import infinityitemeditor.data.tag.TagItemNBT;
import infinityitemeditor.data.version.NBTKeys;
import lombok.Getter;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IItemProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SkullItem;

public class DataItem implements Data<ItemStack, CompoundTag> {
    @Getter
    private final TagItemID item;
    @Getter
    private final NumberRangeInt count;
    @Getter
    private final NumberRangeInt slot;
    @Getter
    private final TagItemNBT tag;


    public DataItem(TagItemID item, NumberRangeInt count, TagItemNBT tag, NumberRangeInt slot) {
        this.item = item;
        this.count = count;
        this.slot = slot;
        this.tag = tag;
    }


    public DataItem() {
        this(ItemStack.EMPTY);
    }

    public DataItem(CompoundTag nbt) {
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


    public DataItem(Item item, int count, CompoundTag tag, int slot) {
        this.item = new TagItemID(item);
        this.count = new NumberRangeInt(count, 1, 64);
        this.slot = new NumberRangeInt(slot, 0, 45);
        this.tag = new TagItemNBT(this, tag);
    }


    public DataItem(Item item, int count, TileEntity te, int slot) {
        this(item, count, getTETag(item, te), slot);
    }


    private static CompoundTag getTETag(Item item, TileEntity te) {
        CompoundTag nbt = new CompoundTag();
        CompoundTag teTag = te.save(new CompoundTag());

        if (item instanceof SkullItem && teTag.contains("Owner")) {
            CompoundTag compoundnbt2 = teTag.getCompound("Owner");
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
        return ItemStack.of(getTag());
    }


    /**
     * This reads the map into an ItemStack including all keys. So no default checks
     * are made. This should be used mainly when the itemstack is needed in an
     * isDefault check to avoid creating endless loops.
     *
     * @return An itemstack including all data, with no cleanup.
     */
    public ItemStack getItemStackFull() {
        return ItemStack.of(getTag());
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
        DataItem item = new DataItem(getTag());
        item.count.set(i);
        this.count.set(this.count.get() - count);
        return item;
    }

    @Override
    public CompoundTag getTag() {
        NBTKeys keys = NBTKeys.keys;
        CompoundTag nbt = new CompoundTag();
        if (!getSlot().isDefault())
            nbt.put(keys.stackSlot(), getSlot().getTag());
        nbt.put(keys.stackID(), getItem().getTag());
        nbt.put(keys.stackCount(), getCount().getTag());
        if (!getTag().isDefault())
            nbt.put(keys.stackTag(), getTag().getTag());
        return nbt;
    }

//    @Override
//    public MutableComponent getPrettyDisplay(String space, int indentation) {
//        IFormattableTextComponent iformattabletextcomponent = new TextComponent("{");
//
//        if (!space.isEmpty()) {
//            iformattabletextcomponent.append("\n");
//        }
//
//        IFormattableTextComponent iformattabletextcomponent1;
//        NBTKeys keys = NBTKeys.keys;
//        String count = keys.stackCount();
//        String slot = keys.stackSlot();
//        String id = keys.stackID();
//        String tag = keys.stackTag();
//
//        boolean showCount = !getCount().isDefault();
//        boolean showSlot = !getSlot().isDefault();
//        boolean showId = !getItem().isDefault();
//        boolean showTag = !getTag().isDefault();
//
//        if (showCount) {
//            iformattabletextcomponent1 = (new TextComponent(Strings.repeat(space, indentation + 1))).append(DataMap.handleEscapePretty(count)).append(String.valueOf(':')).append(" ").append(this.count.getPrettyDisplay(space, indentation + 1));
//            if (showSlot || showId || showTag)
//                iformattabletextcomponent1.append(String.valueOf(',')).append(space.isEmpty() ? " " : "\n");
//            iformattabletextcomponent.append(iformattabletextcomponent1);
//        }
//
//        if (showSlot) {
//            iformattabletextcomponent1 = (new TextComponent(Strings.repeat(space, indentation + 1))).append(DataMap.handleEscapePretty(slot)).append(String.valueOf(':')).append(" ").append(this.slot.getPrettyDisplay(space, indentation + 1));
//            if (showId || showTag)
//                iformattabletextcomponent1.append(String.valueOf(',')).append(space.isEmpty() ? " " : "\n");
//            iformattabletextcomponent.append(iformattabletextcomponent1);
//        }
//
//        if (showId) {
//            iformattabletextcomponent1 = (new TextComponent(Strings.repeat(space, indentation + 1))).append(DataMap.handleEscapePretty(id)).append(String.valueOf(':')).append(" ").append(this.item.getPrettyDisplay(space, indentation + 1));
//            if (showTag)
//                iformattabletextcomponent1.append(String.valueOf(',')).append(space.isEmpty() ? " " : "\n");
//            iformattabletextcomponent.append(iformattabletextcomponent1);
//        }
//
//        if (showTag) {
//            iformattabletextcomponent1 = (new TextComponent(Strings.repeat(space, indentation + 1))).append(DataMap.handleEscapePretty(tag)).append(String.valueOf(':')).append(" ").append(this.tag.getPrettyDisplay(space, indentation + 1));
//            iformattabletextcomponent.append(iformattabletextcomponent1);
//        }
//
//        if (!space.isEmpty()) {
//            iformattabletextcomponent.append("\n").append(Strings.repeat(space, indentation));
//        }
//
//        iformattabletextcomponent.append("}");
//        return iformattabletextcomponent;
//    }


    @Override
    public boolean isDefault() {
        return getItemStack().isEmpty();
    }

    public DataItem copy() {
        return new DataItem(this.getTag());
    }
}
