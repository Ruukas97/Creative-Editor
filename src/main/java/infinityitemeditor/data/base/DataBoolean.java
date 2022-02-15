package infinityitemeditor.data.base;

import infinityitemeditor.screen.widgets.StyledBitToggle;
import infinityitemeditor.screen.widgets.StyledTFToggle;
import infinityitemeditor.screen.widgets.StyledToggle;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.components.Button;
import net.minecraft.nbt.ByteTag;

public class
DataBoolean extends SingularData<Boolean, ByteTag> implements Button.OnPress {
    ChatFormatting SYNTAX_HIGHLIGHTING_TRUE = ChatFormatting.GREEN;
    ChatFormatting SYNTAX_HIGHLIGHTING_FALSE = ChatFormatting.RED;

    public DataBoolean() {
        this(false);
    }


    public DataBoolean(ByteTag nbt) {
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
    public ByteTag getTag() {
        return ByteTag.valueOf(data);
    }

//    @Override
//    public MutableComponent getPrettyDisplay(String space, int indentation) {
//        MutableComponent textBool;
//        if (data) {
//            textBool = (new TextComponent(" (true)")).withStyle(SYNTAX_HIGHLIGHTING_TRUE);
//        }
//        else {
//            textBool = (new TextComponent(" (false)")).withStyle(SYNTAX_HIGHLIGHTING_FALSE);
//        }
//        MutableComponent itextcomponent = (new TextComponent("b")).withStyle(SYNTAX_HIGHLIGHTING_NUMBER_TYPE);
//        return (new TextComponent(String.valueOf(getTag().getAsByte()))).append(itextcomponent).withStyle(SYNTAX_HIGHLIGHTING_NUMBER).append(textBool);
//    }

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
}
