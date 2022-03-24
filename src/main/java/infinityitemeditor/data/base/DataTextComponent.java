package infinityitemeditor.data.base;

import com.mojang.blaze3d.matrix.MatrixStack;
import infinityitemeditor.render.NBTIcons;
import net.minecraft.client.Minecraft;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;

public class DataTextComponent extends SingularData<ITextComponent, StringNBT> {
    public DataTextComponent(ITextComponent data) {
        super(data);
    }


    public DataTextComponent(String data) {
        this(new StringTextComponent(data));
    }


    public String getUnformatted() {
        return data.getString();
//        return data.getUnformattedComponentText(); = OLD
    }


    public String getFormatted() {
        return data.getString();
    } // formatted


    public void set(String s) {
        set(new StringTextComponent(s));
    }


    @Override
    public boolean isDefault() {
        return data.getString().length() == 0;
    }


    @Override
    public StringNBT getNBT() {
        return StringNBT.valueOf(ITextComponent.Serializer.toJson(data));
    }

    @Override
    public ITextComponent getPrettyDisplay(String space, int indentation) {
        String s = DataString.quoteAndEscape(TextComponent.Serializer.toJson(data));
        String s1 = s.substring(0, 1);
        ITextComponent itextcomponent = (new StringTextComponent(s.substring(1, s.length() - 1))).withStyle(SYNTAX_HIGHLIGHTING_STRING);
        return (new StringTextComponent(s1)).append(itextcomponent).append(s1);
    }

    @Override
    public void renderIcon(Minecraft mc, MatrixStack matrix, int x, int y) {
        NBTIcons.STRING.renderIcon(mc, matrix, x, y);
    }
}
