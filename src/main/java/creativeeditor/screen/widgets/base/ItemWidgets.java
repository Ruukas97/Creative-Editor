package creativeeditor.screen.widgets.base;

import creativeeditor.data.DataItem;
import creativeeditor.data.base.DataColor;
import creativeeditor.screen.*;
import creativeeditor.screen.blockentity.GenericBlockScreen;
import creativeeditor.screen.widgets.ClassSpecificWidget;
import creativeeditor.screen.widgets.StyledTextButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.item.*;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.tileentity.LockableTileEntity;

import java.util.Spliterator;
import java.util.function.Consumer;

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
        add(new ClassSpecificWidget(I18n.get("gui.potion"), dItem -> dItem.getItem().getItem() instanceof PotionItem, (item, info) ->
                new StyledTextButton(info.withTrigger(button -> mc.setScreen(new PotionEditorScreen(info.getParent(), item))))
        ));

        add(new ClassSpecificWidget(I18n.get("gui.canplaceon"), dItem -> dItem.getItem().getItem() instanceof BlockItem, (item, info) ->
                new StyledTextButton(info.withTrigger(button -> mc.setScreen(new PlaceDestroyScreen(info.getParent(), item, "canplaceon", item.getTag().getCanPlaceOn()))))
        ));
        add(new ClassSpecificWidget(I18n.get("gui.candestroy"), dItem -> dItem.getItem().getItem() != Items.AIR, (item, info) ->
                new StyledTextButton(info.withTrigger(button -> mc.setScreen(new PlaceDestroyScreen(info.getParent(), item, "candestroy", item.getTag().getCanDestroy()))))
        ));

        add(new ClassSpecificWidget(I18n.get("gui.genericblock"), dItem -> isLockableItem(dItem.getItem().getItem()), (item, info) ->
                new StyledTextButton(info.withTrigger(button -> mc.setScreen(new GenericBlockScreen(info.getParent(), item))))
        ));


        add(new ClassSpecificWidget(I18n.get("gui.color"), dItem -> getColorableItem(dItem) != null, (item, info) -> new StyledTextButton(info.withTrigger(button -> {
            if (item.getItem().getItem() instanceof IDyeableArmorItem) {
                mc.setScreen(new ColorScreen(info.getParent(), item, getColorableItem(item), 10511680, false));
            } else {
                mc.setScreen(new ColorScreen(info.getParent(), item, getColorableItem(item), false));
            }
        }))));
    }

    private DataColor getColorableItem(DataItem item) {
        Item stack = item.getItem().getItem();
        if (stack instanceof PotionItem) {
            return item.getTag().getPotionColor();
        } else if (stack instanceof IDyeableArmorItem) {
            return item.getTag().getDisplay().getColor();
        } else if (stack instanceof FilledMapItem) {
            return item.getTag().getDisplay().getMapColor();
        }
        return null;
    }

    private boolean isLockableItem(Item item) {
        return item == Items.CHEST || item == Items.BEACON || item == Items.ENDER_CHEST ||
                item == Items.TRAPPED_CHEST || item == Items.HOPPER || item == Items.BREWING_STAND ||
                item == Items.FURNACE || item == Items.SHULKER_BOX || item == Items.BARREL ||
                item == Items.DISPENSER || item == Items.DROPPER;
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
