package infinityitemeditor.data.base;

import infinityitemeditor.screen.widgets.StyledBitToggle;
import net.minecraft.client.gui.components.Button;
import net.minecraft.nbt.IntTag;

public class DataBitField extends SingularData<boolean[], IntTag> implements Button.OnPress {
    private final boolean keepSize;


    public DataBitField(boolean keepSize, boolean... data) {
        super(data);
        this.keepSize = keepSize;
    }


    public DataBitField(int size, IntTag data) {
        this(size, data.getAsInt());
    }

    public DataBitField(int size, int data) {
        super(new boolean[size]);
        this.keepSize = true;
        setInt(data);
    }


    @Override
    public void set(boolean[] value) {
        if (keepSize && value.length != data.length) {
            for (int i = 0; i < value.length && i < data.length; i++) {
                data[i] = value[i];
            }
        } else
            super.set(value);
    }


    @Override
    public boolean isDefault() {
        return getInt() == 0;
    }


    public int getInt() {
        return booleanArrayToInt(data);
    }


    public void setInt(int value) {
        for (int i = 0; i < data.length; i++) {
            data[i] = (value & (1 << i)) != 0;
        }
    }


    public static int booleanArrayToInt(boolean[] array) {
        int result = 0;
        for (int i = 0; i < array.length; i++) {
            if (array[i]) {
                result += 1 << i;
            }
        }
        return result;
    }


    @Override
    public IntTag getTag() {
        return IntTag.valueOf(getInt());
    }

//    @Override
//    public MutableComponent getPrettyDisplay(String space, int indentation) {
//        IFormattableTextComponent text = new TextComponent("0b").withStyle(SYNTAX_HIGHLIGHTING_NUMBER_TYPE);
//        for (int i = 0; i < data.length; i++) {
//            text.append(new TextComponent(data[i] ? "1" : "0").withStyle(SYNTAX_HIGHLIGHTING_NUMBER));
//        }
//        return text;
//    }

    @Override
    public void onPress(Button button) {
        if (button instanceof StyledBitToggle) {
            StyledBitToggle but = (StyledBitToggle) button;
            data[but.index] = !data[but.index];
            but.updateMessage(data[but.index]);
        }
    }
}
