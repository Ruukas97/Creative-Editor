package creativeeditor.screen;

import java.util.LinkedList;
import java.util.List;

import org.lwjgl.glfw.GLFW;

import com.mojang.blaze3d.platform.GlStateManager;

import creativeeditor.json.MinecraftHeads;
import creativeeditor.json.MinecraftHeadsCategory;
import creativeeditor.styles.Style;
import creativeeditor.styles.StyleManager;
import creativeeditor.util.ColorUtils.Color;
import creativeeditor.util.GuiUtil;
import creativeeditor.util.InventoryUtils;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.text.TranslationTextComponent;

public class HeadCollectionScreen extends ParentScreen {
    private static MinecraftHeadsCategory selCat = MinecraftHeadsCategory.alphabet;
    private static int currentElement = 0;
    private static String filteredString = null;
    private static String searchString = "";

    private int maxInRow;
    private int amountInPage;

    private MinecraftHeadsCategory loaded = null;

    private List<ItemStack> unfilteredHeads = new LinkedList<ItemStack>();
    private List<ItemStack> filteredHeads = new LinkedList<ItemStack>();


    public HeadCollectionScreen(Screen lastScreen) {
        super( new TranslationTextComponent( "gui.headcollection" ), lastScreen );
    }


    @Override
    public void init() {
        super.init();
        mc.keyboardListener.enableRepeatEvents( true );
        if (loaded != selCat) {
            loadSkulls();
        }
        if (!searchString.equals( filteredString )) {
            filteredHeads.clear();

            for (ItemStack s : unfilteredHeads) {
                if (s.getDisplayName().toString().toLowerCase().contains( searchString.toLowerCase() )) {
                    filteredHeads.add( s );
                }
            }

            currentElement = 0;
            filteredString = searchString;
        }

        maxInRow = (width - 250) / 16;
        amountInPage = maxInRow * 10;
    }


    @Override
    public void onClose() {
        super.onClose();
        mc.keyboardListener.enableRepeatEvents( false );
    }


    @Override
    public boolean mouseClicked( double mouseX, double mouseY, int mouseButton ) {
        super.mouseClicked( mouseX, mouseY, mouseButton );
        if (mouseButton != 0) {
            return false;
        }

        int letterSpace = 80;
        int space = ((width - (maxInRow * 16)) - letterSpace) / 2;
        int nextPageW = fontRenderer.getStringWidth( "-->" );
        int topbar = 20;

        int currentPage = currentElement / amountInPage;
        int amountPages = ((int) Math.ceil( filteredHeads.size() / amountInPage ) + 1);

        int searchW = fontRenderer.getStringWidth( searchString );
        if (searchString.length() > 0 && !searchString.equals( filteredString ) && GuiUtil.isMouseInRegion( (int) mouseX, (int) mouseY, (width / 2) - searchW / 2, 56, searchW, 8 )) {
            init();
        }

        // Next page
        else if (currentPage + 1 < amountPages && GuiUtil.isMouseInRegion( (int) mouseX, (int) mouseY, space + letterSpace + maxInRow * 16 - 3 - nextPageW, 50 + topbar + 168, nextPageW, 8 )) {
            currentElement = Math.min( filteredHeads.size() - 1, (currentPage + 1) * amountInPage );
            return false;
        }
        else if (currentPage > 0 && GuiUtil.isMouseInRegion( (int) mouseX, (int) mouseY, space + letterSpace + maxInRow * 16 - 25 - nextPageW * 2, 50 + topbar + 168, nextPageW, 8 )) {
            currentElement = Math.max( 0, (currentPage - 1) * amountInPage );
            return false;
        }

        for (MinecraftHeadsCategory category : MinecraftHeadsCategory.values()) {
            int x = space + letterSpace / 2;
            int y = category.ordinal() * 15 + 59 + topbar;

            int sWH = fontRenderer.getStringWidth( category.getName() ) / 2;
            if (mouseX > x - sWH && mouseX < x + sWH && mouseY > y - 1 && mouseY < y + 9) {
                selCat = category;
                currentElement = 0;
                init();
                return false;
            }
        }

        if (filteredHeads.size() > 0) {
            for (int i = (int) Math.min( filteredHeads.size() - 1, currentPage * amountInPage ); i < (int) Math.min( filteredHeads.size(), (currentPage + 1) * amountInPage ); i++) {
                int x = space + letterSpace + (16 * (i % maxInRow));
                int y = 50 + topbar + (16 * ((i % amountInPage) / maxInRow));

                if (mouseX > x && mouseX < x + 16 && mouseY > y && mouseY < y + 16) {
                    if (hasShiftDown()) {
                        mc.playerController.sendPacketDropItem( filteredHeads.get( i ) );
                    }
                    else {
                        int slot = InventoryUtils.getEmptySlot( mc.player.inventory );
                        if (slot < 0)
                            mc.playerController.sendPacketDropItem( filteredHeads.get( i ) );
                        else
                            mc.playerController.sendSlotPacket( filteredHeads.get( i ), slot );
                    }
                    return true;
                }
            }
        }

        return super.mouseClicked( mouseX, mouseY, mouseButton );
    }


    @Override
    public boolean keyPressed( int key, int scanCode, int modifiers ) {
        if (super.keyPressed( key, scanCode, modifiers )) {
            return true;
        }
        else if (key == GLFW.GLFW_KEY_BACKSPACE) {
            searchString = searchString.substring( 0, Math.max( searchString.length() - 1, 0 ) );
            if (searchString.length() < 1 && !searchString.equals( filteredString )) {
                init();
            }
            return true;
        }
        else if ((key == GLFW.GLFW_KEY_ENTER || key == GLFW.GLFW_KEY_KP_ENTER) && !searchString.equals( filteredString )) {
            init();
            return true;
        }
        return false;
    }


    @Override
    public boolean charTyped( char key, int modifiers ) {
        if (super.charTyped( key, modifiers ))
            return true;
        if (SharedConstants.isAllowedCharacter( key ) && searchString.length() < 20) {
            searchString += key;
            return true;
        }
        return super.charTyped( key, modifiers );
    }


    @Override
    public void backRender( int mouseX, int mouseY, float p3, Color color ) {
        //super.backRender( mouseX, mouseY, p3, color );
        int topbar = 20;
        int letterSpace = 80;
        int space = ((width - (maxInRow * 16 + 3)) - letterSpace) / 2;
        int currentPage = currentElement / amountInPage;
        int blandColor = GuiUtil.getColorFromRGB( 255, 150, 200, 255 );
        int amountPages = ((int) Math.ceil( filteredHeads.size() / amountInPage ) + 1);

        Style style = StyleManager.getCurrentStyle();
        int mainColor = color.getInt();

        // Topbar
        fill( space, 50, space + letterSpace + (maxInRow * 16) + 3, 50 + topbar, mainColor );
        // Outlines
        fill( space, 50 + topbar, space + 2, 50 + topbar + 161 + topbar, mainColor );
        fill( space + letterSpace + maxInRow * 16 + 1, 50 + topbar, space + letterSpace + maxInRow * 16 + 3, 50 + topbar + 161 + topbar, mainColor );
        fill( space + 2, 50 + topbar + 161, space + letterSpace + (maxInRow * 16) + 1, 50 + topbar + 163, mainColor );
        fill( space, 50 + topbar * 2 + 161, space + letterSpace + (maxInRow * 16) + 3, 50 + topbar * 2 + 163, mainColor );

        // Background
        fill( space, 50 + topbar, space + letterSpace + (maxInRow * 16) + 3, 50 + topbar * 2 + 163, GuiUtil.getColorFromRGB( 100, 70, 50, 200 ) );
        // Letterspace
        fill( space + 2, 50 + topbar, space + letterSpace - 2, 50 + topbar + 161, GuiUtil.getColorFromRGB( 100, 50, 50, 50 ) );


        for (MinecraftHeadsCategory category : MinecraftHeadsCategory.values()) {
            int x = space + letterSpace / 2;
            int y = category.ordinal() * 15 + 59 + topbar;
            int sW = fontRenderer.getStringWidth( category.getName() );
            int sWH = sW / 2;

            drawCenteredString( fontRenderer, I18n.format( "gui.headcollection.category." + category.getName() ), x, y, (style.getFGColor( true, category == selCat || GuiUtil.isMouseInRegion( mouseX, mouseY, x - sWH, y - 1, sW, 10 ) ).getInt()) );
        }

        drawString( fontRenderer, I18n.format( "gui.headcollection" ) + " (" + filteredHeads.size() + ")", space + 7, 56, blandColor );

        drawCenteredString( fontRenderer, searchString.length() > 0 ? searchString : I18n.format( "gui.headcollection.typesearch" ), width / 2, 56, blandColor );

        String pageString = I18n.format( "gui.headcollection.currentpage", currentPage + 1, amountPages );
        drawString( fontRenderer, pageString, space + letterSpace + maxInRow * 16 - fontRenderer.getStringWidth( pageString ), 56, blandColor );

        String nextPage = "-->";
        int nextPageW = fontRenderer.getStringWidth( nextPage );
        if (currentPage + 1 < amountPages) {
            boolean selectedN = GuiUtil.isMouseInRegion( mouseX, mouseY, space + letterSpace + maxInRow * 16 - 3 - nextPageW, 50 + topbar + 168, nextPageW, 8 );
            drawString( fontRenderer, nextPage, space + letterSpace + maxInRow * 16 - 3 - nextPageW, 50 + topbar + 168, style.getFGColor( true, selectedN ).getInt() );
        }

        drawCenteredString( fontRenderer, "" + (currentPage + 1), space + letterSpace + maxInRow * 16 - 13 - nextPageW, 50 + topbar + 168, blandColor );

        if (currentPage > 0) {
            String previousPage = "<--";
            boolean selectedP = GuiUtil.isMouseInRegion( mouseX, mouseY, space + letterSpace + maxInRow * 16 - 25 - nextPageW * 2, 50 + topbar + 168, nextPageW, 8 );
            drawString( fontRenderer, previousPage, space + letterSpace + maxInRow * 16 - 25 - nextPageW * 2, 50 + topbar + 168, style.getFGColor( true, selectedP ).getInt() );
        }

        drawString( fontRenderer, I18n.format( "gui.headcollection.credit" ), space + 7, 50 + topbar + 168, blandColor );

        GlStateManager.pushMatrix();
        RenderHelper.enableGUIStandardItemLighting();
        GlStateManager.disableLighting();
        GlStateManager.enableRescaleNormal();
        GlStateManager.enableColorMaterial();
        GlStateManager.enableLighting();
        itemRenderer.zLevel = 100.0F;
        ItemStack hovered = null;
        if (filteredHeads.size() > 0) {
            for (int i = (int) Math.min( filteredHeads.size() - 1, currentPage * amountInPage ); i < (int) Math.min( filteredHeads.size(), (currentPage + 1) * amountInPage ); i++) {
                int x = space + letterSpace + (16 * (i % maxInRow));
                int y = 50 + topbar + (16 * ((i % amountInPage) / maxInRow));
                itemRenderer.renderItemAndEffectIntoGUI( filteredHeads.get( i ), x, y );
                if (mouseX > x && mouseX < x + 16 && mouseY > y && mouseY < y + 16) {
                    fill( x, y, x + 16, y + 16, GuiUtil.getColorFromRGB( 150, 150, 150, 150 ) );
                    hovered = filteredHeads.get( i );
                }
            }
        }

        GlStateManager.enableLighting();
        GlStateManager.popMatrix();

        int searchW = fontRenderer.getStringWidth( searchString );

        if (hovered != null) {
            GuiUtil.addToolTip( this, mouseX, mouseY, width, height, mouseX, mouseY, hovered.getDisplayName().getFormattedText(), I18n.format( "gui.headcollection.clickhead" ), I18n.format( "gui.headcollection.clickheadshift" ) );
        }
        else if (!searchString.equals( filteredString ) && GuiUtil.isMouseInRegion( mouseX, mouseY, (width / 2) - searchW / 2, 56, searchW, 8 )) {
            GuiUtil.addToolTip( this, mouseX, mouseY, I18n.format( "gui.headcollection.clicksearch" ) );
        }
        else {
            GuiUtil.addToolTip( this, space + 2, 50 + topbar, letterSpace - 4, 161, mouseX, mouseY, "gui.headcollection.changecategory" );
        }

        drawCenteredString( fontRenderer, "Free slots: " + InventoryUtils.getEmptySlots( mc.player.inventory ), width / 2, height - 45, blandColor );
        drawCenteredString( fontRenderer, "Heads in inventory: " + InventoryUtils.countItem( mc.player.inventory, Items.PLAYER_HEAD ), width / 2, height - 35, blandColor );

    }


    public void loadSkulls() {
        unfilteredHeads.clear();
        MinecraftHeads.loadCategory( unfilteredHeads, selCat );
        loaded = selCat;
        filteredString = null;
    }
}
