package infinityitemeditor.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import infinityitemeditor.data.DataItem;
import infinityitemeditor.util.ColorUtils.Color;
import infinityitemeditor.util.ItemRendererUtils;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.ItemStack;

public class ItemInspectorScreen extends ParentScreen {
    private final DataItem item;
    private boolean isScrolling;
    private float rotX = 0f, rotY = 0f;
    private final float itemScale = 10.0f;


    public ItemInspectorScreen(Screen lastScreen, DataItem item) {
        super(new TranslatableComponent("gui.iteminspector"), lastScreen);
        this.item = item;
        this.isScrolling = false;
    }


    @Override
    public boolean mouseDragged(double p_mouseDragged_1_, double p_mouseDragged_3_, int p_mouseDragged_5_, double p_mouseDragged_6_, double p_mouseDragged_8_) {
        if (p_mouseDragged_5_ != 0) {
            this.isScrolling = false;
            return false;
        } else if (!this.isScrolling) {
            this.isScrolling = true;
        } else {
            rotX += p_mouseDragged_6_;
            rotY += p_mouseDragged_8_;
        }
        return true;
    }


    @Override
    public void overlayRender(PoseStack poseStack, int mouseX, int mouseY, float p3, Color color) {
        super.overlayRender(poseStack,mouseX, mouseY, p3, color);
        ItemRendererUtils itemRenderUtils = new ItemRendererUtils(this.itemRenderer);

        ItemStack stack = item.getItemStack();
        poseStack.pushPose();
        RenderHelper.setupForFlatItems(); // front gui lighting
        poseStack.scale(itemScale, itemScale, 1f);
        int x = (int) (width / (2 * itemScale) - 8);
        int y = (int) (30 / itemScale + height / (2 * itemScale) - 11);
        itemRenderUtils.renderItemIntoGUI(stack, x, y, rotX, rotY);
        poseStack.popPose();
    }

}
