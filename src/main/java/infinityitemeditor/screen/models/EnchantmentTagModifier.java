package infinityitemeditor.screen.models;

import infinityitemeditor.data.NumberRangeInt;
import infinityitemeditor.data.base.DataBoolean;
import infinityitemeditor.data.tag.TagEnchantment;
import infinityitemeditor.screen.widgets.NumberField;
import infinityitemeditor.screen.widgets.StyledToggle;
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
        level = new NumberRangeInt(1, 256);
    }

    private void initWidgets(FontRenderer font, int width, int height) {
        vanillaMaxToggle = new StyledToggle(10, height - 55, 100, 20, I18n.get("gui.enchantmentwheel.vanillamax"), vanillaMax);
        levelField = new NumberField(font, 10, height - 30, 20, level);
    }

    @Override
    public void modify(TagEnchantment tag) {
        if(vanillaMax.get()){
            tag.getLevel().set(tag.getEnchantment().getMaxLevel());
        }
        else{
            tag.getLevel().set(level.get());
        }
    }

    @Override
    public Widget[] widgets(FontRenderer font, int width, int height) {
        initWidgets(font, width, height);
        return new Widget[]{vanillaMaxToggle, levelField};
    }
}
