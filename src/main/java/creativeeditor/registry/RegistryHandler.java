package creativeeditor.registry;

import creativeeditor.CreativeEditor;
import creativeeditor.block.BlockBarrier;
import net.minecraft.block.Block;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@Mod.EventBusSubscriber(modid = CreativeEditor.MODID, bus = Bus.MOD)
public class RegistryHandler {

    @SubscribeEvent
    public static void registerParticles(ParticleFactoryRegisterEvent e) {
        //Minecraft mc = Minecraft.getInstance();
        //mc.particles.registerFactory( ParticleTypes.BARRIER, new ParticleBarrier.Factory() );
    }


    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> e) {
        System.out.println("Registering barrier");
        e.getRegistry().register(new BlockBarrier());
    }
}
