package infinityitemeditor.data.tag;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import infinityitemeditor.data.base.SingularData;
import infinityitemeditor.render.NBTIcons;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.nbt.IntNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

import java.awt.*;

public class TagEnum<E extends Enum<E>> extends SingularData<E, IntNBT> implements Button.IPressable {
    @Getter
    private final E[] options;

    public TagEnum(Class<E> clazz, E value) {
        super(value);
        options = clazz.getEnumConstants();
        if (this.data == null) {
            this.data = options[0];
        }
    }

    public void next() {
        set(options[(data.ordinal() + 1) % options.length]);
    }

    public void previous() {
        set(options[(data.ordinal() - 1) % options.length]);
    }

    @Override
    public boolean isDefault() {
        return data == null;
    }

    @Override
    public IntNBT getNBT() {
        return IntNBT.valueOf(data.ordinal());
    }

    @Override
    public ITextComponent getPrettyDisplay(String space, int indentation) {
        return getName().withStyle(SYNTAX_HIGHLIGHTING_NUMBER);
    }

    @Override
    public void onPress(Button button) {
        next();
        button.setMessage(getName());
    }

    public TextComponent getName() {
        return new TranslationTextComponent("enum." + (data.getDeclaringClass().getSimpleName() + '.' + data.name()).toLowerCase());
    }

    public TextComponent getKeyValue() {
        return (TextComponent) new TranslationTextComponent("enum." + data.getDeclaringClass().getSimpleName().toLowerCase() + "._", getPrettyDisplay("", 0)).withStyle(TextFormatting.AQUA);
    }

    @Override
    public void renderIcon(Minecraft mc, MatrixStack matrix, int x, int y) {
        Color color = Color.getHSBColor((float) options.length / data.ordinal(), 1f, 1f);
        RenderSystem.color3f(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f);
        NBTIcons.EMPTY.renderIcon(mc, matrix, x, y);
        RenderSystem.color3f(1f, 1f, 1f);
    }
}
