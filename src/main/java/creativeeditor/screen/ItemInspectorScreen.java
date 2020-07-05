package creativeeditor.screen;

import com.mojang.blaze3d.systems.RenderSystem;

import creativeeditor.data.DataItem;
import creativeeditor.util.ColorUtils.Color;
import creativeeditor.util.ItemRendererUtils;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TranslationTextComponent;

public class ItemInspectorScreen extends ParentScreen {
    private DataItem item;
    private boolean isScrolling;
    private float rotX = 0f, rotY = 0f;
    private float itemScale = 10.0f;


    public ItemInspectorScreen(Screen lastScreen, DataItem item) {
        super( new TranslationTextComponent( "gui.iteminspector" ), lastScreen );
        this.item = item;
        this.isScrolling = false;
    }


    @Override
    public boolean mouseDragged( double p_mouseDragged_1_, double p_mouseDragged_3_, int p_mouseDragged_5_, double p_mouseDragged_6_, double p_mouseDragged_8_ ) {
        if (p_mouseDragged_5_ != 0) {
            this.isScrolling = false;
            return false;
        }
        else if (!this.isScrolling) {
            this.isScrolling = true;
        }
        else {
            rotX += p_mouseDragged_6_;
            rotY += p_mouseDragged_8_;
        }
        return true;
    }


    @Override
    public void overlayRender( int mouseX, int mouseY, float p3, Color color ) {
        super.overlayRender( mouseX, mouseY, p3, color );
        ItemRendererUtils itemRenderUtils = new ItemRendererUtils( this.itemRenderer );

        ItemStack stack = item.getItemStack();
        // GlStateManager.rotated( rotX * 3, 0.0f, 0.0f, -1.0f );
        // GlStateManager.rotatef( rotX, 0.0f, 1.0f, 0f );
        RenderSystem.scalef( itemScale, itemScale, 1f );
        RenderHelper.enableStandardItemLighting();
        int x = (int) (width / (2 * itemScale) - 8);
        int y = (int) (30 / itemScale + height / (2 * itemScale) - 8);
        itemRenderUtils.renderItemIntoGUI( stack, x, y, rotX, rotY );
        // itemRenderer.renderItemIntoGUI( stack, x, y );
        // itemRenderer.renderItemOverlayIntoGUI( font, item.getItemStack(), x, y, null
        // );
        RenderHelper.disableStandardItemLighting();

        RenderSystem.scalef( 1f / itemScale, 1f / itemScale, 1f );
        // GlStateManager.rotatef( 0.1f, -rotX, -rotY, -rotZ );

        // Item frame
        // GuiUtil.drawFrame( width / 2 - 20, height / 2 + 10, width / 2 + 20, height /
        // 2 + 50, 1, color );

        // if (GuiUtil.isMouseIn( mouseX, mouseY, width / 2 - 17, height / 2 + 13, 34,
        // 34 )) {
        // renderTooltip( stack, mouseX, mouseY );
        // }
    }

}
