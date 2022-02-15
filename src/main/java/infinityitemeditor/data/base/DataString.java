package infinityitemeditor.data.base;

import net.minecraft.nbt.StringTag;

public class DataString extends SingularData<String, StringTag> {

    public DataString() {
        this("");
    }


    public DataString(String value) {
        super(value);
    }


    public DataString(StringTag nbt) {
        this(nbt.getAsString());
    }


    public void set(String value) {
        data = (value != null ? value : "");
    }


    @Override
    public boolean isDefault() {
        return data == null || data.isEmpty();
    }


    @Override
    public StringTag getTag() {
        return StringTag.valueOf(data);
    }

//    @Override
//    public MutableComponent getPrettyDisplay(String space, int indentation) {
//        String s = quoteAndEscape(this.data);
//        String s1 = s.substring(0, 1);
//        MutableComponent itextcomponent = (new TextComponent(s.substring(1, s.length() - 1))).withStyle(SYNTAX_HIGHLIGHTING_STRING);
//        return (new TextComponent(s1)).append(itextcomponent).append(s1);
//    }

    public static String quoteAndEscape(String string) {
        StringBuilder stringbuilder = new StringBuilder(" ");
        char c0 = 0;

        for (int i = 0; i < string.length(); ++i) {
            char c1 = string.charAt(i);
            if (c1 == '\\') {
                stringbuilder.append('\\');
            } else if (c1 == '"' || c1 == '\'') {
                if (c0 == 0) {
                    c0 = (char) (c1 == '"' ? 39 : 34);
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
