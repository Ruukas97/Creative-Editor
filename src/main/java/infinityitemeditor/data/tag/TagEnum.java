package infinityitemeditor.data.tag;

import infinityitemeditor.data.base.SingularData;
import lombok.Getter;
import net.minecraft.client.gui.components.Button;
import net.minecraft.nbt.IntTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

public class TagEnum<E extends Enum<E>> extends SingularData<E, IntTag> implements Button.OnPress {
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
    public IntTag getTag() {
        return IntTag.valueOf(data.ordinal());
    }

//    @Override
//    public MutableComponent getPrettyDisplay(String space, int indentation) {
//        return getName().withStyle(SYNTAX_HIGHLIGHTING_NUMBER);
//    }

    @Override
    public void onPress(Button button) {
        next();
        button.setMessage(getName());
    }

    public Component getName(){
        return new TranslatableComponent("enum." + (data.getDeclaringClass().getSimpleName() + '.' + data.name()).toLowerCase());
    }

//    public TextComponent getKeyValue(){
//        return (TextComponent) new TranslatableComponent("enum." + data.getDeclaringClass().getSimpleName().toLowerCase() + "._", getPrettyDisplay("", 0)).withStyle(ChatFormatting.AQUA);
//    }
}
