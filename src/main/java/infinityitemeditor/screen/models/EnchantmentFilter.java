package infinityitemeditor.screen.models;

import com.mojang.datafixers.util.Pair;
import infinityitemeditor.data.DataItem;
import infinityitemeditor.data.tag.TagEnchantment;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.text.TextComponent;

import java.util.Arrays;
import java.util.function.Function;

public class EnchantmentFilter implements TagFilter<TagEnchantment> {
    private final TextComponent name;
    private final Function<Pair<DataItem, TagEnchantment>, Boolean> shouldShow;


    public EnchantmentFilter(String name, Function<Pair<DataItem, TagEnchantment>, Boolean> shouldShow) {
        this.name = new TranslatableComponent("filter._", new TranslatableComponent("filter.enchantment." + name));
        this.shouldShow = shouldShow;
    }


    @Override
    public TextComponent getName() {
        return name;
    }

    @Override
    public boolean shouldShow(DataItem item, TagEnchantment tag) {
        return shouldShow.apply(new Pair<>(item, tag));
    }

    @Override
    public TagEnchantment[] filter(DataItem item, TagEnchantment[] tags) {
        return Arrays.stream(tags).filter(tagAttributeModifier -> shouldShow(item, tagAttributeModifier)).toArray(TagEnchantment[]::new);
    }
}
