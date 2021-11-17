package infinityitemeditor.screen.models;

import infinityitemeditor.data.NumberRangeInt;
import infinityitemeditor.data.base.DataBoolean;
import infinityitemeditor.data.tag.TagEffect;
import infinityitemeditor.screen.widgets.NumberField;
import infinityitemeditor.screen.widgets.StyledToggle;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.Widget;
import net.minecraft.client.resources.I18n;

public class EffectModifier implements TagModifier<TagEffect> {
    private final NumberRangeInt amplifier;
    private NumberField amplifierField;

    private final NumberRangeInt duration;
    private NumberField durationField;

    private final DataBoolean ambient;
    private StyledToggle ambientToggle;

    private final DataBoolean showParticles;
    private StyledToggle showParticlesToggle;

    private final DataBoolean showIcon;
    private StyledToggle showIconToggle;


    public EffectModifier() {
        amplifier = new NumberRangeInt(1, 128);
        duration = new NumberRangeInt(600, 1, 9999999);
        ambient = new DataBoolean(false);
        showParticles = new DataBoolean(true);
        showIcon = new DataBoolean(true);
    }

    private void initWidgets(FontRenderer font, int width, int height) {
        amplifierField = new NumberField(font, 10, height - 130, 20, amplifier);
        durationField = new NumberField(font, 10, height - 105, 20, duration);
        ambientToggle = new StyledToggle(10, height - 80, 80, 20, I18n.get("gui.effectwheel.ambient"), ambient);
        showParticlesToggle = new StyledToggle(10, height - 55, 80, 20, I18n.get("gui.effectwheel.showparticles"), showParticles);
        showIconToggle = new StyledToggle(10, height - 30, 80, 20, I18n.get("gui.effectwheel.showicon"), showIcon);
    }

    @Override
    public void modify(TagEffect tag) {
        tag.getAmplifier().set((byte) (amplifier.get() - 1));
        tag.getDuration().set(duration.get());
        tag.getAmbient().set(ambient.get());
        tag.getShowParticles().set(showParticles.get());
        tag.getShowIcon().set(showIcon.get());
    }

    @Override
    public Widget[] widgets(FontRenderer font, int width, int height) {
        initWidgets(font, width, height);
        return new Widget[]{amplifierField, durationField, ambientToggle, showParticlesToggle, showIconToggle};
    }
}
