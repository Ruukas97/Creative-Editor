package infinityitemeditor.screen.models;

import infinityitemeditor.data.DataItem;
import infinityitemeditor.data.tag.TagAttributeModifier;
import infinityitemeditor.util.AttributeUtils;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.util.text.*;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.Arrays;

public class AttributeWheelType extends WheelType<TagAttributeModifier> {
    private static final TagFilter<TagAttributeModifier> VANILLA = new TagFilter<TagAttributeModifier>() {
        @Override
        public TextComponent getName() {
            return new StringTextComponent("minecraft");
        }

        @Override
        public boolean shouldShow(DataItem item, TagAttributeModifier tag) {
            return AttributeUtils.belongsToNameSpace(tag.getAttribute(), "minecraft");
        }

        @Override
        public TagAttributeModifier[] filter(DataItem item, TagAttributeModifier[] tags) {
            return Arrays.stream(tags).filter(tagAttributeModifier -> shouldShow(item, tagAttributeModifier)).toArray(TagAttributeModifier[]::new);
        }
    };

    private static final TagFilter<TagAttributeModifier> FORGE = new TagFilter<TagAttributeModifier>() {
        @Override
        public TextComponent getName() {
            return new StringTextComponent("forge");
        }

        @Override
        public boolean shouldShow(DataItem item, TagAttributeModifier tag) {
            return AttributeUtils.belongsToNameSpace(tag.getAttribute(), "forge");
        }

        @Override
        public TagAttributeModifier[] filter(DataItem item, TagAttributeModifier[] tags) {
            return Arrays.stream(tags).filter(tagAttributeModifier -> shouldShow(item, tagAttributeModifier)).toArray(TagAttributeModifier[]::new);
        }
    };

    public AttributeWheelType(DataItem dataItem) {
        super(dataItem, VANILLA, FORGE);
    }

    @Override
    public TagModifier<TagAttributeModifier> getTagModifier() {
        return new AttributeTagModifier(dataItem);
    }

    @Override
    public TagAttributeModifier[] newArray(int size) {
        return new TagAttributeModifier[size];
    }

    @Override
    public TagAttributeModifier clone(TagAttributeModifier tag) {
        return new TagAttributeModifier(tag.getNBT());
    }

    @Override
    public TagAttributeModifier[] getAll() {
        return GameRegistry.findRegistry(Attribute.class).getEntries().stream().map(TagAttributeModifier::new).toArray(this::newArray);
    }

    @Override
    public TextComponent displayTag(TagAttributeModifier tag) {
        return AttributeUtils.getText(tag.getAttribute(), tag.createAttributeModifier());
    }

    @Override
    public ITextComponent[] tooltip(TagAttributeModifier tag) {
        return AttributeUtils.getTooltip(tag);
    }
}
