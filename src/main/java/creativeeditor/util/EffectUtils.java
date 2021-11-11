package creativeeditor.util;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import creativeeditor.data.tag.TagEffect;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Effect;
import net.minecraft.util.StringUtils;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EffectUtils {
    private static final IFormattableTextComponent NO_EFFECT = (new TranslationTextComponent("effect.none")).withStyle(TextFormatting.GRAY);


    public static TextComponent getText(TagEffect tag) {
        Effect effect = tag.getEffectId().getEffect();
        TranslationTextComponent component = new TranslationTextComponent(effect.getDescriptionId());

        if (tag.getAmplifier().get() > 0) {
            component = new TranslationTextComponent("potion.withAmplifier", component, new TranslationTextComponent("potion.potency." + tag.getAmplifier().get()));
        }

        if (tag.getDuration().get() > 20) {
            component = new TranslationTextComponent("potion.withDuration", component, formatDuration(tag));
        }

        return (TextComponent) component.withStyle(effect.getCategory().getTooltipFormatting());
    }

    public static String formatDuration(TagEffect tag) {
        if (tag.getAmbient().get()) {
            return "**:**";
        } else {
            int i = MathHelper.floor((float) tag.getDuration().get());
            return StringUtils.formatTickDuration(i);
        }
    }

    public static boolean belongsToNameSpace(Effect effect, String namespace) {
        return effect.getRegistryName().getNamespace().equals(namespace);
    }

    public static ITextComponent[] getTooltip(TagEffect tag) {
        List<Pair<Attribute, AttributeModifier>> list1 = Lists.newArrayList();
        if (tag.getEffectId().isDefault()) {
            return new ITextComponent[]{NO_EFFECT};
        }

        Effect effect = tag.getEffectId().getEffect();
        IFormattableTextComponent component = getText(tag);
        Map<Attribute, AttributeModifier> modifierMap = effect.getAttributeModifiers();

        if (!modifierMap.isEmpty()) {
            ArrayList<ITextComponent> list = new ArrayList<>();
            list.add(component);
            list.add(StringTextComponent.EMPTY);
            list.add((new TranslationTextComponent("potion.whenDrank")).withStyle(TextFormatting.DARK_PURPLE));

            for (Map.Entry<Attribute, AttributeModifier> pair : modifierMap.entrySet()) {
                AttributeModifier attributemodifier2 = pair.getValue();
                double d0 = attributemodifier2.getAmount();
                double d1;
                if (attributemodifier2.getOperation() != AttributeModifier.Operation.MULTIPLY_BASE && attributemodifier2.getOperation() != AttributeModifier.Operation.MULTIPLY_TOTAL) {
                    d1 = attributemodifier2.getAmount();
                } else {
                    d1 = attributemodifier2.getAmount() * 100.0D;
                }

                if (d0 > 0.0D) {
                    list.add((new TranslationTextComponent("attribute.modifier.plus." + attributemodifier2.getOperation().toValue(), ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(d1), new TranslationTextComponent(pair.getKey().getDescriptionId()))).withStyle(TextFormatting.BLUE));
                } else if (d0 < 0.0D) {
                    d1 = d1 * -1.0D;
                    list.add((new TranslationTextComponent("attribute.modifier.take." + attributemodifier2.getOperation().toValue(), ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(d1), new TranslationTextComponent(pair.getKey().getDescriptionId()))).withStyle(TextFormatting.RED));
                }
            }
            return list.toArray(new ITextComponent[0]);
        }
        return new ITextComponent[]{component};
    }
}
