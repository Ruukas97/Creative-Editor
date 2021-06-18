package creativeeditor.screen.widgets;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.BiFunction;
import java.util.function.Function;

import creativeeditor.data.DataItem;
import creativeeditor.screen.ArmorstandScreen;
import creativeeditor.screen.ColorScreen;
import creativeeditor.screen.EnchantmentScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button.IPressable;
import net.minecraft.client.resources.I18n;
import net.minecraft.enchantment.EnchantmentType;
import net.minecraft.item.ArmorStandItem;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.Item;

public class ItemWidgets implements Iterable<ClassSpecificWidget> {
    private static ItemWidgets instance;
    private final ArrayList<ClassSpecificWidget> list;

    private ItemWidgets() {
        list = new ArrayList<>();
        Minecraft mc = Minecraft.getInstance();

        add( new ClassSpecificWidget( I18n.format( "gui.enchanting" ), dItem -> EnchantmentType.ALL.canEnchantItem(dItem.getItem().getItem()), (item, info ) ->
                new StyledTextButton( info.withTrigger( button -> mc.displayGuiScreen( new EnchantmentScreen( info.getParent(), item.getTag().getEnchantments() )) ) )
        ));

        add( new ClassSpecificWidget( I18n.format( "gui.armorstandeditor" ), dItem -> dItem.getItem().getItem() instanceof ArmorStandItem, (item, info ) ->
                new StyledTextButton( info.withTrigger( button -> mc.displayGuiScreen( new ArmorstandScreen( info.getParent(), item ) ) ) )
        ));

        add( new ClassSpecificWidget( I18n.format( "gui.color" ), dItem -> dItem.getItem().getItem() instanceof IDyeableArmorItem, (item, info ) ->
            new StyledTextButton( info.withTrigger( button -> mc.displayGuiScreen( new ColorScreen( info.getParent(), item, item.getTag().getDisplay().getColor(), 10511680, false) ) ) )
        ));
    }


    public static ItemWidgets getInstance() {
        if (instance == null)
            instance = new ItemWidgets();
        return instance;
    }


    @Override
    public Iterator<ClassSpecificWidget> iterator() {
        return list.iterator();
    }


    public void add( ClassSpecificWidget widget ) {
        list.add( widget );
    }


    public void add( String text, Function<DataItem, Boolean> requirement, BiFunction<DataItem, WidgetInfo, Widget> widgetCreator ) {
        add( new ClassSpecificWidget( text, requirement, widgetCreator ) );
    }


    public void add( String text, Class<? extends Item> itemClass, BiFunction<DataItem, WidgetInfo, Widget> widgetCreator ) {
        add( new ClassSpecificWidget( text, itemClass::isInstance, widgetCreator ) );
    }

    public WidgetInfoSupport<?> modifiedTriggerSupport( WidgetInfoSupport<?> sup, IPressable trigger ) {
        return inf -> sup.fromWidgetInfo( inf.withTrigger( trigger ) );
    }
}
