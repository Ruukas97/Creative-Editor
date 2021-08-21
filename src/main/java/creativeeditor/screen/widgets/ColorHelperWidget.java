package creativeeditor.screen.widgets;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.List;

public class ColorHelperWidget extends Widget {

    private ArrayList<ColorButton> children = new ArrayList<>();
    private ColorButton[] colorButtons;

    public ColorHelperWidget(List<IGuiEventListener> children, int wWidth, int wHeight, int width, int height) {
        super(0, 0, wWidth, wHeight, StringTextComponent.EMPTY);
        TextFormatting[] formats = TextFormatting.values();
        int colorAmount = 2 + formats.length;
        int butWidth = wWidth / 12;
        int butHeight = wHeight / 2;

        colorButtons = new ColorButton[colorAmount];
        colorButtons[0] = new ColorButton(children, width - 1 - 13 * ((colorAmount + 2) / 2) + (13), height - 30, butWidth, butHeight, TextFormatting.DARK_RED + "%", "", true);
        colorButtons[1] = new ColorButton(children, width - 1 - 13 * ((colorAmount + 2) / 2) + (13 * 2), height - 30, butWidth, butHeight, "&", "\u00a7r");
        for (int i = 2; i < colorAmount; i++) {
            TextFormatting f = formats[i - 2];
            colorButtons[i] = new ColorButton(children, width - 1 - 13 * ((colorAmount + 2) / 2) + (13 * ((i % (colorAmount / 2)) + 1)), height - 30 + (15 * (i / (colorAmount / 2))), butWidth, butHeight, f.toString() + f.toString().substring(1), f.toString());
        }

        for (int i = 0; i < colorAmount; i++) {
            this.children.add(colorButtons[i]);
        }
    }


    @Override
    public void renderButton(MatrixStack p_230431_1_, int p_230431_2_, int p_230431_3_, float p_230431_4_) {
        children.forEach(t -> t.render(p_230431_1_, p_230431_2_, p_230431_3_, p_230431_4_));
    }


    @Override
    public boolean mouseClicked(double p_231044_1_, double p_231044_3_, int p_231044_5_) {
        children.forEach(t -> t.mouseClicked(p_231044_1_, p_231044_3_, p_231044_5_));
        return false;
    }
}
