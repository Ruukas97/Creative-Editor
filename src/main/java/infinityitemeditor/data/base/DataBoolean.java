package infinityitemeditor.data.base;

import com.mojang.blaze3d.matrix.MatrixStack;
import infinityitemeditor.render.NBTIcons;
import infinityitemeditor.screen.nbt.ContextMenu;
import infinityitemeditor.screen.nbt.INBTNode;
import infinityitemeditor.screen.widgets.StyledBitToggle;
import infinityitemeditor.screen.widgets.StyledTFToggle;
import infinityitemeditor.screen.widgets.StyledToggle;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.Button.IPressable;
import net.minecraft.nbt.ByteNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;

import java.util.List;

public class DataBoolean extends SingularData<Boolean, ByteNBT> implements IPressable {
    TextFormatting SYNTAX_HIGHLIGHTING_TRUE = TextFormatting.GREEN;
    TextFormatting SYNTAX_HIGHLIGHTING_FALSE = TextFormatting.RED;

    public DataBoolean() {
        this(false);
    }


    public DataBoolean(ByteNBT nbt) {
        this(nbt.getAsByte() != 0);
    }


    public DataBoolean(boolean value) {
        super(value);
    }


    public void toggle() {
        set(!data);
    }


    @Override
    public boolean isDefault() {
        return !data;
    }


    @Override
    public ByteNBT getNBT() {
        return ByteNBT.valueOf(data);
    }

    @Override
    public ITextComponent getPrettyDisplay(String space, int indentation) {
        ITextComponent textBool;
        if (data) {
            textBool = (new StringTextComponent(" (true)")).withStyle(SYNTAX_HIGHLIGHTING_TRUE);
        } else {
            textBool = (new StringTextComponent(" (false)")).withStyle(SYNTAX_HIGHLIGHTING_FALSE);
        }
        ITextComponent itextcomponent = (new StringTextComponent("b")).withStyle(SYNTAX_HIGHLIGHTING_NUMBER_TYPE);
        return (new StringTextComponent(String.valueOf(getNBT().getAsByte()))).append(itextcomponent).withStyle(SYNTAX_HIGHLIGHTING_NUMBER).append(textBool);
    }

    @Override
    public void onPress(Button button) {
        toggle();
        if (button instanceof StyledToggle) {
            ((StyledToggle) button).updateMessage(data);
        } else if (button instanceof StyledTFToggle) {
            ((StyledTFToggle) button).updateMessage(data);
        } else if (button instanceof StyledBitToggle) {
            ((StyledBitToggle) button).updateMessage(data);
        }
    }

    @Override
    public void renderIcon(Minecraft mc, MatrixStack matrix, int x, int y) {
        NBTIcons.BOOLEAN.renderIcon(mc, matrix, x, y);
    }
}
