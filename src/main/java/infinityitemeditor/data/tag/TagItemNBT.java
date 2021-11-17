package infinityitemeditor.data.tag;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import infinityitemeditor.data.Data;
import infinityitemeditor.data.DataItem;
import infinityitemeditor.data.NumberRangeInt;
import infinityitemeditor.data.base.*;
import infinityitemeditor.data.tag.block.TagBlockEntity;
import infinityitemeditor.data.tag.entity.TagEntityArmorStand;
import infinityitemeditor.data.version.NBTKeys;
import lombok.Getter;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.util.Constants.NBT;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

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
    private final TagHideFlags hideFlags;

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
    @Getter
    private final TagBlockEntity blockEntityTag;


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
        hideFlags = add(keys.tagHideFlags(), new TagHideFlags(nbt.getInt(keys.tagHideFlags())));

        // Enchantments
        enchantments = add(keys.tagEnchantments(), new TagList<>(nbt.getList(keys.tagEnchantments(), NBT.TAG_COMPOUND), TagEnchantment::new));
        storedEnchantments = add(keys.tagStoredEnchantments(), new TagList<>(nbt.getList(keys.tagStoredEnchantments(), NBT.TAG_COMPOUND), TagEnchantment::new));
        repairCost = add(keys.tagRepairCost(), new DataInteger(nbt.getInt(keys.tagRepairCost())));

        // Attributes
        attributes = add(keys.tagAttributes(), new TagList<>(nbt.getList(keys.tagAttributes(), NBT.TAG_COMPOUND), TagAttributeModifier::new));

        // Effecs
        potionEffects = add(keys.tagCustomPotionEffects(), new TagList<>(nbt.getList(keys.tagCustomPotionEffects(), NBT.TAG_COMPOUND), TagEffect::new));
        potion = add(keys.tagPotion(), new DataString(nbt.getString(keys.tagPotion())));
        potionColor = add(keys.tagCustomPotionColor(), new DataColor(nbt.getInt(keys.tagCustomPotionColor())));
        effects = add(keys.tagEffects(), new TagList<>(nbt.getList(keys.tagCustomPotionEffects(), NBT.TAG_COMPOUND), TagEffect::new));

        // Block tags
        canPlaceOn = add(keys.tagCanPlaceOn(), new TagList<>(nbt.getList(keys.tagCanPlaceOn(), NBT.TAG_STRING), TagItemID::new));
        blockEntityTag = add(keys.tagBlockEntityTag(), new TagBlockEntity(item, nbt.getCompound(keys.tagBlockEntityTag())));
        // Specific Items/Blocks


        // Banners
        patterns = add(keys.tagPatterns(), new TagList<>(nbt.getList(keys.tagPatterns(), NBT.TAG_COMPOUND), TagBannerPattern::new));
        //banner = add( keys.tagBlockEntityTag(), new TagBanner( nbt.getCompound( keys.tagBlockEntityTag() ) ) );

        // Books
        resolved = add(keys.tagResolved(), new DataBoolean(nbt.getBoolean(keys.tagResolved())));
        generation = add(keys.tagGeneration(), new NumberRangeInt(nbt.getInt(keys.tagGeneration()), 0, 3));
        author = add(keys.tagAuthor(), new DataString(nbt.getString(keys.tagAuthor())));
        title = add(keys.tagTitle(), new DataString(nbt.getString(keys.tagTitle())));
        pages = add(keys.tagPages(), new DataListString(nbt.getList(keys.tagPages(), NBT.TAG_STRING)));


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
    public ITextComponent getPrettyDisplay(String space, int indentation) {
        if (map.isEmpty() || isDefault()) {
            return new StringTextComponent("{}");
        } else {
            IFormattableTextComponent iformattabletextcomponent = new StringTextComponent("{");
            List<String> collection = Lists.newArrayList(map.keySet());
            List<String> list = Lists.newArrayList();
            for(String s : collection){
                Data<?, ?> d = map.get(s);
                if(!d.isDefault()){
                    list.add(s);
                }
            }

            Collections.sort(list);
            collection = list;

            if (!space.isEmpty()) {
                iformattabletextcomponent.append("\n");
            }

            IFormattableTextComponent iformattabletextcomponent1;
            for (Iterator<String> iterator = collection.iterator(); iterator.hasNext(); iformattabletextcomponent.append(iformattabletextcomponent1)) {
                String s = iterator.next();
                iformattabletextcomponent1 = (new StringTextComponent(Strings.repeat(space, indentation + 1))).append(DataMap.handleEscapePretty(s)).append(String.valueOf(':')).append(" ").append(map.get(s).getPrettyDisplay(space, indentation + 1));
                if (iterator.hasNext()) {
                    iformattabletextcomponent1.append(String.valueOf(',')).append(space.isEmpty() ? " " : "\n");
                }
            }

            if (!space.isEmpty()) {
                iformattabletextcomponent.append("\n").append(Strings.repeat(space, indentation));
            }

            iformattabletextcomponent.append("}");
            return iformattabletextcomponent;
        }
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
