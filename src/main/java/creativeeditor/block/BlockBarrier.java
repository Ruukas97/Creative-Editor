package creativeeditor.block;

import creativeeditor.CreativeEditor;
import net.minecraft.block.BarrierBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;

public class BlockBarrier extends BarrierBlock {

    public BlockBarrier() {
        super( Block.Properties.create( Material.BARRIER ).hardnessAndResistance( -1.0F, 3600000.8F ).noDrops() );
        setRegistryName( new ResourceLocation( "minecraft", "barrier" ) );
    }


    @SuppressWarnings( "deprecation" )
    @Override
    public BlockRenderType getRenderType( BlockState state ) {
        return CreativeEditor.BARRIER_VISIBLE ? BlockRenderType.MODEL : BlockRenderType.INVISIBLE;
    }
}
