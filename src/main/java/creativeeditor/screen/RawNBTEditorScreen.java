package creativeeditor.screen;

import com.google.gson.JsonSyntaxException;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import creativeeditor.data.DataItem;
import creativeeditor.data.NumberRangeInt;
import creativeeditor.screen.widgets.StyledTextField;
import creativeeditor.util.ColorUtils;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.tileentity.HopperTileEntity;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public class RawNBTEditorScreen extends ParentItemScreen {

    private StyledTextField nbtField;
    private String nbtText = "";
    private int fieldY;
    private int fieldHeight;
    private String statusText = "";

    public RawNBTEditorScreen(Screen lastScreen, DataItem item) {
        super(new TranslationTextComponent("gui.rawnbt"), lastScreen, item);
    }

    @Override
    protected void init() {
        super.init();
        int fieldWidth = this.width / 6 * 4;
        fieldHeight = 20;
        fieldY = (this.height - fieldHeight) / 2;
        nbtField = new StyledTextField(font, (this.width - fieldWidth) / 2, fieldY, fieldWidth, fieldHeight, "nbt");
        nbtField.setMaxStringLength(9999);
        initFieldText();
        renderWidgets.add(nbtField);

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
        CompoundNBT nbt = canApplyNBT();
        if(nbt != null) {
            item = new DataItem(nbt);
            NumberRangeInt c = item.getCount();
            if(c.get() > c.getMax()) c.set(c.getMax());
        }
        return super.keyReleased(p_223281_1_, p_223281_2_, p_223281_3_);
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

    private CompoundNBT canApplyNBT() {
        try {
            statusText = "";
            return JsonToNBT.parseTag(nbtField.getText());
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


}
