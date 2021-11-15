package infinityitemeditor.screen;

import com.google.gson.JsonSyntaxException;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import infinityitemeditor.data.DataItem;
import infinityitemeditor.data.NumberRangeInt;
import infinityitemeditor.screen.widgets.StyledButton;
import infinityitemeditor.screen.widgets.StyledTextField;
import infinityitemeditor.util.ColorUtils;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public class RawNBTEditorScreen extends ParentItemScreen {

    private StyledTextField nbtField;
    private StyledButton copyButton;
    private String nbtText = "";
    private int fieldY;
    private final int fieldHeight = 20;
    private String statusText = "";
    private final int bwidth = 50;
    private final int butHeight = 20;

    public RawNBTEditorScreen(Screen lastScreen, DataItem item) {
        super(new TranslationTextComponent("gui.rawnbt"), lastScreen, item);
    }

    @Override
    protected void init() {
        super.init();
        int fieldWidth = this.width / 6 * 4;
        fieldY = (this.height - fieldHeight) / 2;
        nbtField = new StyledTextField(font, (this.width - fieldWidth) / 2, fieldY, fieldWidth, fieldHeight, "nbt");
        nbtField.setMaxStringLength(9999);
        initFieldText();
        copyButton = new StyledButton((width - bwidth) / 2, nbtField.y + butHeight + 10, bwidth, butHeight, new TranslationTextComponent("gui.loreeditor.copy"), this::copy);
        renderWidgets.add(nbtField);
        renderWidgets.add(copyButton);
        minecraft.keyboardHandler.setSendRepeatsToGui(true);
    }

    private void copy(Button button) {
        minecraft.keyboardHandler.setClipboard(nbtField.getText());
    }

    private void initFieldText() {
        String tag = "{}";
        if (!nbtText.isEmpty()) {
            tag = nbtText;
        } else if (!item.getNBT().isEmpty()) {
            tag = item.getNBT().getAsString();
        }
        nbtField.setText(tag);
    }

    @Override
    public boolean keyReleased(int p_223281_1_, int p_223281_2_, int p_223281_3_) {
        tryUpdateDataItem();
        return super.keyReleased(p_223281_1_, p_223281_2_, p_223281_3_);
    }

    private void tryUpdateDataItem() {
        CompoundNBT nbt = canConvertStringToNBT(nbtField.getText());
        try {
            if (nbt != null) {
                item = new DataItem(nbt);
                NumberRangeInt c = item.getCount();
                if (c.get() > c.getMax()) c.set(c.getMax());
            }
        } catch(JsonSyntaxException ignored) {}
    }

    @Override
    public void mainRender(MatrixStack matrix, int mouseX, int mouseY, float p3, ColorUtils.Color color) {
        super.mainRender(matrix, mouseX, mouseY, p3, color);
        drawCenteredString(matrix, font, statusText, this.width / 2, fieldY - fieldHeight + 5, TextFormatting.RED.getColor());
    }

    @Override
    public void reset(Widget w) {
        nbtText = "";
        statusText = "";
        initFieldText();
    }

    private CompoundNBT canConvertStringToNBT(String s) {
        try {
            statusText = "";
            return JsonToNBT.parseTag(s);
        } catch (JsonSyntaxException | CommandSyntaxException e) {
            statusText = e.getMessage();
        }
        return null;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int mouseButton) {
        nbtText = nbtField.getText();
        return super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void tick() {
        nbtField.tick();
        super.tick();
    }

    @Override
    public void onClose() {
        minecraft.keyboardHandler.setSendRepeatsToGui(false);
        super.onClose();
    }
}
