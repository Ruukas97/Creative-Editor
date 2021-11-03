package creativeeditor.screen;

import creativeeditor.data.DataItem;
import creativeeditor.data.NumberRangeInt;
import creativeeditor.data.tag.TagEffect;
import creativeeditor.data.tag.TagList;
import creativeeditor.screen.widgets.NumberField;
import creativeeditor.screen.widgets.StyledButton;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.TranslationTextComponent;

public class PotionEffectsScreenOld extends ParentItemScreen {
    protected TagList<TagEffect> effectsTag;

    private NumberField level;
    private NumberField time;

    private StyledButton colorButton;
    private StyledButton particleButton;

    private int rotOff = 0;
    private int mouseDist = 0;

    public PotionEffectsScreenOld(Screen lastScreen, DataItem item, TagList<TagEffect> effectsTag) {
        super(new TranslationTextComponent("gui.potioneffects"), lastScreen, item);
        this.effectsTag = effectsTag;
    }

    @Override
    protected void init() {
        super.init();

        level = new NumberField(font, 15, height - 33, 40, 18, new NumberRangeInt(1, 127));
        time = new NumberField(font, 15, height - 60, 40, 18, new NumberRangeInt(1, 99999));

        colorButton = addButton(new StyledButton(15, height - 120, 80, 20, new TranslationTextComponent("gui.potioneffects.showparticles"), button -> {

        }));
        colorButton = addButton(new StyledButton(15, height - 90, 80, 20, new TranslationTextComponent("gui.color"), button -> minecraft.setScreen(new ColorScreen(this, item, item.getTag().getPotionColor(), false))));

    }
}
