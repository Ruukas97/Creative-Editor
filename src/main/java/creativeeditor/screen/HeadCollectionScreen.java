package creativeeditor.screen;

import java.util.LinkedList;
import java.util.List;

import org.lwjgl.glfw.GLFW;

import com.mojang.blaze3d.platform.GlStateManager;

import creativeeditor.data.DataItem;
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
import net.minecraft.util.SoundEvents;
import net.minecraft.util.text.TranslationTextComponent;

public class HeadCollectionScreen extends ParentScreen {
    private List<ItemStack> filteredHeads = new LinkedList<>();

    private static MinecraftHeadsCategory selCat = MinecraftHeadsCategory.alphabet;
    private static int currentElement = 0;
    private static String filteredString = null;
    private static String searchString = "";
    private int divideHeight = 20;

    private int maxInRow;
    private int amountInPage;


    public HeadCollectionScreen(Screen lastScreen) {
        super( new TranslationTextComponent( "gui.headcollection" ), lastScreen );
    }


    @Override
    public void init() {
        super.init();

        filteredString = null;

        if (!searchString.equals( filteredString )) {
            filteredHeads.clear();

            for (ItemStack stack : MinecraftHeads.getHeads( selCat )) {
                DataItem dItem = new DataItem( stack );
                if (dItem.getTag().getSkullOwner().get().getName().toLowerCase().contains( searchString.toLowerCase() )) {
                    filteredHeads.add( stack );
                }
            }

            currentElement = 0;
            filteredString = searchString;
        }

        maxInRow = (width - 250) / 14;
        amountInPage = maxInRow * 10;

        minecraft.keyboardListener.enableRepeatEvents( true );
    }


    @Override
    public void removed() {
        minecraft.keyboardListener.enableRepeatEvents( false );
    }


    @Override
    public boolean mouseClicked( double mouseX, double mouseY, int mouseButton ) {
        int heightOffset = height / divideHeight;
        super.mouseClicked( mouseX, mouseY, mouseButton );
        if (mouseButton != 0) {
            return false;
        }

        int letterSpace = 80;
        int space = ((width - (maxInRow * 16)) - letterSpace) / 2;
        int nextPageW = font.getStringWidth( "-->" );
        int topbar = 20;

        int currentPage = currentElement / amountInPage;
        int amountPages = ((int) Math.ceil( filteredHeads.size() / amountInPage ) + 1);

        int searchW = font.getStringWidth( searchString );
        if (searchString.length() > 0 && !searchString.equals( filteredString ) && GuiUtil.isMouseInRegion( (int) mouseX, (int) mouseY, (width / 2) - searchW / 2, heightOffset + 6, searchW, 8 )) {
            init();
        }

        // Next page
        else if (currentPage + 1 < amountPages && GuiUtil.isMouseInRegion( (int) mouseX, (int) mouseY, space + letterSpace + maxInRow * 16 - 3 - nextPageW, heightOffset + topbar + 168, nextPageW, 8 )) {
            currentElement = Math.min( filteredHeads.size() - 1, (currentPage + 1) * amountInPage );
            playClickSound();
            return false;
        }
        else if (currentPage > 0 && GuiUtil.isMouseInRegion( (int) mouseX, (int) mouseY, space + letterSpace + maxInRow * 16 - 25 - nextPageW * 2, heightOffset + topbar + 168, nextPageW, 8 )) {
            currentElement = Math.max( 0, (currentPage - 1) * amountInPage );
            playClickSound();
            return false;
        }

        for (MinecraftHeadsCategory category : MinecraftHeadsCategory.values()) {
            int x = space + letterSpace / 2;
            int y = category.ordinal() * 15 + heightOffset + 9 + topbar;

            int sWH = font.getStringWidth( category.getName() ) / 2;
            if (mouseX > x - sWH && mouseX < x + sWH && mouseY > y - 1 && mouseY < y + 9) {
                playClickSound();
                selCat = category;
                currentElement = 0;
                init();
                return false;
            }
        }

        if (filteredHeads.size() > 0) {
            for (int i = (int) Math.min( filteredHeads.size() - 1, currentPage * amountInPage ); i < (int) Math.min( filteredHeads.size(), (currentPage + 1) * amountInPage ); i++) {
                int x = space + letterSpace + (16 * (i % maxInRow));
                int y = heightOffset + topbar + (16 * ((i % amountInPage) / maxInRow));

                if (mouseX > x && mouseX < x + 16 && mouseY > y && mouseY < y + 16) {
                    ItemStack is = filteredHeads.get( i );
                    if (hasShiftDown()) {
                        minecraft.playerController.sendPacketDropItem( is );
                    }
                    else {
                        int slot = InventoryUtils.getEmptySlotsCount( minecraft.player.inventory );
                        if (slot <= 0) {
                            minecraft.playerController.sendPacketDropItem( is );
                        }
                        else {
                            int emptySlot = InventoryUtils.getEmptySlot( minecraft.player.inventory );
                            minecraft.playerController.sendSlotPacket( is, emptySlot );
                            minecraft.player.playSound( SoundEvents.ENTITY_ITEM_PICKUP, 0.1F, 1.01F );
                        }

                    }
                    return true;
                }
            }
        }

        return super.mouseClicked( mouseX, mouseY, mouseButton );
    }


    private void playClickSound() {
        minecraft.player.playSound( SoundEvents.UI_BUTTON_CLICK, 0.2F, 1.01F );
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
        else if (key == GLFW.GLFW_KEY_SPACE) {
            searchString += " ";
        }
        else if (searchString.length() < 20) {
            char c = (char) key;
            String cha = String.valueOf( c );
            if (cha.matches( "[a-zA-Z0-9]" )) {
                if (!hasShiftDown()) {
                    cha = cha.toLowerCase();
                }
                searchString += cha;
            }
        }
        return false;
    }


    @Override
    public void backRender( int mouseX, int mouseY, float p3, Color color ) {
        // super.backRender( mouseX, mouseY, p3, color );
        int topbar = 20;
        int heightOffset = height / divideHeight;
        int letterSpace = 85;
        int space = ((width - (maxInRow * 16 + 3)) - letterSpace) / 2;
        int currentPage = currentElement / amountInPage;
        int blandColor = GuiUtil.getColorFromRGB( 255, 230, 230, 245 );
        int amountPages = ((int) Math.ceil( filteredHeads.size() / amountInPage ) + 1);

        Style style = StyleManager.getCurrentStyle();
        int mainColor = color.getInt();

        // Background
        fill( space, heightOffset + topbar, space + letterSpace + (maxInRow * 16) + 3, heightOffset + topbar * 2 + 163, GuiUtil.getColorFromRGB( 110, 0, 0, 0 ) );

        // Letterspace
        fill( space + 2, heightOffset + topbar, space + letterSpace - 2, heightOffset + topbar + 161, GuiUtil.getColorFromRGB( 100, 50, 50, 50 ) );

        // Topbar
        fill( space, heightOffset, space + letterSpace + (maxInRow * 16) + 3, heightOffset + topbar, mainColor );

        // Outlines
        fill( space, heightOffset + topbar, space + 2, heightOffset + topbar + 161 + topbar, mainColor );
        fill( space + letterSpace + maxInRow * 16 + 1, heightOffset + topbar, space + letterSpace + maxInRow * 16 + 3, heightOffset + topbar + 161 + topbar, mainColor );
        fill( space + 2, heightOffset + topbar + 161, space + letterSpace + (maxInRow * 16) + 1, heightOffset + topbar + 163, mainColor );
        fill( space, heightOffset + topbar * 2 + 161, space + letterSpace + (maxInRow * 16) + 3, heightOffset + topbar * 2 + 163, mainColor );

        // Split bar
        fill( space + letterSpace, heightOffset + topbar, space + letterSpace - 2, heightOffset + topbar + 161, mainColor );

        for (MinecraftHeadsCategory category : MinecraftHeadsCategory.values()) {
            int x = space + letterSpace / 2;
            int y = category.ordinal() * 15 + +heightOffset + 9 + topbar;
            int sW = font.getStringWidth( category.getName() );
            int sWH = sW / 2;

            drawCenteredString( font, I18n.format( "gui.headcollection.category." + category.getName() ), x, y, (style.getFGColor( true, category == selCat || GuiUtil.isMouseInRegion( mouseX, mouseY, x - sWH, y - 1, sW, 10 ) ).getInt()) );
        }

        drawString( font, I18n.format( "gui.headcollection" ) + " (" + filteredHeads.size() + ")", space + 7, heightOffset + 6, blandColor );

        drawCenteredString( font, searchString.length() > 0 ? searchString : I18n.format( "gui.headcollection.typesearch" ), width / 2, heightOffset + 6, blandColor );

        String pageString = I18n.format( "gui.headcollection.currentpage", currentPage + 1, amountPages );
        drawString( font, pageString, space + letterSpace + maxInRow * 16 - font.getStringWidth( pageString ), heightOffset + 6, blandColor );

        String nextPage = "-->";
        int nextPageW = font.getStringWidth( nextPage );
        if (currentPage + 1 < amountPages) {
            boolean selectedN = GuiUtil.isMouseInRegion( mouseX, mouseY, space + letterSpace + maxInRow * 16 - 3 - nextPageW, heightOffset + topbar + 168, nextPageW, 8 );
            drawString( font, nextPage, space + letterSpace + maxInRow * 16 - 3 - nextPageW, heightOffset + topbar + 168, style.getFGColor( true, selectedN ).getInt() );
        }

        drawCenteredString( font, "" + (currentPage + 1), space + letterSpace + maxInRow * 16 - 13 - nextPageW, heightOffset + topbar + 168, blandColor );

        if (currentPage > 0) {
            String previousPage = "<--";
            boolean selectedP = GuiUtil.isMouseInRegion( mouseX, mouseY, space + letterSpace + maxInRow * 16 - 25 - nextPageW * 2, heightOffset + topbar + 168, nextPageW, 8 );
            drawString( font, previousPage, space + letterSpace + maxInRow * 16 - 25 - nextPageW * 2, heightOffset + topbar + 168, style.getFGColor( true, selectedP ).getInt() );
        }

        drawString( font, I18n.format( "gui.headcollection.credit" ), space + 7, heightOffset + topbar + 168, blandColor );

        // Has to be before tooltip cause of skull hover
        drawCenteredString( font, I18n.format( "gui.headcollection.freeslots" ) + ": " + InventoryUtils.getEmptySlotsCount( minecraft.player.inventory ), width / 2, height - 45, blandColor );

        drawCenteredString( font, I18n.format( "gui.headcollection.headsininventory" ) + ": " + InventoryUtils.countItem( minecraft.player.inventory, Items.PLAYER_HEAD ), width / 2, height - 35, blandColor );

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
                int y = heightOffset + topbar + (16 * ((i % amountInPage) / maxInRow));
                ItemStack stack = filteredHeads.get( i );
                itemRenderer.renderItemAndEffectIntoGUI( stack, x, y );
                if (hovered == null && mouseX > x && mouseX < x + 16 && mouseY > y && mouseY < y + 16) {
                    fill( x, y, x + 16, y + 16, GuiUtil.getColorFromRGB( 150, 150, 150, 150 ) );
                    hovered = stack;
                }
            }
        }

        GlStateManager.enableLighting();
        GlStateManager.popMatrix();

        int searchW = font.getStringWidth( searchString );

        if (hovered != null) {
            GuiUtil.addToolTip( this, mouseX, mouseY, width, height, mouseX, mouseY, hovered.getDisplayName().getFormattedText(), "§7" + I18n.format( "gui.headcollection.clickhead" ), "§7" + I18n.format( "gui.headcollection.clickheadshift" ) );
        }
        else if (!searchString.equals( filteredString ) && GuiUtil.isMouseInRegion( mouseX, mouseY, (width / 2) - searchW / 2, 56, searchW, 8 )) {
            GuiUtil.addToolTip( this, mouseX, mouseY, I18n.format( "gui.headcollection.clicksearch" ) );
        }
        else {
            GuiUtil.addToolTip( this, space + 2, heightOffset + topbar, letterSpace - 4, 161, mouseX, mouseY, I18n.format( "gui.headcollection.changecategory" ) );
        }

    }
}
