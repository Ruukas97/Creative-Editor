package infinityitemeditor.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import infinityitemeditor.collections.ItemCollection;
import infinityitemeditor.collections.ItemCollections;
import infinityitemeditor.styles.StyleManager;
import infinityitemeditor.util.ColorUtils.Color;
import infinityitemeditor.util.GuiUtil;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.Date;

public class ItemSpawnerScreen extends ParentScreen {
    private static final Inventory TMP_INVENTORY = new Inventory(45);
    private static final int selectedTabIndex = ItemGroup.TAB_BUILDING_BLOCKS.getTabPage();
    private float currentScroll;
    private boolean isScrolling;
    private TextFieldWidget searchField;
    private static ItemCollection collection = ItemCollections.INSTANCE.getCollections().get(0);
    public final NonNullList<ItemStack> itemList = NonNullList.create();
    private long lastTime = new Date().getTime();
    float animation = 0f;
    // Reload button
    // Online/File/In code


    public ItemSpawnerScreen(Screen lastScreen) {
        super(new TranslationTextComponent("gui.itemspawner"), lastScreen);
    }


    @Override
    protected void init() {
        super.init();
    }


    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        if (mouseButton == 0) {
            int i = 0;
            int x = 15;

            for (ItemCollection collection : ItemCollections.INSTANCE) {
                String name = collection.getName();
                int width = font.width(name);
                int y = 30 + 20 * i;

                if (GuiUtil.isMouseInRegion(mouseX, mouseY, x, y, width + 20, 16)) {
                    ItemSpawnerScreen.collection = collection;
                    animation = 0f;
                    return true;
                }

                i++;
            }
        }

        return super.mouseClicked(mouseX, mouseY, mouseButton);
    }


    @Override
    public void backRender(MatrixStack matrix, int mouseX, int mouseY, float p3, Color color) {
        setTopLineWidth(width - 30);
        super.backRender(matrix, mouseX, mouseY, p3, color);
    }


    @Override
    public void mainRender(MatrixStack matrix, int mouseX, int mouseY, float p3, Color color) {
        super.mainRender(matrix, mouseX, mouseY, p3, color);
    }


    @Override
    public void overlayRender(MatrixStack matrix, int mouseX, int mouseY, float p3, Color color) {
        super.overlayRender(matrix, mouseX, mouseY, p3, color);

        itemList.clear();
        collection.fill(itemList);

        int i = 0;
        int widest = 0;
        int x = 10;
        int y = 0;
        for (ItemCollection collection : ItemCollections.INSTANCE) {
            String name = collection.getName();
            int width = font.width(name);
            y = 30 + 20 * i;
            double itemYOffset = 0;

            boolean in = GuiUtil.isMouseInRegion(mouseX, mouseY, x, y, width + 20, 16);
            boolean selected = ItemSpawnerScreen.collection == collection;
            if (selected) {
                long now = new Date().getTime();
                if (now - lastTime > 18) {
                    animation += 0.1f;
                    lastTime = now;
                }
                itemYOffset = -Math.sin(animation) / 1.8;
                RenderSystem.translated(0d, itemYOffset, 0d);
            }

            ItemStack stack = collection.getIcon();
            drawItemStack(stack, (int) x, (int) y, 0f, 0, null);


            drawString(matrix, font, name, x + 20, y + 5, in || selected ? StyleManager.getCurrentStyle().getFGColor(true, true).getInt() : color.getInt());
            if (selected) {
                RenderSystem.translated(0d, -itemYOffset, 0d);
            }

            if (widest < width) {
                widest = width;
            }
            i++;
        }
        widest += 16;

        fill(matrix, 25 + widest, 31, 26 + widest, height - 15, color.getInt());
        int rightWidth = 150;
        fill(matrix, width - 6 - rightWidth, 31, width - 5 - rightWidth, height - 15, color.getInt());

        i = 0;
        int offsetY = 0;
        fill(matrix, width - rightWidth + 5, 39, width - 15, 40, color.getInt());
        offsetY += 20;
        drawCenteredString(matrix, font, "Filters", width - rightWidth / 2 - 5, 28 + 20 * i, color.getInt());
        drawString(matrix, font, "Item ID", width - rightWidth + 5, offsetY + 30 + 20 * i++, color.getInt());
        drawString(matrix, font, "Name", width - rightWidth + 5, offsetY + 30 + 20 * i++, color.getInt());
        drawString(matrix, font, "Tooltip", width - rightWidth + 5, offsetY + 30 + 20 * i++, color.getInt());
        fill(matrix, width - rightWidth + 5, offsetY + 45 + 20 * (i - 1), width - 15, offsetY + 46 + 20 * (i - 1), color.getInt());
        drawCenteredString(matrix, font, "Item Filters", width - rightWidth / 2 - 5, offsetY + 50 + 20 * (i - 1), color.getInt());
        fill(matrix, width - rightWidth + 5, offsetY + 62 + 20 * (i - 1), width - 15, offsetY + 63 + 20 * (i - 1), color.getInt());
        offsetY += 20;
        drawString(matrix, font, "Enchantable", width - rightWidth + 5, offsetY + 30 + 20 * i++, color.getInt());
        drawString(matrix, font, "Armor", width - rightWidth + 5, offsetY + 30 + 20 * i++, color.getInt());
        fill(matrix, width - rightWidth + 5, offsetY + 45 + 20 * (i - 1), width - 15, offsetY + 46 + 20 * (i - 1), color.getInt());
        drawCenteredString(matrix, font, "Block Filters", width - rightWidth / 2 - 5, offsetY + 50 + 20 * (i - 1), color.getInt());
        fill(matrix, width - rightWidth + 5, offsetY + 62 + 20 * (i - 1), width - 15, offsetY + 63 + 20 * (i - 1), color.getInt());
        offsetY += 20;
        drawString(matrix, font, "Block", width - rightWidth + 5, offsetY + 30 + 20 * i++, color.getInt());
        drawString(matrix, font, "Light Level", width - rightWidth + 5, offsetY + 30 + 20 * i++, color.getInt());
        drawString(matrix, font, "Hardness", width - rightWidth + 5, offsetY + 30 + 20 * i++, color.getInt());
        drawString(matrix, font, "TileEntity", width - rightWidth + 5, offsetY + 30 + 20 * i++, color.getInt());


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
            drawItemStack(stack, x, y, 1f, 0, null);
            i++;
        }
    }

}
