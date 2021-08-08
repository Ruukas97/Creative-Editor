package creativeeditor.screen.widgets;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;

public class ColorHelperWidget extends Widget {

    private ArrayList<ColorButton> children = new ArrayList<>();
    private ColorButton[] colorButtons;

    public ColorHelperWidget() {
        super(20, 40, 200, 80, StringTextComponent.EMPTY);
        TextFormatting[] formats = TextFormatting.values();
        int colorAmount = 2 + formats.length;
        colorButtons = new ColorButton[colorAmount];
        colorButtons[0] = new ColorButton(width - 1 - 13 * ((colorAmount + 2) / 2) + (13), height - 30, 13, 15, formats[0].toString().substring(0, 1));
        colorButtons[1] = new ColorButton(width - 1 - 13 * ((colorAmount + 2) / 2) + (13 * 2), height - 30, 13, 15, TextFormatting.DARK_RED + "%");
        for (int i = 2; i < colorAmount; i++) {
            TextFormatting f = formats[i - 2];
            colorButtons[i] = new ColorButton(width - 1 - 13 * ((colorAmount + 2) / 2) + (13 * ((i % (colorAmount / 2)) + 1)), height - 30 + (15 * (i / (colorAmount / 2))), 13, 15, f.toString() + f.toString().substring(1));
        }

        for(int i = 0; i < colorAmount; i++) {
            children.add(colorButtons[i]);
        }
    }


    @Override
    public void renderButton(MatrixStack p_230431_1_, int p_230431_2_, int p_230431_3_, float p_230431_4_) {
        children.forEach(t -> t.render(p_230431_1_, p_230431_2_, p_230431_3_, p_230431_4_));
    }


    // Cancelling events to prevent textfield losing focus

    @Override
    protected boolean isValidClickButton(int p_230987_1_) {
        return false;
    }

    @Override
    public boolean mouseClicked(double p_231044_1_, double p_231044_3_, int p_231044_5_) {
        children.forEach(t -> t.mouseClicked(p_231044_1_, p_231044_3_, p_231044_5_));
        return false;
    }

    @Override
    public boolean changeFocus(boolean p_231049_1_) {
        return false;
    }
}
