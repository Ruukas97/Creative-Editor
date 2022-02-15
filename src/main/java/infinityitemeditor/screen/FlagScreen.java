package infinityitemeditor.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import infinityitemeditor.data.DataItem;
import infinityitemeditor.screen.widgets.StyledBitToggle;
import infinityitemeditor.screen.widgets.StyledButton;
import infinityitemeditor.util.ColorUtils.Color;
import infinityitemeditor.util.HideFlagUtils;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.TranslatableComponent;

public class FlagScreen extends ParentItemScreen {

    public FlagScreen(Screen lastScreen, DataItem editing) {
        super(new TranslatableComponent("gui.itemflag"), lastScreen, editing);
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
                addRenderableWidget(new StyledButton(x, y, 120, 20, I18n.get("flag.switchall"), (Button b) -> {
                    for(int j = 0; j < amount-1; j++) {
                        item.getTag().getHideFlags().get()[j] = !item.getTag().getHideFlags().get()[j];
                        init();
                    }
                }));
                continue;
            }
            addRenderableWidget(new StyledBitToggle(x, y, 120, 20, I18n.get(HideFlagUtils.Flags.values()[i].getKey()), item.getTag().getHideFlags(), i));
        }

    }


    @Override
    public void overlayRender(PoseStack poseStack, int mouseX, int mouseY, float p3, Color color) {
        super.overlayRender(poseStack,mouseX, mouseY, p3, color);
    }

    @Override
    public void mainRender(PoseStack poseStack, int mouseX, int mouseY, float p3, Color color) {
        super.mainRender(poseStack,mouseX, mouseY, p3, color);
        poseStack.pushPose();
        poseStack.scale(0.9F, 0.9F, 0.9F);
        renderTooltip(poseStack,item.getItemStack(), 3, 68);
        poseStack.popPose();
    }


    @Override
    public void backRender(PoseStack poseStack, int mouseX, int mouseY, float p3, Color color) {
        super.backRender(poseStack,mouseX, mouseY, p3, color);
    }
}
