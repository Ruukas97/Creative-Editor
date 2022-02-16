package infinityitemeditor.data.tag;

import infinityitemeditor.data.DataItem;
import infinityitemeditor.data.base.SingularData;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import java.util.ArrayList;
import java.util.List;

public class TagItemList extends SingularData<List<DataItem>, ListNBT> {
    public TagItemList(int size) {
        super(new ArrayList<>(size));
    }

    public TagItemList(ListNBT items, int size) {
        this(size);
        for (INBT nbt : items) {
            if (nbt instanceof CompoundNBT) {
                data.add(new DataItem((CompoundNBT) nbt));
            }
        }
    }

    public TagItemList(ListNBT items) {
        this(items, items.size());
    }

    @Override
    public boolean isDefault() {
        for (DataItem item : data) {
            if (item != null && !item.isDefault()) {
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
        IFormattableTextComponent iformattabletextcomponent = (new StringTextComponent("["));

        for (int i = 0; i < this.data.size(); i++) {
            IFormattableTextComponent iformattabletextcomponent1 = (new StringTextComponent(data.get(i).getItem().getIDExcludingMC())).withStyle(SYNTAX_HIGHLIGHTING_NUMBER);
            iformattabletextcomponent.append(" ").append(iformattabletextcomponent1);
            if (i != this.data.size() - 1) {
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
