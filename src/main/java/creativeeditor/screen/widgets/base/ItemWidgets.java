package creativeeditor.screen.widgets.base;

import creativeeditor.screen.*;
import creativeeditor.screen.blockentity.GenericBlockScreen;
import creativeeditor.screen.models.AttributeWheelType;
import creativeeditor.screen.models.EffectWheelType;
import creativeeditor.screen.models.EnchantmentWheelType;
import creativeeditor.screen.widgets.ClassSpecificWidget;
import creativeeditor.screen.widgets.StyledTextButton;
import creativeeditor.util.ItemUtils;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ArmorStandItem;

import java.util.Spliterator;
import java.util.function.Consumer;

public class ItemWidgets extends WidgetIteratorBase {

    public ItemWidgets() {
        add(new ClassSpecificWidget(I18n.get("gui.attributewheel"), (item, info) ->
                new StyledTextButton(info.withTrigger(button -> mc.setScreen(new WheelScreen<>(info.getParent(), "attributewheel", new AttributeWheelType(item), item, item.getTag().getAttributes()))))
        ));

        add(new ClassSpecificWidget(I18n.get("gui.enchanting"), (item, info) ->
                new StyledTextButton(info.withTrigger(button -> mc.setScreen(new EnchantmentScreen(info.getParent(), item.getTag().getEnchantments()))))
        ));

        add(new ClassSpecificWidget(I18n.get("gui.effectwheel"), (item, info) ->
                new StyledTextButton(info.withTrigger(button -> mc.setScreen(new WheelScreen<>(info.getParent(), "effectwheel", new EffectWheelType(item), item, item.getTag().getEffects()))))
        ));

        add(new ClassSpecificWidget(I18n.get("gui.enchantmentwheel"), (item, info) ->
                new StyledTextButton(info.withTrigger(button -> mc.setScreen(new WheelScreen<>(info.getParent(), "enchantmentwheel", new EnchantmentWheelType(item), item, item.getTag().getEnchantments()))))
        ));

        add(new ClassSpecificWidget(I18n.get("gui.bookenchantmentwheel"), ItemUtils::isEnchantmentStorage, (item, info) ->
                new StyledTextButton(info.withTrigger(button -> mc.setScreen(new WheelScreen<>(info.getParent(), "bookenchantmentwheel", new EnchantmentWheelType(item), item, item.getTag().getEnchantments()))))
        ));

        add(new ClassSpecificWidget(I18n.get("gui.bookenchanting"), ItemUtils::isEnchantmentStorage, (item, info) ->
                new StyledTextButton(info.withTrigger(button -> mc.setScreen(new EnchantmentScreen(info.getParent(), item.getTag().getStoredEnchantments()))))
        ));

        add(new ClassSpecificWidget(I18n.get("gui.armorstandeditor"), dItem -> dItem.getItem().getItem() instanceof ArmorStandItem, (item, info) ->
                new StyledTextButton(info.withTrigger(button -> mc.setScreen(new ArmorstandScreen(info.getParent(), item))))
        ));

        add(new ClassSpecificWidget(I18n.get("gui.canplaceon"), ItemUtils::isTool, (item, info) ->
                new StyledTextButton(info.withTrigger(button -> mc.setScreen(new PlaceDestroyScreen(info.getParent(), item, "canplaceon", item.getTag().getCanPlaceOn()))))
        ));
        add(new ClassSpecificWidget(I18n.get("gui.candestroy"), dItem -> !dItem.getItem().getItem().getToolTypes(dItem.getItemStack()).isEmpty(), (item, info) ->
                new StyledTextButton(info.withTrigger(button -> mc.setScreen(new PlaceDestroyScreen(info.getParent(), item, "candestroy", item.getTag().getCanDestroy()))))
        ));

        add(new ClassSpecificWidget(I18n.get("gui.genericblock"), ItemUtils::isLockableItem, (item, info) ->
                new StyledTextButton(info.withTrigger(button -> mc.setScreen(new GenericBlockScreen(info.getParent(), item))))
        ));

        add(new ClassSpecificWidget(I18n.get("gui.color"), ItemUtils::isColorable, (item, info) -> new StyledTextButton(info.withTrigger(button -> {
            if (item.getItem().getItem() instanceof ArmorItem && ((ArmorItem) item.getItem().getItem()).getMaterial() == ArmorMaterial.LEATHER) {
                mc.setScreen(new ColorScreen(info.getParent(), item, ItemUtils.getColorTag(item), 0x00A06540, false));
            } else {
                mc.setScreen(new ColorScreen(info.getParent(), item, ItemUtils.getColorTag(item), false));
            }
        }))));

        sort();
    }

    @Override
    public void forEach(Consumer<? super ClassSpecificWidget> action) {
        super.forEach(action);
    }

    @Override
    public Spliterator<ClassSpecificWidget> spliterator() {
        return super.spliterator();
    }

}
