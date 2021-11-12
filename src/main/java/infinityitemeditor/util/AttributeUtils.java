package infinityitemeditor.util;

import infinityitemeditor.data.tag.TagAttributeModifier;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.*;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

public class AttributeUtils {
    public static IForgeRegistry<Attribute> getAttributeRegistry() {
        return GameRegistry.findRegistry(Attribute.class);
    }

    public static ResourceLocation getResourceLocation(Attribute attribute) {
        return attribute.getRegistryName();
    }

    public static String getName(Attribute attribute) {
        return ResourceLocationUtils.getShortString(getResourceLocation(attribute));
    }

    public static Attribute getAttribute(ResourceLocation resourceLocation) {
        return getAttributeRegistry().getValue(resourceLocation);
    }

    public static Attribute getAttribute(String resourceLocation) {
        return getAttribute(new ResourceLocation(resourceLocation));
    }

    public static String getNameSpace(Attribute attribute) {
        return getResourceLocation(attribute).getNamespace();
    }

    public static boolean belongsToNameSpace(Attribute attribute, String namespace) {
        return getNameSpace(attribute).equals(namespace);
    }

    public static TextComponent getText(Attribute attribute, AttributeModifier modifier) {
        double d0 = modifier.getAmount();
        //boolean flag = false;
        /*if (p_82840_1_ != null) {
            if (modifier.getId() == Item.BASE_ATTACK_DAMAGE_UUID) {
                d0 = d0 + p_82840_1_.getAttributeBaseValue(Attributes.ATTACK_DAMAGE);
                d0 = d0 + (double) EnchantmentHelper.getDamageBonus(this, CreatureAttribute.UNDEFINED);
                flag = true;
            } else if (modifier.getId() == Item.BASE_ATTACK_SPEED_UUID) {
                d0 += p_82840_1_.getAttributeBaseValue(Attributes.ATTACK_SPEED);
                flag = true;
            }
        }*/

        double d1;
        if (modifier.getOperation() != AttributeModifier.Operation.MULTIPLY_BASE && modifier.getOperation() != AttributeModifier.Operation.MULTIPLY_TOTAL) {
            if (attribute.equals(Attributes.KNOCKBACK_RESISTANCE)) {
                d1 = d0 * 10.0D;
            } else {
                d1 = d0;
            }
        } else {
            d1 = d0 * 100.0D;
        }



        /*if (flag) {
            return new StringTextComponent(" ").append(new TranslationTextComponent("attribute.modifier.equals." + modifier.getOperation().toValue(), ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(d1), new TranslationTextComponent(attribute.getDescriptionId()))).withStyle(TextFormatting.DARK_GREEN);
        } else */
        if (d0 < 0.0D) {
            d1 = d1 * -1.0D;
            return (TextComponent) new TranslationTextComponent("attribute.modifier.take." + modifier.getOperation().toValue(), ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(d1), new TranslationTextComponent(attribute.getDescriptionId())).withStyle(TextFormatting.RED);
        } else {
            return (TextComponent) (new TranslationTextComponent("attribute.modifier.plus." + modifier.getOperation().toValue(), ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(d1), new TranslationTextComponent(attribute.getDescriptionId()))).withStyle(TextFormatting.BLUE);
        }
    }

    public static ITextComponent[] getTooltip(TagAttributeModifier tag) {
        return new ITextComponent[]{
                getText(tag.getAttribute(), tag.createAttributeModifier()),
                tag.getSlot().getKeyValue(),
                tag.getOperation().getKeyValue(),
                new TranslationTextComponent("attribute.amount._", tag.getAmount().getPrettyDisplay("", 0)).withStyle(TextFormatting.AQUA),
                new StringTextComponent(getNameSpace(tag.getAttribute())).withStyle(TextFormatting.BLUE),
        };
    }
}
