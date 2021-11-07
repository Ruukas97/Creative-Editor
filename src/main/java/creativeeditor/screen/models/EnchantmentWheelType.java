package creativeeditor.screen.models;

import creativeeditor.data.DataItem;
import creativeeditor.data.tag.TagAttributeModifier;
import creativeeditor.data.tag.TagEnchantment;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.Arrays;

public class EnchantmentWheelType extends WheelType<TagEnchantment>{
    private static final TagFilter<TagEnchantment> MINECRAFT = new TagFilter<TagEnchantment>() {
        @Override
        public ITextComponent getName() {
            return new StringTextComponent("minecraft");
        }

        @Override
        public boolean shouldShow(TagEnchantment tag) {
            return tag.getEnchantment().getRegistryName().getNamespace().equals("minecraft");
        }

        @Override
        public TagEnchantment[] filter(TagEnchantment[] tags) {
            return Arrays.stream(tags).filter(this::shouldShow).toArray(TagEnchantment[]::new);
        }
    };

    public EnchantmentWheelType(DataItem dataItem) {
        super(dataItem, MINECRAFT);
    }


    @Override
    public TagModifier<TagEnchantment> getTagModifier() {
        return null;
    }

    @Override
    public TagEnchantment[] newArray(int size) {
        return new TagEnchantment[size];
    }

    @Override
    public TagEnchantment[] getAll() {
        return GameRegistry.findRegistry(Enchantment.class).getEntries().stream().map(TagEnchantment::new).toArray(this::newArray);
    }

    @Override
    public TextComponent displayTag(TagEnchantment tag) {
        return null;
    }
}
