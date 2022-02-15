package infinityitemeditor.data.tag;

import com.google.common.collect.Lists;
import infinityitemeditor.data.Data;
import infinityitemeditor.data.base.SingularData;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;

import java.util.List;
import java.util.ListIterator;
import java.util.function.Function;

public class TagList<E extends Data<?, ?>> extends SingularData<List<E>, ListTag> implements Iterable<E> {
    protected Function<Tag, E> addFunction;


    public TagList(Function<Tag, E> addFunction) {
        this(Lists.newArrayList());
        this.addFunction = addFunction;
    }


    public TagList(ListTag nbt, Function<Tag, E> addFunction) {
        this(addFunction);
        nbt.forEach(this::add);
    }


    public TagList(List<E> list) {
        super(list);
    }


    public void add(E value) {
        if (value != null)
            data.add(value);
    }


    public void add(Tag nbt) {
        add(addFunction.apply(nbt));
    }

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
            if (!dat.isDefault()) {
                nbt.add(dat.getTag());
            }
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
