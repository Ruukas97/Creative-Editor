package infinityitemeditor.registry;

import infinityitemeditor.InfinityItemEditor;
import infinityitemeditor.block.BlockBarrier;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = InfinityItemEditor.MODID, bus = Bus.MOD)
public class RegistryHandler {


    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> e) {
        System.out.println("Registering barrier");
        e.getRegistry().register(new BlockBarrier());
    }
}
