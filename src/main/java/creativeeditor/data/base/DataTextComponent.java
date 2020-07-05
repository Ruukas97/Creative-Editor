package creativeeditor.data.base;

import net.minecraft.nbt.StringNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class DataTextComponent extends SingularData<ITextComponent, StringNBT> {
    public DataTextComponent(ITextComponent data) {
        super( data );
    }


    public DataTextComponent(String data) {
        this( new StringTextComponent( data ) );
    }


    public String getUnformatted() {
        return data.getUnformattedComponentText();
    }


    public String getFormatted() {
        return data.getFormattedText();
    }


    public void set( String s ) {
        set( new StringTextComponent( s ) );
    }


    @Override
    public boolean isDefault() {
        return data.getUnformattedComponentText().length() == 0;
    }


    @Override
    public StringNBT getNBT() {
        return StringNBT.valueOf( ITextComponent.Serializer.toJson( data ) );
    }
}
