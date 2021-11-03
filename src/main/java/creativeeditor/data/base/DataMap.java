package creativeeditor.data.base;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import creativeeditor.data.Data;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Pattern;

public class DataMap extends SingularData<Map<String, Data<?, ?>>, CompoundNBT> {
    public static final Pattern SIMPLE_VALUE = Pattern.compile("[A-Za-z0-9._+-]+");

    public DataMap() {
        this(Maps.newHashMap());
    }


    public DataMap(Map<String, Data<?, ?>> map) {
        super(map);
    }


    public DataMap(CompoundNBT nbt) {
        this();
        if (nbt == null || nbt.isEmpty())
            return;
        for (String key : nbt.getAllKeys()) {
            INBT value = nbt.get(key);
            if (value != null)
                put(key, Data.getDataFromNBT(value));
        }
    }


    @Nullable
    public Data<?, ?> getData(String key) {
        return data.get(key);
    }


    @Nonnull
    public Data<?, ?> getDataDefaulted(String key, @Nonnull Data<?, ?> defaultValue) {
        if (!data.containsKey(key)) {
            Data<?, ?> dat = data.get(key);
            if (dat != null)
                return dat;
        }

        put(key, defaultValue);
        return defaultValue;
    }


    @SuppressWarnings("unchecked")
    @Nonnull
    public <T extends Data<?, ?>> T getDataDefaultedForced(String key, @Nonnull T defaultValue) {
        if (data.containsKey(key)) {
            Data<?, ?> existing = data.get(key);
            if (existing.getClass() == defaultValue.getClass()) {
                return (T) existing;
            }
        }

        put(key, defaultValue);
        return defaultValue;
    }


    public void put(String key, Data<?, ?> value) {
        if (value != null)
            data.put(key, value);
    }


    public void clear() {
        data.clear();
    }


    @Override
    public boolean isDefault() {
        if (data.isEmpty())
            return true;

        for (Entry<String, Data<?, ?>> entry : data.entrySet()) {
            if (!entry.getValue().isDefault())
                return false;
        }

        return true;
    }


    public CompoundNBT getNBTIncludeAll() {
        CompoundNBT nbt = new CompoundNBT();
        data.forEach((key, value) -> {
            if (value instanceof DataMap)
                nbt.put(key, ((DataMap) value).getNBTIncludeAll());
            else
                nbt.put(key, value.getNBT());
        });
        return nbt;
    }


    @Override
    public CompoundNBT getNBT() {
        CompoundNBT nbt = new CompoundNBT();
        data.forEach((key, value) -> {
            if (!value.isDefault())
                nbt.put(key, value.getNBT());
        });
        return nbt;
    }

    @Override
    public ITextComponent getPrettyDisplay(String space, int indentation) {
        if (data.isEmpty()) {
            return new StringTextComponent("{}");
        } else {
            IFormattableTextComponent iformattabletextcomponent = new StringTextComponent("{");
            List<String> collection = Lists.newArrayList(data.keySet());
            Collections.sort(collection);


            if (!space.isEmpty()) {
                iformattabletextcomponent.append("\n");
            }

            IFormattableTextComponent iformattabletextcomponent1;
            for (Iterator<String> iterator = collection.iterator(); iterator.hasNext(); iformattabletextcomponent.append(iformattabletextcomponent1)) {
                String s = iterator.next();
                iformattabletextcomponent1 = (new StringTextComponent(Strings.repeat(space, indentation + 1))).append(handleEscapePretty(s)).append(String.valueOf(':')).append(" ").append(data.get(s).getPrettyDisplay(space, indentation + 1));
                if (iterator.hasNext()) {
                    iformattabletextcomponent1.append(String.valueOf(',')).append(space.isEmpty() ? " " : "\n");
                }
            }

            if (!space.isEmpty()) {
                iformattabletextcomponent.append("\n").append(Strings.repeat(space, indentation));
            }

            iformattabletextcomponent.append("}");
            return iformattabletextcomponent;
        }
    }

    public static ITextComponent handleEscapePretty(String string) {
        if (SIMPLE_VALUE.matcher(string).matches()) {
            return (new StringTextComponent(string)).withStyle(SYNTAX_HIGHLIGHTING_KEY);
        } else {
            String s = StringNBT.quoteAndEscape(string);
            String s1 = s.substring(0, 1);
            ITextComponent itextcomponent = (new StringTextComponent(s.substring(1, s.length() - 1))).withStyle(SYNTAX_HIGHLIGHTING_KEY);
            return (new StringTextComponent(s1)).append(itextcomponent).append(s1);
        }
    }
}
