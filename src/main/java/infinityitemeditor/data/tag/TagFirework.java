package infinityitemeditor.data.tag;

import com.google.common.base.Strings;
import infinityitemeditor.data.base.DataByte;
import infinityitemeditor.data.base.DataMap;
import infinityitemeditor.data.base.SingularData;
import infinityitemeditor.data.version.NBTKeys;
import lombok.Getter;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.MutableComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraftforge.common.util.Constants.NBT;

public class TagFirework extends SingularData<TagList<TagExplosion>, CompoundTag> {
    @Getter
    private final DataByte flight;


    public TagFirework(CompoundTag nbt) {
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
    public CompoundTag getTag() {
        NBTKeys keys = NBTKeys.keys;
        CompoundTag nbt = new CompoundTag();
        nbt.put(keys.fireworksFlight(), flight.getTag());
        nbt.put(keys.fireworksExplosions(), data.getTag());
        return nbt;
    }

    @Override
    public MutableComponent getPrettyDisplay(String space, int indentation) {
        if (isDefault()) {
            return new TextComponent("{}");
        } else {
            IFormattableTextComponent iformattabletextcomponent = new TextComponent("{");

            if (!space.isEmpty()) {
                iformattabletextcomponent.append("\n");
            }

            IFormattableTextComponent iformattabletextcomponent1;
            NBTKeys keys = NBTKeys.keys;
            String explosions = keys.fireworksExplosions();
            String flight = keys.fireworksFlight();

            iformattabletextcomponent1 = (new TextComponent(Strings.repeat(space, indentation + 1))).append(DataMap.handleEscapePretty(explosions)).append(String.valueOf(':')).append(" ").append(this.data.getPrettyDisplay(space, indentation + 1));
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
