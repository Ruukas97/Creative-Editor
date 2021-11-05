package creativeeditor.screen.models;

import creativeeditor.data.DataItem;
import creativeeditor.data.tag.TagAttributeModifier;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class AttributeWheelType extends WheelType<TagAttributeModifier> {
    private static final TagFilter<TagAttributeModifier> VANILLA = new TagFilter<TagAttributeModifier>() {
        @Override
        public ITextComponent getName() {
            return null;
        }

        @Override
        public boolean shouldShow(TagAttributeModifier tag) {
            return GameRegistry.findRegistry(Attribute.class).get;
        }

        @Override
        public TagAttributeModifier[] filter(TagAttributeModifier[] tags) {
            return new TagAttributeModifier[0];
        }
    };

    public AttributeWheelType(DataItem dataItem) {
        super(dataItem, VANILLA);
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
}
