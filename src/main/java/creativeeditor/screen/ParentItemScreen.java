package creativeeditor.screen;

import com.mojang.blaze3d.platform.GlStateManager;

import creativeeditor.data.DataItem;
import creativeeditor.util.ColorUtils.Color;
import creativeeditor.util.GuiUtil;
import creativeeditor.widgets.StyledButton;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.text.ITextComponent;

public class ParentItemScreen extends ParentScreen {
    protected DataItem item;

    // Back, reset, drop, save button (has essential buttons)
    protected boolean hasEssButtons = true;

    // render item
    protected boolean renderItem = true;
    protected float itemScale = 2.0f;

    // render tooltip top left
    protected boolean renderToolTip = false;


    public ParentItemScreen(ITextComponent title, Screen lastScreen, DataItem editing) {
        super( title, lastScreen );
        this.item = editing;
    }


    @Override
    protected void init() {
        super.init();
        buttons.clear();
        children().clear();
        renderWidgets.clear();

        if (hasEssButtons) {
            int bwidth = 50;
            int posX = width / 2 - (bwidth / 2);
            int posY = height / 7 * 6;
            String butCloseBack = lastScreen == null ? "gui.main.close" : "gui.main.back";

            addButton( new StyledButton( posX - bwidth - 1, posY, bwidth, 20, I18n.format( butCloseBack ), b -> {
                mc.displayGuiScreen( lastScreen );
            } ) );
            addButton( new StyledButton( posX, posY - 11, bwidth, 20, I18n.format( "gui.main.reset" ), b -> {
                mc.displayGuiScreen( new MainScreen( this.lastScreen, new DataItem( item.getItem().getItem(), 1, null, item.getSlot().get() ) ) );
            } ) );
            addButton( new StyledButton( posX, posY + 10, bwidth, 20, I18n.format( "gui.main.save" ), b -> {
                if (item.getItem().getItem() != Items.AIR)
                    mc.playerController.sendSlotPacket( item.getItemStack(), 36 + mc.player.inventory.currentItem );
            } ) );
            addButton( new StyledButton( posX + bwidth + 1, posY, bwidth, 20, I18n.format( "gui.main.drop" ), b -> {
                if (item.getItem().getItem() != Items.AIR)
                    mc.playerController.sendPacketDropItem( item.getItemStack() );
                // Shift for /give
            } ) );
        }
    }


    public DataItem getItem() {
        return this.item;
    }


    public void setRenderItem( boolean shouldRender, float scale ) {
        this.renderItem = shouldRender;
        if (scale > 0)
            this.itemScale = scale;
    }


    @Override
    public void mainRender( int mouseX, int mouseY, float p3, Color color ) {
        super.mainRender( mouseX, mouseY, p3, color );
    }


    @Override
    public void overlayRender( int mouseX, int mouseY, float p3, Color color ) {
        super.overlayRender( mouseX, mouseY, p3, color );
        // Item (Tooltip must render last or colors will be messed up)
        if (renderItem) {
            ItemStack stack = item.getItemStack();

            Item ite = item.getItem().getItem();
            if (ite == Items.AIR) {
                int x = (int) (width / 2);
                int y = (int) (30 + height / (2) - 5);
                drawCenteredString( font, ite.getDisplayName( stack ).getFormattedText(), x, y, -1 );
            }
            else {
                GlStateManager.scalef( itemScale, itemScale, 1f );
                RenderHelper.enableGUIStandardItemLighting();
                int x = (int) (width / (2 * itemScale) - 8);
                int y = (int) (30 / itemScale + height / (2 * itemScale) - 8);
                itemRenderer.renderItemIntoGUI( stack, x, y );
                itemRenderer.renderItemOverlayIntoGUI( font, item.getItemStack(), x, y, null );
                RenderHelper.disableStandardItemLighting();

                GlStateManager.scalef( 1f / itemScale, 1f / itemScale, 1f );
            }

            // Item frame
            GuiUtil.drawFrame( width / 2 - 20, height / 2 + 10, width / 2 + 20, height / 2 + 50, 1, color );

            if (GuiUtil.isMouseIn( mouseX, mouseY, width / 2 - 17, height / 2 + 13, 34, 34 )) {
                renderTooltip( stack, mouseX, mouseY );
            }
        }
    }
}
