package infinityitemeditor.block;

import infinityitemeditor.InfinityItemEditor;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BarrierBlock;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.util.ResourceLocation;

public class BlockBarrier extends BarrierBlock {

    public BlockBarrier() {
        super(AbstractBlock.Properties.of(Material.BARRIER).strength(-1.0F, 3600000.8F).noDrops().noOcclusion());
        setRegistryName(new ResourceLocation("minecraft", "barrier"));
    }


    @SuppressWarnings("deprecation")
    @Override
    public BlockRenderType getRenderShape(BlockState state) {
        return InfinityItemEditor.BARRIER_VISIBLE ? BlockRenderType.MODEL : BlockRenderType.INVISIBLE;
    }
}
