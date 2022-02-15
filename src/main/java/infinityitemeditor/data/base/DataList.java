package infinityitemeditor.data.base;

import com.google.common.collect.Lists;
import infinityitemeditor.data.Data;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;

import java.util.List;
import java.util.ListIterator;

public abstract class DataList<E extends Data<?, ?>> extends SingularData<List<E>, ListTag> implements Iterable<E> {
    public DataList() {
        this(Lists.newArrayList());
    }


    public DataList(ListTag nbt) {
        this();
        nbt.forEach(this::add);
    }


    public DataList(List<E> list) {
        super(list);
    }


    public void add(E value) {
        data.add(value);
    }


    public abstract <T extends Tag> void add(T nbt);

    public void remove(E value) {
        data.remove(value);
    }

    public void remove(int index) {
        data.remove(index);
    }

    public void clear() {
        data.clear();
    }


    @Override
    public boolean isDefault() {
        return data.isEmpty();
    }


    @Override
    public ListTag getTag() {
        ListTag nbt = new ListTag();
        data.forEach(dat -> {
            if (!dat.isDefault())
                nbt.add(dat.getTag());
        });
        return nbt;
    }

//    @Override
//    public MutableComponent getPrettyDisplay(String space, int indentation) {
//        if (data.isEmpty()) {
//            return new TextComponent("[]");
//        }/* else if (INLINE_ELEMENT_TYPES.contains(this.type) && this.size() <= 8) {
//            String s1 = ", ";
//            IFormattableTextComponent iformattabletextcomponent2 = new TextComponent("[");
//
//            for(int j = 0; j < this.list.size(); ++j) {
//                if (j != 0) {
//                    iformattabletextcomponent2.append(", ");
//                }
//
//                iformattabletextcomponent2.append(this.list.get(j).getPrettyDisplay());
//            }
//
//            iformattabletextcomponent2.append("]");
//            return iformattabletextcomponent2;
//        } */ else {
//            IFormattableTextComponent iformattabletextcomponent = new TextComponent("[");
//            if (!space.isEmpty()) {
//                iformattabletextcomponent.append("\n");
//            }
//
//            String s = String.valueOf(',');
//
//            for (int i = 0; i < data.size(); ++i) {
//                IFormattableTextComponent iformattabletextcomponent1 = new TextComponent(Strings.repeat(space, indentation + 1));
//                iformattabletextcomponent1.append(data.get(i).getPrettyDisplay(space, indentation + 1));
//                if (i != data.size() - 1) {
//                    iformattabletextcomponent1.append(s).append(space.isEmpty() ? " " : "\n");
//                }
//
//                iformattabletextcomponent.append(iformattabletextcomponent1);
//            }
//
//            if (!space.isEmpty()) {
//                iformattabletextcomponent.append("\n").append(Strings.repeat(space, indentation));
//            }
//
//            iformattabletextcomponent.append("]");
//            return iformattabletextcomponent;
//        }
//    }

    @Override
    public ListIterator<E> iterator() {
        return data.listIterator();
    }
}
