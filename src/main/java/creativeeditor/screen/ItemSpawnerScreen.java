package creativeeditor.screen;

import java.util.Date;

import com.mojang.blaze3d.systems.RenderSystem;

import creativeeditor.collections.ItemCollection;
import creativeeditor.collections.ItemCollections;
import creativeeditor.styles.StyleManager;
import creativeeditor.util.ColorUtils.Color;
import creativeeditor.util.GuiUtil;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TranslationTextComponent;

public class ItemSpawnerScreen extends ParentScreen {
    private static final Inventory TMP_INVENTORY = new Inventory( 45 );
    private static int selectedTabIndex = ItemGroup.BUILDING_BLOCKS.getIndex();
    private float currentScroll;
    private boolean isScrolling;
    private TextFieldWidget searchField;
    private static ItemCollection collection = ItemCollections.INSTANCE.getCollections().get( 0 );
    public final NonNullList<ItemStack> itemList = NonNullList.create();
    private long lastTime = new Date().getTime();
    float animation = 0f;
    // Reload button
    // Online/File/In code


    public ItemSpawnerScreen(Screen lastScreen) {
        super( new TranslationTextComponent( "gui.itemspawner" ), lastScreen );
    }


    @Override
    protected void init() {
        super.init();
    }


    @Override
    public boolean mouseClicked( double mouseX, double mouseY, int mouseButton ) {
        if (mouseButton == 0) {
            int i = 0;
            int x = 15;

            for (ItemCollection collection : ItemCollections.INSTANCE) {
                String name = collection.getName();
                int width = font.getStringWidth( name );
                int y = 30 + 20 * i;

                if (GuiUtil.isMouseInRegion( mouseX, mouseY, x, y, width + 20, 16 )) {
                    ItemSpawnerScreen.collection = collection;
                    animation = 0f;
                    return true;
                }

                i++;
            }
        }

        return super.mouseClicked( mouseX, mouseY, mouseButton );
    }


    @Override
    public void backRender( int mouseX, int mouseY, float p3, Color color ) {
        setTopLineWidth( width - 30 );
        super.backRender( mouseX, mouseY, p3, color );
    }


    @Override
    public void mainRender( int mouseX, int mouseY, float p3, Color color ) {
        super.mainRender( mouseX, mouseY, p3, color );
    }


    @Override
    public void overlayRender( int mouseX, int mouseY, float p3, Color color ) {
        super.overlayRender( mouseX, mouseY, p3, color );

        itemList.clear();
        collection.fill( itemList );

        int i = 0;
        int widest = 0;
        int x = 10;
        int y = 0;
        for (ItemCollection collection : ItemCollections.INSTANCE) {
            String name = collection.getName();
            int width = font.getStringWidth( name );
            y = 30 + 20 * i;
            double itemYOffset = 0;

            boolean in = GuiUtil.isMouseInRegion( mouseX, mouseY, x, y, width + 20, 16 );
            boolean selected = ItemSpawnerScreen.collection == collection;
            if (selected) {
                long now = new Date().getTime();
                if (now - lastTime > 18) {
                    animation += 0.1f;
                    lastTime = now;
                }
                itemYOffset = -Math.sin( animation ) / 1.8;
                RenderSystem.translated( 0d, itemYOffset, 0d );
            }

            ItemStack stack = collection.getIcon();
            drawItemStack( stack, x, y, 0, 0, null );


            drawString( font, name, x + 20, y + 5, in || selected ? StyleManager.getCurrentStyle().getFGColor( true, true ).getInt() : color.getInt() );
            if (selected) {
                RenderSystem.translated( 0d, -itemYOffset, 0d );
            }

            if (widest < width) {
                widest = width;
            }
            i++;
        }
        widest += 16;

        fill( 25 + widest, 31, 26 + widest, height - 15, color.getInt() );
        int rightWidth = 150;
        fill( width - 6 - rightWidth, 31, width - 5 - rightWidth, height - 15, color.getInt() );

        i = 0;
        int offsetY = 0;
        fill( width - rightWidth + 5, 39, width - 15, 40, color.getInt() );
        offsetY += 20;
        drawCenteredString( font, "Filters", width - rightWidth / 2 - 5, 28 + 20 * i, color.getInt() );
        drawString( font, "Item ID", width - rightWidth + 5, offsetY + 30 + 20 * i++, color.getInt() );
        drawString( font, "Name", width - rightWidth + 5, offsetY + 30 + 20 * i++, color.getInt() );
        drawString( font, "Tooltip", width - rightWidth + 5, offsetY + 30 + 20 * i++, color.getInt() );
        fill( width - rightWidth + 5, offsetY + 45 + 20 * (i - 1), width - 15, offsetY + 46 + 20 * (i - 1), color.getInt() );
        drawCenteredString( font, "Item Filters", width - rightWidth / 2 - 5, offsetY + 50 + 20 * (i - 1), color.getInt() );
        fill( width - rightWidth + 5, offsetY + 62 + 20 * (i - 1), width - 15, offsetY + 63 + 20 * (i - 1), color.getInt() );
        offsetY += 20;
        drawString( font, "Enchantable", width - rightWidth + 5, offsetY + 30 + 20 * i++, color.getInt() );
        drawString( font, "Armor", width - rightWidth + 5, offsetY + 30 + 20 * i++, color.getInt() );
        fill( width - rightWidth + 5, offsetY + 45 + 20 * (i - 1), width - 15, offsetY + 46 + 20 * (i - 1), color.getInt() );
        drawCenteredString( font, "Block Filters", width - rightWidth / 2 - 5, offsetY + 50 + 20 * (i - 1), color.getInt() );
        fill( width - rightWidth + 5, offsetY + 62 + 20 * (i - 1), width - 15, offsetY + 63 + 20 * (i - 1), color.getInt() );
        offsetY += 20;
        drawString( font, "Block", width - rightWidth + 5, offsetY + 30 + 20 * i++, color.getInt() );
        drawString( font, "Light Level", width - rightWidth + 5, offsetY + 30 + 20 * i++, color.getInt() );
        drawString( font, "Hardness", width - rightWidth + 5, offsetY + 30 + 20 * i++, color.getInt() );
        drawString( font, "TileEntity", width - rightWidth + 5, offsetY + 30 + 20 * i++, color.getInt() );


        i = 0;
        int columns = (width - 40 - widest - rightWidth) / 20;
        int rows = (height - 50) / 20;
        for (ItemStack stack : itemList) {
            int column = i % columns;
            int row = i / columns;
            if (row >= rows) {
                break;
            }

            x = 30 + widest + 20 * column;
            y = 25 + 20 * row;
            drawItemStack( stack, x, y, 0, 0, null );
            i++;
        }
    }

}
