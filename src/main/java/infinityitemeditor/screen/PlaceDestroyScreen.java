package infinityitemeditor.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import infinityitemeditor.data.DataItem;
import infinityitemeditor.data.tag.TagItemID;
import infinityitemeditor.data.tag.TagList;
import infinityitemeditor.screen.widgets.ScrollableScissorWindow;
import infinityitemeditor.screen.widgets.StyledButton;
import infinityitemeditor.util.ColorUtils;
import net.minecraft.block.Block;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.registries.ForgeRegistries;

public class PlaceDestroyScreen extends ParentItemScreen {

    private TagList<TagItemID> canPlaceOnList;
    private ScrollableScissorWindow list;
    private ScrollableScissorWindow added;


    public PlaceDestroyScreen(Screen lastScreen, DataItem editing, String guiName, TagList<TagItemID> tagList) {
        super(new TranslationTextComponent("gui." + guiName), lastScreen, editing);
        canPlaceOnList = tagList;
    }

    @Override
    protected void init() {
        super.init();
        renderItem = false;
        int yStart = 40;
        int yEnd = (height / 4 * 3) - (yStart / 2) - 5;
        int containerWidth = width / 7 * 3 - 10;
        int spacing = 10;
        int xStart = (width - (containerWidth * 2 + spacing)) / 2;

        added = addButton(new ScrollableScissorWindow(xStart, yStart, containerWidth, yEnd, new TranslationTextComponent("gui.placedestroy.applied")));
        list = addButton(new ScrollableScissorWindow(xStart + spacing + containerWidth, yStart, containerWidth, yEnd, new TranslationTextComponent("gui.placedestroy.all")));
        outerLoop:
        for (Block block : ForgeRegistries.BLOCKS.getValues()) {
            if (block.asItem() != Items.AIR) {
                for (TagItemID id : canPlaceOnList) {
                    if (block.asItem() == id.getItem()) {
                        addCanPlaceOn(block, id, false);
                        continue outerLoop;
                    }
                }
                StyledButton button = addToAddedButton(block);
                list.getWidgets().add(button);
            }
        }
    }

    private StyledButton addToAddedButton(Block block) {
        StyledButton button = new StyledButton(0, 0, 50, 20, block.getName(), b -> {
            addCanPlaceOn(block, null, true);
            list.getWidgets().remove(b);
        });
        return button;
    }

    @Override
    public void mainRender(MatrixStack matrix, int mouseX, int mouseY, float p3, ColorUtils.Color color) {
        drawString(matrix, font, added.getMessage(), added.x, added.y - 10, color.getInt());
        drawString(matrix, font, list.getMessage(), list.x, list.y - 10, color.getInt());
        super.mainRender(matrix, mouseX, mouseY, p3, color);
    }

    private void addCanPlaceOn(Block block, TagItemID tagItemR, boolean shouldAdd) {
        TagItemID tagItem = new TagItemID(Item.BY_BLOCK.get(block));
        if (tagItemR != null) {
            tagItem = tagItemR;
        }
        TagItemID finalTagItem = tagItem; // final temp for lambda
        StyledButton button = new StyledButton(0, 0, 50, 20, block.getName(), t -> {
            added.getWidgets().remove(t);
            canPlaceOnList.remove(finalTagItem);
            list.getWidgets().add(addToAddedButton(block));
        });
        added.getWidgets().add(button);
        if (shouldAdd) canPlaceOnList.add(finalTagItem);
    }
}
