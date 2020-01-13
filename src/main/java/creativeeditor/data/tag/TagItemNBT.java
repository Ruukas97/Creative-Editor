package creativeeditor.data.tag;

import javax.annotation.Nonnull;

import creativeeditor.data.DataItem;
import creativeeditor.data.NumberRange;
import creativeeditor.data.base.DataBoolean;
import creativeeditor.data.base.DataInteger;
import creativeeditor.data.base.DataList;
import creativeeditor.data.base.DataMap;
import creativeeditor.data.base.DataString;
import net.minecraft.nbt.CompoundNBT;

public class TagItemNBT extends DataMap {

	public TagItemNBT() {
		super();
	}

	public TagItemNBT(CompoundNBT nbt) {
		super(nbt);
	}

	// General Tags: https://minecraft.gamepedia.com/Player.dat_format#General_Tags

	@Nonnull
	public TagDamage getDamageTag(DataItem item) {
		return (TagDamage) getDataDefaultedForced("Damage", new TagDamage(item));
	}

	@Nonnull
	public DataBoolean getUnbreakableTag() {
		return (DataBoolean) getDataDefaulted("Unbreakable", new DataBoolean());
	}

	@Nonnull
	public DataList getCanDestroyTag() {
		return (DataList) getDataDefaulted("CanDestroy", new DataList());
	}

	@Nonnull
	public DataInteger getCustomModelDataTag() {
		return (DataInteger) getDataDefaulted("CustomModelData", new DataInteger());
	}

	// Block Tags: https://minecraft.gamepedia.com/Player.dat_format#Block_Tags

	@Nonnull
	public DataList getCanPlaceOnTag() {
		return (DataList) getDataDefaulted("CanPlaceOn", new DataList());
	}

	// TODO https://minecraft.gamepedia.com/Block_entity tags
	@Nonnull
	public DataMap getBlockEntityTag() {
		return (DataMap) getDataDefaulted("BlockEntityTag", new DataMap());
	}

	// TODO https://minecraft.gamepedia.com/Block_states
	@Nonnull
	public DataMap getBlockStateTag() {
		return (DataMap) getDataDefaulted("BlockStateTag", new DataMap());
	}

	// Enchantments: https://minecraft.gamepedia.com/Player.dat_format#Enchantments

	// TODO https://minecraft.gamepedia.com/Enchanting#Summary_of_enchantments
	@Nonnull
	public DataList getEnchantmentsTag() {
		return (DataList) getDataDefaulted("Enchantments", new DataList());
	}

	@Nonnull
	public DataList getStoredEnchantmentsTag() {
		return (DataList) getDataDefaulted("StoredEnchantments", new DataList());
	}

	@Nonnull
	public DataInteger getRepairCostTag() {
		return (DataInteger) getDataDefaulted("RepairCost", new DataInteger());
	}

	// Attributes:
	// https://minecraft.gamepedia.com/Player.dat_format#Attribute_Modifiers

	@Nonnull
	public DataList getAttributesTag() {
		return (DataList) getDataDefaulted("AttributeModifiers", new DataList());
	}

	// Potions: https://minecraft.gamepedia.com/Player.dat_format#Potion_Effects

	@Nonnull
	public DataList getPotionEffectsTag() {
		return (DataList) getDataDefaulted("CustomPotionEffects", new DataList());
	}

	@Nonnull
	public DataString getPotionTag() {
		return (DataString) getDataDefaulted("Potion", new DataString());
	}

	@Nonnull
	public DataInteger getPotionColorTag() {
		return (DataInteger) getDataDefaulted("CustomPotionColor", new DataInteger());
	}

	// Crossbows: https://minecraft.gamepedia.com/Player.dat_format#Crossbows

	// List of items
	@Nonnull
	public DataList getChargedProjectilesTag() {
		return (DataList) getDataDefaulted("ChargedProjectiles", new DataList());
	}

	@Nonnull
	public DataBoolean getChargedTag() {
		return (DataBoolean) getDataDefaulted("Charged", new DataBoolean());
	}

	// Display: https://minecraft.gamepedia.com/Player.dat_format#Display_Properties

	@Nonnull
	public TagDisplay getDisplayTag() {
		return (TagDisplay) getDataDefaultedForced("display", new TagDisplay());
	}

	@Nonnull
	public DataInteger getHideFlagsTag() {
		return (DataInteger) getDataDefaulted("HideFlags", new DataInteger());
	}

	// Written Books:
	// https://minecraft.gamepedia.com/Player.dat_format#Written_Books

	@Nonnull
	public DataBoolean getResolvedTag() {
		return (DataBoolean) getDataDefaulted("resolved", new DataBoolean());
	}

	@Nonnull
	public NumberRange getGenerationTag() {
		return (NumberRange) getDataDefaulted("generation", new NumberRange(0, 3));
	}

	@Nonnull
	public DataString getAuthorTag() {
		return (DataString) getDataDefaulted("author", new DataString());
	}

	@Nonnull
	public DataString getTitleTag() {
		return (DataString) getDataDefaulted("title", new DataString());
	}

	@Nonnull
	public DataList getPagesTag() {
		return (DataList) getDataDefaulted("pages", new DataList());
	}

	// Player Heads: https://minecraft.gamepedia.com/Player.dat_format#Player_Heads

	// TODO Map variant
	@Nonnull
	public DataString getSkullOwnerTag() {
		return (DataString) getDataDefaulted("SkullOwner", new DataString());
	}

	// Fireworks: https://minecraft.gamepedia.com/Player.dat_format#Fireworks

	// Firework star data
	@Nonnull
	public DataMap getExplosionTag() {
		return (DataMap) getDataDefaulted("Explosion", new DataMap());
	}

	@Nonnull
	// List of explosions and single flight height
	public DataMap getFireworksTag() {
		return (DataMap) getDataDefaulted("Fireworks", new DataMap());
	}

	// Armor Stands and Spawn Eggs:
	// https://minecraft.gamepedia.com/Player.dat_format#Armor_Stands_and_Spawn_Eggs

	@Nonnull
	// List of explosions and single flight height
	public DataMap getEntityTag() {
		return (DataMap) getDataDefaulted("EntityTag", new DataMap());
	}

	// Buckets of Fish:
	// https://minecraft.gamepedia.com/Player.dat_format#Buckets_of_Fish

	@Nonnull
	public DataInteger getBucketVariantTag() {
		return (DataInteger) getDataDefaulted("BucketVariantTag", new DataInteger());
	}

	// EntityTag

	// Maps: https://minecraft.gamepedia.com/Player.dat_format#Maps

	@Nonnull
	public DataInteger getMapTag() {
		return (DataInteger) getDataDefaulted("map", new DataInteger());
	}

	@Nonnull
	public DataInteger getMapScaleDirectionTag() {
		return (DataInteger) getDataDefaulted("map_scale_direction", new DataInteger(1));
	}

	@Nonnull
	public DataList getDecorationsTag() {
		return (DataList) getDataDefaulted("Decorations", new DataList());
	}

	// MapColor in display tag

	// Suspicious Stew:
	// https://minecraft.gamepedia.com/Player.dat_format#Suspicious_Stew

	@Nonnull
	public DataList getEffectsTag() {
		return (DataList) getDataDefaulted("Effects", new DataList());
	}

	// Debug Stick: https://minecraft.gamepedia.com/Player.dat_format#Debug_Sticks

	@Nonnull
	public DataMap getDebugPropertysTag() {
		return (DataMap) getDataDefaulted("DebugProperty", new DataMap());
	}
}
