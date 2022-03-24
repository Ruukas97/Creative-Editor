package infinityitemeditor.screen.widgets;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ColorHelperWidget extends Widget {

    private ArrayList<ColorButton> children = new ArrayList<>();
    private ColorButton[] colorButtons;
    private final int xOffset = 8;
    private final int yOffset = 40;
    private int butWidth;
    private int butHeight;
    public int x;
    public int y;


    public ColorHelperWidget(List<IGuiEventListener> children, int wWidth, int wHeight, int width, int height) {
        super(0, 0, wWidth, wHeight, StringTextComponent.EMPTY);
        TextFormatting[] formats = TextFormatting.values();
        int colorAmount = 2 + formats.length;
        butWidth = wWidth / 12;
        butHeight = wHeight / 2;

        colorButtons = new ColorButton[colorAmount];
        x = width - xOffset - butWidth * ((colorAmount + 2) / 2) + (butWidth);
        y = height - yOffset;
        colorButtons[0] = new ColorButton(children, x, y, butWidth, butHeight, TextFormatting.DARK_RED + "%", "", true);
        colorButtons[1] = new ColorButton(children, width - xOffset - butWidth * ((colorAmount + 2) / 2) + (butWidth * 2), height - yOffset, butWidth, butHeight, "&", "\u00a7r");
        for (int i = 2; i < colorAmount; i++) {
            TextFormatting f = formats[i - 2];
            int x = width - xOffset - butWidth * ((colorAmount + 2) / 2) + (butWidth * ((i % (colorAmount / 2)) + 1));
            int y = height - yOffset + (15 * (i / (colorAmount / 2)));
            String s = f + f.toString().substring(1);
            colorButtons[i] = new ColorButton(children, x, y, butWidth, butHeight, s, f.toString());
        }
        this.children.addAll(Arrays.asList(colorButtons));
    }


    @Override
    public int getWidth() {
        return butWidth * 12;
    }

    @Override
    public int getHeight() {
        return butHeight * 2;
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
