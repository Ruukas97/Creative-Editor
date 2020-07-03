package creativeeditor.screen;

import com.mojang.blaze3d.platform.GlStateManager;

import creativeeditor.data.DataItem;
import creativeeditor.util.ColorUtils.Color;
import creativeeditor.util.GuiUtil;
import creativeeditor.widgets.StyledButton;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.ITextComponent;

public class ParentItemScreen extends ParentScreen {
    protected DataItem item;

    // Back, reset, drop, save button (has essential buttons)
    protected boolean hasEssButtons = true;
    protected StyledButton backButton, resetButton, saveButton, dropButton;

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
            int posY = height - 42;
            boolean hasLastscreen = lastScreen != null;
            String butCloseBack = hasLastscreen ? "gui.main.back" : "gui.main.close";

            backButton = addButton( new StyledButton( posX - bwidth - 1, posY, bwidth, 20, I18n.format( butCloseBack ), this::back ) );

            resetButton = addButton( new StyledButton( posX, (hasLastscreen ? posY : posY - 11), bwidth, 20, I18n.format( "gui.main.reset" ), this::reset ) );

            saveButton = hasLastscreen ? null : addButton( new StyledButton( posX, posY + 10, bwidth, 20, I18n.format( "gui.main.save" ), this::save ) );

            dropButton = addButton( new StyledButton( posX + bwidth + 1, posY, bwidth, 20, I18n.format( "gui.main.drop" ), this::drop ) );
        }
    }


    public void back( Widget w ) {
        minecraft.displayGuiScreen( lastScreen );
    }


    public void reset( Widget w ) {
        DataItem dItem = new DataItem( item.getItem().getItem(), 1, new CompoundNBT(), item.getSlot().get() );
        item = dItem;
        Screen last = lastScreen;
        while (last instanceof ParentItemScreen) {
            ParentItemScreen lastItemScreen = (ParentItemScreen) last;
            lastItemScreen.item = dItem;
            last = lastItemScreen.lastScreen;
        }
        init();
    }


    public void save( Widget w ) {
        if (item.getItem().getItem() != Items.AIR)
            minecraft.playerController.sendSlotPacket( item.getItemStack(), 36 + minecraft.player.inventory.currentItem );
    }


    public void drop( Widget w ) {
        if (item.getItem().getItem() != Items.AIR)
            minecraft.playerController.sendPacketDropItem( item.getItemStack() );
        // Shift for /give
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
                drawCenteredString( font, ite.getDisplayName( stack ).getFormattedText(), x, y, color.getInt() );
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
            if (itemScale == 2f)
                GuiUtil.drawFrame( width / 2 - 20, height / 2 + 10, width / 2 + 20, height / 2 + 50, 1, color );


            // TODO Item scale support
            if (GuiUtil.isMouseIn( mouseX, mouseY, width / 2 - 17, height / 2 + 13, 34, 34 ))
                renderTooltip( stack, mouseX, mouseY );
        }
    }
}
