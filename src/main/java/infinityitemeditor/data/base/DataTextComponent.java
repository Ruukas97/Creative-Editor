package infinityitemeditor.data.base;

import net.minecraft.nbt.StringTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;

public class DataTextComponent extends SingularData<Component, StringTag> {
    public DataTextComponent(MutableComponent data) {
        super(data);
    }


    public DataTextComponent(String data) {
        this(new TextComponent(data));
    }


    public String getUnformatted() {
        return data.getString();
//        return data.getUnformattedComponentText(); = OLD
    }


    public String getFormatted() {
        return data.getString();
    } // formatted


    public void set(String s) {
        set(new TextComponent(s));
    }


    @Override
    public boolean isDefault() {
        return data.getString().length() == 0;
    }


    @Override
    public StringTag getTag() {
        return StringTag.valueOf(MutableComponent.Serializer.toJson(data));
    }

//    @Override
//    public MutableComponent getPrettyDisplay(String space, int indentation) {
//        String s = DataString.quoteAndEscape(TextComponent.Serializer.toJson(data));
//        String s1 = s.substring(0, 1);
//        MutableComponent itextcomponent = (new TextComponent(s.substring(1, s.length() - 1))).withStyle(SYNTAX_HIGHLIGHTING_STRING);
//        return (new TextComponent(s1)).append(itextcomponent).append(s1);
//    }
}
