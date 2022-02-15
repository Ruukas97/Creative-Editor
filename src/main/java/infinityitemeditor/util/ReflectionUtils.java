package infinityitemeditor.util;

import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.world.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.util.concurrent.Callable;
import java.util.function.Supplier;

public class ReflectionUtils {
    public static void setTeisr(Item item, Supplier<Callable<ItemStackTileEntityRenderer>> teisr) {
        if (teisr == null)
            return;
        @SuppressWarnings("deprecation")
        ItemStackTileEntityRenderer tmp = DistExecutor.callWhenOn(Dist.CLIENT, teisr);
        if (tmp == null)
            return;
        Supplier<ItemStackTileEntityRenderer> ister = () -> tmp;
        if (ister != null)
            ObfuscationReflectionHelper.setPrivateValue(Item.class, item, ister, "ister");
    }
}
