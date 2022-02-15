package infinityitemeditor.data.tag;

import infinityitemeditor.data.Data;
import infinityitemeditor.data.base.DataBoolean;
import infinityitemeditor.data.base.DataByte;
import infinityitemeditor.data.base.DataInteger;
import lombok.Getter;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.potion.Effect;
import net.minecraft.util.RegistryKey;

import java.util.Map;

public class TagEffect implements Data<EffectInstance, CompoundTag> {
    /*
     * See: {@link net.minecraft.potion.Potion} {@link Potions} {@link Effect} {@link EffectInstance}
     */
    @Getter
    private final TagEffectId effectId;
    @Getter
    private final DataByte amplifier;
    @Getter
    private final DataInteger duration;
    @Getter
    private final DataBoolean ambient;
    @Getter
    private final DataBoolean showParticles;
    @Getter
    private final DataBoolean showIcon;


    public TagEffect(Tag nbt) {
        this(nbt instanceof CompoundTag ? (CompoundTag) nbt : new CompoundTag());
    }

    public TagEffect(CompoundTag nbt) {
        effectId = new TagEffectId(nbt.getByte("Id"));
        amplifier = new DataByte(nbt.getByte("Amplifier"));
        duration = new DataInteger(nbt.getInt("Duration"));
        ambient = new DataBoolean(nbt.getBoolean("Ambient"));
        showParticles = new DataBoolean(nbt.getBoolean("ShowParticles"));
        showIcon = new DataBoolean(nbt.getBoolean("ShowIcon"));
    }

    public TagEffect(Map.Entry<RegistryKey<Effect>, Effect> registryEntry) {
        effectId = new TagEffectId((byte) Effect.getId(registryEntry.getValue()));
        amplifier = new DataByte((byte) 1);
        duration = new DataInteger( (byte) 1);
        ambient = new DataBoolean(false);
        showParticles = new DataBoolean(false);
        showIcon = new DataBoolean(false);
    }


    @Override
    public EffectInstance getData() {
        return EffectInstance.load(getTag());
    }

    @Override
    public boolean isDefault() {
        return false;
    }


    @Override
    public CompoundTag getTag() {
        CompoundTag nbt = new CompoundTag();
        nbt.put("Id", effectId.getTag());
        if (amplifier.get() > 1) {
            nbt.put("Amplifier", amplifier.getTag());
        }
        if (duration.get() > 1) {
            nbt.put("Duration", duration.getTag());
        }
        if (!ambient.get()) {
            nbt.put("Ambient", ambient.getTag());
        }
        if (!showParticles.get()) {
            nbt.put("ShowParticles", showParticles.getTag());
        }
        if (!showIcon.get()) {
            nbt.put("ShowIcon", showIcon.getTag());
        }
        return nbt;
    }

//    @Override
//    public Component getPrettyDisplay(String space, int indentation) {
//        return EffectUtils.getText(this);
//    }
}
