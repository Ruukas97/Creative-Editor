package infinityitemeditor.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import infinityitemeditor.InfinityItemEditor;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.util.ResourceLocation;

public enum TreeIcons {
    COLLAPSED(0),
    EXPANDED(1);

    public static final ResourceLocation ICONS = new ResourceLocation(InfinityItemEditor.MODID, "textures/gui/treeview.png");

    @Getter
    final int index;

    TreeIcons(int i) {
        this.index = i;
    }

    private int getXIndex() {
        return index % 3;
    }

    private int getYIndex() {
        return index / 3;
    }

    public void renderIcon(Minecraft mc, MatrixStack matrix, int x, int y) {
        mc.getTextureManager().bind(ICONS);
        AbstractGui.blit(matrix,
                x, y,
                9 * getXIndex(), 9 * getYIndex(),
                9, 9,
                32, 32
        );

    }
}
