package creativeeditor.data.tag;

import creativeeditor.data.base.SingularData;
import lombok.Getter;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.nbt.IntNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public class TagEnum<E extends Enum<E>> extends SingularData<E, IntNBT> implements Button.IPressable {
    @Getter
    private final E[] options;

    public TagEnum(Class<E> clazz, E value) {
        super(value);
        options = clazz.getEnumConstants();
        if(this.data == null){
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

    public TextComponent getName(){
        return new TranslationTextComponent("enum." + (data.getDeclaringClass().getSimpleName() + '.' + data.name()).toLowerCase());
    }

    public TextComponent getKeyValue(){
        return (TextComponent) new TranslationTextComponent("enum." + data.getDeclaringClass().getSimpleName().toLowerCase() + "._", getPrettyDisplay("", 0)).withStyle(TextFormatting.AQUA);
    }
}
