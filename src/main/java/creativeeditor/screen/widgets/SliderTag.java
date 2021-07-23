package creativeeditor.screen.widgets;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.util.text.StringTextComponent;
import org.lwjgl.glfw.GLFW;

import creativeeditor.data.NumberRangeInt;
import creativeeditor.styles.IStyledSlider;
import creativeeditor.styles.StyleManager;
import creativeeditor.styles.StyleSpectrum;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class SliderTag extends Widget implements IStyledSlider<Integer> {
    private final NumberRangeInt range;

    public String display;
    public boolean drawString;


    public SliderTag(int x, int y, int width, int height, String display, NumberRangeInt range) {
        this(x, y, width, height, display, true, range);
    }


    public SliderTag(int x, int y, int width, int height, NumberRangeInt range) {
        this(x, y, width, height, "", true, range);
    }


    public SliderTag(int x, int y, int width, int height, String display, boolean drawString, NumberRangeInt range) {
        super(x, y, width, height, new StringTextComponent(display));
        this.display = display;
        this.drawString = drawString;
        this.range = range;

        setMessage(new StringTextComponent(drawString ? display + range.get() : ""));
    }


    @Override
    public int getYImage(boolean b) {
        return 0;
    }


    @Override
    public void renderButton(MatrixStack matrix, int mouseX, int mouseY, float p3) {
        updateSlider();
        StyleManager.getCurrentStyle().renderButton(matrix, this, mouseX, mouseY, p3);
    }


    public void renderBg(Minecraft mc, int mouseX, int mouseY) {
        if (!this.visible)
            return;

        StyleManager.getCurrentStyle().renderSlider(null, this, mouseX, mouseY);
    }


    private void setValueFromMouse(double mouseX) {
        double updateValue;
        if (StyleManager.getCurrentStyle() instanceof StyleSpectrum)
            updateValue = (mouseX - (x + 1d)) / (width - 2.5d);
        else
            updateValue = (mouseX - (x + 4)) / (double) (width - 8);
        int round = (int) Math.round(updateValue * (range.getMax() - range.getMin()) + range.getMin());
        setValue(round);
    }


    @Override
    public void onClick(double mouseX, double mouseY) {
        setValueFromMouse(mouseX);
    }


    @Override
    public boolean keyPressed(int key1, int key2, int key3) {
        if (key1 == GLFW.GLFW_KEY_LEFT && getValue() > getMin()) {
            range.set(getValue() - 1);
            updateSlider();
            return true;
        }

        if (key1 == GLFW.GLFW_KEY_RIGHT && getValue() < getMax()) {
            range.set(getValue() + 1);
            updateSlider();
            return true;
        }

        return false;
    }


    public void setValue(int value) {
        int old = getValue();
        range.set(value);
        if (getValue() != old) {
            updateSlider();
        }
    }


    public void updateSlider() {
        setMessage(new StringTextComponent(drawString ? display + getValue() : ""));
    }


    @Override
    protected void onDrag(double mouseX, double p_onDrag_3_, double p_onDrag_5_, double p_onDrag_7_) {
        setValueFromMouse(mouseX);
        super.onDrag(mouseX, p_onDrag_3_, p_onDrag_5_, p_onDrag_7_);
    }


    @Override
    public void playDownSound(SoundHandler soundHandler) {
    }


    @Override
    public void onRelease(double mouseX, double mouseY) {
        super.playDownSound(Minecraft.getInstance().getSoundManager());
    }


    @Override
    public int getFGColor() {
        return StyleManager.getCurrentStyle().getFGColor(this).getInt();
    }


    @Override
    public Widget getWidget() {
        return this;
    }


    @Override
    public void setHovered(boolean b) {
        isHovered = b;
    }


    @Override
    public Integer getValue() {
        return range.get();
    }


    @Override
    public Integer getMin() {
        return range.getMin();
    }


    @Override
    public Integer getMax() {
        return range.getMax();
    }
}
