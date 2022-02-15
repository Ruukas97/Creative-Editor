package infinityitemeditor.screen.models;

import infinityitemeditor.data.DataItem;
import infinityitemeditor.data.tag.TagEnchantment;
import infinityitemeditor.util.EnchantmentUtils;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.text.MutableComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.Arrays;

public class EnchantmentWheelType extends WheelType<TagEnchantment>  {
    private static final EnchantmentFilter SUPPORTED = new EnchantmentFilter("supported", pair -> pair.getSecond().getEnchantment().canEnchant(pair.getFirst().getItemStack()));
    private static final EnchantmentFilter ENCHANTING_TABLE = new EnchantmentFilter("enchantingtable", pair -> pair.getSecond().getEnchantment().canApplyAtEnchantingTable(pair.getFirst().getItemStack()));
    private static final EnchantmentFilter STOREABLE = new EnchantmentFilter("storeable", pair -> pair.getSecond().getEnchantment().isAllowedOnBooks());
    private static final EnchantmentFilter TREASURE = new EnchantmentFilter("treasure", pair -> pair.getSecond().getEnchantment().isTreasureOnly());
    private static final EnchantmentFilter CURSE = new EnchantmentFilter("curse", pair -> pair.getSecond().getEnchantment().isCurse());
    private static final EnchantmentFilter DISCOVERABLE = new EnchantmentFilter("discoverable", pair -> pair.getSecond().getEnchantment().isCurse());

    private static final TagFilter<TagEnchantment> MINECRAFT = new TagFilter<TagEnchantment>() {
        @Override
        public TextComponent getName() {
            return new TextComponent("Minecraft");
        }

        @Override
        public boolean shouldShow(DataItem item, TagEnchantment tag) {
            return tag.getEnchantment().getRegistryName().getNamespace().equals("minecraft");
        }

        @Override
        public TagEnchantment[] filter(DataItem item, TagEnchantment[] tags) {
            return Arrays.stream(tags).filter(tagAttributeModifier -> shouldShow(item, tagAttributeModifier)).toArray(TagEnchantment[]::new);
        }
    };

    public EnchantmentWheelType(DataItem dataItem) {
        super(dataItem, SUPPORTED, ENCHANTING_TABLE, STOREABLE, TREASURE, CURSE, DISCOVERABLE, MINECRAFT);
    }

    @Override
    public TagModifier<TagEnchantment> getTagModifier() {
        return new EnchantmentTagModifier();
    }

    @Override
    public TagEnchantment[] newArray(int size) {
        return new TagEnchantment[size];
    }

    @Override
    public TagEnchantment clone(TagEnchantment tag) {
        return new TagEnchantment(tag.getTag());
    }

    @Override
    public TagEnchantment[] getAll() {
        return GameRegistry.findRegistry(Enchantment.class).getEntries().stream().map(TagEnchantment::new).toArray(this::newArray);
    }

    @Override
    public TextComponent displayTag(TagEnchantment tag) {
        return (TextComponent) tag.getPrettyDisplay("", 0);
    }

    @Override
    public MutableComponent[] tooltip(TagEnchantment tag) {
        return EnchantmentUtils.getTooltip(tag);
    }

    @Override
    public int compare(TagEnchantment o1, TagEnchantment o2) {
        int curse = Boolean.compare(o1.getEnchantment().isCurse(), o2.getEnchantment().isCurse());
        if(curse != 0){
            return curse;
        }
        return super.compare(o1, o2);
    }
}
