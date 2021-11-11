package creativeeditor.data.tag;

import creativeeditor.data.base.DataByte;
import net.minecraft.nbt.ByteNBT;
import net.minecraft.potion.Effect;

public class TagEffectId extends DataByte {
    public TagEffectId(ByteNBT value) {
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
