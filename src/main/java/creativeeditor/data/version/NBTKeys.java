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
    
    
    public abstract String displayMapColor();


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
    
    // Effecs
    public abstract String tagCustomPotionEffects();
    public abstract String tagPotion();
    public abstract String tagCustomPotionColor();
    public abstract String tagEffects();
    
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
    
    // Fireworks
    public abstract String tagExplosion();
    public abstract String explosionFlicker();
    public abstract String explosionTrail();
    public abstract String explosionShape();
    public abstract String explosionColors();
    public abstract String explosionFadeColor();
    public abstract String tagFireworks();
    public abstract String fireworksFlight();
    public abstract String fireworksExplosions();
    
    // Heads
    public abstract String tagSkullOwner();

    // Maps
    public abstract String tagMapNumber();
    public abstract String tagMapDecorations();
    public abstract String decorationId();
    public abstract String decorationType();
    public abstract String decorationRotation();


    // Entity Tags
    public abstract String tagEntityTag();
}
