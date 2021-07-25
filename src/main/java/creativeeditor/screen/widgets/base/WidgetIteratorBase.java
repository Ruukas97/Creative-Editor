package creativeeditor.screen.widgets.base;

import creativeeditor.data.DataItem;
import creativeeditor.screen.widgets.ClassSpecificWidget;
import creativeeditor.screen.widgets.WidgetInfo;
import creativeeditor.screen.widgets.WidgetInfoSupport;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.item.Item;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.BiFunction;
import java.util.function.Function;

public class WidgetIteratorBase implements Iterable<ClassSpecificWidget> {

    public ArrayList<ClassSpecificWidget> list;
    protected Minecraft mc;

    public WidgetIteratorBase() {
        list = new ArrayList<>();
        mc = Minecraft.getInstance();
    }

    @Override
    public Iterator<ClassSpecificWidget> iterator() {
        return list.iterator();
    }

    protected void add(ClassSpecificWidget widget) {
        list.add(widget);
    }


    protected void add(String text, Function<DataItem, Boolean> requirement, BiFunction<DataItem, WidgetInfo, Widget> widgetCreator) {
        add(new ClassSpecificWidget(text, requirement, widgetCreator));
    }


    protected void add(String text, Class<? extends Item> itemClass, BiFunction<DataItem, WidgetInfo, Widget> widgetCreator) {
        add(new ClassSpecificWidget(text, itemClass::isInstance, widgetCreator));
    }

    public WidgetInfoSupport<?> modifiedTriggerSupport(WidgetInfoSupport<?> sup, Button.IPressable trigger) {
        return inf -> sup.fromWidgetInfo(inf.withTrigger(trigger));
    }

}
