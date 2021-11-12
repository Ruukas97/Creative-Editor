package infinityitemeditor.util;

import infinityitemeditor.data.tag.TagEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class EnchantmentUtils {
    public static ITextComponent[] getTooltip(TagEnchantment tag) {
        Enchantment ench = tag.getEnchantment();
        return new TextComponent[]{
                new TranslationTextComponent(ench.getDescriptionId()),
                new TranslationTextComponent("gui.enchantment.rarity." + ench.getRarity().toString().toLowerCase()),
                new TranslationTextComponent("gui.enchantment.tooltip.rarity", new TranslationTextComponent("gui.enchantment.rarity." + ench.getRarity().toString().toLowerCase())),
                new TranslationTextComponent("gui.enchantment.tooltip.minlevel", ench.getMinLevel()),
                new TranslationTextComponent("gui.enchantment.tooltip.maxlevel", ench.getMaxLevel()),
                new TranslationTextComponent("gui.enchantment.tooltip.type", ench.category != null ? ench.category.toString().toLowerCase() : "N/A"),
                new TranslationTextComponent(ench.getDescriptionId() + ".desc"),
        };
    }
}
