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
    
    // Potion Effecs
    public abstract String tagCustomPotionEffects();
    public abstract String tagPotion();
    public abstract String tagCustomPotionColor();
    
    // Block
    public abstract String tagCanPlaceOn();

    public abstract String tagBlockEntityTag();

    public abstract String tagBlockStateTag();


    // Specific Item/Blocks
    
    // Books
    public abstract String tagResolved();
    public abstract String tagGeneration();
    public abstract String tagAuthor();
    public abstract String tagTitle();
    public abstract String tagPages();

    
    // Crossbows
    public abstract String tagChargedProjectiles();
    public abstract String tagCharged();
    
    // Heads
    public abstract String tagSkullOwner();


    // Entity Tags
    public abstract String tagEntityTag();
}
