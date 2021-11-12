package infinityitemeditor.data.tag;

import com.google.common.base.Strings;
import infinityitemeditor.data.base.DataByte;
import infinityitemeditor.data.base.DataMap;
import infinityitemeditor.data.base.SingularData;
import infinityitemeditor.data.version.NBTKeys;
import lombok.Getter;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.util.Constants.NBT;

public class TagFirework extends SingularData<TagList<TagExplosion>, CompoundNBT> {
    @Getter
    private final DataByte flight;


    public TagFirework(CompoundNBT nbt) {
        this(new TagList<>(nbt.getList(NBTKeys.keys.fireworksExplosions(), NBT.TAG_COMPOUND), TagExplosion::new), nbt.getByte(NBTKeys.keys.fireworksFlight()));
    }


    public TagFirework(TagList<TagExplosion> list, byte flight) {
        super(list);
        this.flight = new DataByte(flight);
    }


    @Override
    public boolean isDefault() {
        return data.isDefault();
    }


    @Override
    public CompoundNBT getNBT() {
        NBTKeys keys = NBTKeys.keys;
        CompoundNBT nbt = new CompoundNBT();
        nbt.put(keys.fireworksFlight(), flight.getNBT());
        nbt.put(keys.fireworksExplosions(), data.getNBT());
        return nbt;
    }

    @Override
    public ITextComponent getPrettyDisplay(String space, int indentation) {
        if (isDefault()) {
            return new StringTextComponent("{}");
        } else {
            IFormattableTextComponent iformattabletextcomponent = new StringTextComponent("{");

            if (!space.isEmpty()) {
                iformattabletextcomponent.append("\n");
            }

            IFormattableTextComponent iformattabletextcomponent1;
            NBTKeys keys = NBTKeys.keys;
            String explosions = keys.fireworksExplosions();
            String flight = keys.fireworksFlight();

            iformattabletextcomponent1 = (new StringTextComponent(Strings.repeat(space, indentation + 1))).append(DataMap.handleEscapePretty(explosions)).append(String.valueOf(':')).append(" ").append(this.data.getPrettyDisplay(space, indentation + 1));
            iformattabletextcomponent1.append(String.valueOf(',')).append(space.isEmpty() ? " " : "\n");
            iformattabletextcomponent1.append(Strings.repeat(space, indentation + 1)).append(DataMap.handleEscapePretty(flight)).append(String.valueOf(':')).append(" ").append(this.flight.getPrettyDisplay(space, indentation + 1));

            if (!space.isEmpty()) {
                iformattabletextcomponent.append("\n").append(Strings.repeat(space, indentation));
            }

            iformattabletextcomponent.append("}");
            return iformattabletextcomponent;
        }
    }
}
