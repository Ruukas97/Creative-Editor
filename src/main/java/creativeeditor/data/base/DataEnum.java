package creativeeditor.data.base;

import lombok.Getter;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.nbt.IntNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class DataEnum<E extends Enum<E>> extends SingularData<E, IntNBT> implements Button.IPressable {
    @Getter
    private final E[] options;

    public DataEnum(E value) {
        super(value);
        options = value.getDeclaringClass().getEnumConstants();
    }

    public void next() {
        set(options[(data.ordinal() + 1) % options.length]);
    }

    public void previous() {
        set(options[(data.ordinal() - 1) % options.length]);
    }

    @Override
    public boolean isDefault() {
        return data.ordinal() == 0;
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

    public TextComponent getName(){
        return new TranslationTextComponent("enum." + (data.getDeclaringClass().getSimpleName() + '.' + data.name()).toLowerCase());
    }
}
