package infinityitemeditor.data.tag;

import com.mojang.blaze3d.matrix.MatrixStack;
import infinityitemeditor.data.Data;
import infinityitemeditor.data.base.DataBoolean;
import infinityitemeditor.data.base.DataByte;
import infinityitemeditor.data.base.DataInteger;
import infinityitemeditor.util.EffectUtils;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.renderer.texture.PotionSpriteUploader;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.text.ITextComponent;

import java.util.Map;

public class TagEffect implements Data<EffectInstance, CompoundNBT> {
    @Getter
    @Setter
    protected Data<?, ?> parent;

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


    public TagEffect(INBT nbt) {
        this(nbt instanceof CompoundNBT ? (CompoundNBT) nbt : new CompoundNBT());
    }

    public TagEffect(CompoundNBT nbt) {
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
        duration = new DataInteger((byte) 1);
        ambient = new DataBoolean(false);
        showParticles = new DataBoolean(false);
        showIcon = new DataBoolean(false);
    }


    @Override
    public EffectInstance getData() {
        return EffectInstance.load(getNBT());
    }

    @Override
    public boolean isDefault() {
        return false;
    }


    @Override
    public CompoundNBT getNBT() {
        CompoundNBT nbt = new CompoundNBT();
        nbt.put("Id", effectId.getNBT());
        if (amplifier.get() > 1) {
            nbt.put("Amplifier", amplifier.getNBT());
        }
        if (duration.get() > 1) {
            nbt.put("Duration", duration.getNBT());
        }
        if (!ambient.get()) {
            nbt.put("Ambient", ambient.getNBT());
        }
        if (!showParticles.get()) {
            nbt.put("ShowParticles", showParticles.getNBT());
        }
        if (!showIcon.get()) {
            nbt.put("ShowIcon", showIcon.getNBT());
        }
        return nbt;
    }

    @Override
    public ITextComponent getPrettyDisplay(String space, int indentation) {
        return EffectUtils.getText(this);
    }

    @Override
    public void renderIcon(Minecraft mc, MatrixStack matrix, int x, int y) {
        int blitOffset = 0;
        if (mc.screen != null) {
            blitOffset = mc.screen.getBlitOffset();
        }
        PotionSpriteUploader potionspriteuploader = mc.getMobEffectTextures();
        Effect effect = getEffectId().getEffect();
        TextureAtlasSprite textureatlassprite = potionspriteuploader.get(effect);
        mc.getTextureManager().bind(textureatlassprite.atlas().location());

        AbstractGui.blit(matrix, -9, -9, blitOffset, 18, 18, textureatlassprite);
    }
}
