package creativeeditor.data.base;

import net.minecraft.nbt.StringNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class DataString extends SingularData<String, StringNBT> {

    public DataString() {
        this("");
    }


    public DataString(String value) {
        super(value);
    }


    public DataString(StringNBT nbt) {
        this(nbt.getAsString());
    }


    public void set(String value) {
        data = (value != null ? value : "");
    }


    @Override
    public boolean isDefault() {
        return data == null || data.length() < 1;
    }


    @Override
    public StringNBT getNBT() {
        return StringNBT.valueOf(data);
    }

    @Override
    public ITextComponent getPrettyDisplay(String space, int indentation) {
        String s = quoteAndEscape(this.data);
        String s1 = s.substring(0, 1);
        ITextComponent itextcomponent = (new StringTextComponent(s.substring(1, s.length() - 1))).withStyle(SYNTAX_HIGHLIGHTING_STRING);
        return (new StringTextComponent(s1)).append(itextcomponent).append(s1);
    }

    public static String quoteAndEscape(String string) {
        StringBuilder stringbuilder = new StringBuilder(" ");
        char c0 = 0;

        for(int i = 0; i < string.length(); ++i) {
            char c1 = string.charAt(i);
            if (c1 == '\\') {
                stringbuilder.append('\\');
            } else if (c1 == '"' || c1 == '\'') {
                if (c0 == 0) {
                    c0 = (char)(c1 == '"' ? 39 : 34);
                }

                if (c0 == c1) {
                    stringbuilder.append('\\');
                }
            }

            stringbuilder.append(c1);
        }

        if (c0 == 0) {
            c0 = '"';
        }

        stringbuilder.setCharAt(0, c0);
        stringbuilder.append(c0);
        return stringbuilder.toString();
    }
}
