package creativeeditor.data.tag;

import creativeeditor.data.DataItem;
import creativeeditor.data.base.DataTextComponent;
import creativeeditor.data.version.NBTKeys;
import lombok.Getter;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class TagDisplayName extends DataTextComponent {
    private boolean returnEmpty = true;
    private @Getter
    final DataItem item;


    public TagDisplayName(StringNBT name, DataItem item) {
        this( name.getAsString(), item );
    }


    public TagDisplayName(String name, DataItem item) {
        super( name.equals( "" ) ? new TranslationTextComponent( item.getItem().getItem().getDescriptionId() ) : ITextComponent.Serializer.fromJson( name ) );
        returnEmpty = false;
        this.item = item;
    }


    @Override
    public boolean isDefault() {
        if (returnEmpty || getUnformatted().length() == 0 || getUnformatted().equals( "Custom Name" )) {
            return true;
        }

        return data.getFormattedText().equals( getDefault().getFormattedText() ) || data instanceof TranslationTextComponent;
    }


    public void reset() {
        set( getDefault() );
    }


    public ITextComponent getDefault() {
        NBTKeys keys = NBTKeys.keys;
        returnEmpty = true;
        ItemStack copy = item.getItemStack();
        returnEmpty = false;
        CompoundNBT display = copy.getChildTag( keys.tagDisplay() );
        if (display != null) {
            display.remove( keys.displayName() );
        }
        return copy.getDisplayName();
    }
}
