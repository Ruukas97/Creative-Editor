package creativeeditor.data.tag;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.mojang.authlib.GameProfile;

import creativeeditor.data.Data;
import creativeeditor.data.DataItem;
import creativeeditor.data.base.DataBoolean;
import creativeeditor.data.base.DataListString;
import creativeeditor.data.tag.entity.TagEntityArmorStand;
import creativeeditor.data.tag.entity.TagEntityArmorStand.Pose;
import creativeeditor.data.version.NBTKeys;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.Constants.NBT;

public class TagItemNBT implements Data<TagItemNBT, CompoundNBT> {
    private final @Getter DataItem item;
    private final CompoundNBT unserializedNBT;

    // General
    private final @Getter TagDamage damage;
    private final @Getter DataBoolean unbreakable;
    private final @Getter DataListString canDestroy;

    // Block Tags
    private final @Getter DataListString canPlaceOn;

    // Display
    private final @Getter TagDisplay display;

    private final @Getter TagEnchantments enchantments;

    private @Getter @Setter TagGameProfile skullOwner;
    
    private @Getter TagEntityArmorStand armorStandTag;


    public TagItemNBT(DataItem item, CompoundNBT nbt) {
        super();
        nbt = nbt != null ? nbt.copy() : new CompoundNBT();
        this.item = item;
        NBTKeys keys = NBTKeys.keys;
        // General
        damage = new TagDamage( this.item, nbt );
        unbreakable = new DataBoolean( nbt.getBoolean( keys.tagUnbreakable() ) );
        canDestroy = new DataListString( nbt.getList( keys.tagCanDestroy(), NBT.TAG_STRING ) );

        // Block tags
        canPlaceOn = new DataListString( nbt.getList( keys.tagCanPlaceOn(), NBT.TAG_STRING ) );

        // Display
        display = new TagDisplay( item, nbt );

        // Enchantments
        enchantments = new TagEnchantments( nbt.getList( keys.tagEnchantments(), NBT.TAG_COMPOUND ) );

        // Item Specific
        // Heads
        if (nbt.contains( keys.tagSkullOwner(), NBT.TAG_COMPOUND )) {
            skullOwner = new TagGameProfile( nbt.getCompound( keys.tagSkullOwner() ) );
        }
        else if (nbt.contains( keys.tagSkullOwner(), NBT.TAG_STRING ) && !StringUtils.isBlank( nbt.getString( keys.tagSkullOwner() ) )) {
            skullOwner = new TagGameProfile( nbt.getString( keys.tagSkullOwner() ) );
        }
        else {
            skullOwner = new TagGameProfile( (GameProfile) null );
        }


        unserializedNBT = nbt.copy();

        Map<String, Data<?, ?>> dataMap = getKeyToDataMap();
        for (String key : dataMap.keySet()) {
            unserializedNBT.remove( key );
        }
    }


    private Map<String, Data<?, ?>> getKeyToDataMap() {
        Map<String, Data<?, ?>> map = new HashMap<>();
        NBTKeys keys = NBTKeys.keys;
        map.put( keys.tagDamage(), damage );
        map.put( keys.tagUnbreakable(), unbreakable );
        map.put( keys.tagCanDestroy(), canDestroy );
        map.put( keys.tagCanPlaceOn(), canPlaceOn );
        map.put( keys.tagDisplay(), display );
        map.put( keys.tagSkullOwner(), skullOwner );
        return map;
    }


    @Override
    public CompoundNBT getNBT() {
        CompoundNBT nbt = unserializedNBT.copy();
        Map<String, Data<?, ?>> dataMap = getKeyToDataMap();
        for (String key : dataMap.keySet()) {
            Data<?, ?> data = dataMap.get( key );
            if (!data.isDefault())
                nbt.put( key, data.getNBT() );
        }
        return nbt;
    }


    @Override
    public boolean isDefault() {
        if (!unserializedNBT.isEmpty())
            return false;

        Map<String, Data<?, ?>> dataMap = getKeyToDataMap();
        for (String key : dataMap.keySet()) {
            Data<?, ?> data = dataMap.get( key );
            if (!data.isDefault())
                return false;
        }
        return false;
    }


    @Override
    public TagItemNBT getData() {
        return this;
    }
}
