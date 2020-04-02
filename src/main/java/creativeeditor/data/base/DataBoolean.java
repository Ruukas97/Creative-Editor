package creativeeditor.data.base;

import creativeeditor.widgets.StyledToggle;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.Button.IPressable;
import net.minecraft.nbt.ByteNBT;

public class DataBoolean extends SingularData<Boolean, ByteNBT> implements IPressable {

    public DataBoolean() {
        this( false );
    }


    public DataBoolean(ByteNBT nbt) {
        this( nbt.getByte() != 0 );
    }


    public DataBoolean(boolean value) {
        super( value );
    }


    public void toggle() {
        set( !data );
    }


    @Override
    public boolean isDefault() {
        return !data;
    }


    @Override
    public ByteNBT getNBT() {
        return new ByteNBT( (byte) (data ? 1 : 0) );
    }


    @Override
    public void onPress( Button button ) {
        toggle();
        if (button instanceof StyledToggle) {
            ((StyledToggle) button).updateMessage( data );
        }
    }
}
