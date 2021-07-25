package creativeeditor.screen.widgets.base;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

import creativeeditor.data.DataItem;
import creativeeditor.screen.ArmorstandScreen;
import creativeeditor.screen.ColorScreen;
import creativeeditor.screen.EnchantmentScreen;
import creativeeditor.screen.EntitySpecificScreen;
import creativeeditor.screen.widgets.ClassSpecificWidget;
import creativeeditor.screen.widgets.StyledTextButton;
import creativeeditor.screen.widgets.WidgetInfo;
import creativeeditor.screen.widgets.WidgetInfoSupport;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button.IPressable;
import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.item.*;

public class ItemWidgets extends WidgetIteratorBase {

    public ItemWidgets() {
        add(new ClassSpecificWidget(I18n.get("gui.enchanting"), dItem -> EnchantmentType.BREAKABLE.canEnchant(dItem.getItem().getItem()), (item, info) ->
                new StyledTextButton(info.withTrigger(button -> mc.setScreen(new EnchantmentScreen(info.getParent(), item.getTag().getEnchantments()))))
        ));

        add(new ClassSpecificWidget(I18n.get("gui.armorstandeditor"), dItem -> dItem.getItem().getItem() instanceof ArmorStandItem, (item, info) ->
                new StyledTextButton(info.withTrigger(button -> mc.setScreen(new ArmorstandScreen(info.getParent(), item))))
        ));
        add(new ClassSpecificWidget(I18n.get("gui.entityspecific"), dItem -> isEntityItem(dItem.getItem().getItem()), (item, info) ->
                new StyledTextButton(info.withTrigger(button -> mc.setScreen(new EntitySpecificScreen(info.getParent(), item))))
        ));

        add(new ClassSpecificWidget(I18n.get("gui.color"), dItem -> dItem.getItem().getItem() instanceof IDyeableArmorItem, (item, info) ->
                new StyledTextButton(info.withTrigger(button -> mc.setScreen(new ColorScreen(info.getParent(), item, item.getTag().getDisplay().getColor(), 10511680, false))))
        ));
    }

    private boolean isEntityItem(Item item) {
        return item instanceof SpawnEggItem || item instanceof ItemFrameItem || item instanceof ArmorStandItem || item instanceof MinecartItem;
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
