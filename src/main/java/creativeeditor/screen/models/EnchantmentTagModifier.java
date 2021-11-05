package creativeeditor.screen.models;

import com.google.common.collect.Lists;
import creativeeditor.data.DataItem;
import creativeeditor.data.NumberRangeInt;
import creativeeditor.data.tag.TagEnchantment;
import creativeeditor.screen.widgets.NumberField;
import creativeeditor.screen.widgets.WidgetInfo;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.gui.widget.Widget;

import java.util.List;
import java.util.function.BiFunction;

public class EnchantmentTagModifier implements TagModifier<TagEnchantment> {
    @Getter
    @Setter
    private NumberRangeInt level;

    public EnchantmentTagModifier() {
        this.level = new NumberRangeInt(1, Integer.MAX_VALUE);
    }

    @Override
    public void modify(TagEnchantment tag) {
        tag.getLevel().set(level.get());
    }

    @Override
    public List<BiFunction<DataItem, WidgetInfo, Widget>> widgets() {



        return Lists.newArrayList(
                (item, info) -> new NumberField(info, level)
        );
    }
}
