package infinityitemeditor.screen.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import infinityitemeditor.data.DataItem;
import infinityitemeditor.screen.ParentItemScreen;
import infinityitemeditor.screen.widgets.StyledDataTextField;
import infinityitemeditor.util.ColorUtils;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TranslatableComponent;

public class GenericBlockScreen extends ParentItemScreen {

    private StyledDataTextField lockField;


    public GenericBlockScreen(Screen lastScreen, DataItem editing) {
        super(new TranslatableComponent("gui.genericblock"), lastScreen, editing);
    }


    @Override
    protected void init() {
        super.init();
        int fieldWidth = this.width / 3;
        int fieldHeight = 20;
        int fieldY = (this.height - fieldHeight) / 2;
        lockField = new StyledDataTextField(font, (this.width - fieldWidth) / 2, fieldY, fieldWidth, fieldHeight, item.getTag().getBlockEntityTag().getLocked());
        lockField.setMaxStringLength(9999);
        renderWidgets.add(lockField);
    }

    @Override
    public void mainRender(PoseStack poseStack, int mouseX, int mouseY, float p3, ColorUtils.Color color) {
        super.mainRender(poseStack,mouseX, mouseY, p3, color);
        drawCenteredString(poseStack,font, new TranslatableComponent("block.tag.lock"), width / 2, this.height / 2 - 30, color.getInt());
    }
}
