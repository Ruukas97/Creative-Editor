package creativeeditor.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import creativeeditor.data.DataItem;
import creativeeditor.screen.widgets.StyledBitToggle;
import creativeeditor.screen.widgets.StyledButton;
import creativeeditor.util.ColorUtils.Color;
import creativeeditor.util.HideFlagUtils;
import creativeeditor.util.ItemRendererUtils;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TranslationTextComponent;

public class FlagScreen extends ParentItemScreen {

    public FlagScreen(Screen lastScreen, DataItem editing) {
        super(new TranslationTextComponent("gui.itemflag"), lastScreen, editing);
    }


    @Override
    protected void init() {
        super.init();
        // render Item
        setRenderItem(true, 1.5f);

        hasEssButtons = true;

        int thirdWidth = width / 4;
        int amount = HideFlagUtils.Flags.values().length + 1;
        for (int i = 0; i < amount; i++) {
            int x = (i < amount / 2 ? 2 * thirdWidth : 3 * thirdWidth + 20) - 80; // 1/3 of width if i < 3, other 2/3 of width
            int y = height / 7 * 2 + (30 * (i < amount / 2 ? i : i - amount / 2)); // i*30, or (i-3)*30 if i>=3
            if(i >= amount-1) {
                addButton(new StyledButton(x, y, 120, 20, I18n.get("flag.switchall"), (Button b) -> {
                    for(int j = 0; j < amount-1; j++) {
                        item.getTag().getHideFlags().get()[j] = !item.getTag().getHideFlags().get()[j];
                        init();
                    }
                }));
                continue;
            }
            addButton(new StyledBitToggle(x, y, 120, 20, I18n.get(HideFlagUtils.Flags.values()[i].getKey()), item.getTag().getHideFlags(), i));
        }

    }


    @Override
    public void overlayRender(MatrixStack matrix, int mouseX, int mouseY, float p3, Color color) {
        super.overlayRender(matrix, mouseX, mouseY, p3, color);
    }

    @Override
    public void mainRender(MatrixStack matrix, int mouseX, int mouseY, float p3, Color color) {
        super.mainRender(matrix, mouseX, mouseY, p3, color);
        matrix.pushPose();
        matrix.scale(0.9F, 0.9F, 0.9F);
        ItemRendererUtils.renderFormattedItemNBT(matrix, item, 10, 50, width / 3 - 1, height, -1, font );
        matrix.popPose();
    }


    @Override
    public void backRender(MatrixStack matrix, int mouseX, int mouseY, float p3, Color color) {
        super.backRender(matrix, mouseX, mouseY, p3, color);
    }
}
