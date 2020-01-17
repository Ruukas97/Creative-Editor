package creativeeditor.util;

import java.util.concurrent.Callable;
import java.util.function.Supplier;

import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

public class ReflectionUtils {
	public static void setTeisr(Item item, Supplier<Callable<ItemStackTileEntityRenderer>> teisr) {
		Object tmp = teisr == null ? null : net.minecraftforge.fml.DistExecutor.callWhenOn(Dist.CLIENT, teisr);
		Supplier<ItemStackTileEntityRenderer> teis = tmp == null ? null
				: () -> (net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer) tmp;
		if (teis != null)
			ObfuscationReflectionHelper.setPrivateValue(Item.class, item, teis, "teisr");
	}
}
