package creativeeditor.data.version;

public abstract class NBTKeys {
    public static NBTKeys keys = new NBTKeysLatest();


    // ItemStack
    public abstract String stackID();


    public abstract String stackCount();


    public abstract String stackTag();


    public abstract String stackSlot();


    // ItemStack's Tag
    // General
    public abstract String tagDamage();


    public abstract String tagUnbreakable();


    public abstract String tagCanDestroy();


    public abstract String tagCustomModelData();


    // Display
    public abstract String tagDisplay();


    public abstract String displayColor();


    public abstract String displayName();


    public abstract String displayLore();


    // Block
    public abstract String tagCanPlaceOn();


    public abstract String tagBlockEntityTag();


    public abstract String tagBlockStateTag();


    // Enchantments
    public abstract String tagEnchantments();


    public abstract String enchantmentID();


    public abstract String enchantmentLevel();


    public abstract String tagStoredEnchantments();


    public abstract String tagRepairCost();


    // Attributes
    public abstract String tagAttributes();


    public abstract String attributeName();


    public abstract String attributeDisplay();


    public abstract String attributeSlot();


    public abstract String attributeOperation();


    public abstract String attributeAmount();


    public abstract String attributeUUIDMost();


    public abstract String attributeUUIDLeast();


    // Item Specific
    public abstract String tagSkullOwner();


    //Entity Tags
    public abstract String tagEntityTag();
}
