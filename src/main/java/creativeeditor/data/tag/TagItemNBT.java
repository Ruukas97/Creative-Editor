package creativeeditor.data.tag;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.mojang.authlib.GameProfile;

import creativeeditor.data.Data;
import creativeeditor.data.DataItem;
import creativeeditor.data.NumberRangeInt;
import creativeeditor.data.base.DataBoolean;
import creativeeditor.data.base.DataColor;
import creativeeditor.data.base.DataInteger;
import creativeeditor.data.base.DataListString;
import creativeeditor.data.base.DataString;
import creativeeditor.data.tag.entity.TagEntityArmorStand;
import creativeeditor.data.version.NBTKeys;
import lombok.Getter;
import net.minecraft.nbt.CompoundNBT;
import net.minecraftforge.common.util.Constants.NBT;

public class TagItemNBT implements Data<TagItemNBT, CompoundNBT> {
    @Getter
    private final DataItem item;
    private final CompoundNBT unserializedNBT;

    // General
    @Getter
    private final TagDamage damage;
    @Getter
    private final DataBoolean unbreakable;
    @Getter
    private final TagList<TagItemID> canDestroy;


    // Display
    @Getter
    private final TagDisplay display;

    // Enchantments
    @Getter
    private final TagList<TagEnchantment> enchantments;
    @Getter
    private final TagList<TagEnchantment> storedEnchantments;
    @Getter
    private final DataInteger repairCost;

    // Attributes
    @Getter
    private final TagList<TagAttributeModifier> attributes;

    // Effects
    @Getter
    private final TagEffects potionEffects;
    @Getter
    private final DataString potion;
    @Getter
    private final DataColor potionColor;

    // Block Tags
    @Getter
    private final TagList<TagItemID> canPlaceOn;


    // Specific Items

    // Books
    @Getter
    private final DataBoolean resolved;
    @Getter
    private final NumberRangeInt generation;
    @Getter
    private final DataString author;
    @Getter
    private final DataString title;
    @Getter
    private final DataListString pages;

    // Crossbows
    @Getter
    private final TagList<TagItemID> chargedProjectiles;
    @Getter
    private final DataBoolean charged;
    
    // Fireworks
    @Getter
    private final TagExplosion explosion;
    @Getter
    private final TagFireworks fireworks;
    

    // Heads
    @Getter
    private final TagGameProfile skullOwner;

    // Entities
    @Getter
    private final TagEntityArmorStand armorStandTag;


    public TagItemNBT(DataItem item, CompoundNBT nbt) {
        super();
        nbt = nbt != null ? nbt.copy() : new CompoundNBT();
        this.item = item;
        NBTKeys keys = NBTKeys.keys;
        // General
        damage = new TagDamage( item, nbt );
        unbreakable = new DataBoolean( nbt.getBoolean( keys.tagUnbreakable() ) );
        canDestroy = new TagList<>( nbt.getList( keys.tagCanDestroy(), NBT.TAG_STRING ) );

        // Display
        display = new TagDisplay( item, nbt.getCompound( keys.tagDisplay() ) );

        // Enchantments
        enchantments = new TagList<>( nbt.getList( keys.tagEnchantments(), NBT.TAG_COMPOUND ) );
        storedEnchantments = new TagList<>( nbt.getList( keys.tagStoredEnchantments(), NBT.TAG_COMPOUND ) );
        repairCost = new DataInteger( nbt.getInt( keys.tagRepairCost() ) );

        // Attributes
        attributes = new TagList<>( nbt.getList( keys.tagAttributes(), NBT.TAG_COMPOUND ) );

        // Effecs
        potionEffects = new TagEffects( nbt.getList( keys.tagCustomPotionEffects(), NBT.TAG_COMPOUND ) );
        potion = new DataString( nbt.getString( keys.tagPotion() ) ); // TODO Potion tag
        potionColor = new DataColor( nbt.getInt( keys.tagCustomPotionColor() ) );

        // Block tags
        canPlaceOn = new TagList<>( nbt.getList( keys.tagCanPlaceOn(), NBT.TAG_STRING ) );

        // Specific Items/Blocks

        // Books
        resolved = new DataBoolean( nbt.getBoolean( keys.tagResolved() ) );
        generation = new NumberRangeInt( nbt.getInt( keys.tagGeneration() ), 0, 3 );
        author = new DataString( nbt.getString( keys.tagAuthor() ) );
        title = new DataString( nbt.getString( keys.tagTitle() ) );
        pages = new DataListString( nbt.getList( keys.tagPages(), NBT.TAG_COMPOUND ) );


        // Crossbows
        chargedProjectiles = new TagList<>( nbt.getList( keys.tagChargedProjectiles(), NBT.TAG_STRING ) );
        charged = new DataBoolean( nbt.getBoolean( keys.tagCharged() ) );
        
        // Fireworks
        explosion = new TagExplosion( nbt.getCompound( keys.tagExplosion() ) );
        fireworks = new TagFireworks( nbt.getCompound( keys.tagFireworks() ) );

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

        // Entity Tags
        armorStandTag = new TagEntityArmorStand( nbt.getCompound( keys.tagEntityTag() ) );


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
        map.put( keys.tagDisplay(), display );
        map.put( keys.tagEnchantments(), enchantments );
        map.put( keys.tagStoredEnchantments(), storedEnchantments );
        map.put( keys.tagRepairCost(), repairCost );
        map.put( keys.tagAttributes(), attributes );
        map.put( keys.tagCustomPotionEffects(), potionEffects );
        map.put( keys.tagPotion(), potion );
        map.put( keys.tagCustomPotionColor(), potionColor );
        map.put( keys.tagCanPlaceOn(), canPlaceOn );
        map.put( keys.tagChargedProjectiles(), chargedProjectiles );
        map.put( keys.tagCharged(), charged );
        map.put( keys.tagSkullOwner(), skullOwner );
        map.put( keys.tagEntityTag(), armorStandTag );
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
