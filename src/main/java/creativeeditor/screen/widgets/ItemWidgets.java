package creativeeditor.screen.widgets;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.BiFunction;
import java.util.function.Function;

import creativeeditor.data.DataItem;
import creativeeditor.screen.ArmorstandScreen;
import creativeeditor.screen.ColorScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button.IPressable;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ArmorStandItem;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.Item;

public class ItemWidgets implements Iterable<ClassSpecificWidget> {
    private static ItemWidgets instance;
    private ArrayList<ClassSpecificWidget> list;


    private ItemWidgets() {
        list = new ArrayList<>();
        // WidgetInfoSupport<StyledTextButton> winfo =
        // StyledTextButton.getWidgetSupport();
        Minecraft mc = Minecraft.getInstance();
        add( new ClassSpecificWidget( I18n.format( "gui.armorstandeditor" ), instanceofFunction( ArmorStandItem.class ), ( item, info ) -> {
            return new StyledTextButton( info.withTrigger( button -> mc.displayGuiScreen( new ArmorstandScreen( info.getParent(), item ) ) ) );
        } ) );

        add( new ClassSpecificWidget( I18n.format( "gui.color" ), dItem -> {
            return dItem.getItem().getItem() instanceof IDyeableArmorItem;
        }, ( item, info ) -> {
            return new StyledTextButton( info.withTrigger( button -> mc.displayGuiScreen( new ColorScreen( info.getParent(), item, item.getTag().getDisplay().getColor(), 10511680, false) ) ) );
        } ) );
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
        add( new ClassSpecificWidget( text, instanceofFunction( itemClass ), widgetCreator ) );
    }


    public static Function<DataItem, Boolean> instanceofFunction( Class<? extends Item> itemClass ) {
        return i -> itemClass.isInstance( i );
    }


    public WidgetInfoSupport<?> modifiedTriggerSupport( WidgetInfoSupport<?> sup, IPressable trigger ) {
        return inf -> sup.fromWidgetInfo( inf.withTrigger( trigger ) );
    }
}
