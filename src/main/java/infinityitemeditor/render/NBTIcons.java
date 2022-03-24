package infinityitemeditor.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import infinityitemeditor.InfinityItemEditor;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.util.ResourceLocation;

public enum NBTIcons {
    BYTE(0),
    DOUBLE(1),
    FLOAT(2),
    INT(3),
    LONG(4),
    SHORT(5),
    STRING(6),
    COMPOUND_TAG(7),
    BYTE_ARRAY(8),
    INT_ARRAY(9),
    LIST(10),
    BOOLEAN(11),
    LONG_ARRAY(12),
    EMPTY(13);

    public static final ResourceLocation ICONS = new ResourceLocation(InfinityItemEditor.MODID, "textures/gui/nbtsheet.png");

    @Getter
    final int index;

    NBTIcons(int i) {
        this.index = i;
    }

    private int getXIndex() {
        return index % 4;
    }

    private int getYIndex() {
        return index / 4;
    }

    public void renderIcon(Minecraft mc, MatrixStack matrix, int x, int y) {
        mc.getTextureManager().bind(ICONS);
        AbstractGui.blit(matrix,
                x, y,
                16 * getXIndex(), 16 * getYIndex(),
                16, 16,
                64, 64
        );

    }
}
