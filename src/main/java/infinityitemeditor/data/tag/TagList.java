package infinityitemeditor.data.tag;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import infinityitemeditor.data.Data;
import infinityitemeditor.data.base.SingularData;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import java.util.List;
import java.util.ListIterator;
import java.util.function.Function;

public class TagList<E extends Data<?, ?>> extends SingularData<List<E>, ListNBT> implements Iterable<E> {
    protected Function<INBT, E> addFunction;


    public TagList(Function<INBT, E> addFunction) {
        this(Lists.newArrayList());
        this.addFunction = addFunction;
    }


    public TagList(ListNBT nbt, Function<INBT, E> addFunction) {
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


    public void add(INBT nbt) {
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
    public ListNBT getNBT() {
        ListNBT nbt = new ListNBT();
        data.forEach(dat -> {
            if (!dat.isDefault()) {
                nbt.add(dat.getNBT());
            }
        });
        return nbt;
    }

    @Override
    public ITextComponent getPrettyDisplay(String space, int indentation) {
        if (data.isEmpty()) {
            return new StringTextComponent("[]");
        }/* else if (INLINE_ELEMENT_TYPES.contains(this.type) && this.size() <= 8) {
            String s1 = ", ";
            IFormattableTextComponent iformattabletextcomponent2 = new StringTextComponent("[");

            for(int j = 0; j < this.list.size(); ++j) {
                if (j != 0) {
                    iformattabletextcomponent2.append(", ");
                }

                iformattabletextcomponent2.append(this.list.get(j).getPrettyDisplay());
            }

            iformattabletextcomponent2.append("]");
            return iformattabletextcomponent2;
        } */ else {
            IFormattableTextComponent iformattabletextcomponent = new StringTextComponent("[");
            if (!space.isEmpty()) {
                iformattabletextcomponent.append("\n");
            }

            String s = String.valueOf(',');

            for (int i = 0; i < data.size(); ++i) {
                IFormattableTextComponent iformattabletextcomponent1 = new StringTextComponent(Strings.repeat(space, indentation + 1));
                iformattabletextcomponent1.append(data.get(i).getPrettyDisplay(space, indentation + 1));
                if (i != data.size() - 1) {
                    iformattabletextcomponent1.append(s).append(space.isEmpty() ? " " : "\n");
                }

                iformattabletextcomponent.append(iformattabletextcomponent1);
            }

            if (!space.isEmpty()) {
                iformattabletextcomponent.append("\n").append(Strings.repeat(space, indentation));
            }

            iformattabletextcomponent.append("]");
            return iformattabletextcomponent;
        }
    }


    @Override
    public ListIterator<E> iterator() {
        return data.listIterator();
    }
}
