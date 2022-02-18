package infinityitemeditor.data;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.mojang.blaze3d.matrix.MatrixStack;
import infinityitemeditor.data.base.DataMap;
import infinityitemeditor.render.NBTIcons;
import infinityitemeditor.screen.nbt.INBTNode;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import java.util.*;

public class DataUnserializedCompound implements Data<DataUnserializedCompound, CompoundNBT> {
    @Getter
    @Setter
    private Data<?, ?> parent;

    private final Map<String, Data<?, ?>> serialized = new HashMap<>();
    private final CompoundNBT unserializedNBT;

    public DataUnserializedCompound(CompoundNBT nbt) {
        if (nbt == null) {
            nbt = new CompoundNBT();
        }
        unserializedNBT = nbt.copy();
    }

    protected <T extends Data<?, ?>> T add(String key, T data) {
        serialized.put(key, data);
        unserializedNBT.remove(key);
        return data;
    }


    @Override
    public CompoundNBT getNBT() {
        CompoundNBT nbt = unserializedNBT.copy();
        for (String key : serialized.keySet()) {
            Data<?, ?> data = serialized.get(key);
            if (!data.isDefault())
                nbt.put(key, data.getNBT());
        }
        return nbt;
    }

    @Override
    public ITextComponent getPrettyDisplay(String space, int indentation) {
        if (serialized.isEmpty() || isDefault()) {
            return new StringTextComponent("{}");
        } else {
            IFormattableTextComponent iformattabletextcomponent = new StringTextComponent("{");
            List<String> collection = Lists.newArrayList(serialized.keySet());
            List<String> list = Lists.newArrayList();
            for (String s : collection) {
                Data<?, ?> d = serialized.get(s);
                if (!d.isDefault()) {
                    list.add(s);
                }
            }

            Collections.sort(list);
            collection = list;

            if (!space.isEmpty()) {
                iformattabletextcomponent.append("\n");
            }

            IFormattableTextComponent iformattabletextcomponent1;
            for (Iterator<String> iterator = collection.iterator(); iterator.hasNext(); iformattabletextcomponent.append(iformattabletextcomponent1)) {
                String s = iterator.next();
                iformattabletextcomponent1 = (new StringTextComponent(Strings.repeat(space, indentation + 1))).append(DataMap.handleEscapePretty(s)).append(String.valueOf(':')).append(" ").append(serialized.get(s).getPrettyDisplay(space, indentation + 1));
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

    @Override
    public boolean isDefault() {
        if (!unserializedNBT.isEmpty())
            return false;

        for (String key : serialized.keySet()) {
            Data<?, ?> data = serialized.get(key);
            if (!data.isDefault())
                return false;
        }
        return true;
    }


    @Override
    public DataUnserializedCompound getData() {
        return this;
    }

    @Override
    public void renderIcon(Minecraft mc, MatrixStack matrix, int x, int y) {
        NBTIcons.COMPOUND_TAG.renderIcon(mc, matrix, x, y);
    }

    @Override
    public Map<String, ? extends INBTNode> getSubNodes() {
        Map<String, Data<?, ?>> map = new HashMap<>();
        for (Map.Entry<String, Data<?, ?>> entry : serialized.entrySet()) {
            if (entry.getValue().isDefault()) {
                continue;
            }
            map.put(entry.getKey(), entry.getValue());
        }
        for (String key : unserializedNBT.getAllKeys()) {
            INBT nbt = unserializedNBT.get(key);
            if (nbt == null) {
                continue;
            }
            Data<?, ?> data = Data.getDataFromNBT(nbt);
            if (data == null || data.isDefault()) {
                continue;
            }
            map.put(key, data);

        }
        return map;
    }
}
