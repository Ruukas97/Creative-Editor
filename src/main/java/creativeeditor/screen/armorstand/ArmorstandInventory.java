package creativeeditor.screen.armorstand;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.util.Constants;

import java.util.List;

public class ArmorstandInventory implements IInventory {

    public final NonNullList<ItemStack> armor = NonNullList.withSize( 4, ItemStack.EMPTY );
    public final NonNullList<ItemStack> hands = NonNullList.withSize( 2, ItemStack.EMPTY );
    private final List<NonNullList<ItemStack>> compartments = ImmutableList.of(this.armor, this.hands);
    private ItemStack armorStandStack;
    public ArmorStandEntity entityStand;


    public ArmorstandInventory(ItemStack stack) {
        this.armorStandStack = stack;
        entityStand = new ArmorStandEntity(EntityType.ARMOR_STAND, Minecraft.getInstance().level);
        applyItemDataToMob();
    }

    public void applyItemDataToMob()
    {
        CompoundNBT tag = armorStandStack.getTag();

        if ( tag != null && tag.contains( "EntityTag", Constants.NBT.TAG_COMPOUND ) )
        {
            entityStand.readAdditionalSaveData( tag.getCompound( "EntityTag" ) );
        }
    }

    @Override
    public int getContainerSize() {
        return armor.size() + hands.size();
    }

    @Override
    public boolean isEmpty() {
        for(ItemStack itemstack : this.hands) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }

        for(ItemStack itemstack1 : this.armor) {
            if (!itemstack1.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack getItem(int p_70301_1_) {
        List<ItemStack> list = null;

        for(NonNullList<ItemStack> nonnulllist : this.compartments) {
            if (p_70301_1_ < nonnulllist.size()) {
                list = nonnulllist;
                break;
            }

            p_70301_1_ -= nonnulllist.size();
        }

        return list == null ? ItemStack.EMPTY : list.get(p_70301_1_);
    }

    @Override
    public ItemStack removeItem(int p_70298_1_, int p_70298_2_) {
        List<ItemStack> list = null;

        for(NonNullList<ItemStack> nonnulllist : this.compartments) {
            if (p_70298_1_ < nonnulllist.size()) {
                list = nonnulllist;
                break;
            }

            p_70298_1_ -= nonnulllist.size();
        }

        return list != null && !list.get(p_70298_1_).isEmpty() ? ItemStackHelper.removeItem(list, p_70298_1_, p_70298_2_) : ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeItemNoUpdate(int p_70304_1_) {
        NonNullList<ItemStack> nonnulllist = null;

        for(NonNullList<ItemStack> nonnulllist1 : this.compartments) {
            if (p_70304_1_ < nonnulllist1.size()) {
                nonnulllist = nonnulllist1;
                break;
            }

            p_70304_1_ -= nonnulllist1.size();
        }

        if (nonnulllist != null && !nonnulllist.get(p_70304_1_).isEmpty()) {
            ItemStack itemstack = nonnulllist.get(p_70304_1_);
            nonnulllist.set(p_70304_1_, ItemStack.EMPTY);
            return itemstack;
        } else {
            return ItemStack.EMPTY;
        }
    }

    @Override
    public void setItem(int p_70299_1_, ItemStack p_70299_2_) {
        NonNullList<ItemStack> nonnulllist = null;

        for(NonNullList<ItemStack> nonnulllist1 : this.compartments) {
            if (p_70299_1_ < nonnulllist1.size()) {
                nonnulllist = nonnulllist1;
                break;
            }

            p_70299_1_ -= nonnulllist1.size();
        }

        if (nonnulllist != null) {
            nonnulllist.set(p_70299_1_, p_70299_2_);
        }
    }

    @Override
    public void setChanged() {

    }

    @Override
    public boolean stillValid(PlayerEntity p_70300_1_) {
        return false;
    }

    @Override
    public void clearContent() {
        for(List<ItemStack> list : this.compartments) {
            list.clear();
        }
    }
}
