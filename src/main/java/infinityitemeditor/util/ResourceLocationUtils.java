package infinityitemeditor.util;

import net.minecraft.util.ResourceLocation;

public class ResourceLocationUtils {
    public static String getShortString(ResourceLocation location){
        return location.getNamespace().equals("minecraft") ? location.getPath() : location.toString();
    }
}
