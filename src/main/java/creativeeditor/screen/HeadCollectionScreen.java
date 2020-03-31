package creativeeditor.screen;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import org.lwjgl.glfw.GLFW;

import com.google.gson.Gson;
import com.mojang.authlib.GameProfile;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.brigadier.exceptions.CommandSyntaxException;

import creativeeditor.data.DataItem;
import creativeeditor.data.tag.TagGameProfile;
import creativeeditor.styles.Style;
import creativeeditor.styles.StyleManager;
import creativeeditor.util.GuiUtil;
import creativeeditor.util.InventoryUtils;
import creativeeditor.util.MinecraftHead;
import creativeeditor.util.ColorUtils.Color;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class HeadCollectionScreen extends ParentScreen {

    public static final String[] CATEGORIES = { "alphabet", "animals", "blocks", "decoration", "food-drinks", "humans", "humanoid", "miscellaneous", "monsters", "plants" };
    private static final String API_URL = "https://minecraft-heads.com/scripts/api.php?cat=";

    private static int selCat = 0;
    private static int currentElement = 0;
    private static String filteredString = null;
    private static String searchString = "";

    private int maxInRow;
    private int amountInPage;

    private int loaded = -1;

    private final Screen lastScreen;
    private List<ItemStack> allSkulls = new LinkedList<ItemStack>();
    private List<ItemStack> filteredSkulls = new LinkedList<ItemStack>();


    public HeadCollectionScreen(Screen lastScreen) {
        super( new TranslationTextComponent( "gui.headcollection" ), lastScreen );
        this.lastScreen = lastScreen;

    }


    @Override
    public void init() {
        super.init();
        mc.keyboardListener.enableRepeatEvents( true );
        if (loaded != selCat) {
            try {
                loadSkulls();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (!searchString.equals( filteredString )) {
            filteredSkulls.clear();

            for (ItemStack s : allSkulls) {
                if (s.getDisplayName().toString().toLowerCase().contains( searchString.toLowerCase() )) {
                    filteredSkulls.add( s );
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
        mc.keyboardListener.enableRepeatEvents( false );
    }


    @Override
    public boolean mouseClicked( double mouseX, double mouseY, int mouseButton ) {
        if (mouseButton != 0) {
            return false;
        }

        int letterSpace = 80;
        int space = ((width - (maxInRow * 16)) - letterSpace) / 2;
        int nextPageW = fontRenderer.getStringWidth( "-->" );
        int topbar = 20;

        int currentPage = currentElement / amountInPage;
        int amountPages = ((int) Math.ceil( filteredSkulls.size() / amountInPage ) + 1);

        int searchW = fontRenderer.getStringWidth( searchString );
        if (searchString.length() > 0 && !searchString.equals( filteredString ) && GuiUtil.isMouseInRegion( (int) mouseX, (int) mouseY, (width / 2) - searchW / 2, 56, searchW, 8 )) {
            init();
        }

        // Next page
        else if (currentPage + 1 < amountPages && GuiUtil.isMouseInRegion( (int) mouseX, (int) mouseY, space + letterSpace + maxInRow * 16 - 3 - nextPageW, 50 + topbar + 168, nextPageW, 8 )) {
            currentElement = Math.min( filteredSkulls.size() - 1, (currentPage + 1) * amountInPage );
            return false;
        }
        else if (currentPage > 0 && GuiUtil.isMouseInRegion( (int) mouseX, (int) mouseY, space + letterSpace + maxInRow * 16 - 25 - nextPageW * 2, 50 + topbar + 168, nextPageW, 8 )) {
            currentElement = Math.max( 0, (currentPage - 1) * amountInPage );
            return false;
        }

        for (int i = 0; i < CATEGORIES.length; i++) {
            int x = space + letterSpace / 2;
            int y = i * 15 + 59 + topbar;

            int sWH = fontRenderer.getStringWidth( CATEGORIES[i] ) / 2;
            if (mouseX > x - sWH && mouseX < x + sWH && mouseY > y - 1 && mouseY < y + 9) {
                selCat = i;
                currentElement = 0;
                init();
                return false;
            }
        }

        if (filteredSkulls.size() > 0) {
            for (int i = (int) Math.min( filteredSkulls.size() - 1, currentPage * amountInPage ); i < (int) Math.min( filteredSkulls.size(), (currentPage + 1) * amountInPage ); i++) {
                int x = space + letterSpace + (16 * (i % maxInRow));
                int y = 50 + topbar + (16 * ((i % amountInPage) / maxInRow));

                if (mouseX > x && mouseX < x + 16 && mouseY > y && mouseY < y + 16) {
                    if (hasShiftDown()) {
                        mc.playerController.sendPacketDropItem( filteredSkulls.get( i ) );
                    }
                    else {
                        int slot = InventoryUtils.getEmptySlot( mc.player.inventory );
                        if (slot < 0)
                            mc.playerController.sendPacketDropItem( filteredSkulls.get( i ) );
                        else
                            mc.playerController.sendSlotPacket( filteredSkulls.get( i ), slot );
                    }
                    return true;
                }
            }
        }

        return super.mouseClicked( mouseX, mouseY, mouseButton );
    }


    @Override
    public boolean keyPressed( int key, int scanCode, int modifiers ) {
        if (key == 1) {
            this.mc.displayGuiScreen( lastScreen );

            if (this.mc.currentScreen == null) {
                this.mc.setGameFocused( true );
            }
        }
        else if (key == GLFW.GLFW_KEY_BACKSPACE) {
            searchString = searchString.substring( 0, Math.max( searchString.length() - 1, 0 ) );
            if (searchString.length() < 1 && !searchString.equals( filteredString )) {
                init();
            }
        }
        else if ((key == GLFW.GLFW_KEY_ENTER || key == GLFW.GLFW_KEY_KP_ENTER) && !searchString.equals( filteredString )) {
            init();
        }
        return super.keyPressed( key, scanCode, modifiers );
    }


    @Override
    public boolean charTyped( char key, int modifiers ) {
        if (SharedConstants.isAllowedCharacter( key ) && searchString.length() < 20) {
            searchString += key;
            return true;
        }
        return super.charTyped( key, modifiers );
    }


    @Override
    public void backRender( int mouseX, int mouseY, float p3, Color color ) {
        int topbar = 20;
        int letterSpace = 80;
        int space = ((width - (maxInRow * 16 + 3)) - letterSpace) / 2;
        int currentPage = currentElement / amountInPage;
        int blandColor = GuiUtil.getColorFromRGB( 255, 150, 200, 255 );
        int amountPages = ((int) Math.ceil( filteredSkulls.size() / amountInPage ) + 1);

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


        for (int i = 0; i < CATEGORIES.length; i++) {
            int x = space + letterSpace / 2;
            int y = i * 15 + 59 + topbar;
            int sW = fontRenderer.getStringWidth( CATEGORIES[i] );
            int sWH = sW / 2;

            drawCenteredString( fontRenderer, I18n.format( "gui.headcollection.category." + CATEGORIES[i] ), x, y, (style.getFGColor( true, i == selCat || GuiUtil.isMouseInRegion( mouseX, mouseY, x - sWH, y - 1, sW, 10 ) ).getInt()) );
        }

        drawString( fontRenderer, I18n.format( "gui.headcollection" ) + " (" + filteredSkulls.size() + ")", space + 7, 56, blandColor );

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

        drawString( fontRenderer, "From https://minecraft-heads.com API", space + 7, 50 + topbar + 168, blandColor );

        GlStateManager.pushMatrix();
        RenderHelper.enableGUIStandardItemLighting();
        GlStateManager.disableLighting();
        GlStateManager.enableRescaleNormal();
        GlStateManager.enableColorMaterial();
        GlStateManager.enableLighting();
        itemRenderer.zLevel = 100.0F;
        ItemStack hovered = null;
        if (filteredSkulls.size() > 0) {
            for (int i = (int) Math.min( filteredSkulls.size() - 1, currentPage * amountInPage ); i < (int) Math.min( filteredSkulls.size(), (currentPage + 1) * amountInPage ); i++) {
                int x = space + letterSpace + (16 * (i % maxInRow));
                int y = 50 + topbar + (16 * ((i % amountInPage) / maxInRow));
                itemRenderer.renderItemAndEffectIntoGUI( filteredSkulls.get( i ), x, y );
                if (mouseX > x && mouseX < x + 16 && mouseY > y && mouseY < y + 16) {
                    fill( x, y, x + 16, y + 16, GuiUtil.getColorFromRGB( 150, 150, 150, 150 ) );
                    hovered = filteredSkulls.get( i );
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


	public void loadSkulls() throws IOException {
		allSkulls.clear();
		URL url = new URL(API_URL + CATEGORIES[selCat]);
		InputStreamReader reader = new InputStreamReader(url.openStream());
		Gson gson = new Gson();
		MinecraftHead[] headArray = gson.fromJson(reader, MinecraftHead[].class);
		reader.close();
		for (MinecraftHead head : headArray) {
			try {
				String nbt = "{SkullOwner:{Id:\"" + head.getUuid() + "\",Properties:{textures:[{Value:\""
						+ head.getValue() + "\"}]}}}";
				DataItem skull = new DataItem(Items.PLAYER_HEAD, nbt);
				skull.getDisplayNameTag().set(new StringTextComponent(head.getName()));
				allSkulls.add(skull.getItemStack());
			} catch (CommandSyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		loaded = selCat;
		filteredString = null;
	}

	@Override
	public boolean isPauseScreen() {
		return false;
	}

}
