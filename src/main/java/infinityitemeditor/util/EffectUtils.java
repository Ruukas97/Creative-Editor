package infinityitemeditor.util;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import infinityitemeditor.data.tag.TagEffect;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.potion.Effect;
import net.minecraft.util.StringUtils;
import net.minecraft.util.math.Mth;
import net.minecraft.util.text.*;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EffectUtils {
    private static final IFormattableTextComponent NO_EFFECT = (new TranslatableComponent("effect.none")).withStyle(ChatFormatting.GRAY);


    public static TextComponent getText(TagEffect tag) {
        Effect effect = tag.getEffectId().getEffect();
        TranslatableComponent component = new TranslatableComponent(effect.getDescriptionId());

        if (tag.getAmplifier().get() > 0) {
            component = new TranslatableComponent("potion.withAmplifier", component, I18n.exists("potion.potency." + tag.getAmplifier().get()) ? new TranslatableComponent("potion.potency." + tag.getAmplifier().get()) : new TextComponent(String.valueOf(tag.getAmplifier().get() + 1)));
        }

        if (tag.getDuration().get() > 20) {
            component = new TranslatableComponent("potion.withDuration", component, formatDuration(tag));
        }

        return (TextComponent) component.withStyle(effect.getCategory().getTooltipFormatting());
    }

    public static String formatDuration(TagEffect tag) {
        if (tag.getAmbient().get()) {
            return "**:**";
        } else {
            int i = Mth.floor((float) tag.getDuration().get());
            return StringUtils.formatTickDuration(i);
        }
    }

    public static boolean belongsToNameSpace(Effect effect, String namespace) {
        return effect.getRegistryName().getNamespace().equals(namespace);
    }

    public static MutableComponent[] getTooltip(TagEffect tag) {
        List<Pair<Attribute, AttributeModifier>> list1 = Lists.newArrayList();
        if (tag.getEffectId().isDefault()) {
            return new MutableComponent[]{NO_EFFECT};
        }

        Effect effect = tag.getEffectId().getEffect();
        IFormattableTextComponent component = getText(tag);
        Map<Attribute, AttributeModifier> modifierMap = effect.getAttributeModifiers();

        if (!modifierMap.isEmpty()) {
            ArrayList<MutableComponent> list = new ArrayList<>();
            list.add(component);
            list.add(TextComponent.EMPTY);
            list.add((new TranslatableComponent("potion.whenDrank")).withStyle(ChatFormatting.DARK_PURPLE));

            for (Map.Entry<Attribute, AttributeModifier> pair : modifierMap.entrySet()) {
                AttributeModifier attributemodifier2 = pair.getValue();
                double d0 = attributemodifier2.getAmount();
                double d1;
                if (attributemodifier2.getOperation() != AttributeModifier.Operation.MULTIPLY_BASE && attributemodifier2.getOperation() != AttributeModifier.Operation.MULTIPLY_TOTAL) {
                    d1 = attributemodifier2.getAmount();
                } else {
                    d1 = attributemodifier2.getAmount() * 100.0D;
                }

                if (d0 >= 0.0D) {
                    list.add((new TranslatableComponent("attribute.modifier.plus." + attributemodifier2.getOperation().toValue(), ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(d1), new TranslatableComponent(pair.getKey().getDescriptionId()))).withStyle(ChatFormatting.BLUE));
                } else if (d0 < 0.0D) {
                    d1 = d1 * -1.0D;
                    list.add((new TranslatableComponent("attribute.modifier.take." + attributemodifier2.getOperation().toValue(), ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(d1), new TranslatableComponent(pair.getKey().getDescriptionId()))).withStyle(ChatFormatting.RED));
                }
            }
            return list.toArray(new MutableComponent[0]);
        }
        return new MutableComponent[]{component};
    }
}
