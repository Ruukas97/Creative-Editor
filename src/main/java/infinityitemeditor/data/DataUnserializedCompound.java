package infinityitemeditor.data;

import net.minecraft.nbt.CompoundTag;

import java.util.HashMap;
import java.util.Map;

public class DataUnserializedCompound implements Data<DataUnserializedCompound, CompoundTag> {
    private final Map<String, Data<?, ?>> serialized = new HashMap<>();
    private final CompoundTag unserializedNBT;

    public DataUnserializedCompound(CompoundTag nbt) {
        if (nbt == null) {
            nbt = new CompoundTag();
        }
        unserializedNBT = nbt.copy();
    }

    protected <T extends Data<?, ?>> T add(String key, T data) {
        serialized.put(key, data);
        unserializedNBT.remove(key);
        return data;
    }


    @Override
    public CompoundTag getTag() {
        CompoundTag nbt = unserializedNBT.copy();
        for (String key : serialized.keySet()) {
            Data<?, ?> data = serialized.get(key);
            if (!data.isDefault())
                nbt.put(key, data.getTag());
        }
        return nbt;
    }

//    @Override
//    public MutableComponent getPrettyDisplay(String space, int indentation) {
//        if (serialized.isEmpty() || isDefault()) {
//            return new TextComponent("{}");
//        } else {
//            IFormattableTextComponent iformattabletextcomponent = new TextComponent("{");
//            List<String> collection = Lists.newArrayList(serialized.keySet());
//            List<String> list = Lists.newArrayList();
//            for (String s : collection) {
//                Data<?, ?> d = serialized.get(s);
//                if (!d.isDefault()) {
//                    list.add(s);
//                }
//            }
//
//            Collections.sort(list);
//            collection = list;
//
//            if (!space.isEmpty()) {
//                iformattabletextcomponent.append("\n");
//            }
//
//            IFormattableTextComponent iformattabletextcomponent1;
//            for (Iterator<String> iterator = collection.iterator(); iterator.hasNext(); iformattabletextcomponent.append(iformattabletextcomponent1)) {
//                String s = iterator.next();
//                iformattabletextcomponent1 = (new TextComponent(Strings.repeat(space, indentation + 1))).append(DataMap.handleEscapePretty(s)).append(String.valueOf(':')).append(" ").append(serialized.get(s).getPrettyDisplay(space, indentation + 1));
//                if (iterator.hasNext()) {
//                    iformattabletextcomponent1.append(String.valueOf(',')).append(space.isEmpty() ? " " : "\n");
//                }
//            }
//
//            if (!space.isEmpty()) {
//                iformattabletextcomponent.append("\n").append(Strings.repeat(space, indentation));
//            }
//
//            iformattabletextcomponent.append("}");
//            return iformattabletextcomponent;
//        }
//    }

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
}
