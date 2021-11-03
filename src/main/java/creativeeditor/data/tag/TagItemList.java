package creativeeditor.data.tag;

import creativeeditor.data.DataItem;
import creativeeditor.data.base.SingularData;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class TagItemList extends SingularData<DataItem[], ListNBT>{
    public TagItemList(int size) {
        super(new DataItem[size]);
    }

    public TagItemList(ListNBT items, int size) {
        this(size);
        int i = 0;
        for(INBT nbt : items){
            if(nbt instanceof CompoundNBT){
                data[i] = new DataItem((CompoundNBT) nbt);
            }
            i++;
        }
    }

    public TagItemList(ListNBT items) {
        this(items, items.size());
    }

    @Override
    public boolean isDefault() {
        for (DataItem item : data){
            if(item != null && !item.isDefault()){
                return false;
            }
        }
        return true;
    }

    @Override
    public ListNBT getNBT() {
        ListNBT nbt = new ListNBT();
        int slot = 0;
        for (DataItem item : data) {
            if (item == null) {
                item = new DataItem();
            }
            item.getSlot().set(slot++);
            nbt.add(item.getNBT());
        }
        return nbt;
    }

    @Override
    public ITextComponent getPrettyDisplay(String space, int indentation) {
        ITextComponent itextcomponent = (new StringTextComponent("B")).withStyle(SYNTAX_HIGHLIGHTING_NUMBER_TYPE);
        IFormattableTextComponent iformattabletextcomponent = (new StringTextComponent("[")).append(itextcomponent).append(";");

        for(int i = 0; i < this.data.length; ++i) {
            IFormattableTextComponent iformattabletextcomponent1 = (new StringTextComponent(data[i].getItem().getIDExcludingMC())).withStyle(SYNTAX_HIGHLIGHTING_NUMBER);
            iformattabletextcomponent.append(" ").append(iformattabletextcomponent1).append(itextcomponent);
            if (i != this.data.length - 1) {
                iformattabletextcomponent.append(",");
            }
        }

        iformattabletextcomponent.append("]");
        return iformattabletextcomponent;
    }

    public ListNBT getNBTEmptyDefaults() {
        ListNBT nbt = new ListNBT();
        int slot = 0;
        for (DataItem item : data) {
            if (item == null || item.getCount().get() == 0 || item.getItem().getItem() == Items.AIR) {
                nbt.add(new CompoundNBT());
            } else {
                item.getSlot().set(slot);
                nbt.add(item.getNBT());
            }
            slot++;
        }
        return nbt;
    }
}
