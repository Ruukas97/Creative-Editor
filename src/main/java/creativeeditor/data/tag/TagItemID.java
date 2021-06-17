package creativeeditor.data.tag;

import javax.annotation.Nonnull;

import creativeeditor.data.base.DataString;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.StringNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.GameData;

public class TagItemID extends DataString {
    public TagItemID(INBT value) {
        super( value instanceof StringNBT ? value.getString() : "minecraft:air" );
    }


    public TagItemID(StringNBT value) {
        super( value );
    }


    public TagItemID(String value) {
        super( value );
    }


    public TagItemID(Item item) {
        this( getIDFromItem( item ) );
    }


    public TagItemID() {
        this( Items.AIR );
    }


    @Nonnull
    public Item getItem() {
        return GameData.getWrapperDefaulted( Item.class ).getOrDefault( new ResourceLocation( get() ) );
    }

    public String getIDExcludingMC() {
        if(data.startsWith("minecraft:")){
            return data.substring(10);
        }
        return data;
    }

    public void setItem( Item item ) {
        set( getIDFromItem( item ) );
    }


    public static String getIDFromItem( Item item ) {
        return item != null ? item.getRegistryName().toString() : "minecraft:air";
    }


    @Override
    public boolean isDefault() {
        return false;
    }
}
