package creativeeditor.data.tag;

import creativeeditor.data.DataItem;
import creativeeditor.data.base.SingularData;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;

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
        for(DataItem item : data){
            if(item == null){
                item = new DataItem();
            }
            item.getSlot().set(slot++);
            nbt.add(item.getNBT());
        }
        return nbt;
    }
}
