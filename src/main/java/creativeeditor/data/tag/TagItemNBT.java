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
    private final Map<String, Data<?, ?>> map = new HashMap<>();
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

    @Getter
    private final TagHideFlags displayHideFlags;

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
    private final TagList<TagEffect> potionEffects;
    @Getter
    private final DataString potion;
    @Getter
    private final DataColor potionColor;
    @Getter
    private final TagList<TagEffect> effects;

    // Block Tags
    @Getter
    private final TagList<TagItemID> canPlaceOn;


    // Specific Items

    // Banner
    @Getter
    private final TagList<TagBannerPattern> patterns;
    //@Getter
    //private final TagBanner banner;

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
    private final TagFirework fireworks;

    // Heads
    @Getter
    private final TagGameProfile skullOwner;

    // Maps
    @Getter
    private final DataInteger mapNumber;
    // map_scale_direction?
    @Getter
    private final TagList<TagMapDecoration> mapDecorations;

    // Entities
    @Getter
    private final TagEntityArmorStand armorStandTag;


    public TagItemNBT(DataItem item, CompoundNBT nbt) {
        super();

        nbt = nbt != null ? nbt.copy() : new CompoundNBT();
        this.item = item;
        NBTKeys keys = NBTKeys.keys;
        // General
        damage = add(keys.tagDamage(), new TagDamage(item, nbt.getInt(keys.tagDamage())));
        unbreakable = add(keys.tagUnbreakable(), new DataBoolean(nbt.getBoolean(keys.tagUnbreakable())));
        canDestroy = add(keys.tagCanDestroy(), new TagList<TagItemID>(nbt.getList(keys.tagCanDestroy(), NBT.TAG_STRING), TagItemID::new));

        // Display
        display = add(keys.tagDisplay(), new TagDisplay(item, nbt.getCompound(keys.tagDisplay())));

        // Flags
        displayHideFlags = add(keys.tagHideFlags(), new TagHideFlags(TagHideFlags.getFlags(nbt.getInt(keys.tagHideFlags()))));

        // Enchantments
        enchantments = add(keys.tagEnchantments(), new TagList<>(nbt.getList(keys.tagEnchantments(), NBT.TAG_COMPOUND), TagEnchantment::new));
        storedEnchantments = add(keys.tagStoredEnchantments(), new TagList<>(nbt.getList(keys.tagStoredEnchantments(), NBT.TAG_COMPOUND), TagEnchantment::new));
        repairCost = add(keys.tagRepairCost(), new DataInteger(nbt.getInt(keys.tagRepairCost())));

        // Attributes
        attributes = add(keys.tagAttributes(), new TagList<>(nbt.getList(keys.tagAttributes(), NBT.TAG_COMPOUND), TagAttributeModifier::new));

        // Effecs
        potionEffects = add(keys.tagCustomPotionEffects(), new TagList<>(nbt.getList(keys.tagCustomPotionEffects(), NBT.TAG_COMPOUND), TagEffect::new));
        potion = add(keys.tagPotion(), new DataString(nbt.getString(keys.tagPotion()))); // TODO Potion tag
        potionColor = add(keys.tagCustomPotionColor(), new DataColor(nbt.getInt(keys.tagCustomPotionColor())));
        effects = add(keys.tagEffects(), new TagList<>(nbt.getList(keys.tagCustomPotionEffects(), NBT.TAG_COMPOUND), TagEffect::new));

        // Block tags
        canPlaceOn = add(keys.tagCanPlaceOn(), new TagList<>(nbt.getList(keys.tagCanPlaceOn(), NBT.TAG_STRING), TagItemID::new));

        // Specific Items/Blocks

        // Banners
        patterns = add(keys.tagPatterns(), new TagList<>(nbt.getList(keys.tagPatterns(), NBT.TAG_COMPOUND), TagBannerPattern::new));
        //banner = add( keys.tagBlockEntityTag(), new TagBanner( nbt.getCompound( keys.tagBlockEntityTag() ) ) );

        // Books
        resolved = add(keys.tagResolved(), new DataBoolean(nbt.getBoolean(keys.tagResolved())));
        generation = add(keys.tagGeneration(), new NumberRangeInt(nbt.getInt(keys.tagGeneration()), 0, 3));
        author = add(keys.tagAuthor(), new DataString(nbt.getString(keys.tagAuthor())));
        title = add(keys.tagTitle(), new DataString(nbt.getString(keys.tagTitle())));
        pages = add(keys.tagPages(), new DataListString(nbt.getList(keys.tagPages(), NBT.TAG_COMPOUND)));


        // Crossbows
        chargedProjectiles = add(keys.tagChargedProjectiles(), new TagList<>(nbt.getList(keys.tagChargedProjectiles(), NBT.TAG_STRING), TagItemID::new));
        charged = add(keys.tagCharged(), new DataBoolean(nbt.getBoolean(keys.tagCharged())));

        // Fireworks
        explosion = add(keys.tagExplosion(), new TagExplosion(nbt.getCompound(keys.tagExplosion())));
        fireworks = add(keys.tagFireworks(), new TagFirework(nbt.getCompound(keys.tagFireworks())));

        // Heads
        if (nbt.contains(keys.tagSkullOwner(), NBT.TAG_COMPOUND)) {
            skullOwner = new TagGameProfile(nbt.getCompound(keys.tagSkullOwner()));
        } else if (nbt.contains(keys.tagSkullOwner(), NBT.TAG_STRING) && !StringUtils.isBlank(nbt.getString(keys.tagSkullOwner()))) {
            skullOwner = new TagGameProfile(nbt.getString(keys.tagSkullOwner()));
        } else {
            skullOwner = new TagGameProfile((GameProfile) null);
        }
        add(keys.tagSkullOwner(), skullOwner);

        // Map
        mapNumber = add(keys.tagMapNumber(), new DataInteger(nbt.getInt(keys.tagMapNumber())));
        mapDecorations = add(keys.tagMapDecorations(), new TagList<>(nbt.getList(keys.tagMapDecorations(), NBT.TAG_COMPOUND), TagMapDecoration::new));
        // Entity Tags
        armorStandTag = add(keys.tagEntityTag(), new TagEntityArmorStand(nbt.getCompound(keys.tagEntityTag())));


        unserializedNBT = nbt.copy();

        //Minecraft mc = Minecraft.getInstance();
        //mc.player.sendMessage( new StringTextComponent( "Before: " + item.getItem().get() ).appendSibling( unserializedNBT.toFormattedComponent() ) );


        for (String key : map.keySet()) {
            unserializedNBT.remove(key);
        }
        //mc.player.sendMessage( new StringTextComponent( "After: " + item.getItem().get() ).appendSibling( unserializedNBT.toFormattedComponent() ) );
    }


    private <T extends Data<?, ?>> T add(String key, T data) {
        map.put(key, data);
        return data;
    }


    @Override
    public CompoundNBT getNBT() {
        CompoundNBT nbt = unserializedNBT.copy();
        for (String key : map.keySet()) {
            Data<?, ?> data = map.get(key);
            if (!data.isDefault())
                nbt.put(key, data.getNBT());
        }
        return nbt;
    }


    @Override
    public boolean isDefault() {
        if (!unserializedNBT.isEmpty())
            return false;

        for (String key : map.keySet()) {
            Data<?, ?> data = map.get(key);
            if (!data.isDefault())
                return false;
        }
        return true;
    }


    @Override
    public TagItemNBT getData() {
        return this;
    }
}
