package infinityitemeditor.data.tag;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import infinityitemeditor.data.Data;
import infinityitemeditor.data.tag.block.TagBlockEntity;
import infinityitemeditor.data.version.NBTKeys;
import infinityitemeditor.render.NBTIcons;
import infinityitemeditor.util.ColorUtils;
import infinityitemeditor.util.ItemRendererUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.item.DyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.tileentity.BannerPattern;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import org.apache.commons.lang3.tuple.Pair;

@AllArgsConstructor
public class TagBannerPattern implements Data<Pair<BannerPattern, DyeColor>, CompoundNBT> {
    @Getter
    @Setter
    protected Data<?, ?> parent;
    @Getter
    @Setter
    private BannerPattern pattern;
    @Getter
    @Setter
    private DyeColor color;


    public TagBannerPattern(INBT nbt) {
        this(nbt instanceof CompoundNBT ? (CompoundNBT) nbt : new CompoundNBT());
    }


    public TagBannerPattern(CompoundNBT nbt) {
        NBTKeys keys = NBTKeys.keys;
        color = DyeColor.byId(nbt.getInt(keys.patternColor()));
        pattern = BannerPattern.byHash(nbt.getString(keys.patternPattern()));
    }


    @Override
    public Pair<BannerPattern, DyeColor> getData() {
        return Pair.of(pattern, color);
    }


    @Override
    public boolean isDefault() {
        return pattern == null;
    }


    @Override
    public CompoundNBT getNBT() {
        NBTKeys keys = NBTKeys.keys;
        CompoundNBT nbt = new CompoundNBT();
        if (color != null) {
            nbt.putInt(keys.patternColor(), color.getId());
        }
        if (pattern != null) {
            nbt.putString(keys.patternPattern(), pattern.getHashname());
        }
        return nbt;
    }

    @Override
    public ITextComponent getPrettyDisplay(String space, int indentation) {
        return new StringTextComponent(color.getName()).append(" ").append(pattern.getFilename());
    }

    @Override
    public void renderIcon(Minecraft mc, MatrixStack matrix, int x, int y) {
        ColorUtils.Color color = new ColorUtils.Color(0xFF555555);
        RenderSystem.color3f(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f);
        NBTIcons.EMPTY.renderIcon(mc, matrix, x, y);
        ResourceLocation location = pattern.location(true);
        location = new ResourceLocation("textures/" + location.getPath() + ".png");
        mc.getTextureManager().bind(location);
        color = new ColorUtils.Color(getColor().getColorValue());
        RenderSystem.color3f(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f);
        AbstractGui.blit(matrix,
                x + 4, y,
                0, 0,
                8, 16,
                24, 24
        );
        RenderSystem.color3f(1f, 1f, 1f);
    }
}
