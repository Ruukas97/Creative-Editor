package creativeeditor.block;

import net.minecraft.block.BarrierBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;

public class BlockBarrier extends BarrierBlock {

    public BlockBarrier() {
        super( Block.Properties.create( Material.BARRIER ).hardnessAndResistance( -1.0F, 3600000.8F ).noDrops() );
        setRegistryName( new ResourceLocation( "minecraft", "barrier" ) );
    }


    @Override
    public BlockRenderType getRenderType( BlockState state ) {
        return BlockRenderType.MODEL;
    }
}
