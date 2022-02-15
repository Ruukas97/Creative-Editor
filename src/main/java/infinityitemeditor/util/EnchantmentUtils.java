package infinityitemeditor.util;

import infinityitemeditor.data.tag.TagEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.text.MutableComponent;
import net.minecraft.util.text.TextComponent;

public class EnchantmentUtils {
    public static MutableComponent[] getTooltip(TagEnchantment tag) {
        Enchantment ench = tag.getEnchantment();
        return new TextComponent[]{
                new TranslatableComponent(ench.getDescriptionId()),
                new TranslatableComponent("gui.enchantment.rarity." + ench.getRarity().toString().toLowerCase()),
                new TranslatableComponent("gui.enchantment.tooltip.rarity", new TranslatableComponent("gui.enchantment.rarity." + ench.getRarity().toString().toLowerCase())),
                new TranslatableComponent("gui.enchantment.tooltip.minlevel", ench.getMinLevel()),
                new TranslatableComponent("gui.enchantment.tooltip.maxlevel", ench.getMaxLevel()),
                new TranslatableComponent("gui.enchantment.tooltip.type", ench.category != null ? ench.category.toString().toLowerCase() : "N/A"),
                new TranslatableComponent(ench.getDescriptionId() + ".desc"),
        };
    }
}
