package infinityitemeditor.screen.models;

import infinityitemeditor.data.DataItem;
import infinityitemeditor.data.NumberRangeInt;
import infinityitemeditor.data.base.DataBoolean;
import infinityitemeditor.data.tag.TagAttributeModifier;
import infinityitemeditor.data.tag.TagEnum;
import infinityitemeditor.screen.widgets.NumberField;
import infinityitemeditor.screen.widgets.StyledEnumSwitcher;
import infinityitemeditor.screen.widgets.StyledToggle;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.*;

public class AttributeTagModifier implements TagModifier<TagAttributeModifier> {
    private final DataBoolean infinity;
    private StyledToggle infinityToggle;

    private final TagEnum<AttributeModifier.Operation> operation;
    private StyledEnumSwitcher operationSwitcher;

    private final TagEnum<EquipmentSlot> slot;
    private StyledEnumSwitcher slotSwitcher;

    private final NumberRangeInt level;
    private NumberField levelField;
    private final NumberRangeInt levelFractional;
    private NumberField fractionalField;

    public AttributeTagModifier(DataItem dataItem) {
        Item item = dataItem.getItem().getItem();
        if (item instanceof ArmorItem) {
            ArmorItem armor = (ArmorItem) item;
            slot = new TagEnum<>(EquipmentSlot.class, armor.getSlot());
        } else if (item instanceof ShieldItem || item instanceof ArrowItem || item == Items.TOTEM_OF_UNDYING) {
            slot = new TagEnum<>(EquipmentSlot.class, EquipmentSlot.OFFHAND);
        } else {
            slot = new TagEnum<>(EquipmentSlot.class, EquipmentSlot.MAINHAND);
        }
        infinity = new DataBoolean();
        operation = new TagEnum<>(AttributeModifier.Operation.class, AttributeModifier.Operation.ADDITION);
        level = new NumberRangeInt(1, 0, 99999999);
        levelFractional = new NumberRangeInt(0, 999);
    }

    private void initWidgets(Font font, int width, int height) {
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
    public Widget[] widgets(Font font, int width, int height) {
        initWidgets(font, width, height);
        return new Widget[]{infinityToggle, operationSwitcher, slotSwitcher, levelField, fractionalField};
    }
}
