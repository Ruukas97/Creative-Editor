package creativeeditor.screen;

import com.google.gson.JsonSyntaxException;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import creativeeditor.data.DataItem;
import creativeeditor.screen.widgets.StyledButton;
import creativeeditor.screen.widgets.StyledTextField;
import creativeeditor.util.ColorUtils;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.resources.I18n;
import net.minecraft.nbt.JsonToNBT;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public class RawNBTEditorScreen extends ParentItemScreen {

    private StyledTextField nbtField;
    private StyledButton applyButton;
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

        int butWidth = 80;
        applyButton = addButton(new StyledButton((this.width - butWidth) / 2, fieldY + fieldHeight + 10, butWidth, 20, I18n.get("gui.rawnbt.apply"), t -> applyNBT()));
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
    public void mainRender(MatrixStack matrix, int mouseX, int mouseY, float p3, ColorUtils.Color color) {
        super.mainRender(matrix, mouseX, mouseY, p3, color);
        drawCenteredString(matrix, font, statusText, this.width / 2, fieldY - fieldHeight + 5, TextFormatting.RED.getColor());
    }

    @Override
    public void reset(Widget w) {
        nbtText = "";
        initFieldText();
    }

    private void applyNBT() {
        try {
            item = new DataItem(JsonToNBT.parseTag(nbtField.getText()));
            statusText = "";
            System.out.println(item.getItemStack().getCount());
        } catch (JsonSyntaxException | CommandSyntaxException e) {
            statusText = e.getMessage();
            e.printStackTrace();
        }
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
