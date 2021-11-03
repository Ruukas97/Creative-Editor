package creativeeditor.screen.models;

import com.google.common.collect.Lists;
import creativeeditor.data.DataItem;
import creativeeditor.data.NumberRangeInt;
import creativeeditor.data.base.DataDouble;
import creativeeditor.data.base.DataEnum;
import creativeeditor.data.tag.TagAttributeModifier;
import creativeeditor.screen.widgets.NumberField;
import creativeeditor.screen.widgets.StyledEnumSwitcher;
import creativeeditor.screen.widgets.WidgetInfo;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;

import java.util.List;
import java.util.function.BiFunction;

public class AttributeTagModifier implements TagModifier<TagAttributeModifier>{
    private DataEnum<EquipmentSlotType> slot;
    private DataDouble amount;
    private DataEnum<Operation> operation;

    public AttributeTagModifier(DataItem dataItem){
        Item item = dataItem.getItem().getItem();
        if(item instanceof ArmorItem){
            ArmorItem armor = (ArmorItem) item;
            slot = new DataEnum<>(armor.getSlot());
        }
        else if(item instanceof ShieldItem || item instanceof ArrowItem || item == Items.TOTEM_OF_UNDYING){
            slot = new DataEnum<>(EquipmentSlotType.OFFHAND);
        }
        else{
            slot = new DataEnum<>(EquipmentSlotType.MAINHAND);
        }
        amount = new DataDouble(1);
        operation = new DataEnum<>(Operation.ADDITION);
    }

    @Override
    public void modify(TagAttributeModifier tag) {
        //tag.getLevel().set(level.get());
    }

    @Override
    public List<BiFunction<DataItem, WidgetInfo, Widget>> widgets() {
        return Lists.newArrayList(
                (item, info) -> new StyledEnumSwitcher(info, slot),
                (item, info) -> new StyledEnumSwitcher(info, operation)
        );
    }
}
