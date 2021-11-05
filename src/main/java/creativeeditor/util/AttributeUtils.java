package creativeeditor.util;

import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

public class AttributeUtils {
    public static IForgeRegistry<Attribute> getAttributeRegistry(){
        return GameRegistry.findRegistry(Attribute.class);
    }

    public static ResourceLocation getResourceLocation(Attribute attribute){
        return attribute.getRegistryName();
    }

    public static String getName(Attribute attribute){
        return ResourceLocationUtils.getShortString(getResourceLocation(attribute));
    }

    public static Attribute getAttribute(ResourceLocation resourceLocation){
        return getAttributeRegistry().getValue(resourceLocation);
    }

    public static Attribute getAttribute(String resourceLocation){
        return getAttribute(new ResourceLocation(resourceLocation));
    }

    public static String getNameSpace(Attribute attribute){
        return getResourceLocation(attribute).getNamespace();
    }

    public static boolean belongToNameSpace(Attribute attribute, String namespace){
        return getNameSpace(attribute).equals(namespace);
    }
}
