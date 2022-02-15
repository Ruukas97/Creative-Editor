package infinityitemeditor.screen.widgets;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.GuiEventListener;
import net.minecraft.client.gui.IRenderable;

public abstract class UIElement extends AbstractGui implements GuiEventListener, IRenderable {
    @Override
    public abstract void render(PoseStack poseStack, int mouseX, int mouseY, float partialTicks);
}
