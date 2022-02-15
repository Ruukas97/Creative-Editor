package infinityitemeditor.screen;

import com.mojang.blaze3d.vertex.PoseStack;
import infinityitemeditor.data.DataItem;
import infinityitemeditor.screen.widgets.StyledButton;
import infinityitemeditor.screen.widgets.StyledTextField;
import infinityitemeditor.util.ColorUtils;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.TranslatableComponent;

public class TagExplorerScreen extends ParentScreen {

    private DataItem item;
    private StyledTextField textField;
    private StyledButton button;
    private final int fieldWidth = 200;
    private final int fieldHeight = 20;
    int yField;

    public TagExplorerScreen(Screen lastScreen, DataItem item) {
        super(new TranslatableComponent("gui.tagexplorer"), lastScreen);
        this.item = item;
    }

    @Override
    protected void init() {
        super.init();
        yField = height / 2 - fieldHeight / 2;
        textField = addWidget(new StyledTextField(minecraft.font, width / 2 - fieldWidth / 2, yField, fieldWidth, fieldHeight, ""));
        textField.setText("{Damage:%d}");
        int buttonWidth = 100;
        button = addRenderableWidget(new StyledButton(width / 2 - buttonWidth / 2, yField + (fieldHeight * 2), buttonWidth, fieldHeight, I18n.get("gui.tagexplorer.explore"), t -> minecraft.setScreen(null)));

    }

    @Override
    public void mainRender(PoseStack poseStack, int mouseX, int mouseY, float p3, ColorUtils.Color color) {
        super.mainRender(poseStack,mouseX, mouseY, p3, color);
        textField.render(poseStack,mouseY, mouseY, p3);
        drawCenteredString(poseStack,font, I18n.get("gui.tagexplorer.help"), width / 2, yField - fieldHeight, color.getInt());
    }

    @Override
    public boolean keyPressed(int key1, int key2, int key3) {
        textField.keyPressed(key1, key2, key3);
        return super.keyPressed(key1, key2, key3);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        textField.mouseClicked(mouseX, mouseY, mouseButton);
        return super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public boolean charTyped(char p_231042_1_, int p_231042_2_) {
        textField.charTyped(p_231042_1_, p_231042_2_);
        return super.charTyped(p_231042_1_, p_231042_2_);
    }

    @Override
    public void tick() {
        textField.tick();
        super.tick();
    }
}
