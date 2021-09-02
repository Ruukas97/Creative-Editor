package creativeeditor.screen.widgets;

import com.mojang.blaze3d.matrix.MatrixStack;
import creativeeditor.styles.IStyledSlider;
import creativeeditor.styles.StyleManager;
import creativeeditor.styles.StyleSpectrum;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.glfw.GLFW;

import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class StyledSlider extends Widget implements IStyledSlider<Integer> {
    public int value;

    public String display;
    public boolean drawString;

    public int min;
    public int max;

    @Nullable
    public SliderHandler handler = null;


    protected StyledSlider(int x, int y, int width, int height, String display, int value, int min, int max) {
        this(x, y, width, height, display, true, value, min, max, null);
    }


    protected StyledSlider(int x, int y, int width, int height, int value, int min, int max) {
        this(x, y, width, height, "", false, value, min, max, null);
    }


    protected StyledSlider(int x, int y, int width, int height, String display, boolean drawString, int value, int min, int max, SliderHandler handler) {
        super(x, y, width, height, new StringTextComponent(display));
        this.value = value;
        this.display = display;
        this.drawString = drawString;
        this.min = min;
        this.max = max;
        this.handler = handler;

        setMessage(new StringTextComponent(drawString ? display + value : ""));
    }


    @Override
    public int getYImage(boolean par1) {
        return 0;
    }


    @Override
    public void renderButton(MatrixStack matrix, int mouseX, int mouseY, float p3) {
        StyleManager.getCurrentStyle().renderButton(matrix, this, mouseX, mouseY, p3);
        super.renderButton(matrix, mouseX, mouseY, p3);
    }


    @Override
    public void renderBg(MatrixStack matrix, Minecraft mc, int mouseX, int mouseY) {
        if (!this.visible)
            return;

        StyleManager.getCurrentStyle().renderSlider(matrix, this, mouseX, mouseY);
    }


    private void setValueFromMouse(double mouseX) {
        double updateValue;
        if (StyleManager.getCurrentStyle() instanceof StyleSpectrum)
            updateValue = (mouseX - (x + 1d)) / (width - 2.5d);
        else
            updateValue = (mouseX - (x + 4)) / (double) (width - 8);
        setValue((int) Math.round(updateValue * (max - min) + min));
    }


    @Override
    public void onClick(double mouseX, double mouseY) {
        setValueFromMouse(mouseX);
    }


    @Override
    public boolean keyPressed(int key1, int key2, int key3) {
        if (key1 == GLFW.GLFW_KEY_LEFT && value > min) {
            value--;
            updateSlider();
            return true;
        }

        if (key1 == GLFW.GLFW_KEY_RIGHT && value < max) {
            value++;
            updateSlider();
            return true;
        }

        return false;
    }


    public void setValue(int value) {
        int clamped = MathHelper.clamp(value, min, max);
        if (this.value != clamped) {
            this.value = clamped;
            updateSlider();
        }
    }


    public void updateSlider() {
        setMessage(new StringTextComponent(drawString ? display + value : ""));
        if (handler != null)
            handler.onSlideValue(this);
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
    public Integer getValue() {
        return value;
    }


    @Override
    public Integer getMin() {
        return min;
    }


    @Override
    public Integer getMax() {
        return max;
    }


    public interface SliderHandler {
        void onSlideValue(StyledSlider slider);
    }


    @Override
    public void setHovered(boolean b) {
        isHovered = b;
    }

    @Override
    public void renderBackground(MatrixStack matrix, Minecraft mc, int mouseX, int mouseY) {
        super.renderBg(matrix, mc, mouseX, mouseY);
    }
}
