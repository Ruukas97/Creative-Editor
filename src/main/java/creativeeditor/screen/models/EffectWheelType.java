package creativeeditor.screen.models;

import com.google.common.collect.Lists;
import creativeeditor.data.DataItem;
import creativeeditor.data.tag.TagEffect;
import creativeeditor.util.EffectUtils;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.ArrayList;
import java.util.Arrays;

public class EffectWheelType extends WheelType<TagEffect> {
    private static final TagFilter<TagEffect> BENEFICIAL = new TagFilter<TagEffect>() {
        @Override
        public TextComponent getName() {
            return new TranslationTextComponent("filter.effect.beneficial");
        }

        @Override
        public boolean shouldShow(DataItem item, TagEffect tag) {
            return tag.getEffectId().getEffect().isBeneficial();
        }

        @Override
        public TagEffect[] filter(DataItem item, TagEffect[] tags) {
            return Arrays.stream(tags).filter(tag -> shouldShow(item, tag)).toArray(TagEffect[]::new);
        }
    };

    private static final TagFilter<TagEffect> NEUTRAL = new TagFilter<TagEffect>() {
        @Override
        public TextComponent getName() {
            return new TranslationTextComponent("filter.effect.neutral");
        }

        @Override
        public boolean shouldShow(DataItem item, TagEffect tag) {
            return tag.getEffectId().getEffect().getCategory() == EffectType.NEUTRAL;
        }

        @Override
        public TagEffect[] filter(DataItem item, TagEffect[] tags) {
            return Arrays.stream(tags).filter(tag -> shouldShow(item, tag)).toArray(TagEffect[]::new);
        }
    };

    private static final TagFilter<TagEffect> HARMFUL = new TagFilter<TagEffect>() {
        @Override
        public TextComponent getName() {
            return new TranslationTextComponent("filter.effect.harmful");
        }

        @Override
        public boolean shouldShow(DataItem item, TagEffect tag) {
            return tag.getEffectId().getEffect().getCategory() == EffectType.HARMFUL;
        }

        @Override
        public TagEffect[] filter(DataItem item, TagEffect[] tags) {
            return Arrays.stream(tags).filter(tag -> shouldShow(item, tag)).toArray(TagEffect[]::new);
        }
    };

    private static final TagFilter<TagEffect> VANILLA = new TagFilter<TagEffect>() {
        @Override
        public TextComponent getName() {
            return new StringTextComponent("minecraft");
        }

        @Override
        public boolean shouldShow(DataItem item, TagEffect tag) {
            return EffectUtils.belongsToNameSpace(tag.getEffectId().getEffect(), "minecraft");
        }

        @Override
        public TagEffect[] filter(DataItem item, TagEffect[] tags) {
            return Arrays.stream(tags).filter(tag -> shouldShow(item, tag)).toArray(TagEffect[]::new);
        }
    };


    public EffectWheelType(DataItem dataItem) {
        super(dataItem, BENEFICIAL, NEUTRAL, HARMFUL, VANILLA);
    }

    @Override
    public TagModifier<TagEffect> getTagModifier() {
        return new EffectModifier();
    }

    @Override
    public TagEffect[] newArray(int size) {
        return new TagEffect[size];
    }

    @Override
    public TagEffect clone(TagEffect tag) {
        return new TagEffect(tag.getNBT());
    }

    @Override
    public TagEffect[] getAll() {
        return GameRegistry.findRegistry(Effect.class).getEntries().stream().map(TagEffect::new).toArray(this::newArray);
    }

    @Override
    public TextComponent displayTag(TagEffect tag) {
        return (TextComponent) tag.getPrettyDisplay("", 0);
    }

    @Override
    public ITextComponent[] tooltip(TagEffect tag) {
        ArrayList<ITextComponent> list = Lists.newArrayList();
        PotionUtils.addPotionTooltip(dataItem.getItemStack(), list, 1f);
        ITextComponent[] array = new ITextComponent[list.size()];
        for (int i = 0; i < list.size(); i++) {
            array[i] = list.get(i);
        }
        return array;
    }
}
