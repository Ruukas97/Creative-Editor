package infinityitemeditor.screen.nbt;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

import java.util.Map;

public interface INBTNode {
    default ITextComponent getNodeName(String key) {
        return new StringTextComponent(key);
    }

    void renderIcon(Minecraft mc, MatrixStack matrix, int x, int y);

    default Map<String, ? extends INBTNode> getSubNodes() {
        return null;
    }

    default ContextMenu getContextMenu() {
        return null;
    }
}
