package creativeeditor.block;

import creativeeditor.CreativeEditor;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class BlockBarrier extends BarrierBlock {

    public BlockBarrier() {
        super( AbstractBlock.Properties.of(Material.BARRIER).strength(-1.0F, 3600000.8F).noDrops().noOcclusion());
        setRegistryName( new ResourceLocation( "minecraft", "barrier" ) );
    }


    @SuppressWarnings( "deprecation" )
    @Override
    public BlockRenderType getRenderShape( BlockState state ) {
        return CreativeEditor.BARRIER_VISIBLE ? BlockRenderType.MODEL : BlockRenderType.INVISIBLE;
    }
}
