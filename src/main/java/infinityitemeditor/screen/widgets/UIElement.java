package infinityitemeditor.screen.widgets;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.IRenderable;

public abstract class UIElement extends AbstractGui implements IGuiEventListener, IRenderable {
    @Override
    public abstract void render(MatrixStack matrix, int mouseX, int mouseY, float partialTicks);
}
