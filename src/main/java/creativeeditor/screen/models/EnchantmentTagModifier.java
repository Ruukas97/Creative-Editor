package creativeeditor.screen.models;

import creativeeditor.data.NumberRangeInt;
import creativeeditor.data.base.DataBoolean;
import creativeeditor.data.tag.TagEnchantment;
import creativeeditor.screen.widgets.NumberField;
import creativeeditor.screen.widgets.StyledToggle;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.resources.I18n;

public class EnchantmentTagModifier implements TagModifier<TagEnchantment> {
    private final DataBoolean vanillaMax;
    private StyledToggle vanillaMaxToggle;

    private final NumberRangeInt level;
    private NumberField levelField;

    public EnchantmentTagModifier() {
        vanillaMax = new DataBoolean();
        level = new NumberRangeInt(1, 1, 256);
    }

    private void initWidgets(FontRenderer font, int width, int height) {
        vanillaMaxToggle = new StyledToggle(10, height - 55, 80, 20, I18n.get("gui.enchantmentwheel.vanillamax"), vanillaMax);
        levelField = new NumberField(font, 10, height - 30, 20, level);
    }

    @Override
    public void modify(TagEnchantment tag) {
        tag.getLevel().set(level.get());
    }

    @Override
    public Widget[] widgets(FontRenderer font, int width, int height) {
        initWidgets(font, width, height);
        return new Widget[]{vanillaMaxToggle, levelField};
    }
}
