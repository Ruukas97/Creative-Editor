package infinityitemeditor.data.tag;

import infinityitemeditor.data.base.DataByte;
import net.minecraft.nbt.ByteTag;
import net.minecraft.potion.Effect;

public class TagEffectId extends DataByte {
    public TagEffectId(ByteTag value) {
        super(value);
    }


    public TagEffectId(byte value) {
        super(value);
    }


    public TagEffectId(Effect effect) {
        this((byte) Effect.getId(effect));
    }

    public Effect getEffect() {
        return Effect.byId(data);
    }

    public void setEffect(Effect effect) {
        data = (byte) Effect.getId(effect);
    }

    @Override
    public boolean isDefault() {
        return false;
    }
}
