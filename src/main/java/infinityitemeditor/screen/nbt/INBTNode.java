package infinityitemeditor.screen.nbt;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.ITextComponent;

public interface INBTNode {
    ITextComponent getNodeName();
    void renderIcon(Minecraft mc, MatrixStack matrix, int x, int y);
    INBTNode getSubNodes();
}
