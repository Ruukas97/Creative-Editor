package creativeeditor.screen.models;

import creativeeditor.data.DataItem;
import creativeeditor.data.tag.TagAttributeModifier;
import creativeeditor.util.AttributeUtils;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.util.text.*;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.Arrays;

public class AttributeWheelType extends WheelType<TagAttributeModifier> {
    private static final TagFilter<TagAttributeModifier> VANILLA = new TagFilter<TagAttributeModifier>() {
        @Override
        public ITextComponent getName() {
            return new StringTextComponent("minecraft");
        }

        @Override
        public boolean shouldShow(TagAttributeModifier tag) {
            return AttributeUtils.belongToNameSpace(tag.getAttribute(), "minecraft");
        }

        @Override
        public TagAttributeModifier[] filter(TagAttributeModifier[] tags) {
            return Arrays.stream(tags).filter(this::shouldShow).toArray(TagAttributeModifier[]::new);
        }
    };

    private static final TagFilter<TagAttributeModifier> FORGE = new TagFilter<TagAttributeModifier>() {
        @Override
        public ITextComponent getName() {
            return new StringTextComponent("forge");
        }

        @Override
        public boolean shouldShow(TagAttributeModifier tag) {
            return AttributeUtils.belongToNameSpace(tag.getAttribute(), "forge");
        }

        @Override
        public TagAttributeModifier[] filter(TagAttributeModifier[] tags) {
            return Arrays.stream(tags).filter(this::shouldShow).toArray(TagAttributeModifier[]::new);
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
    public TagAttributeModifier[] getAll() {
        return GameRegistry.findRegistry(Attribute.class).getEntries().stream().map(TagAttributeModifier::new).toArray(this::newArray);
    }

    @Override
    public TextComponent displayTag(TagAttributeModifier tag) {
        return AttributeUtils.getText(tag.getAttribute(), tag.createAttributeModifier());
    }
}
