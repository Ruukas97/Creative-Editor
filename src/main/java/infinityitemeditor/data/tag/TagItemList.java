package infinityitemeditor.data.tag;

import infinityitemeditor.data.DataItem;
import infinityitemeditor.data.base.SingularData;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.Items;

public class TagItemList extends SingularData<DataItem[], ListTag>{
    public TagItemList(int size) {
        super(new DataItem[size]);
    }

    public TagItemList(ListTag items, int size) {
        this(size);
        int i = 0;
        for(Tag nbt : items){
            if(nbt instanceof CompoundTag){
                data[i] = new DataItem((CompoundTag) nbt);
            }
            i++;
        }
    }

    public TagItemList(ListTag items) {
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
    public ListTag getTag() {
        ListTag nbt = new ListTag();
        int slot = 0;
        for (DataItem item : data) {
            if (item == null) {
                item = new DataItem();
            }
            item.getSlot().set(slot++);
            nbt.add(item.getTag());
        }
        return nbt;
    }

//    @Override
//    public MutableComponent getPrettyDisplay(String space, int indentation) {
//        IFormattableTextComponent iformattabletextcomponent = (new TextComponent("["));
//
//        for(int i = 0; i < this.data.length; i++) {
//            IFormattableTextComponent iformattabletextcomponent1 = (new TextComponent(data[i].getItem().getIDExcludingMC())).withStyle(SYNTAX_HIGHLIGHTING_NUMBER);
//            iformattabletextcomponent.append(" ").append(iformattabletextcomponent1);
//            if (i != this.data.length - 1) {
//                iformattabletextcomponent.append(",");
//            }
//        }
//
//        iformattabletextcomponent.append("]");
//        return iformattabletextcomponent;
//    }

    public ListTag getNBTEmptyDefaults() {
        ListTag nbt = new ListTag();
        int slot = 0;
        for (DataItem item : data) {
            if (item == null || item.getCount().get() == 0 || item.getItem().getItem() == Items.AIR) {
                nbt.add(new CompoundTag());
            } else {
                item.getSlot().set(slot);
                nbt.add(item.getTag());
            }
            slot++;
        }
        return nbt;
    }
}
