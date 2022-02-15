package infinityitemeditor.block;

import infinityitemeditor.InfinityItemEditor;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.BarrierBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;

public class BlockBarrier extends BarrierBlock {

    public BlockBarrier() {
        super(BlockBehaviour.Properties.of(Material.BARRIER).strength(-1.0F, 3600000.8F).noDrops().noOcclusion());
        setRegistryName(new ResourceLocation("minecraft", "barrier"));
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return InfinityItemEditor.BARRIER_VISIBLE ? RenderShape.MODEL : RenderShape.INVISIBLE;
    }
}
