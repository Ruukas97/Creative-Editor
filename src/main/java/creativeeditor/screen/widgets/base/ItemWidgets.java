package creativeeditor.screen.widgets.base;

import creativeeditor.data.DataItem;
import creativeeditor.data.base.DataColor;
import creativeeditor.data.tag.TagAttributeModifier;
import creativeeditor.screen.*;
import creativeeditor.screen.blockentity.GenericBlockScreen;
import creativeeditor.screen.models.AttributeTagModifier;
import creativeeditor.screen.widgets.ClassSpecificWidget;
import creativeeditor.screen.widgets.StyledTextButton;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.*;
import net.minecraft.tileentity.LockableLootTileEntity;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.Spliterator;
import java.util.function.Consumer;

public class ItemWidgets extends WidgetIteratorBase {

    public ItemWidgets() {
        add(new ClassSpecificWidget(I18n.get("gui.attributemodifiers"), (item, info) ->
                new StyledTextButton(info.withTrigger(button -> mc.setScreen(new WheelScreen<TagAttributeModifier>(info.getParent(), new TranslationTextComponent("gui.attributemodifiers"), new AttributeTagModifier(item), item))))
        ));

        add(new ClassSpecificWidget(I18n.get("gui.enchanting"), (item, info) ->
                new StyledTextButton(info.withTrigger(button -> mc.setScreen(new EnchantmentScreen(info.getParent(), item.getTag().getEnchantments()))))
        ));

        add(new ClassSpecificWidget(I18n.get("gui.armorstandeditor"), dItem -> dItem.getItem().getItem() instanceof ArmorStandItem, (item, info) ->
                new StyledTextButton(info.withTrigger(button -> mc.setScreen(new ArmorstandScreen(info.getParent(), item))))
        ));

        add(new ClassSpecificWidget(I18n.get("gui.canplaceon"), dItem -> dItem.getItem().getItem() instanceof BlockItem, (item, info) ->
                new StyledTextButton(info.withTrigger(button -> mc.setScreen(new PlaceDestroyScreen(info.getParent(), item, "canplaceon", item.getTag().getCanPlaceOn()))))
        ));
        add(new ClassSpecificWidget(I18n.get("gui.candestroy"), dItem -> !dItem.getItem().getItem().getToolTypes(dItem.getItemStack()).isEmpty(), (item, info) ->
                new StyledTextButton(info.withTrigger(button -> mc.setScreen(new PlaceDestroyScreen(info.getParent(), item, "candestroy", item.getTag().getCanDestroy()))))
        ));

        add(new ClassSpecificWidget(I18n.get("gui.genericblock"), dItem -> isLockableItem(dItem.getItem().getItem()), (item, info) ->
                new StyledTextButton(info.withTrigger(button -> mc.setScreen(new GenericBlockScreen(info.getParent(), item))))
        ));

        add(new ClassSpecificWidget(I18n.get("gui.color"), this::isColorable, (item, info) -> new StyledTextButton(info.withTrigger(button -> {
            if (item.getItem().getItem() instanceof ArmorItem && ((ArmorItem) item.getItem().getItem()).getMaterial() == ArmorMaterial.LEATHER) {
                mc.setScreen(new ColorScreen(info.getParent(), item, getColorTag(item), 0x00A06540, false));
            } else {
                mc.setScreen(new ColorScreen(info.getParent(), item, getColorTag(item), false));
            }
        }))));
    }

    /**
     * @see net.minecraft.client.renderer.color.ItemColors
     */
    private boolean isColorable(DataItem dataItem){
        Item item = dataItem.getItem().getItem();
        return item instanceof IDyeableArmorItem || isPotion(dataItem) || item == Items.TIPPED_ARROW || item == Items.FILLED_MAP;
    }

    private boolean isPotion(DataItem dataItem){
        return dataItem.getItem().getItem() instanceof PotionItem;
    }

    private DataColor getColorTag(DataItem dataItem) {
        Item item = dataItem.getItem().getItem();
        if (isPotion(dataItem)) {
            return dataItem.getTag().getPotionColor();
        }
        else if (item instanceof FilledMapItem) {
            return dataItem.getTag().getDisplay().getMapColor();
        }
        return dataItem.getTag().getDisplay().getColor();
    }

    private boolean isLockableItem(Item item) {
        if (item instanceof BlockItem) {
            BlockItem b = (BlockItem) item;
            return b.getBlock().createTileEntity(b.getBlock().defaultBlockState(), null) instanceof LockableLootTileEntity;
        }
        return false;
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
