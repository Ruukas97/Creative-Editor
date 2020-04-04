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
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.JsonToNBT;

public class DataItem implements Data<ItemStack, CompoundNBT> {
    private @Getter TagItemID item;
    private @Getter NumberRangeInt count, slot;
    private @Getter TagItemNBT tag;


    public DataItem(TagItemID item, NumberRangeInt count, TagItemNBT tag, NumberRangeInt slot) {
        this.item = item;
        this.count = count;
        this.slot = slot;
        this.tag = tag;
    }


    public DataItem() {
        this( ItemStack.EMPTY, 0 );
    }


    public DataItem(ItemStack stack) {
        this( stack, 0 );
    }


    public DataItem(Item item, String nbtString) throws CommandSyntaxException {
        this( item, 1, JsonToNBT.getTagFromJson( nbtString ), 0 );
    }


    public DataItem(ItemStack stack, int slot) {
        this( stack.getItem(), stack.getCount(), stack.getTag(), slot );
    }


    public DataItem(Item item, int count, CompoundNBT tag, int slot) {
        this.item = new TagItemID( item );
        this.count = new NumberRangeInt( count, 1, 64 );
        this.slot = new NumberRangeInt( slot, 0, 45 );
        this.tag = new TagItemNBT( this, tag );
    }


    @Override
    public ItemStack getData() {
        return getItemStack();
    }


    /**
     * This reads the map into an ItemStack including all keys. So no default checks
     * are made. This should be used mainly when the itemstack is needed in an
     * isDefault check to avoid creating endless loops.
     * 
     * @return An itemstack including all data, with no cleanup.
     */
    public ItemStack getItemStack() {
        return ItemStack.read( getNBT() );
    }


    public ItemStack getItemStackFull() {
        return ItemStack.read( getNBT() );
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


    @Override
    public CompoundNBT getNBT() {
        NBTKeys keys = NBTKeys.keys;
        CompoundNBT nbt = new CompoundNBT();
        if (!getSlot().isDefault())
            nbt.put( keys.stackSlot(), getSlot().getNBT() );
        nbt.put( keys.stackID(), getItem().getNBT() );
        nbt.put( keys.stackCount(), getCount().getNBT() );
        if (!getTag().isDefault())
            nbt.put( keys.stackTag(), getTag().getNBT() );
        return nbt;
    }


    @Override
    public boolean isDefault() {
        return getItemStack().isEmpty();
    }
}
