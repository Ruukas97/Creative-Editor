package creativeeditor.screen.models;

import creativeeditor.data.DataItem;
import creativeeditor.data.NumberRangeInt;
import creativeeditor.data.base.DataBoolean;
import creativeeditor.data.base.DataEnum;
import creativeeditor.data.tag.TagAttributeModifier;
import creativeeditor.screen.widgets.NumberField;
import creativeeditor.screen.widgets.StyledEnumSwitcher;
import creativeeditor.screen.widgets.StyledToggle;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;

public class AttributeTagModifier implements TagModifier<TagAttributeModifier> {
    private final DataBoolean infinity;
    private StyledToggle infinityToggle;

    private final DataEnum<Operation> operation;
    private StyledEnumSwitcher operationSwitcher;

    private final DataEnum<EquipmentSlotType> slot;
    private StyledEnumSwitcher slotSwitcher;

    private final NumberRangeInt level;
    private NumberField levelField;
    private final NumberRangeInt levelFractional;
    private NumberField fractionalField;

    public AttributeTagModifier(DataItem dataItem) {
        Item item = dataItem.getItem().getItem();
        if (item instanceof ArmorItem) {
            ArmorItem armor = (ArmorItem) item;
            slot = new DataEnum<>(armor.getSlot());
        } else if (item instanceof ShieldItem || item instanceof ArrowItem || item == Items.TOTEM_OF_UNDYING) {
            slot = new DataEnum<>(EquipmentSlotType.OFFHAND);
        } else {
            slot = new DataEnum<>(EquipmentSlotType.MAINHAND);
        }
        infinity = new DataBoolean();
        operation = new DataEnum<>(Operation.ADDITION);
        level = new NumberRangeInt(1, 0, 99999999);
        levelFractional = new NumberRangeInt(0, 999);
    }

    private void initWidgets(FontRenderer font, int width, int height) {
        infinityToggle = new StyledToggle(15, height - 123, 80, 20, I18n.get("gui.attributemodifiers.infinity"), infinity);
        operationSwitcher = new StyledEnumSwitcher(15, height - 93, 80, 20, operation);
        slotSwitcher = new StyledEnumSwitcher(15, height - 63, 80, 20, slot);
        levelField = new NumberField(font, 15, height - 32, 20, level);
        fractionalField = new NumberField(font, 100, height - 32, 20, levelFractional);
    }

    @Override
    public void modify(TagAttributeModifier tag) {
        if (infinity.get()) {
            tag.getAmount().set(Double.POSITIVE_INFINITY);
        } else {
            tag.getAmount().set(level.get() + (double) levelFractional.get() / 1000);
        }
        tag.setOperation(operation.get());
        tag.setSlot(slot.get());
    }

    @Override
    public Widget[] widgets(FontRenderer font, int width, int height) {
        initWidgets(font, width, height);
        return new Widget[]{infinityToggle, operationSwitcher, slotSwitcher, levelField, fractionalField};
    }
}
