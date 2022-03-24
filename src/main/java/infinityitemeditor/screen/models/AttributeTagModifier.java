package infinityitemeditor.screen.models;

import infinityitemeditor.data.DataItem;
import infinityitemeditor.data.NumberRangeInt;
import infinityitemeditor.data.base.DataBoolean;
import infinityitemeditor.data.tag.TagEnum;
import infinityitemeditor.data.tag.TagAttributeModifier;
import infinityitemeditor.screen.widgets.NumberField;
import infinityitemeditor.screen.widgets.StyledEnumSwitcher;
import infinityitemeditor.screen.widgets.StyledToggle;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;

public class AttributeTagModifier implements TagModifier<TagAttributeModifier> {
    private final DataBoolean infinity;
    private StyledToggle infinityToggle;

    private final TagEnum<Operation> operation;
    private StyledEnumSwitcher operationSwitcher;

    private final TagEnum<EquipmentSlotType> slot;
    private StyledEnumSwitcher slotSwitcher;

    private final NumberRangeInt level;
    private NumberField levelField;
    private final NumberRangeInt levelFractional;
    private NumberField fractionalField;

    public AttributeTagModifier(DataItem dataItem) {
        slot = new TagEnum<>(EquipmentSlotType.class, dataItem.getAppropriateEquipmentSlot());
        infinity = new DataBoolean();
        operation = new TagEnum<>(Operation.class, Operation.ADDITION);
        level = new NumberRangeInt(1, 0, 99999999);
        levelFractional = new NumberRangeInt(0, 999);
    }

    private void initWidgets(FontRenderer font, int width, int height) {
        infinityToggle = new StyledToggle(10, height - 105, 80, 20, I18n.get("gui.attributewheel.infinity"), infinity);
        operationSwitcher = new StyledEnumSwitcher(10, height - 80, 80, 20, operation);
        slotSwitcher = new StyledEnumSwitcher(10, height - 55, 80, 20, slot);
        levelField = new NumberField(font, 10, height - 30, 20, level);
        fractionalField = new NumberField(font, 15 + levelField.getWidth(), height - 30, 20, levelFractional);
    }

    @Override
    public void modify(TagAttributeModifier tag) {
        if (infinity.get()) {
            tag.getAmount().set(Double.POSITIVE_INFINITY);
        } else {
            tag.getAmount().set(level.get() + (double) levelFractional.get() / 1000);
        }
        tag.getOperation().set(operation.get());
        tag.getSlot().set(slot.get());
    }

    @Override
    public Widget[] widgets(FontRenderer font, int width, int height) {
        initWidgets(font, width, height);
        return new Widget[]{infinityToggle, operationSwitcher, slotSwitcher, levelField, fractionalField};
    }
}
